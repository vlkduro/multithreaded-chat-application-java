# Application de chat multi-thread en Java

Auteur : Quentin VALAKOU, Université de Technologie de Compiègne | Automne 2025, AI13.

## Résumé

Cette application est un système de chat en socket Java très simple. Il est composé d'un serveur (server.jar) et d'un client (client.jar).

Le serveur écoute sur votre adresse locale (127.0.0.1) au port 10080.

- Il ne peut exister qu'une seule instance de serveur (server.jar) qui tourne à la fois.
- Il peut y avoir plusieurs clients connectés sur le même serveur (lancez plusieurs fois le client).

##### Commandes :

- /quit : se déconnecter du chat.
- il n'est pas possible d'avoir plus d'un utilisateur ayant le même pseudo connecté en même temps sur le serveur.
- un pseudo doit être valide (au moins 3 caractères, pas plus de 16, sans caracètes spéciaux : $, \*, &, /, etc).

## Installation

Ce projet utilise Maven.
Il n'est pas nécessaire de l'installer.
A la racine du projet :

1. Pour build (optionnel).
   Si vous voulez rebuild après modification :

   ```bash
   mvnw clean package
   ```

   Cela va créer deux fichiers JARs exécutables :

   ```bash
   server/target/server-1.0.0.jar
   client/target/client-1.0.0.jar
   ```

2. Lancer le serveur :
   ```bash
   java -jar server/target/server-1.0.0.jar
   ```
3. Lancer un ou plusieurs clients :
   ```bash
   java -jar client/target/client-1.0.0.jar
   ```

## Arborescence du projet

```
project-root/
├── pom.xml                  # Parent Maven build file
│
├── server/                  # Server module
│   ├── pom.xml
│   └── src/main/java/...    # Server source code
│
├── client/                  #      Client module
│   ├── pom.xml
│   └── src/main/java/...    # Client source code
│
├── docs/                    # Documentation
└── README.md                # Project documentation
```

## Development

- Modifier le code source : _server/src/main/java_ ou _client/src/main/java_
- Rebuild avec Maven :
  ```bash
  mvnw clean package
  ```

---

Auteur : Quentin Valakou
