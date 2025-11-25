@echo off
REM Script to test Database functionality
REM Must have SQLite JDBC and Gson libraries

setlocal enabledelayedexpansion

echo ================================================
echo   GPA Calculator - Database Test
echo   Saikat (2207002)
echo ================================================
echo.

REM Create output directory
if not exist "build" mkdir build

REM Download dependencies to lib folder
echo [INFO] Downloading required libraries...
if not exist "lib\sqlite-jdbc-3.44.0.0.jar" (
    echo Downloading SQLite JDBC driver...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.0.0/sqlite-jdbc-3.44.0.0.jar', 'lib\sqlite-jdbc-3.44.0.0.jar')" 2>nul
)

if not exist "lib\gson-2.10.1.jar" (
    echo Downloading Gson library...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar', 'lib\gson-2.10.1.jar')" 2>nul
)

echo.
echo [INFO] Compiling Java classes...

cd src

REM Compile all classes in src to build directory
javac -d ..\build -cp "..\lib\*" *.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile Java files
    cd ..
    exit /b 1
)

cd ..

echo [OK] Compilation successful!
echo.
echo [INFO] Running Database Tests...
echo.

REM Run tests
java -cp "build;lib\*" DatabaseTest

if !ERRORLEVEL! EQU 0 (
    echo.
    echo ================================================
    echo    ALL TESTS PASSED SUCCESSFULLY!
    echo ================================================
) else (
    echo.
    echo ================================================
    echo    TEST FAILED!
    echo ================================================
    exit /b 1
)

endlocal

