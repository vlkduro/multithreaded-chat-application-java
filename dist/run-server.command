#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
[[ -x "$JAVA_BIN" ]] || JAVA_BIN="java"
exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar" "$@"
