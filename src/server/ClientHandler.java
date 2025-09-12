package server;

import java.io.*;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientHandler extends Thread {
    private final ServeurSocket serveurSocket;
    private final ConcurrentHashMap<Socket, PrintWriter> clients = new ConcurrentHashMap<>();
    private final Set<String> setPseudo = ConcurrentHashMap.newKeySet();

    public ClientHandler(ServeurSocket serveurSocket) {
        this.serveurSocket = serveurSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = serveurSocket.getSocket().accept();
                handleClient(client);
            } catch (IOException e) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, e);
                break;
            }
        }
    }


    // Dans ClientHandler
    private void handleClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("Rentrez votre pseudo pour l'identification:");
            String pseudo;
            while (true) {
                pseudo = in.readLine();
                if (pseudo == null) { // client parti pendant le handshake
                    client.close();
                    return;
                }
                pseudo = pseudo.trim();

                if (!isPseudoValid(pseudo)) {
                    out.println("Pseudo invalide. Utilisez 3–16 lettres/chiffres/_/-. Recommencez:");
                    continue;
                }
                if (!setPseudo.add(pseudo)) {
                    out.println("Pseudo déjà pris. Connexion refusée.");
                    client.close();
                    return;
                }
                break; // pseudo OK et unique
            }

            clients.put(client, out);
            System.out.println("Client accepté (" + pseudo + ") depuis port distant " + client.getPort());

            MessageHandler mh = new MessageHandler(pseudo, client, in, out, clients, setPseudo);
            mh.start();

        } catch (IOException e) {
            java.util.logging.Logger.getLogger(ClientHandler.class.getName())
                    .log(java.util.logging.Level.INFO, "Client déconnecté pendant le handshake", e);
            try { client.close(); } catch (IOException ignored) {}
        }
    }

    private boolean isPseudoValid(String s) {
        if (s == null) return false;
        s = s.trim();
        int n = s.length();
        if (n < 3 || n > 16) return true;
        /**for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (!(Character.isLetterOrDigit(ch) || ch == '_' || ch == '-')) return false;
        }**/
        return true;
    }

}
