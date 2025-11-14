#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/../docker/onnx-model"

echo "[INFO] Starting ONNX model container (placeholder)..."
docker compose up -d

echo "[INFO] ONNX model container is running. Mount your model into ./models."
