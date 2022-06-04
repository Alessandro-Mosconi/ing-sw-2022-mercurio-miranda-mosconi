package it.polimi.ingsw.network;

import it.polimi.ingsw.view.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
    private View view;
    private static boolean isMyTurn = true;

    public static void main(String[] args){

        Pattern pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        Scanner scanner = new Scanner(System.in);
        String ip = "127.0.0.1";
        int socketPort = 1234;
        boolean portFailed = true;
        boolean ipFailed = true;
        boolean viewFailed = true;

        /* Acquire the IP address from the user*/
        while(ipFailed){
            System.out.println("IP address of server?");
            ip = scanner.nextLine();

            if(pattern.matcher(ip).matches()) ipFailed = false;
            else System.out.println("Not valid IPv4 address for " + ip);
        }

        /* Acquire the number of Port from the user*/
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

        /* Open connection to the server. */
        Socket server;
        try {
            server = new Socket(ip, socketPort);
            server.setSoTimeout(10000);
            System.out.println("socket initialized");
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

        Pinger pinger = null;

        try (
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in))
        ) {
            //launch pinger thread
            pinger = new Pinger(out, server, "franco");
            Thread thread = new Thread(pinger, "clientPing" + server.getInetAddress());
            thread.start();
            String viewChoice=null;

            View view = null;

            while(viewFailed) {
                System.out.println("Do you prefer CLI version or GUI version? [c]/[g] ");
                viewChoice = stdIn.readLine();
                if(viewChoice.equals("C")||viewChoice.equals("c"))
                {
                    view = new CLI();
                    viewFailed=false;
                }
                else if(viewChoice.equals("G")||viewChoice.equals("g")){
                    System.out.println("non ancora implementata");
                    //view = new GUI();
                    viewFailed=false;
                }
                else viewFailed=true;
            }


            NetworkHandler networkHandler = new NetworkHandler(out, in, view);

            while (true) {
                    System.out.println("Ora leggo: ");
                    String input = in.readLine();
                    String output=null;

                    if (input != null)
                        if (!input.equals("ping")) {
                            System.out.println("processing...");
                            networkHandler.process(input);
                            if(view.isUpdated()){
                                view.setUpdated(false);
                                out.println("MODEL_UPDATED");
                            }
                            else {
                                output = networkHandler.send_msg();
                                System.out.println("Ora invio questo msg: "+ output);
                                out.println(output);
                            }
                        }
            }
        } catch (SocketTimeoutException e) {
            System.err.println("Server no more reachable " + ip);
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }

    try {
        pinger.stop();
        server.close();
    } catch (IOException e) {
        System.out.println("Error with the socket closing");
    }
}
}