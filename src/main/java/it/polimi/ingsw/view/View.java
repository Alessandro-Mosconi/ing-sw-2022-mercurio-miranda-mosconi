package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.NetworkHandler;
import it.polimi.ingsw.network.Phase;

import java.util.*;

public abstract class View {
    protected String username;
    private GuiStarter guiStarter;
    protected Phase phase;
    protected String idGame;
    protected GameMode gamemode;
    protected Integer playerNumber;
    protected Player player;
    protected ArrayList<String> playersUsername;
    protected ArrayList<Player> players;
    protected ArrayList<CloudTile> clouds = new ArrayList<>();
    protected IslandManager islandManager = new IslandManager(new ArrayList<>());
    protected ArrayList<CharacterCard> characterCards = new ArrayList<>();
    protected MessageType messageType;
    protected TowerColor towerColor;
    protected ArrayList<WizardType> wizards = new ArrayList<>(4){{
        this.addAll(Arrays.asList(WizardType.values()));
    }};
    protected ArrayList<TowerColor> towerColors = new ArrayList<>(3){{
        this.addAll(Arrays.asList(TowerColor.values()));
    }};
    protected AssistantCard chosenAssistantCard;
    protected CharacterCard chosenCharacterCard;
    protected PawnColor colorToMove;
    protected Integer destination;
    protected Integer MN_shift;
    protected Integer chosenCloudPos;
    protected Integer chosenIslandPos;
    protected String activeEffect;
    protected String serverIP;
    protected int serverPort;

    public GuiStarter getGuiStarter() {
        return guiStarter;
    }
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public void setNetworkHandler(NetworkHandler networkHandler) {
        this.networkHandler = networkHandler;
    }

    private NetworkHandler networkHandler;

    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    public String getServerIP() {
        return serverIP;
    }
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
    public void connect(){
    }
    public String getActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(String activeEffect) {
        this.activeEffect = activeEffect;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    protected Parameter parameter = new Parameter();

    public void setCardUsed(boolean cardUsed) {
        this.cardUsed = cardUsed;
    }
    protected boolean cardUsed = false;



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

    public abstract void showTable();
    public abstract void chooseMNmovement();

    public abstract void chooseCT();

    public void updateView() {

        setUpdated(true);
    }

    public abstract void showUsedAssistantCards();

    public void showEndGameWindow(String winnerID) {
    }

    public void prepareMessage() {
    }

    public void processScene() {
    }
}
