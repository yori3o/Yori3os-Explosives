"""Check the packaged mod matrix after `gradlew buildAll` (Python 3.11+)."""

from collections import Counter
import hashlib
import json
from pathlib import Path
import struct
import tomllib
import zipfile

ROOT = Path(__file__).resolve().parents[1]
PROPERTIES = tomllib.loads((ROOT / "stonecutter.properties.toml").read_text("utf-8"))
VERSION = PROPERTIES["mod"]["version"]
OUTPUT = ROOT / "build" / "libs" / VERSION
ITEMS = {"bomb", "grenade", "dynamite", "sticky_bomb", "sticky_grenade", "sticky_dynamite"}
VERSIONS = ("1.21.1", "26.1", "26.2", "26.3")


def require(condition, message):
    if not condition:
        raise RuntimeError(message)


def check_jar(path, minecraft, loader):
    legacy = minecraft == "1.21.1"
    with zipfile.ZipFile(path) as jar:
        names = set(jar.namelist())
        duplicates = [name for name, count in Counter(jar.namelist()).items() if count > 1]
        require(not duplicates, f"Duplicate entries: {duplicates}")
        require(not any("com/example/" in name or "assets/template/" in name for name in names), "Template leftovers")
        require("LICENSE-yo_bombs" in names, "Missing license")
        require("logo.png" in names, "Missing icon")
        metadata_name = "fabric.mod.json" if loader == "fabric" else "META-INF/neoforge.mods.toml"
        wrong_metadata = "META-INF/neoforge.mods.toml" if loader == "fabric" else "fabric.mod.json"
        require(wrong_metadata not in names, f"Wrong loader metadata: {wrong_metadata}")
        metadata_text = jar.read(metadata_name).decode("utf-8")
        require("${" not in metadata_text, "Unexpanded metadata")
        if loader == "fabric":
            metadata = json.loads(metadata_text)
            require(metadata["id"] == "yo_bombs" and metadata["version"] == VERSION, "Wrong Fabric identity")
            for entries in metadata["entrypoints"].values():
                for entry in entries:
                    require(entry.replace(".", "/") + ".class" in names, f"Missing entrypoint: {entry}")
            require(metadata["depends"]["java"] == (">=21" if legacy else ">=25"), "Wrong Java requirement")
        else:
            metadata = tomllib.loads(metadata_text)
            require(metadata["mods"][0]["modId"] == "yo_bombs", "Wrong NeoForge identity")
            dependencies = {dep["modId"] for dep in metadata["dependencies"]["yo_bombs"]}
            require({"minecraft", "neoforge", "yet_another_config_lib_v3"} <= dependencies, "Missing required dependencies")

        for name in names:
            if name.endswith((".json", ".toml")):
                data = jar.read(name).decode("utf-8")
                require("${" not in data, f"Unexpanded resource: {name}")
                if name.endswith(".json"):
                    json.loads(data)
            if name.endswith(".class"):
                require(not name.startswith("com/yori3o/yo_bombs/" + ("neoforge/" if loader == "fabric" else "fabric/")), f"Wrong loader class: {name}")
                major = struct.unpack(">H", jar.read(name)[6:8])[0]
                require(major == (65 if legacy else 69), f"Wrong bytecode level {major}: {name}")

        mixins = json.loads(jar.read("yo_bombs.mixins.json"))
        require(mixins["compatibilityLevel"] == ("JAVA_21" if legacy else "JAVA_25"), "Wrong mixin Java level")
        for mixin in mixins["mixins"]:
            require((mixins["package"] + "." + mixin).replace(".", "/") + ".class" in names, "Missing mixin class")
        if legacy and loader == "fabric":
            refmap = json.loads(jar.read(mixins["refmap"]))
            require(bool(refmap["mappings"]), "Missing Fabric 1.21.1 remapping")
            for mixin in mixins["mixins"]:
                name = (mixins["package"] + "." + mixin).replace(".", "/")
                require(bool(refmap["mappings"].get(name)), f"Missing Fabric mixin mapping: {mixin}")

        recipes = {Path(name).stem: json.loads(jar.read(name)) for name in names
                   if name.startswith("data/yo_bombs/recipe/") and name.endswith(".json")}
        require(set(recipes) == ITEMS, "Missing recipes")
        for item, recipe in recipes.items():
            require(recipe["result"]["id"] == "yo_bombs:" + item, f"Wrong recipe result: {item}")
            ingredients = recipe.get("key", {}).values()
            require(all(isinstance(value, dict if legacy else str) for value in ingredients), f"Wrong ingredient format: {item}")
        require("data/yo_bombs/tags/item/bomb_case_material.json" in names, "Missing material tag")
        for item in ITEMS:
            for kind, suffix in [("models/item", ".json"), ("textures/item", ".png")]:
                require(f"assets/yo_bombs/{kind}/{item}{suffix}" in names, f"Missing asset: {item}")
            require((f"assets/yo_bombs/items/{item}.json" in names) == (not legacy), "Wrong item model definition format")
        for language in ["en_us", "ru_ru"]:
            require(f"assets/yo_bombs/lang/{language}.json" in names, "Missing translations")
        for seconds in [3, 5, 7]:
            require(f"assets/yo_bombs/sounds/fuse_{seconds}_sec.ogg" in names, "Missing fuse sound")


def main():
    checksums = []
    for minecraft in VERSIONS:
        for loader in ("fabric", "neoforge"):
            filename = f"yo_bombs-{loader}-{VERSION}+{minecraft}.jar"
            path = OUTPUT / filename
            require(path.is_file(), f"Build first: missing {path}")
            check_jar(path, minecraft, loader)
            source = path.with_name(path.stem + "-sources.jar")
            require(source.is_file(), f"Missing sources: {source}")
            with zipfile.ZipFile(source) as jar:
                require("com/yori3o/yo_bombs/common/YoBombs.java" in jar.namelist(), "Missing common source")
            for artifact in (path, source):
                checksums.append(f"{hashlib.sha256(artifact.read_bytes()).hexdigest()}  {artifact.name}")
            print(f"OK {filename}")
    (OUTPUT / "SHA256SUMS.txt").write_text("\n".join(checksums) + "\n", "utf-8")
    print("Verified all 8 mod JARs and 8 source JARs.")


if __name__ == "__main__":
    main()
