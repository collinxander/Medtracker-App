#!/bin/bash

# MedTrack Build Script for macOS
# This script downloads JavaFX SDK and compiles the project

set -e

MEDTRACKER_DIR="/Users/collinarnsworth/Desktop/MEDTRACKER"
JAVAFX_SDK_DIR="$MEDTRACKER_DIR/javafx-sdk"

echo "🏥 MedTrack Build Script"
echo "========================"
echo ""

# Download JavaFX SDK if not already present
if [ ! -d "$JAVAFX_SDK_DIR" ]; then
    echo "📥 Downloading JavaFX SDK 25..."
    cd "$MEDTRACKER_DIR"
    
    # Try using wget if curl fails
    if command -v wget &> /dev/null; then
        wget https://gluonhq.com/download/javafx-sdk-25/ -O javafx-sdk.zip
    else
        curl -L -o javafx-sdk.zip https://download2.gluonhq.com/openjfx/25.0.1/openjfx-25.0.1_osx-aarch64_bin-sdk.zip || \
        curl -L -o javafx-sdk.zip https://download2.gluonhq.com/openjfx/25.0.1/openjfx-25.0.1_osx-x64_bin-sdk.zip
    fi
    
    if [ -f javafx-sdk.zip ]; then
        echo "📦 Extracting JavaFX SDK..."
        unzip -q javafx-sdk.zip
        rm javafx-sdk.zip
        echo "✅ JavaFX SDK extracted"
    else
        echo "❌ Failed to download JavaFX SDK"
        exit 1
    fi
else
    echo "✅ JavaFX SDK already present"
fi

echo ""
echo "🔨 Compiling Java files..."

cd "$MEDTRACKER_DIR"

# Get JavaFX lib path
JAVAFX_LIB="$JAVAFX_SDK_DIR/lib"

# Compile all Java files
javac \
    --module-path "$JAVAFX_LIB" \
    --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.charts \
    *.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "🚀 To run MedTrack, use:"
    echo "   java --module-path $JAVAFX_LIB --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.charts MedTrack"
else
    echo "❌ Compilation failed"
    exit 1
fi
