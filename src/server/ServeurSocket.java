package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServeurSocket {

    private ServerSocket socket;

    public ServeurSocket(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public ServerSocket getSocket() {
        if (socket != null) {
            return socket;
        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        try {
            ServeurSocket serveurSocket = new ServeurSocket(10080); // socket conn
            ClientHandler clientHandler = new ClientHandler(serveurSocket);
            System.out.println("Listening at " + serveurSocket.socket.getLocalPort());
            clientHandler.start();

        } catch (IOException e){
            Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, e);        }
    }


}
