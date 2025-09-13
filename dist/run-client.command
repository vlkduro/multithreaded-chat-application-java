#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
[[ -x "$JAVA_BIN" ]] || JAVA_BIN="java"
HOST="${1:-127.0.0.1}"
PORT="${2:-10080}"
exec "$JAVA_BIN" -jar "$SCRIPT_DIR/client.jar" "$HOST" "$PORT"
