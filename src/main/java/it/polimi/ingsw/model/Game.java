package it.polimi.ingsw.model;
import java.util.*;
public class Game {
    //attributes
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private String gameID;
    private GameMode gameMode;
    private ArrayList<CloudTile> cloudTiles;
    private ArrayList<SchoolBoard> schoolBoards;
    private IslandManager islandManager;
    private Map<PawnColor, Integer> bag;
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
    public void setBag(Map<PawnColor,Integer> bag){
        this.bag=bag;
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
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
    public void setNumberOfPlayers(int numberOfPlayers) {
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
    }
    public void updateProfessor(PawnColor color){
        int currentMax=0;
        /*
        si potrebbe tenere in game una mappa che tenga traccia del numero di studenti che ha il player
        che possiede già il prof in modo da effettuare la update solo quando serve (?) piuttosto che farlo
        per ogni studente spostato
        */
        for (SchoolBoard s : schoolBoards){
            if(s.getStudentHall().get(color)>currentMax){
                currentMax=s.getStudentHall().get(color);
                for(SchoolBoard f : schoolBoards){
                    f.getProfessorTable().replace(color,false);
                }
                s.getProfessorTable().replace(color,true);
            }
        }
    }
    public ArrayList<CharacterCard> shuffleCharacterCards(ArrayList<CharacterCard> allCharacterCards) {
        Collections.shuffle(allCharacterCards);
        return allCharacterCards;
    }
    public void SetUpGame(int numberOfPlayers, GameMode gamemode)/*gamemode e noOfPlayers si prendono dal controller?*/{
        this.setNumberOfPlayers(numberOfPlayers);
        this.setGameMode(gamemode);
        //this.gameID=setGameID(); randomico
        ArrayList<CloudTile> cloudTiles = new ArrayList<CloudTile>(numberOfPlayers);
        {
            for(int i=0;i<numberOfPlayers;i++){
                cloudTiles.add(new CloudTile());
            }
        }
        {
            for(CloudTile c : cloudTiles){
                for(int i=0;i<3;i++){
                    moveFromBagToCloud(c);
                }
                if(numberOfPlayers==3){
                    moveFromBagToCloud(c);
                }
            }
        }
        ArrayList<SchoolBoard> schoolBoards= new ArrayList<SchoolBoard>(numberOfPlayers);
        {
            for(int i=0;i<numberOfPlayers;i++){
                schoolBoards.add(new SchoolBoard());
            }
        }
        ArrayList<Island> islands = new ArrayList<Island>(12);
        {
            {
                for(int i=0;i<12;i++){
                    islands.add(new Island());
                     if(i!=0&&i!=5){
                         PawnColor rdColor=PawnColor.randomColor();
                         this.bag.replace(rdColor, this.bag.get(rdColor)-1);
                         islands.get(i).addStudent(rdColor);
                    }//initializes islands with students
                     else if(i==0){
                         islands.get(i).setMotherNature(true);
                     }//initializes mothernature
                }
            }
        }
        IslandManager islandManager= new IslandManager(islands);
        ArrayList<Player> players = new ArrayList<Player>(numberOfPlayers);
        {
            for(int i=0;i<numberOfPlayers;i++){
                players.add(new Player());
            }
        }
        Player firstPlayer = SelectFirstPlayer();
        /*Iterator iterator = players.iterator();
        while(iterator.next()!=firstPlayer){
            iterator.next();
        }*/ //TODO
        ArrayList<WizardType> wizards = new ArrayList<WizardType>(numberOfPlayers);{
            for(Player p : players) {
                wizards.add(p.getDeck().getWizard());
            }
        }
        for(PawnColor color : PawnColor.values()){
            this.setBag(color, 26);
        }//setta la bag ai valori di default
        if(gamemode.equals(GameMode.expert)){
            ArrayList<CharacterCard> chosenCharacterCards = initChosenCharacterCards();
            this.setChosenCharacterCards(chosenCharacterCards);
            this.setBank(20);
        }
/*TODO
 1)allocare parti fisiche del gioco (cloudsX, islandsX, bagX, schoolboardsX, decks)
 2) scegliere 1st playerX;
 3)riempire cloudX e isoleX;
 */
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
    }
    public ArrayList<CharacterCard> initChosenCharacterCards() {
        return (ArrayList<CharacterCard>) getAllCharacterCards().subList(0,2);
    }
    public ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards(allCharacterCards);
    } /* restituisce tutte le charcards in disordine;
     quando si chiama la init si pescano le prime 3 in disordine;
     dopo la init si accede alle chosen tramite la get*/
    public void updatePlayerOrder(){
        //todo comparator
    }
}