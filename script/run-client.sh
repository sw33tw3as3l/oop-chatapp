#!/bin/bash

# Script to compile and run the chat client
# Usage: ./run-client.sh [host] [port]
# Default: host=127.0.0.1, port=5555

set -e  # Exit on error

# Get the project root directory (parent of script directory)
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
COMMON_DIR="$PROJECT_ROOT/common"
CLIENT_DIR="$PROJECT_ROOT/client"
HOST="${1:-127.0.0.1}"
PORT="${2:-5555}"

echo "=== Chat Client Build & Run Script ==="
echo "Project root: $PROJECT_ROOT"
echo "Connecting to: $HOST:$PORT"
echo ""

# Check if Java is installed
if ! command -v javac &> /dev/null; then
    echo "Error: javac not found. Please install Java JDK."
    exit 1
fi

# Create build directories
echo "Creating build directories..."
mkdir -p "$COMMON_DIR/build/classes"
mkdir -p "$CLIENT_DIR/build/classes"

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

# Compile client module
echo "Compiling client module..."
CLIENT_SRC="$CLIENT_DIR/src/java"
if [ ! -d "$CLIENT_SRC" ]; then
    echo "Error: Client source directory not found: $CLIENT_SRC"
    exit 1
fi

CLASSPATH="$COMMON_DIR/build/classes:$CLIENT_DIR/build/classes"
JAVA_FILES=$(find "$CLIENT_SRC" -name "*.java")
if [ -z "$JAVA_FILES" ]; then
    echo "Error: No Java files found in $CLIENT_SRC"
    exit 1
fi

javac -d "$CLIENT_DIR/build/classes" -sourcepath "$CLIENT_SRC" -cp "$CLASSPATH" $JAVA_FILES 2>&1 | grep -v "^Note:" || true

if [ $? -ne 0 ]; then
    echo "Error: Failed to compile client module"
    exit 1
fi

echo "Client module compiled successfully."
echo ""

# Run the client
echo "Starting client..."
echo "Connecting to server at $HOST:$PORT"
echo ""

cd "$CLIENT_DIR"
java -cp "$CLIENT_DIR/build/classes:$COMMON_DIR/build/classes" com.ap.chat.client.app.ClientMain "$HOST" "$PORT"

