package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


// MessageHandler s'occupe de l'envoi et de la réception des messages sur les sockets clients.
// Il contient aussi la fonction broadcast pour envoyer à tous les clients.

public class MessageHandler extends Thread {
    private final String pseudo;
    private final Socket client;
    private final BufferedReader in;   // réutilise le même reader, pas de double-lecture
    private final PrintWriter out;     // writer de ce client
    private final ConcurrentHashMap<Socket, PrintWriter> clients;
    private final Set<String> setPseudo;

    public MessageHandler(String pseudo,
                          Socket client,
                          BufferedReader in,
                          PrintWriter out,
                          ConcurrentHashMap<Socket, PrintWriter> clients,
                          Set<String> setPseudo) {
        this.pseudo = pseudo;
        this.client = client;
        this.in = in;
        this.out = out;
        this.clients = clients;
        this.setPseudo = setPseudo;
    }

    // Principe du broadcast
    // Fait une boucle sur la liste des clients, récupère leur OutStream et envoie un message à tous les clients.
    private void broadcast(String msg) {
        for (PrintWriter w : clients.values()) {
            w.println(msg);
        }
    }

    @Override
    public void run() {
        try {
            broadcast(">>> " + pseudo + " a rejoint le chat");
            out.println("Bienvenue " + pseudo + " ! Tapez /quit pour quitter.");
            out.println("------------------------");
            out.flush();
            String line;
            while ((line = in.readLine()) != null) {
                if ("/quit".equalsIgnoreCase(line.trim())) {
                    out.println("Au revoir !");
                    System.out.println("Client déconnecté (" + pseudo + ") depuis port distant " + client.getPort());
                    break;
                }
                broadcast(pseudo + " a dit : " + line);
            }
        } catch (IOException e) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, "Client déconnecté", e);
        } finally {
            try {
                client.close(); // Libérer le socket.
                out.close(); // Libérer le flux Out.
                in.close(); // Libérer le flux In.
            } catch (IOException ignored) {}
            clients.remove(client); // On retire le client de la liste de clients connectés
            setPseudo.remove(pseudo);
            broadcast(pseudo + " a quitté la conversation" );
        }
    }
}
