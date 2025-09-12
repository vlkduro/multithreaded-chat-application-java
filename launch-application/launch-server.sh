#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
if [[ ! -x "$JAVA_BIN" ]]; then
  JAVA_BIN="java"
fi

PORT="${1:-}"

if [[ -n "$PORT" ]]; then
  exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar" "$PORT"
else
  exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar"
fi
