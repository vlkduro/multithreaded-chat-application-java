#!/usr/bin/env bash
set -euo pipefail

set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"; [[ -x "$JAVA_BIN" ]] || JAVA_BIN="java"
JAR="$SCRIPT_DIR/../dist/server.jar"
exec "$JAVA_BIN" -jar "$JAR" "$@"
PORT="${1:-}"

if [[ -n "$PORT" ]]; then
  exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar" "$PORT"
else
  exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar"
fi
