# Connect Six AI

Connect Six AI 是一个 17 x 17 六子棋（Connect6）命令行落子程序。程序从标准输入读取当前棋盘和执棋方，并输出下一手落子坐标：开局时输出 1 个点，正常回合输出 2 个点。

## 版本选择与改进说明

仓库原来同时包含 `Connext_6_1st_version` 和 `Connext_6_2nd_version`。对比后，2nd 版更适合作为最终版基础：它把候选第一落点、第二落点和总分组织成 `Step`，再按分值排序，结构比 1st 版中并行维护两个点列表更清晰，也更方便继续扩展。

最终版在 2nd 版思路上做了这些整理：

- 合并为根目录下的一份项目，避免两个版本并存导致使用者不知道运行哪一份。
- 使用中心点 `(9, 9)` 作为先手开局点，比旧版硬编码的偏置开局更稳健。
- 修正旧版评分分析会在多次调用之间累加状态的问题，保证每个候选点独立评分。
- 修正候选为空时可能越界的问题，并增加兜底合法落子选择。
- 输出统一使用换行，便于命令行、评测器和脚本读取。
- 增加 Windows PowerShell 与 Bash 运行脚本，并加入一个轻量级自检类 `Test`。

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

## 算法概览

最终版使用启发式搜索：

1. 只在已有棋子周围 3 格范围内生成候选点，减少无效搜索。
2. 对每个候选点分别计算己方进攻价值和对手威胁价值。
3. 第一落点取排名靠前的候选点，模拟后重新计算第二落点。
4. 对两点组合进行总分排序，分数相同时选择更靠近棋盘中心的组合。

评分重点包括六连、活五、眠五、活四、眠四以及较低阶连接。防守分会略微加权，因此当对手有直接成六威胁时，程序会优先封堵。

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.