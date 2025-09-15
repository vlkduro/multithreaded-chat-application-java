# Java Multi-threaded Chat Application

## Documentation 

French version available at : docs/README-fr.md
Version française : docs/README-fr.md

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

1. Start the server:  
   ```bash
   ./dist/run-server.command
   ```

2. Launch one or more clients:  
   ```bash
   ./dist/run-client.command
   ```

## Project Structure
```
project-root/
├── src/                   # Java source code (client and server)
│   ├── client/            # Client-side code
│   └── server/            # Server-side code
│
├── dist/                  # Compiled JAR files and launch scripts
│   ├── server.jar
│   ├── client.jar
│   ├── run-server.command # Start the server (CLI)
│   └── run-client.command # Start a client (CLI)
│
├── compile-deploy.command # Script to compile and redeploy JARs
└── README.md              # Project documentation
```

## Development & Recompilation
To modify and rebuild the project:

1. Edit the source code in the `src/` directory.  
2. Run the following command:  
   ```bash
   ./compile-deploy.command
   ```
3. The script will compile the updated code and generate the new `.jar` files in the `dist/` directory.

---

Ready to use and easy to extend for experimenting with **CLI-based socket programming in Java**.  
