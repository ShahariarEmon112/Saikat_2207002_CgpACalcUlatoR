@echo off
echo ================================================
echo   Creating Portable Package
echo   CGPA Calculator - Saikat (2207002)
echo ================================================
echo.

echo This will create a package that can run on any device
echo without needing Maven!
echo.

echo [1/3] Compiling project...
call mvn clean compile
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed!
    pause
    exit /b 1
)
echo.

echo [2/3] Copying dependencies...
call mvn dependency:copy-dependencies -DoutputDirectory=target/libs
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Failed to copy dependencies!
    pause
    exit /b 1
)
echo.

echo [3/3] Creating package...
call mvn package
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Package creation failed!
    pause
    exit /b 1
)
echo.

echo ================================================
echo SUCCESS! Portable package created!
echo ================================================
echo.
echo Share the entire 'target' folder with others.
echo.
echo To run on any device with Java 21+:
echo.
echo   cd target
echo   java -cp "cgpa-calculator-1.0-SNAPSHOT.jar;libs/*" Main
echo.
echo Creating run script for other devices...

(
echo @echo off
echo echo Starting CGPA Calculator...
echo java -cp "cgpa-calculator-1.0-SNAPSHOT.jar;libs/*" Main
echo if %%ERRORLEVEL%% NEQ 0 ^(
echo     echo.
echo     echo ERROR: Make sure Java 21+ is installed!
echo     echo Download from: https://adoptium.net/
echo     pause
echo ^)
) > target\run.bat

echo.
echo Created: target\run.bat
echo.
echo ================================================
echo INSTRUCTIONS:
echo 1. Share the entire 'target' folder
echo 2. On other device, just double-click 'run.bat'
echo 3. Only Java 21+ required (no Maven needed!)
echo ================================================
echo.
pause
