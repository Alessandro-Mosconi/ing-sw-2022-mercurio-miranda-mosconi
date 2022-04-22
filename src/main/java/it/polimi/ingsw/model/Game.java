package it.polimi.ingsw.model;
import java.lang.reflect.Array;
import java.util.*;
public class Game {
    //attributes
    private ArrayList<Player> players;
    private Long numberOfPlayers;
    private String gameID;
    private GameMode gameMode;
    private ArrayList<CloudTile> cloudTiles;
    private ArrayList<SchoolBoard> schoolBoards;
    private IslandManager islandManager;
    protected Map<PawnColor, Integer> bag; //da vedere perché funziona solo se protected
    private ArrayList<CharacterCard> allCharacterCards; /*dare dimensione quando si istanzia
     "new ArrayList<charcards>(..dimensione..)"*/
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
    public ArrayList<CloudTile> getCloudTiles() {
        return cloudTiles;
    }
    public void setCloudTiles(ArrayList<CloudTile> cloudTiles) {
        this.cloudTiles = cloudTiles;
    }
    public ArrayList<SchoolBoard> getSchoolBoards() {
        return schoolBoards;
    }
    public void setSchoolBoards(ArrayList<SchoolBoard> schoolBoards) {
        this.schoolBoards = schoolBoards;
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
    public void setChosenCharacterCards(ArrayList<CharacterCard> chosenCharacterCards) {
        this.chosenCharacterCards = chosenCharacterCards;
    }
    public ArrayList<CharacterCard> getChosenCharacterCards() {
        return chosenCharacterCards;
    }
//
    public void moveFromBagToCloud(CloudTile cloud){
        PawnColor rdColor=PawnColor.randomColor();
        if(this.bag.get(rdColor)==0){
            while(this.bag.get(rdColor)==0){
                rdColor=PawnColor.randomColor();
            }
        }//controllo che la bag abbia disponibilità di studenti del colore random
        this.bag.replace(rdColor, this.bag.get(rdColor)-1);
        cloud.getStudents().replace(rdColor, cloud.getStudents().get(rdColor)+1);
        //manca implementazione della classe cloudtiles -> l'errore dovrebbe risolversi quando la si implementa.
    }
    public void updateProfessor(PawnColor color){
        /*int max=-1;
        for (SchoolBoard p : schoolBoards){
            controllo max player
        }*/
        //TODO update prof
        /*settare professorTable di tutti i player*/
    }
    public ArrayList<CharacterCard> shuffleCharacterCards(ArrayList<CharacterCard> allCharacterCards) {
        Collections.shuffle(allCharacterCards);
        return allCharacterCards;
    }
    public void SetUpGame(int numberOfPlayers, GameMode gamemode){
        ArrayList<CloudTile> cloudTiles = new ArrayList<CloudTile>(numberOfPlayers);
        ArrayList<SchoolBoard> schoolBoards= new ArrayList<SchoolBoard>(numberOfPlayers);
        IslandManager islandManager= new IslandManager();
        Player firstPlayer = new Player();
        firstPlayer=SelectFirstPlayer();
        for(PawnColor color : PawnColor.values()){
            this.setBag(color, 26);
        }//setta la bag ai valori di default

        if(gamemode.equals(GameMode.expert)){
            initChosenCharacterCards();
            setBank(20);
        }
//TODO 1)allocare parti fisiche del gioco (clouds, islands, bag, schoolboards, decks)
// 2) scegliere 1st player;
// 3)riempire cloud e isole;
    }
    public Player SelectFirstPlayer(){
        int min_val=0;
        int max_val=players.size();
        Random rd=new Random();
        int randNum=min_val+rd.nextInt((max_val - min_val) + 1);
        return players.get(randNum);
    }//genera casualmente il primo giocatore che lancia la carta assistente al primo turno
    public void setBag(PawnColor color, int n){
        this.bag.replace(color,n);
    //di default setta a 26 ogni color
    }
    public ArrayList<CharacterCard> initChosenCharacterCards() {
        return (ArrayList<CharacterCard>) getAllCharacterCards().subList(0,2);
    }
    public ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards(allCharacterCards);
    } /* restituisce tutte le charcards in disordine;
     quando si chiama la init si pescano le prime 3 in disordine;
     dopo la init si accede alle chosen tramite la get*/



}
