#!/bin/bash
echo "================================================"
echo "  CGPA Calculator - Saikat (2207002)"
echo "================================================"
echo ""
echo "Compiling and running the application..."
echo ""

mvn clean compile
if [ $? -ne 0 ]; then
    echo ""
    echo "[ERROR] Compilation failed!"
    exit 1
fi

echo ""
echo "Starting JavaFX application..."
echo ""
mvn javafx:run
