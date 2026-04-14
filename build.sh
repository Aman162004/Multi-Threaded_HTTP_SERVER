#!/bin/bash
# Compilation script for Group 5 Web Server

echo "Cleaning out old class files..."
rm -rf out 2>/dev/null
mkdir -p out

echo "Compiling source code..."
# Compile all java files in src
find src -name "*.java" > sources.txt
javac -d out @sources.txt

rm sources.txt

if [ $? -eq 0 ]; then
    echo "Compilation Successful."
else
    echo "Compilation Failed."
    exit 1
fi
