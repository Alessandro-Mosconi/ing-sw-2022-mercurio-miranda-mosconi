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
    private ArrayList<String> playersUsername;
    private ArrayList<Player> players;
    private ArrayList<CloudTile> clouds;
    private IslandManager islandManager;
    private ArrayList<CharacterCard> characterCards;
    private MessageType messageType;
    private TowerColor towerColor;
    private ArrayList<WizardType> wizards = new ArrayList<>(4){{
        this.addAll(Arrays.asList(WizardType.values()));
    }};
    private ArrayList<TowerColor> towerColors;
    private AssistantCard chosenAssistantCard;
    private CharacterCard chosenCharacterCard;
    private PawnColor colorToMove;
    private Integer destination;
    private Integer MN_shift;
    private Integer chosenCloudPos;
    private Integer chosenIslandPos;




    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    private boolean updated = false;
/*
    public View(){

        playerNumber = 0;

        wizards = new ArrayList<WizardType>();
        for(WizardType w : WizardType.values()){
            wizards.add(w);
        }
        towerColors = new ArrayList<>();
        for(TowerColor t : TowerColor.values()){
            towerColors.add(t);
        }
    }

 */

    public Integer getChosenIslandPos() {
        return chosenIslandPos;
    }

    public void setChosenIslandPos(Integer chosenIslandPos) {
        this.chosenIslandPos = chosenIslandPos;
    }

    public Integer getChosenCloudPos() {
        return chosenCloudPos;
    }

    public void setChosenCloudPos(Integer chosenCloudPos) {
        this.chosenCloudPos = chosenCloudPos;
    }

    public Integer getMN_shift() {
        return MN_shift;
    }

    public void setMN_shift(Integer MN_shift) {
        this.MN_shift = MN_shift;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public PawnColor getColorToMove() {
        return colorToMove;
    }

    public void setColorToMove(PawnColor colorToMove) {
        this.colorToMove = colorToMove;
    }

    public CharacterCard getChosenCharacterCard() {
        return chosenCharacterCard;
    }

    public void setChosenCharacterCard(CharacterCard chosenCharacterCard) {
        this.chosenCharacterCard = chosenCharacterCard;
    }

    public AssistantCard getChosenAssistantCard() {
        return chosenAssistantCard;
    }

    public void setChosenAssistantCard(AssistantCard chosenAssistantCard) {
        this.chosenAssistantCard = chosenAssistantCard;
    }

    public void removeTowerColor(TowerColor towerColor){
        this.towerColors.remove(towerColor);
    }

    public void removeWizard(WizardType wizard){
        this.wizards.remove(wizard);
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    public ArrayList<String> getPlayersUsername() {
        return playersUsername;
    }

    public void setPlayersUsername(ArrayList<String> playersUsername) {
        this.playersUsername = playersUsername;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public void setCharacterCards(ArrayList<CharacterCard> characterCards) {
        this.characterCards = characterCards;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /*
    public ArrayList<SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }

    public void setSchoolBoards(ArrayList<SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
    }

     */

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

    public abstract void login();

    public abstract void settings();

    public abstract void chooseAssistantCard();

    public abstract void choosePawnMove();

    /*public Message waiting(){
        return null;
    }*/

    //public void mainboard();

    //public abstract Message chooseWizard();

    public abstract void chooseMNmovement();

    public abstract void chooseCT();

    public void updateView() {

        setUpdated(true);
    }
}
