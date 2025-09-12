package client;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket {

    private Socket socket;

    public ClientSocket(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) {

        try {
            ClientSocket client = new ClientSocket("127.0.0.1", 10080);
            MessageReceptor messageReceptor = new MessageReceptor(client);
            UserInputHandler userInputHandler = new UserInputHandler(client);
            messageReceptor.start(); // On lance le thrad pour capter l'output
            userInputHandler.start(); // On lance le thread pour capter l'input
        } catch (IOException e) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, e);
        }


    }

}
