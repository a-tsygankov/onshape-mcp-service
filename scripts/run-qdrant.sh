#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR/../docker/qdrant"

echo "[INFO] Starting Qdrant (and initializing collection 'parts')..."
docker compose up -d

echo "[INFO] Qdrant is starting. Use 'docker compose logs -f' to watch logs."
