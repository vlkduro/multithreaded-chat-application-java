package server;

import java.io.*;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientHandler extends Thread {
    private final ServeurSocket serveurSocket;
    // Clients connectés : Map de sockets client + leur OutPut stream
    // PrintWrite est utilisé pour son auto-flush + encodage (UTF8) plus simple que Output Stream
    private final ConcurrentHashMap<Socket, PrintWriter> clientsConnected = new ConcurrentHashMap<>();
    private final Set<String> setPseudo = ConcurrentHashMap.newKeySet();

    public ClientHandler(ServeurSocket serveurSocket) {
        this.serveurSocket = serveurSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // L'acceptation de nouveaux clients est gérée par un thread car .accept() est bloquante.
                Socket client = serveurSocket.getSocket().accept();
                // On gère la vérification du nouveau client dans un nouveau thread pour laisser les autres arriver.
                new Thread(() -> handleClient(client), "client-" + client.getPort()).start();
            } catch (IOException e) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
                break;
            }
        }
    }


    // Dans ClientHandler
    private void handleClient(Socket client) {
        try {
            // On initialise de nouveau IO Stream pour le nouveau client afin de communiquer avec lui.
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // out est un PrintWrite qui serait l'OutputStream du socket client, l'auto-flush est à true.
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("Rentrez votre pseudo pour l'identification:");
            // Vérification du pseudo
            String pseudo;
            while (true) {
                pseudo = in.readLine();
                if (pseudo == null) {
                    continue;
                }
                pseudo = pseudo.trim();

                if (!isPseudoValid(pseudo)) {
                    out.println("Pseudo invalide. Utilisez 3–16 lettres/chiffres/_/-. Recommencez:");
                    continue;
                }
                if (!setPseudo.add(pseudo)) {
                    out.println("Un pseudo similaire est déjà connecté sur le serveur. Recommencez. ");
                    continue;
                }
                break; // pseudo OK et unique
            }

            clientsConnected.put(client, out); // On ajoute notre client dans le setClient. Idéal pour détecter les doublons.
            System.out.println("Client accepté (" + pseudo + ") depuis port distant " + client.getPort());

            // MessageHandler va gérer les IO Stream du client. ClientHandler lui laisse la main.
            MessageHandler mh = new MessageHandler(pseudo, client, in, out, clientsConnected, setPseudo);
            // Thread séparé
            mh.start();

        } catch (IOException e) {
            java.util.logging.Logger.getLogger(ClientHandler.class.getName())
                    .log(java.util.logging.Level.INFO, "Client déconnecté pendant le handshake", e);
            try { client.close(); } catch (IOException ignored) {}
        }
    }

    // Vérification d'un pseudo (non vide, assez long).
    private boolean isPseudoValid(String s) {
        if (s == null) return false;
        s = s.trim();
        int n = s.length();
        if (n < 3 || n > 16) {
            return false;
        }

        // pour une vérification plus poussée, pas utilisée ici.
        /**for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (!(Character.isLetterOrDigit(ch) || ch == '_' || ch == '-')) return false;
        }**/
        return true;
    }

}
