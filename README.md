# Application de chat multi-thread en Java

Auteur : Quentin VALAKOU, Université de Technologie de Compiègne | Automne 2025, AI13.

## Résumé

Cette application est un système de chat en socket Java très simple. Il est composé d'un serveur (server.jar) et d'un client (client.jar).

L'application lance un serveur sur votre adresse local (127.0.0.1) au port 10080.

### Commandes

- /quit : se déconnecter du chat.
- il n'est pas possible d'avoir plus d'un utilisateur ayant le même pseudo connecté en même temps sur le serveur.
- un pseudo doit être valide (au moins 3 caractères, pas plus de 16, sans caracètes spéciaux : $, *, &, /, etc).

## Installation

#### MacOS/Linux

- Pour lancer l'application, lancez votre serveur en cliquant sur run-server.command (dist/run-server.command)
- Ensuite lancez un ou plusieurs clients avec run-client.command (dist/run-client.command).

#### Windows

- Pour lancer l'application, lancez votre serveur en cliquant sur run-server.command (dist/run-serveur.bat)
- Ensuite lancez un ou plusieurs client avec run-client.bat (dist/run-client.bat).
- Note : par défaut, les .bat ne sont pas dans le Git. Créez les en lançant "compile-deploy.cmd".

#### Attention

Il est possible que les scripts ne se lancent pas si votre machine n'autorise pas l'exécution. Pour cela, dans la racine du projet, lancez :

```bash
chmod +x dist/run-server.command

chmod +x dist/run-client.command
```

## Modifier et recompiler

- Il est possible de modifier le code source et de recompiler facilement. Pour cela, sauvegardez vos modifications et lancez "compile-deploy.command" pour Mac/Linux et "compile-deploy.cmd" pour Windows. Cela compilera le code et créera les nouveaux .jar.

# Hiérarchie

```plaintext
ai13-devoir-socket/
├─ dist/ # fichiers de distributions jar
│  ├─ client.jar
│  ├─ server.jar
│  ├─ run-client.command
│  └─ run-server.command
├─ launch-application/ # (obsolète, utiliser dist/run-xxxx.command)
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
│  │  ├─ ClientSocket.java # création du client socket
│  │  ├─ MessageReceptor.java # gère les messages reçus par le serveur
│  │  └─ UserInputHandler.java # gère les messages envoyés par le client
│  └─ server/
│     ├─ ClientHandler.java # gère l'ensemble des clients (vérification pseudo)
│     ├─ MessageHandler.java # gère les fluxs IO stream des clients.
│     └─ ServeurSocket.java # création du serveur socket. 
└─ .idea/                    # fichiers de configuration IDE
```
