"""Run block-collision regressions against an isolated development server."""

import argparse
import json
from pathlib import Path
import re
import time

from smoke_servers import ROOT, smoke


def position(rcon):
    response = rcon.send('data get entity @e[tag=yb_collision,limit=1] Pos')
    number = r'(-?\d+(?:\.\d+)?(?:[Ee][+-]?\d+)?)[dDfF]?'
    match = re.search(r'\[\s*' + number + r',\s*' + number + r',\s*' + number + r'\s*\]', response)
    if match is None:
        raise RuntimeError('Cannot read projectile position: ' + response)
    return tuple(float(value) for value in match.groups())


def run_cases(rcon):
    rcon.send('forceload add 240 240 288 288')
    rcon.send('tick unfreeze')
    time.sleep(2)
    rcon.send('tick freeze')
    results = []
    cases = [
        ('wall', (255, 250.2, 256.5), (2, 0, 0), 'stone', 255.825),
        ('grazing_edge', (255, 250.2, 255.9), (2, 0, 0), 'stone', 255.825),
        ('grazing_vertex', (255, 250.9, 255.9), (2, 0, 0), 'stone', 255.825),
        ('fast_wall', (254, 250.2, 256.5), (8, 0, 0), 'stone', 255.825),
        ('above_bottom_slab', (255, 250.6, 256.5), (2, 0, 0), 'oak_slab[type=bottom]', None),
        ('bottom_slab_edge', (255, 250.2, 255.9), (2, 0, 0), 'oak_slab[type=bottom]', 255.825),
        ('fence_edge', (255, 250.2, 256.3), (2, 0, 0), 'oak_fence', 256.2),
    ]
    for name, start, motion, block, stop_x in cases:
        rcon.send('kill @e[tag=yb_collision]')
        rcon.send('fill 252 248 252 276 255 260 air')
        rcon.send(f'setblock 256 250 256 {block}')
        x, y, z = start
        dx, dy, dz = motion
        response = rcon.send(f'summon yo_bombs:bomb {x:.6f} {y:.6f} {z:.6f} {{Tags:["yb_collision"],NoGravity:1b,Motion:[{dx}d,{dy}d,{dz}d]}}')
        if 'Summoned' not in response:
            raise RuntimeError(response)
        rcon.send('tick step 1')
        time.sleep(0.15)
        actual = position(rcon)
        passed = x + 1e-5 < actual[0] <= stop_x + 1e-5 if stop_x is not None else actual[0] >= x + dx * .99 - 1e-5
        results.append({'case': name, 'passed': passed, 'position': actual})
    rcon.send('kill @e[tag=yb_collision]')
    rcon.send('fill 252 248 252 276 255 260 air')
    rcon.send('setblock 256 250 256 stone')
    rcon.send('setblock 257 251 256 stone')
    rcon.send('summon yo_bombs:bomb 256.4 251.1 256.5 {Tags:["yb_collision"],NoGravity:1b,Motion:[1d,-0.5d,0d]}')
    rcon.send('tick step 1')
    time.sleep(0.15)
    actual = position(rcon)
    # A diagonal pair of blocks must not swallow the projectile after a floor bounce.
    overlap = actual[0] + 0.175 > 257 + 1e-5 and actual[0] - 0.175 < 258 - 1e-5 and actual[1] < 252 - 1e-5 and actual[1] + .35 > 251 + 1e-5
    results.append({'case': 'floor_bounce_into_adjacent_block', 'passed': not overlap, 'position': actual})
    rcon.send('kill @e[tag=yb_collision]')
    rcon.send('fill 252 248 252 276 255 260 air')
    rcon.send('setblock 256 250 256 stone')
    rcon.send('summon yo_bombs:sticky_bomb 255 250.2 256.5 {Tags:["yb_collision"],Motion:[2d,0d,0d]}')
    rcon.send('tick step 1')
    time.sleep(0.15)
    first = position(rcon)
    attached = '1b' in rcon.send('data get entity @e[tag=yb_collision,limit=1] NoGravity')
    rcon.send('tick step 2')
    time.sleep(0.2)
    stationary = position(rcon)
    results.append({'case': 'sticky_attachment', 'passed': attached and max(abs(a-b) for a,b in zip(first,stationary)) < 1e-6, 'position': stationary})
    rcon.send('setblock 256 250 256 water')
    rcon.send('tick step 1')
    time.sleep(0.15)
    # Entity NBT omits NoGravity when false on some Minecraft versions.
    released = '1b' not in rcon.send('data get entity @e[tag=yb_collision,limit=1] NoGravity')
    released = released and position(rcon)[1] < stationary[1] - 1e-5
    results.append({'case': 'sticky_support_becomes_non_solid', 'passed': released, 'position': position(rcon)})
    rcon.send('kill @e[tag=yb_collision]')
    rcon.send('setblock 268 250 256 dispenser[facing=east]')
    rcon.send('item replace block 268 250 256 container.0 with yo_bombs:bomb 1')
    rcon.send('setblock 268 249 256 redstone_block')
    rcon.send('tick step 6')
    time.sleep(0.4)
    response = rcon.send('execute positioned 269 250 256 if entity @e[type=yo_bombs:bomb,distance=..8]')
    results.append({'case': 'dispenser_spawns_projectile', 'passed': 'Test passed' in response, 'response': response})
    rcon.send('kill @e[type=yo_bombs:bomb]')
    rcon.send('summon yo_bombs:grenade 270.5 300.0 256.5 {Tags:["yb_fuse"],NoGravity:1b}')
    rcon.send('tick step 59')
    time.sleep(3.1)
    before = 'Test passed' in rcon.send('execute if entity @e[tag=yb_fuse]')
    rcon.send('tick step 1')
    time.sleep(0.15)
    after = 'Test passed' in rcon.send('execute if entity @e[tag=yb_fuse]')
    results.append({'case': 'fuse_expires_at_configured_tick', 'passed': before and not after})
    rcon.send('kill @e[tag=yb_fuse]')
    rcon.send('tick unfreeze')
    return results


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('target')
    parser.add_argument('--record-only', action='store_true')
    parser.add_argument('--output', default='build/review/collision-report.json')
    args = parser.parse_args()
    results = []
    smoke(args.target, validation=lambda rcon: results.extend(run_cases(rcon)))
    output = ROOT / args.output
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps({'target': args.target, 'cases': results}, indent=2) + '\n', 'utf-8')
    for result in results:
        print(('PASS' if result['passed'] else 'FAIL'), result['case'], result.get('position', result.get('response')))
    if not args.record_only and not all(result['passed'] for result in results):
        raise SystemExit(1)


if __name__ == '__main__':
    main()
