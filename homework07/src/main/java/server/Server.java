package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Server {

    public final static int POST = 5050;
    public final static String HOST = "localhost";


    public static void main(String[] args) throws IOException {
        new Server().startServer();
    }

    public void startServer() throws IOException {
        int i = 0;
        ServerSocket server = new ServerSocket(5050, 0, InetAddress.getByName(HOST));
        System.out.println("Server started");
        while (true) {
            new ClientRequestProcessor(server.accept(), i).start();
            i++;
        }
    }
}
