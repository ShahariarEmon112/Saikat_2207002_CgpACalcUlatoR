@echo off
echo ================================================
echo   Building Executable JAR Package
echo   CGPA Calculator - Saikat (2207002)
echo ================================================
echo.

echo [1/2] Cleaning and compiling...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

echo.
echo [2/2] Creating executable JAR...
call mvn package
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Package creation failed!
    pause
    exit /b 1
)

echo.
echo ================================================
echo SUCCESS! Executable JAR created at:
echo target\cgpa-calculator-1.0-SNAPSHOT.jar
echo ================================================
echo.
echo To run on any device with Java 21+:
echo java -jar target\cgpa-calculator-1.0-SNAPSHOT.jar
echo.
pause
