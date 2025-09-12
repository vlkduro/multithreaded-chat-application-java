#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
if [[ ! -x "$JAVA_BIN" ]]; then
  JAVA_BIN="java"
fi

# Arguments : hôte et port (défauts: 127.0.0.1:10080)
HOST="${1:-127.0.0.1}"
PORT="${2:-10080}"

exec "$JAVA_BIN" -jar "$SCRIPT_DIR/client.jar" "$HOST" "$PORT"
