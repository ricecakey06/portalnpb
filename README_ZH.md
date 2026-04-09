# Portal NPB

[English README](README.md)

`Portal NPB` 是一个仅服务端安装的 Fabric 模组，用于统计由玩家触发的方块更新所导致的、同一游戏刻内地狱门方块自毁的次数。

注意：本模组由OpenAI的GPT5.4编写完成，没有可用性保障。

## 功能

- 跟踪由玩家放置方块和破坏方块引发的方块更新链。
- 统计门框失效后，在同一游戏刻内自毁的 `minecraft:nether_portal` 方块数量。
- 每个世界每 tick 刷新一次统计结果。
- 将累计值写入所有满足以下条件的计分板 objective：
  - 计分准则为 `dummy`
  - 名称后缀为 `.npb`
- 仅需安装在服务端，客户端无需安装。

## 版本支持

- Minecraft `1.21`
- Minecraft `1.21.10`
- Java `21`
- Fabric Loader `0.17.3+`

## 构建

```powershell
.\gradlew.bat clean build
```

构建产物：

- `versions/1.21/build/libs/portalnpb-1.21-<version>.jar`
- `versions/1.21.10/build/libs/portalnpb-1.21.10-<version>.jar`

## 项目结构

- `build.gradle`
  - preprocess 节点定义
- `common.gradle`
  - 各 Minecraft 版本共用的 Gradle 配置
- `settings.gradle`
  - 从 `settings.json` 加载子项目
- `versions/mainProject`
  - 指定 preprocess 的主源码版本
- `versions/1.21.10/src/main`
  - 规范源码目录
- `versions/1.21/build/preprocessed`
  - 为 `1.21` 自动生成的预处理源码目录

## 参考
- yh-bbl: https://gitee.com/harvey-husky/yh-bbl
- gugle-carpet-addition: https://github.com/Gu-ZT/gugle-carpet-addition

## 许可证

MIT，详见 [LICENSE](LICENSE)。
