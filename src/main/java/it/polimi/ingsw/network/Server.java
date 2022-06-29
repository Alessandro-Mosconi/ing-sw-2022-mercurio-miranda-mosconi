package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;


public class Server {

    /**
     * Opens a socket and accepts clients.
     * @param args -
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int socketPort = 1234;
        boolean portFailed = true;

        while(portFailed) {
            try {
                System.out.println("Server port?");
                socketPort = Integer.parseInt(scanner.nextLine());
                portFailed = false;
                if (socketPort >= 65535 || socketPort <= 0) {
                    portFailed = true;
                    System.out.println("port must be an Integer in the range [1, 65535]: " + socketPort + " is out of bound");
                }

                } catch (IllegalArgumentException e) {
                System.out.println("port must be an Integer in the range [1, 65535]: " + e);
                }
        }


        ServerSocket socket;
        try {
            socket = new ServerSocket(socketPort);
            System.out.println("Server socket initialized");
        } catch (IOException e){
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        int i=0;

        while (true) {
            try {
                i++;
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                client.setSoTimeout(10000);
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "server_" + i + "] " + client.getInetAddress());
                thread.start();

            } catch (SocketTimeoutException e) {
                System.out.println("connection lost "+ e);
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
