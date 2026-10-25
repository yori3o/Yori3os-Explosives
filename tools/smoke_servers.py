"""Launch isolated development servers and exercise explosive entities over RCON.

NeoForge's development launcher starts a test world. Fabric is checked up to its
EULA gate unless the isolated test directory already has an accepted eula.txt.
This script never changes eula.txt. Python 3.11+ and JDK 25 are required.
"""

import argparse
import json
import os
from pathlib import Path
import re
import secrets
import shutil
import socket
import struct
import subprocess
import time

ROOT = Path(__file__).resolve().parents[1]
ITEMS = ("bomb", "grenade", "dynamite", "sticky_bomb", "sticky_grenade", "sticky_dynamite")
MATRIX = [f"{version}-{loader}" for version in ("1.21.1", "26.1", "26.2", "26.3")
          for loader in ("fabric", "neoforge")]


def free_port():
    with socket.socket() as connection:
        connection.bind(("127.0.0.1", 0))
        return connection.getsockname()[1]


class Rcon:
    def __init__(self, port, password):
        self.connection = socket.create_connection(("127.0.0.1", port), timeout=10)
        self.request = 0
        self.send(password, kind=3)

    def read_exact(self, size):
        result = b""
        while len(result) < size:
            chunk = self.connection.recv(size - len(result))
            if not chunk:
                raise RuntimeError("RCON connection closed")
            result += chunk
        return result

    def send(self, command, kind=2):
        self.request += 1
        payload = struct.pack("<ii", self.request, kind) + command.encode("utf-8") + b"\0\0"
        self.connection.sendall(struct.pack("<i", len(payload)) + payload)
        size = struct.unpack("<i", self.read_exact(4))[0]
        response = self.read_exact(size)
        response_id, _ = struct.unpack("<ii", response[:8])
        if response_id == -1:
            raise RuntimeError("RCON authentication failed")
        return response[8:-2].decode("utf-8", errors="replace")


def smoke(target, validation=None):
    directory = ROOT / "run" / "smoke" / target
    directory.mkdir(parents=True, exist_ok=True)
    server_port, rcon_port = free_port(), free_port()
    password = secrets.token_hex(24)
    # Only the isolated smoke directory is configured or modified.
    (directory / "server.properties").write_text("\n".join([
        "server-ip=127.0.0.1", f"server-port={server_port}", "online-mode=false",
        "enable-rcon=true", f"rcon.port={rcon_port}", f"rcon.password={password}",
        "view-distance=2", "simulation-distance=2", "generate-structures=false",
        "pause-when-empty-seconds=0", "max-tick-time=60000",
    ]) + "\n", "utf-8")
    log = ROOT / "build" / "smoke" / f"{target}.log"
    log.parent.mkdir(parents=True, exist_ok=True)
    java_home = os.environ.get("JAVA_HOME")
    java = str(Path(java_home) / "bin" / "java.exe") if java_home and os.name == "nt" else shutil.which("java")
    command = [java, "-jar", str(ROOT / "gradle/wrapper/gradle-wrapper.jar"),
               f":{target}:runServer", f"-Pmod.runDirectory={directory}",
               "--max-workers=1", "--console=plain"]
    rcon = None
    with log.open("wb") as output:
        process = subprocess.Popen(command, cwd=ROOT, stdout=output, stderr=subprocess.STDOUT,
                                   stdin=subprocess.DEVNULL)
        try:
            deadline = time.monotonic() + 240
            while time.monotonic() < deadline:
                text = log.read_text("utf-8", errors="replace")
                if "Done (" in text:
                    break
                if process.poll() is not None:
                    if "You need to agree to the EULA" in text and "Failed to start" not in text:
                        config = directory / "config/yo_bombs-server.json5"
                        if not config.exists():
                            raise RuntimeError(f"{target}: mod did not initialize before EULA gate")
                        return {"target": target, "result": "initialized_eula_gate", "log": str(log.relative_to(ROOT))}
                    raise RuntimeError(f"{target}: server exited before startup; inspect {log}")
                time.sleep(0.5)
            else:
                raise RuntimeError(f"{target}: startup timeout; inspect {log}")

            deadline = time.monotonic() + 20
            while time.monotonic() < deadline:
                try:
                    rcon = Rcon(rcon_port, password)
                    break
                except OSError:
                    time.sleep(0.5)
            if rcon is None:
                raise RuntimeError(f"{target}: RCON did not start")
            if validation is not None:
                validation(rcon)
            rcon.send("forceload add 0 0 128 0")
            for index, item in enumerate(ITEMS):
                response = rcon.send(f'summon yo_bombs:{item} {index * 24} 1000 0 {{Tags:["yo_bombs_smoke"]}}')
                if "Summoned" not in response:
                    raise RuntimeError(f"{target}: could not summon {item}: {response}")
            time.sleep(9)
            rcon.send("scoreboard objectives add yo_bombs_smoke dummy")
            rcon.send("execute store result score remaining yo_bombs_smoke run execute if entity @e[tag=yo_bombs_smoke]")
            response = rcon.send("scoreboard players get remaining yo_bombs_smoke")
            if not re.search(r"\bhas 0\b", response):
                raise RuntimeError(f"{target}: explosive fuse did not expire: {response}")
            for table in ("abandoned_mineshaft", "simple_dungeon", "stronghold_corridor"):
                response = rcon.send(f"loot spawn 0 1000 0 loot minecraft:chests/{table}")
                if "Unknown" in response or "Failed" in response:
                    raise RuntimeError(f"{target}: loot table failed: {response}")
            rcon.send("stop")
            process.wait(timeout=45)
            if process.returncode != 0:
                raise RuntimeError(f"{target}: server exited with {process.returncode}; inspect {log}")
            text = log.read_text("utf-8", errors="replace")
            if any(error in text for error in ("Failed to parse", "Couldn't parse", "Mixin apply failed", "Exception in server tick loop")):
                raise RuntimeError(f"{target}: runtime errors; inspect {log}")
            return {"target": target, "result": "world_entities_loot_passed", "log": str(log.relative_to(ROOT))}
        finally:
            if process.poll() is None:
                if rcon is not None:
                    try:
                        rcon.send("stop")
                        process.wait(timeout=30)
                    except (OSError, RuntimeError, subprocess.TimeoutExpired):
                        pass
                if process.poll() is None:
                    # Only terminate the process tree created by this test.
                    if os.name == "nt":
                        subprocess.run(["taskkill", "/PID", str(process.pid), "/T", "/F"], capture_output=True)
                    else:
                        process.terminate()
            if rcon is not None:
                rcon.connection.close()


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("targets", nargs="*", choices=MATRIX)
    args = parser.parse_args()
    report = ROOT / "build/smoke/report.json"
    previous = json.loads(report.read_text("utf-8")) if report.exists() else []
    results = {item["target"]: item for item in previous}
    for target in args.targets or MATRIX:
        print(f"Starting {target}", flush=True)
        result = smoke(target)
        results[target] = result
        print(f"OK {target}: {result['result']}", flush=True)
        report.write_text(json.dumps([results[key] for key in MATRIX if key in results], indent=2) + "\n", "utf-8")


if __name__ == "__main__":
    main()
