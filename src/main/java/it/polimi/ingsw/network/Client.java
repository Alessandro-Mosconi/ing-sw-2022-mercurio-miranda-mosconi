package it.polimi.ingsw.network;

import com.google.gson.*;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.*;
import it.polimi.ingsw.networkhandler.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) throws IOException {

        String hostName = "127.0.0.1";
        int portNumber = 1234;

        Message msg = new Message("Franco", MessageType.CREATE_MATCH, "Game1");

        System.out.println(msg.toSend());

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));

        ) {
            while (true) {

                out.println(msg.toSend());

                String input = in.readLine();
                System.out.println(input);

                String end;
                end = stdIn.readLine();
                if(end.equalsIgnoreCase("quit"))
                    out.println(end);

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
