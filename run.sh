#!/bin/bash

# MedTrack Run Script for macOS
# Usage: ./run.sh

cd "$(dirname "$0")"

JAVAFX_LIB="./javafx-sdk/lib"

echo "🏥 MedTrack - Your Personal Health Log"
echo "======================================"
echo ""

java --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.graphics MedTrack "$@"
