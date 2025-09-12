package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageReceptor extends Thread {
    private final ClientSocket clientSocket;
    byte b[]=new byte[1024];

    public MessageReceptor(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream in = clientSocket.getSocket().getInputStream();
            while (true) {
                int n = in.read(b);
                if (n>0) System.out.println(new String(b, 0, n, java.nio.charset.StandardCharsets.UTF_8));
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageReceptor.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
