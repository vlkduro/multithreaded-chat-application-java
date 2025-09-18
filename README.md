# Java Multi-threaded Chat Application

## Documentation

- French version available at : [README-fr.md](docs/README-fr.md)
- Version française : [README-fr.md](docs/README-fr.md)

## Overview

This project is a simple **command-line chat system** built with Java sockets.  
It consists of a **server** (`server.jar`) and a **client** (`client.jar`).  
By default, the server runs on the local address `127.0.0.1` at port `10080`.

## Features

- CLI-only (no graphical interface).
- Multi-threaded chat with multiple clients.
- `/quit` command to disconnect from the chat.
- Unique usernames: two users cannot connect with the same username simultaneously.
- Username validation rules:
  - Minimum 3 characters, maximum 16 characters.
  - No special characters such as `$`, `*`, `&`, `/`, etc.

## Installation

This project uses Maven.
From the root folder, run :

1. Build the project (optional).
   If you want to rebuild the project after modification :

   ```bash
   mvnw clean package
   ```

   This will create executable JARs in :

   ```bash
   server/target/server-1.0.0.jar
   client/target/client-1.0.0.jar
   ```

2. Start the server :
   ```bash
   java -jar server/target/server-1.0.0.jar
   ```
3. Launch one or more clients :
   ```bash
   java -jar client/target/client-1.0.0.jar
   ```

## Project Structure

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

- Edit source code in _server/src/main/java_ or _client/src/main/java_
- Rebuild with Maven :
  ```bash
  mvnw clean package
  ```

---

Ready to use and easy to extend for experimenting with **CLI-based socket programming in Java**.
