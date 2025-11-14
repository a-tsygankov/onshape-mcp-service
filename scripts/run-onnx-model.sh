##!/usr/bin/env bash
#set -euo pipefail
#
## Resolve absolute path of the script directory
#SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
#PROJECT_ROOT="${SCRIPT_DIR}/.."
#MODEL_DIR="${PROJECT_ROOT}/docker/onnx-model/models"
#
#echo "[INFO] Project root: ${PROJECT_ROOT}"
#echo "[INFO] ONNX model directory expected at: ${MODEL_DIR}"
#
## Ensure model directory exists
#if [ ! -d "${MODEL_DIR}" ]; then
#  echo "[INFO] Model directory does not exist. Creating it..."
#  mkdir -p "${MODEL_DIR}"
#else
#  echo "[INFO] Model directory already exists."
#fi
#
## Ensure RW permissions (no execute)
#echo "[INFO] Setting RW permissions on model directory..."
#chmod -R u+rw,go+rw "${MODEL_DIR}" || echo "[WARN] chmod may be limited under macOS."
#
## Change into ONNX docker folder
#cd "${PROJECT_ROOT}/docker/onnx-model"
#
#echo "[INFO] Starting ONNX model container..."
#docker compose up -d
#
#echo "[INFO] ONNX model container started."
