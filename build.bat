@echo off
setlocal
cd /d "%~dp0"
echo KitaBlox API 1.21.11
 echo.
echo This project is configured for IntelliJ's Gradle 8.14 + Java 21.
echo In IntelliJ: Gradle JVM = Java 21, Distribution = Gradle 8.14.
echo.
gradle build
if errorlevel 1 (
  echo.
  echo BUILD FAILED.
  pause
  exit /b 1
)
echo.
echo BUILD SUCCESSFUL.
echo JAR: %CD%\build\libs\kitablox-api-0.1.0+mc1.21.11.jar
pause
