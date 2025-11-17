@echo off
echo ================================================
echo   CGPA Calculator - Saikat (2207002)
echo ================================================
echo.
echo Compiling and running the application...
echo.

call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)

echo.
echo Starting JavaFX application...
echo.
call mvn javafx:run

pause
