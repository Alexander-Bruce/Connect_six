# 定义工程名和源码目录
$PROJECT_NAME = "Connect_6"
$SRC_DIR = "src"
$BUILD_DIR = "bin"

# 检查并创建编译输出目录
if (-Not (Test-Path $BUILD_DIR)) {
    New-Item -ItemType Directory -Path $BUILD_DIR
}

# 编译源代码
$javaFiles = Get-ChildItem -Path $SRC_DIR -Recurse -Filter *.java
$javacArgs = @("-d", $BUILD_DIR) + ($javaFiles | ForEach-Object { $_.FullName })
& javac @javacArgs

# 检查编译是否成功
if ($LASTEXITCODE -eq 0) {
    Write-Output "Compilation successful."
} else {
    Write-Output "Compilation failed."
    exit 1
}

# 运行程序
& java -cp $BUILD_DIR Main
