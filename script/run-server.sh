#!/bin/bash

# Script to compile and run the chat server
# Usage: ./run-server.sh [port]
# Default port: 5555

set -e  # Exit on error

# Get the project root directory (parent of script directory)
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
COMMON_DIR="$PROJECT_ROOT/common"
SERVER_DIR="$PROJECT_ROOT/server"
PORT="${1:-5555}"

echo "=== Chat Server Build & Run Script ==="
echo "Project root: $PROJECT_ROOT"
echo "Port: $PORT"
echo ""

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo "Error: javac not found. Please install Java JDK."
    exit 1
fi

# Create build directories
echo "Creating build directories..."
mkdir -p "$COMMON_DIR/build/classes"
mkdir -p "$SERVER_DIR/build/classes"

# Compile common module
echo "Compiling common module..."
COMMON_SRC="$COMMON_DIR/src/java"
if [ ! -d "$COMMON_SRC" ]; then
    echo "Error: Common source directory not found: $COMMON_SRC"
    exit 1
fi

JAVA_FILES=$(find "$COMMON_SRC" -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "Error: No Java files found in $COMMON_SRC"
    exit 1
fi

javac -d "$COMMON_DIR/build/classes" -sourcepath "$COMMON_SRC" $JAVA_FILES 2>&1 | grep -v "^Note:" || true

if [ $? -ne 0 ]; then
    echo "Error: Failed to compile common module"
    exit 1
fi

echo "Common module compiled successfully."
echo ""

# Compile server module
echo "Compiling server module..."
SERVER_SRC="$SERVER_DIR/src/main/java"
if [ ! -d "$SERVER_SRC" ]; then
    echo "Error: Server source directory not found: $SERVER_SRC"
    exit 1
fi

CLASSPATH="$COMMON_DIR/build/classes:$SERVER_DIR/build/classes"
JAVA_FILES=$(find "$SERVER_SRC" -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "Error: No Java files found in $SERVER_SRC"
    exit 1
fi

javac -d "$SERVER_DIR/build/classes" -sourcepath "$SERVER_SRC" -cp "$CLASSPATH" $JAVA_FILES 2>&1 | grep -v "^Note:" || true

if [ $? -ne 0 ]; then
    echo "Error: Failed to compile server module"
    exit 1
fi

echo "Server module compiled successfully."
echo ""

# Run the server
echo "Starting server on port $PORT..."
echo "Press Ctrl+C to stop the server"
echo ""

cd "$SERVER_DIR"
java -cp "$SERVER_DIR/build/classes:$COMMON_DIR/build/classes" com.ap.chat.server.app.ServerMain "$PORT"

