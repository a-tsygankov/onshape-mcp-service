#!/usr/bin/env bash
set -euo pipefail

IDEA_BIN="${IDEA_BIN:-idea}"

PLUGINS=(
  "org.jetbrains.plugins.docker"
  "org.jetbrains.plugins.kubernetes"
  "org.intellij.plugins.markdown"
  "com.jetbrains.restClient"
  "org.jetbrains.kotlin"
  "com.intellij.spring"
  "org.zalando.intellij.swagger"
)

echo "[INFO] Using IDEA binary: $IDEA_BIN"
echo "[INFO] Installing plugins: ${PLUGINS[*]}"

for plugin in "${PLUGINS[@]}"; do
  echo "[INFO] Installing plugin: $plugin"
  "$IDEA_BIN" installPlugins "$plugin" || echo "[WARN] Failed to install $plugin"
done

echo "[INFO] Done. Restart IntelliJ IDEA to activate plugins."
