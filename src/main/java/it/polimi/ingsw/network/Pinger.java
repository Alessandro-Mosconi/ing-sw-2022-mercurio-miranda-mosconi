package it.polimi.ingsw.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Pinger implements Runnable
{
    private PrintWriter out;
    Socket socket;
    String name;
    boolean exit;

    public Pinger(PrintWriter out, Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        this.out = out;
        this.exit = false;
    }

    @Override
    public void run() {

        try {

            while(!exit)
            {
                out.println("ping");
                //System.out.println(name + " ping sent");
                Thread.sleep(5000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[" + socket.getInetAddress() + "] " + ">> Ping terminato <<");
    }

    public void stop()
    {
        exit = true;
    }
}
