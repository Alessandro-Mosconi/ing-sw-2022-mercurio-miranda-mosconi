package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.*;

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
    private boolean ready2go = false;

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    private NetworkHandler networkHandler;

    public GuiStarter getGuiStarter() {
        return guiStarter;
    }
    public void setGuiStarter(GuiStarter guiStarter) {
        this.guiStarter = guiStarter;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void preparelogin() {
        networkHandler.prepare_msg();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void login() {
        System.out.println("ciao ho stampato");
        player = new Player();
        Message msg_out = new Message();
        player.setNickName(username);
        setUsername(username);

        msg_out.setType(MessageType.CREATE_MATCH);
        setMessageType(MessageType.CREATE_MATCH);
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
