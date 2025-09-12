# ai13-devoir-socket

## Résumé

Cette application est un système de chat en socket Java très simple. Il est composé d'un serveur (server.jar) et d'un client (client.jar).

L'application lance un serveur sur votre adresse local (127.0.0.1) au port 10080.

## Installation

- Pour lancer l'application, lancez votre serveur en cliquant sur run-server.command (dist/run-serveur.command)
- Ensuite lancez un, ou plusieurs clients avec run-client.command (dist/run-client.command).

## Modifier et recompiler

- Il est possible de modifier le code source et de recompiler facilement. Pour cela, sauvegardez vos modifications et lancez "compile-deploy.command". Cela compilera le code et créera les nouveaux .jar.

# Hiérarchie

ai13-devoir-socket/
├─ dist/
│  ├─ client.jar
│  ├─ server.jar
│  ├─ run-client.command
│  └─ run-server.command
├─ launch-application/ (obsolète, utiliser /dist/ruun-xxx.command
│  ├─ launch-client.command
│  ├─ launch-client.sh
│  ├─ launch-server.command
│  ├─ launch-server.sh
│  └─ run.sh
├─ out/
│  ├─ client/                # classes compilées
│  ├─ server/                # classes compilées
│  └─ sources.txt
├─ src/
│  ├─ client/
│  │  ├─ ClientSocket.java  # création du socket client
│  │  ├─ MessageReceptor.java # écoute des messages reçues par le client
│  │  └─ UserInputHandler.java # gère les entrées du client pour l'envoi vers le serveur
│  └─ server/
│     ├─ ClientHandler.java # gère l'ensemble des clients (vérification pseudo).
│     ├─ MessageHandler.java # gère les fluxs sockets des clients.
│     └─ ServeurSocket.java # créations du serveur socket.
