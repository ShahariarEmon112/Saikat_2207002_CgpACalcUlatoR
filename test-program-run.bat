@echo off
REM Test the interactive program demo with automated inputs

echo.
echo ==========================================
echo   Running GPA Calculator Program Test
echo ==========================================
echo.

cd /d "%~dp0"

REM Add 3 sample courses
(
  echo 1
  echo Data Structures
  echo CSC201
  echo 3.0
  echo A
  echo Dr. Ahmed
  echo Dr. Fatima
  echo 1
  echo Database Design
  echo CSC301
  echo 3.5
  echo B+
  echo Prof. Khan
  echo Dr. Noor
  echo 1
  echo Web Development
  echo CSC401
  echo 4.0
  echo A-
  echo Dr. Hassan
  echo Ms. Zara
  echo 2
  echo 3
  echo 4
  echo 8
) | java -cp "build;lib/*" ProgramDemo

echo.
echo ==========================================
echo   Program Test Completed
echo ==========================================
echo.

REM Show generated files
echo.
echo Checking generated files:
dir /b *.db *.json 2>nul
echo.
