# Connect Six AI

语言：[English](README.md) | 中文

Connect Six AI 是一个 17 x 17 六子棋（Connect6）命令行落子程序。程序从标准输入读取当前棋盘和执棋方，并输出下一手落子坐标：开局时输出 1 个点，正常回合输出 2 个点。

## 技术实现

- 棋盘使用 `17 x 17` 整数矩阵表示：`0` 表示黑子，`1` 表示白子，`2` 表示空位。
- `ConnectSixAi` 从已有棋子周围 3 格内生成候选空点；空棋盘时直接选择中心点 `(9, 9)` 开局。
- `BoardEvaluator` 从横向、纵向、主对角线、副对角线 4 个方向扫描棋型，统计连续棋子数量和两端开放情况，再对六连、活五、眠五、活四等形势加权评分。
- 落子搜索采用两点启发式：先给第一落点排序，模拟高分第一点后重新计算第二落点，再对两点组合打总分。
- 评分同时考虑进攻和防守，并适当提高防守权重，使程序优先封堵对手的直接威胁。
- 程序会校验棋盘尺寸、执棋方、坐标范围和重复落子；如果附近没有候选点，会回退选择合法空点组合。

## 项目结构

```text
.
├── src/
│   ├── Main.java                  # 标准输入/输出入口
│   ├── Test.java                  # 轻量级自检
│   ├── evaluation/
│   │   ├── BoardEvaluator.java    # 棋型评分
│   │   └── ConnectSixAi.java      # 候选生成与两点组合搜索
│   └── variables/
│       ├── Point.java             # 坐标值对象
│       └── Step.java              # 一手两点落子
├── run.sh                         # macOS/Linux 运行脚本
├── run.ps1                        # Windows PowerShell 运行脚本
├── README.md
├── README.zh-CN.md
└── LICENSE
```

## 环境要求

- JDK 8 或更高版本
- 可用的 `javac` 和 `java` 命令

检查 Java 环境：

```bash
javac -version
java -version
```

## 输入格式

第一行包含 3 个整数：

```text
blackN whiteN side
```

- `blackN`：棋盘上已有黑子数量。
- `whiteN`：棋盘上已有白子数量。
- `side`：当前状态。
  - `0`：程序作为黑方先手开局，此时不需要继续输入棋盘点位。
  - `1`：轮到黑方落子。
  - `2`：轮到白方落子。

当 `side` 为 `1` 或 `2` 时，后续输入：

1. `blackN` 行黑子坐标。
2. `whiteN` 行白子坐标。

坐标均为 1 到 17 的行列坐标：

```text
row column
```

## 输出格式

开局时输出一个坐标：

```text
row column
```

正常回合输出两个坐标：

```text
row1 column1 row2 column2
```

## 运行方法

### Windows PowerShell

```powershell
.\run.ps1
```

也可以直接传入输入：

```powershell
@"
1 0 2
9 9
"@ | .\run.ps1
```

### macOS / Linux

```bash
chmod +x ./run.sh
./run.sh
```

也可以直接传入输入：

```bash
printf "1 0 2\n9 9\n" | ./run.sh
```

### 手动编译运行

```bash
mkdir -p build/classes
javac -encoding UTF-8 -d build/classes $(find src -name "*.java")
java -cp build/classes Main
```

## 示例

黑方先手开局：

```text
0 0 0
```

输出：

```text
9 9
```

棋盘已有一个黑子，轮到白方：

```text
1 0 2
9 9
```

可能输出：

```text
8 9 9 8
```

具体输出会由当前评分和候选排序决定，但保证坐标合法且两个点不重复。

## 自检

编译后可以运行自检类：

```bash
java -cp build/classes Test
```

自检覆盖：

- 开局点是否为中心。
- 常规回合是否输出两个合法空点。
- 对手形成活五时是否优先封堵。

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.