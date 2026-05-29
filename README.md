# Connect Six AI

Language: English | [中文](README.zh-CN.md)

Connect Six AI is a command-line move generator for Connect6 on a 17 x 17 board. It reads the current board state and side to move from standard input, then prints the next move: one point for the opening move, or two points for a normal turn.

## Technical Implementation

- The board is represented as a `17 x 17` integer matrix: `0` for black, `1` for white, and `2` for empty.
- `ConnectSixAi` generates candidate points from empty cells within a radius of 3 around existing stones. If the board is empty, it opens at the center `(9, 9)`.
- `BoardEvaluator` scores a point by scanning four directions: horizontal, vertical, diagonal, and anti-diagonal. It counts connected stones and open ends, then assigns higher weights to six-in-a-row, open five, blocked five, open four, and smaller patterns.
- Move selection uses a two-stone heuristic search. It ranks first-stone candidates, simulates each strong first point, recalculates second-stone candidates, then scores the final pair.
- Attack and defense are both considered. Defensive score is slightly boosted so the AI blocks immediate opponent threats before chasing lower-value attacks.
- The program validates board size, side values, coordinates, duplicate stones, and falls back to the first legal pair if no nearby candidate is found.

## Project Structure

```text
.
├── src/
│   ├── Main.java                  # Standard input/output entry point
│   ├── Test.java                  # Lightweight self-checks
│   ├── evaluation/
│   │   ├── BoardEvaluator.java    # Pattern scoring
│   │   └── ConnectSixAi.java      # Candidate generation and move search
│   └── variables/
│       ├── Point.java             # Board coordinate value object
│       └── Step.java              # Two-point move value object
├── run.sh                         # macOS/Linux run script
├── run.ps1                        # Windows PowerShell run script
├── README.md
├── README.zh-CN.md
└── LICENSE
```

## Requirements

- JDK 8 or later
- Available `javac` and `java` commands

Check your Java environment:

```bash
javac -version
java -version
```

## Input Format

The first line contains 3 integers:

```text
blackN whiteN side
```

- `blackN`: number of black stones already on the board.
- `whiteN`: number of white stones already on the board.
- `side`: current state.
  - `0`: the program plays the black opening move. No board coordinates are needed.
  - `1`: black to move.
  - `2`: white to move.

When `side` is `1` or `2`, provide the remaining input in this order:

1. `blackN` lines of black-stone coordinates.
2. `whiteN` lines of white-stone coordinates.

Coordinates are 1-based row and column values from 1 to 17:

```text
row column
```

## Output Format

Opening move:

```text
row column
```

Normal turn:

```text
row1 column1 row2 column2
```

## Usage

### Windows PowerShell

```powershell
.\run.ps1
```

Pipe input directly:

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

Pipe input directly:

```bash
printf "1 0 2\n9 9\n" | ./run.sh
```

### Manual Build And Run

```bash
mkdir -p build/classes
javac -encoding UTF-8 -d build/classes $(find src -name "*.java")
java -cp build/classes Main
```

## Examples

Black opening move:

```text
0 0 0
```

Output:

```text
9 9
```

One black stone on the board, white to move:

```text
1 0 2
9 9
```

Possible output:

```text
8 9 9 8
```

The exact move depends on the current board evaluation, but the output will contain legal, distinct empty points.

## Self-Check

After compiling, run:

```bash
java -cp build/classes Test
```

The self-check covers:

- Center opening move.
- Legal two-point response.
- Blocking an immediate open-five threat.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.