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
    private Client client;
    private boolean ready2go = false;
    private Player playerSchoolboard;
    private String winnerUsername;

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
    }

    public Player getPlayerSchoolboard() {
        return playerSchoolboard;
    }

    public void setPlayerSchoolboard(Player playerSchoolboard) {
        this.playerSchoolboard = playerSchoolboard;
    }

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
    public void prepareMessage() {
        networkHandler.sendMessage();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void processScene(){
        System.out.println("La gui ha questa fase:" + phase);
        switch (phase){
            case SETTINGS -> {
                guiStarter.switchToWizardsScene();
            }
            case PLANNING -> {
                guiStarter.switchToDeckScene();
                guiStarter.switchToMainBoard();
            }
            case CHOOSING_FIRST_MOVE, CHOOSING_SECOND_MOVE, CHOOSING_THIRD_MOVE -> {
                System.out.println("Ora faccio partire la scelta");
                guiStarter.switchToMainBoard();
                guiStarter.choosePawnMove();
            }
            case CHOOSING_MN_SHIFT -> {
                guiStarter.switchToMainBoard();
                guiStarter.chooseMNmovement();
            }
            case CHOOSING_CT -> {
                guiStarter.switchToMainBoard();
                guiStarter.choseCT();
            }
        }
    }
    @Override
    public void login() {
        player = new Player();
        player.setNickName(username);
        setUsername(username);
        setIdGame(idGame);
        Message msg_out = new Message();

        if (messageType.equals(MessageType.JOIN_MATCH)){
            msg_out.setType(MessageType.JOIN_MATCH);
            setMessageType(MessageType.JOIN_MATCH);
        }
        else {
            msg_out.setType(MessageType.CREATE_MATCH);
            setMessageType(MessageType.CREATE_MATCH);
        }
    }


    /**
     * Shows the endgame window.
     * @param winnerID the winner's username.
     */
    public void showEndGameWindow(String winnerID){
        this.setWinnerUsername(winnerID);
        GuiStarter.getCurrentApplication().showEndGameWindow();
    }

    @Override
    public void settings() {
        player.getSchoolBoard().setTowersColor(towerColor);
    }

    @Override
    public void chooseAssistantCard() {
        player.setMaxShift(chosenAssistantCard.getMotherMovement());
        chosenAssistantCard.setConsumed(true);
        player.setLastAssistantCard(chosenAssistantCard);
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
        //todo qui dobbiamo mostrare le assistant card usate..
    }


}
