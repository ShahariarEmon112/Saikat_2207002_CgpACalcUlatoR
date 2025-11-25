@echo off
echo Starting CGPA Calculator...
java -cp "cgpa-calculator-1.0-SNAPSHOT.jar;libs/*" Main
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Make sure Java 21+ is installed!
    echo Download from: https://adoptium.net/
    pause
)
