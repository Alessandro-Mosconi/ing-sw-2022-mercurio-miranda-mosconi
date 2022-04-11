package it.polimi.ingsw.model;
import java.util.*;
public class Game {
    //attributes
    private ArrayList<Player> players;
    private Long numberOfPlayers;
    private String gameID;
    private GameMode gameMode;
    private CloudTile[] cloudTiles;
    private SchoolBoard[] schoolBoards;
    private IslandManager islandManager;
    protected Map<PawnColor, Integer> bag; //da vedere perché funziona solo se protected
    private ArrayList<CharacterCard> allCharacterCards; //dare dimensione quando si istanzia - "new ArrayList<charcards>(..dimensione..)"
    private ArrayList<Integer> playerOrder;
    private ArrayList<WizardType> wizards;
    private ArrayList<CharacterCard> chosenCharacterCards;
    private int bank;
//methods @Setter @Getter

    public void setGameMode(GameMode gamemode){
        this.gameMode=gamemode;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public void setGameID(String gameID){
        this.gameID=gameID;
    }
    public String getGameID() {
        return gameID;
    }
    public CloudTile[] getCloudTiles(){
        return cloudTiles;
    }
    public SchoolBoard[] getSchoolBoards(){
        return schoolBoards;
    }
    public IslandManager getIslandManager() {
        return islandManager;
    }
    public Map<PawnColor, Integer> getBag() {
        return bag;
    }

    public void setBank(int coins){
        this.bank=coins;
    }
    public int getBank(){
        return bank;
    }
    public ArrayList<Integer> getPlayerOrder() {
        return playerOrder;
    }
    public void setPlayerOrder(ArrayList<Integer> playerOrder) {
        this.playerOrder = playerOrder;
    }
    public void setWizards(ArrayList<WizardType> wizards) {
        this.wizards = wizards;
    }
    public ArrayList<WizardType> getWizards() {
        return wizards;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public Long getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public void setNumberOfPlayers(Long numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    public void setAllCharacterCards(ArrayList<CharacterCard> allCharacterCards) {
        this.allCharacterCards = allCharacterCards;
    }
    public void setIslandManager(IslandManager islandManager) {
        this.islandManager = islandManager;
    }
    public void setSchoolBoards(SchoolBoard[] schoolBoards) {
        this.schoolBoards = schoolBoards;
    }
    public void setCloudTiles(CloudTile[] cloudTiles) {
        this.cloudTiles = cloudTiles;
    }
    public void setChosenCharacterCards(ArrayList<CharacterCard> chosenCharacterCards) {
        this.chosenCharacterCards = chosenCharacterCards;
    }
    public ArrayList<CharacterCard> getChosenCharacterCards() {
        return chosenCharacterCards;
    }
//
    public void moveFromBagToCloud(PawnColor color, CloudTile cloud){
        //TODO
    }
    public void updateProfessor(PawnColor color){
        //TODO
    }
    public ArrayList<CharacterCard> shuffleCharacterCards(ArrayList<CharacterCard> allCharacterCards) {
        Collections.shuffle(allCharacterCards);
        return allCharacterCards;
    }
    public void SetUpGame(int numberOfPlayers, GameMode gamemode){
//TODO 1)allocare parti fisiche del gioco; 2) scegliere 1st player; 3)riempire cloud e isole; 4)randomchoosecharactercards;
    }
    public Player SelectFirstPlayer(){
        int min_val=0;
        int max_val=players.size();
        Random rd=new Random();
        int randNum=min_val+rd.nextInt((max_val - min_val) + 1);
        return players.get(randNum);
    }
    public void setBag(PawnColor color, int n){
        this.bag.replace(color,n);
    }
    public ArrayList<CharacterCard> initChosenCharacterCards() {
        return (ArrayList<CharacterCard>) getAllCharacterCards().subList(0,2);
    }

    public ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards(allCharacterCards);
    }
}
