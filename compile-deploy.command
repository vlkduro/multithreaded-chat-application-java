#!/usr/bin/env bash
set -euo pipefail

# --- Réglages ---
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
echo $SCRIPT_DIR
PROJECT_ROOT="$SCRIPT_DIR"
SRC_DIR="$PROJECT_ROOT/src"
OUT_DIR="$PROJECT_ROOT/out"
DIST_DIR="$PROJECT_ROOT/dist"

SERVER_MAIN="server.ServeurSocket"
CLIENT_MAIN="client.ClientSocket"

# --- Préparation ---
echo "== Nettoyage =="
rm -rf "$OUT_DIR" "$DIST_DIR"
mkdir -p "$OUT_DIR" "$DIST_DIR"

# --- Compilation ---
echo "== Compilation =="
# Liste des sources
find "$SRC_DIR" -name "*.java" > "$OUT_DIR/sources.txt"
if [[ ! -s "$OUT_DIR/sources.txt" ]]; then
  echo "Erreur: aucun fichier .java trouvé sous $SRC_DIR"
  exit 1
fi

# Compile en UTF-8 vers out/
javac -encoding UTF-8 -d "$OUT_DIR" @"$OUT_DIR/sources.txt"

# Vérifie la présence des classes main compilées
SERVER_CLASS_FILE="$OUT_DIR/${SERVER_MAIN//.//}.class"
CLIENT_CLASS_FILE="$OUT_DIR/${CLIENT_MAIN//.//}.class"

if [[ ! -f "$SERVER_CLASS_FILE" ]]; then
  echo "Erreur: classe principale introuvable: $SERVER_MAIN (attendu: $SERVER_CLASS_FILE)"
  exit 1
fi
if [[ ! -f "$CLIENT_CLASS_FILE" ]]; then
  echo "Erreur: classe principale introuvable: $CLIENT_MAIN (attendu: $CLIENT_CLASS_FILE)"
  exit 1
fi

# --- Packaging JAR (compatible JDK 8) ---
echo "== Packaging JAR =="
# jar cfe <jar> <Main-Class> -C <dir> .
jar cfe "$DIST_DIR/server.jar" "$SERVER_MAIN" -C "$OUT_DIR" .
jar cfe "$DIST_DIR/client.jar" "$CLIENT_MAIN" -C "$OUT_DIR" .

# --- Lanceurs .command (optionnels, pratiques pour double-clic) ---
cat > "$DIST_DIR/run-server.command" << 'EOF'
#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
[[ -x "$JAVA_BIN" ]] || JAVA_BIN="java"
exec "$JAVA_BIN" -jar "$SCRIPT_DIR/server.jar" "$@"
EOF

cat > "$DIST_DIR/run-client.command" << 'EOF'
#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
JAVA_BIN="$SCRIPT_DIR/runtime/bin/java"
[[ -x "$JAVA_BIN" ]] || JAVA_BIN="java"
HOST="${1:-127.0.0.1}"
PORT="${2:-10080}"
exec "$JAVA_BIN" -jar "$SCRIPT_DIR/client.jar" "$HOST" "$PORT"
EOF

chmod +x "$DIST_DIR/run-server.command" "$DIST_DIR/run-client.command"

echo
echo "== OK =="
echo "Serveur: $DIST_DIR/server.jar  (double-clique: run-server.command)"
echo "Client : $DIST_DIR/client.jar  (double-clique: run-client.command)"
