@echo off
REM Script to test Database functionality
REM Can be compiled without JavaFX

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
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.0.0/sqlite-jdbc-3.44.0.0.jar', 'lib\sqlite-jdbc-3.44.0.0.jar')"
)

if not exist "lib\gson-2.10.1.jar" (
    echo Downloading Gson library...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar', 'lib\gson-2.10.1.jar')"
)

if not exist "lib\slf4j-api-2.0.9.jar" (
    echo Downloading SLF4J API library...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar', 'lib\slf4j-api-2.0.9.jar')"
)

if not exist "lib\slf4j-nop-2.0.9.jar" (
    echo Downloading SLF4J NOP library...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/slf4j/slf4j-nop/2.0.9/slf4j-nop-2.0.9.jar', 'lib\slf4j-nop-2.0.9.jar')"
)

echo.
echo [INFO] Compiling Java classes (without JavaFX-dependent classes)...

cd src

REM Compile base classes first
javac -d ..\build Course.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile Course.java
    cd ..
    exit /b 1
)

javac -d ..\build GPACalculator.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile GPACalculator.java
    cd ..
    exit /b 1
)

REM Now compile database classes with classpath including build directory
javac -cp "..\build;..\lib\*" -d ..\build DatabaseManager.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile DatabaseManager.java
    cd ..
    exit /b 1
)

javac -cp "..\build;..\lib\*" -d ..\build JSONHandler.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile JSONHandler.java
    cd ..
    exit /b 1
)

REM Compile standalone test (no JavaFX)
javac -cp "..\build;..\lib\*" -d ..\build DatabaseTestStandalone.java
if !ERRORLEVEL! NEQ 0 (
    echo [ERROR] Failed to compile DatabaseTestStandalone.java
    cd ..
    exit /b 1
)

cd ..

echo [OK] Compilation successful!
echo.
echo [INFO] Running Database Tests...
echo.

REM Run tests
java -cp "build;lib\*" DatabaseTestStandalone

if !ERRORLEVEL! EQU 0 (
    echo.
    echo ================================================
    echo    ALL TESTS PASSED SUCCESSFULLY!
    echo ================================================
    echo.
    echo Database file created: gpa_calculator.db
    echo JSON export file: courses_export.json
) else (
    echo.
    echo ================================================
    echo    TEST FAILED!
    echo ================================================
    exit /b 1
)

endlocal


