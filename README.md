# Portal NPB

[简体中文](README_ZH.md)

`Portal NPB` is a server-side Fabric mod for tracking same-tick nether portal block breaks caused by player-triggered block updates.

Notice: This mod is made by OpenAI GPT 5.4, and there is no guarantee for its availability.

## Features

- Tracks player-caused block update chains from both block placement and block breaking.
- Counts how many `minecraft:nether_portal` blocks self-break in the same tick after the frame becomes invalid.
- Flushes scores once per world tick.
- Writes the accumulated value to every scoreboard objective that matches:
  - criterion: `dummy`
  - name suffix: `.npb`
- Works as a server-side only mod. Clients do not need to install it.

## Version Matrix

- Minecraft `1.21`
- Minecraft `1.21.10`
- Java `21`
- Fabric Loader `0.17.3+`

## Build

```powershell
.\gradlew.bat clean build
```

Build outputs:

- `versions/1.21/build/libs/portalnpb-1.21-<version>.jar`
- `versions/1.21.10/build/libs/portalnpb-1.21.10-<version>.jar`

## Project Layout

- `build.gradle`
  - preprocess node definitions
- `common.gradle`
  - shared Gradle configuration for all Minecraft versions
- `settings.gradle`
  - loads subprojects from `settings.json`
- `versions/mainProject`
  - declares the main source version used by preprocess
- `versions/1.21.10/src/main`
  - canonical source tree
- `versions/1.21/build/preprocessed`
  - generated source tree for `1.21`

## Reference
- yh-bbl: https://gitee.com/harvey-husky/yh-bbl
- gugle-carpet-addition: https://github.com/Gu-ZT/gugle-carpet-addition

## License
MIT. See [LICENSE](LICENSE).
