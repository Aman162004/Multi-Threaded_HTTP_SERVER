#!/bin/bash
# Startup script for Group 5 Web Server

# Check if application has been compiled
if [ ! -d "out" ]; then
    echo "Running build..."
    ./build.sh
fi

echo "Starting Server..."
# Run the main class
java -cp out server.Main
