package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.lang.reflect.Array;
import java.util.*;

public abstract class View {
    private String username;
    private String idGame;
    private GameMode gamemode;
    private Integer playerNumber;
    private Player player;
    private ArrayList<String> players;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> clouds;
    private IslandManager islandManager;
    private Set<CharacterCard> characterCards;
    private MessageType messageType;
    private ArrayList<WizardType> wizards;
    private ArrayList<TowerColor> towerColors;

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    private boolean updated = false;

    public View(){
        wizards = new ArrayList<WizardType>();
        for(WizardType w : WizardType.values()){
            wizards.add(w);
        }
        towerColors = new ArrayList<>();
        for(TowerColor t : TowerColor.values()){
            towerColors.add(t);
        }
    }

    public ArrayList<TowerColor> getTowerColors() {
        return towerColors;
    }

    public void setTowerColors(ArrayList<TowerColor> towerColors) {
        this.towerColors = towerColors;
    }
    public ArrayList<WizardType> getWizards() {
        return wizards;
    }

    public void setWizards(ArrayList<WizardType> wizards) {
        this.wizards = wizards;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        System.out.println(this.username);
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Set<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public void setCharacterCards(Set<CharacterCard> characterCards) {
        this.characterCards = characterCards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public void setSchoolBoards(ArrayList<SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
    }

    public ArrayList<CloudTile> getClouds() {
        return clouds;
    }

    public void setClouds(ArrayList<CloudTile> clouds) {
        this.clouds = clouds;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }

    public void setIslandManager(IslandManager islandManager) {
        this.islandManager = islandManager;
    }

    public abstract Message login();

    public abstract Message settings();

    public abstract Message chooseAssistantCard();

    public abstract Message choosePawnMove();

    /*public Message waiting(){
        return null;
    }*/

    //public void mainboard();

    //public abstract Message chooseWizard();

    public abstract Message chooseMNmovement();

    public abstract Message chooseCT();

    public void updateView() {

        setUpdated(true);
    }
}
