#!/bin/bash

# 定义工程名和源码目录
PROJECT_NAME="Connect_6_2nd_version"
SRC_DIR="src"
BUILD_DIR="bin"

# 检查并创建编译输出目录
if [ ! -d "$BUILD_DIR" ]; then
    mkdir $BUILD_DIR
fi

# 编译源代码
javac -d $BUILD_DIR $(find $SRC_DIR -name "*.java")

# 运行程序
java -cp $BUILD_DIR Main
