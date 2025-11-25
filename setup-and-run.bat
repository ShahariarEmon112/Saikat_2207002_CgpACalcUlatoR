@echo off
echo ================================================
echo   CGPA Calculator Setup Verification
echo   By: Saikat (2207002)
echo ================================================
echo.

echo [Step 1/4] Checking Java installation...
java -version 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java is not installed or not in PATH!
    echo.
    echo Please install Java JDK 21 or higher from:
    echo https://adoptium.net/
    echo.
    pause
    exit /b 1
)
echo [OK] Java is installed
echo.

echo [Step 2/4] Checking Maven installation...
mvn -version 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven is not installed or not in PATH!
    echo.
    echo Please install Maven from:
    echo https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)
echo [OK] Maven is installed
echo.

echo [Step 3/4] Downloading dependencies (first time only)...
echo This may take a few minutes...
echo.
call mvn clean install -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Failed to download dependencies!
    echo Please check your internet connection and try again.
    echo.
    pause
    exit /b 1
)
echo [OK] Dependencies downloaded successfully
echo.

echo [Step 4/4] Starting application...
echo.
call mvn javafx:run

pause
