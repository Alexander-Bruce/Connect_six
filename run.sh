#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_DIR="$PROJECT_ROOT/build/classes"

rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR"

javac -encoding UTF-8 -d "$BUILD_DIR" $(find "$PROJECT_ROOT/src" -name "*.java")
java -cp "$BUILD_DIR" Main