package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.Pinger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class GUI extends View{
    private GuiStarter guiStarter;
    private Object usernameMonitor = new Object();
    private Client client;

    public GuiStarter getGuiStarter() {
        return guiStarter;
    }
    public void setGuiStarter(GuiStarter guiStarter) {
        this.guiStarter = guiStarter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void login() {
        synchronized (usernameMonitor) {
            while (username == null) {
                try {
                    usernameMonitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //guiStarter.main(null);
        //username = String.valueOf(controller.getUsername());

        System.out.println("ciao ho stampato");

        player = new Player();
        Message msg_out = new Message();
        player.setNickName(username);
        setUsername(username);

    }

    public void endLogin(String username){
        this.username = username;
        usernameMonitor.notifyAll();
        return;
    }
    public void connect(){
        Socket server;

        try {
            server = new Socket(serverIP, serverPort);
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
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            //launch pinger thread
            pinger = new Pinger(out, server, "franco");
            Thread thread = new Thread(pinger, "clientPing" + server.getInetAddress());
            thread.start();

            NetworkHandler networkHandler = new NetworkHandler(out, in, this);
            networkHandler.start();
        }
        catch (SocketTimeoutException e) {
            System.err.println("Server no more reachable " + serverIP);
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverIP);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverIP);
            System.exit(1);
        }
    }
    @Override
    public void settings() {

    }

    @Override
    public void chooseAssistantCard() {

    }

    @Override
    public void choosePawnMove() {

    }

    @Override
    public void showTable() {

    }

    @Override
    public void chooseMNmovement() {

    }

    @Override
    public void chooseCT() {

    }

    @Override
    public void showUsedAssistantCards() {

    }
}
