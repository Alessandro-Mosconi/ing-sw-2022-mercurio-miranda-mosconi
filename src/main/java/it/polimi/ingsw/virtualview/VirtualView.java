package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Message;

import java.util.ArrayList;
import java.util.Set;

public class VirtualView {

    private Message msg_in;
    private String username;
    private String idGame;
    private GameMode gamemode;
    private Integer playerNumber;
    private Player player;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> clouds;
    private IslandManager islandManager;
    private Set<CharacterCard> characterCards;
    private boolean online = true;
    private ArrayList<String> players;

    public Message getMsg_in() { return msg_in; }

    public void setMsg_in(Message msg_in) { this.msg_in = msg_in; }

    public ArrayList<String> getPlayers() { return players; }

    public void setPlayers(ArrayList<String> players) { this.players = players; }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public Integer getPlayerNumber() { return playerNumber; }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void sendAnswer(){} //da implementare

    public void read(String input){

        Gson gson = new Gson();
        Message msg = gson.fromJson(input, Message.class);

        this.msg_in = msg;

    }



    //private ArrayList<Player> players;
    //private GameMode chosenGameMode;

    //todo compito della virtual view è di prendere i player dal network handler.
    /*public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
*/
    public Player askForPlayerData() {
        Player player= new Player();
        //todo deve chiedere ad ogni client di inserire tutti i dati richiesti (username, colore e mago)
        return player;
    }

    public AssistantCard askForAssistantCard() {
        AssistantCard chosenAssistantCard = new AssistantCard();
        //todo chiede, riceve e dejsonizza la scelta
        return chosenAssistantCard;
    }

    public void askForMovement(){
    }

    //todo modificare appena facciamo la virtualView, ofc
    public int askForMNMovement(){
        return 1;
    }

    //todo modificare appena facciamo la virtualView, ofc 2
    public int askForCloudTile(){
        return 1;
    }
}
