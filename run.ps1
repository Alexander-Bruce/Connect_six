$ErrorActionPreference = "Stop"

$ProjectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$BuildDir = Join-Path $ProjectRoot "build\classes"

if (Test-Path $BuildDir) {
    Remove-Item -Recurse -Force $BuildDir
}
New-Item -ItemType Directory -Force $BuildDir | Out-Null

$SourceFiles = Get-ChildItem (Join-Path $ProjectRoot "src") -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
& javac -encoding UTF-8 -d $BuildDir $SourceFiles

$InputLines = @($input)
if ($InputLines.Count -gt 0) {
    $InputText = ($InputLines -join [Environment]::NewLine) + [Environment]::NewLine
    $InputText | & java -cp $BuildDir Main
} else {
    & java -cp $BuildDir Main
}