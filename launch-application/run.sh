# Rendre exécutables
chmod +x run-server.sh run-client.sh

# Lancer le serveur
./run-server.sh          # ou ./run-server.sh 10080 si ton main accepte un port

# Dans un autre terminal, lancer un client
./run-client.sh          # par défaut se connecte à 127.0.0.1:10080
# ou vers une autre machine du LAN :
./run-client.sh 192.168.1.42 10080
