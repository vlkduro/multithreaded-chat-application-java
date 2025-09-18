package com.vlkduro.chat.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket {

    private final Socket socket;

    private ClientSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    /**
     * Tente de se connecter en boucle avec une petite animation en CLI.
     * - connectTimeoutMs : timeout d’une tentative (évite un blocage long)
     * - retryDelayMs : délai entre tentatives
     * Ctrl+C pour interrompre proprement.
     */
    public static ClientSocket connectWithRetry(String host, int port,
                                                int connectTimeoutMs,
                                                int retryDelayMs) throws InterruptedException {
        final char[] frames = {'|', '/', '-', '\\'};
        int frame = 0;
        int attempt = 0;

        while (true) {
            attempt++;
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress(host, port), connectTimeoutMs);
                // (Optionnel) Timeout de lecture pour éviter de bloquer à l'infini sur les reads
                s.setSoTimeout(30_000);

                // Efface la ligne du spinner puis confirme
                System.out.print("\r\033[K");
                System.out.printf("Connecté à %s:%d (tentative %d).\n", host, port, attempt);
                return new ClientSocket(s);
            } catch (IOException e) {
                // Affiche l’animation + message court, puis attend avant de retenter
                System.out.printf("\r%c  Serveur %s:%d indisponible (tentative %d). Nouvelle tentative dans %d ms...",
                        frames[frame++ % frames.length], host, port, attempt, retryDelayMs);
                System.out.flush();
                Thread.sleep(retryDelayMs);
            }
        }
    }

    public static void main(String[] args) {
        final String host = "127.0.0.1";
        final int port = 10080;

        try {
            ClientSocket client = ClientSocket.connectWithRetry(host, port, 400, 500);

            // On ne lance les threads qu’une fois connecté
            MessageReceptor messageReceptor = new MessageReceptor(client);
            UserInputHandler userInputHandler = new UserInputHandler(client);
            messageReceptor.start(); // Thread pour capter l'output
            userInputHandler.start(); // Thread pour capter l'input

        } catch (InterruptedException e) {
            // Interruption (ex: Ctrl+C) pendant l’attente
            System.out.print("\r\033[K");
            System.out.println("Arrêt demandé. Au revoir.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, "Erreur au démarrage du client", e);
        }
    }
}

