package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInputHandler extends Thread {
    private final ClientSocket clientSocket;
    public UserInputHandler(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // On utilise un OutPutStream de type PrintWrite, plus simple pour gérer les bytes.
            // La gestion du pseudo est laissée du côté serveur, c'est lui qui enregistre le pseudo et le vérifie.
            PrintWriter out = new PrintWriter(clientSocket.getSocket().getOutputStream(), true); // auto-flush
            Scanner input = new Scanner(System.in,  "UTF-8");
            while (true) {
                String message = input.nextLine();
                if ("/quit".equalsIgnoreCase(message)) {
                    out.println("/quit");
                    clientSocket.getSocket().close();
                    break;
                }
                // Laisse le serveur préfixer avec le pseudo
                out.println(message);            }
        } catch (IOException e) {
            Logger.getLogger(UserInputHandler.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
