package it.polimi.ingsw.model;
import java.util.*;

public class Game {
//attributes
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private int gameID;
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
    public void setGameID(int gameID){
        this.gameID=gameID;
    }
    public int getGameID() {
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
    public void setIslandManager(IslandManager islandManager) {
        this.islandManager = islandManager;
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
    public void setChosenCharacterCards(ArrayList<CharacterCard> chosenCharacterCards) {
        this.chosenCharacterCards = chosenCharacterCards;
    }
    public ArrayList<CharacterCard> getChosenCharacterCards() {
        return chosenCharacterCards;
    }
//other methods
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
        che possiede già il prof in modo da effettuare la update solo quando serve piuttosto che farlo
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
        fillBag();
        /* generatore randomico ID del game (?)
        Random rn = new Random();
        int n = 100000 - 0 + 1;
        int j = rn.nextInt() % n;
        this.setGameID(j); */
        ArrayList<CloudTile> cloudTiles = generateCloudTiles(numberOfPlayers);
        fillCloudTiles(numberOfPlayers, cloudTiles);
        ArrayList<Island> islands = generateIslands();
        IslandManager islandManager= new IslandManager(islands);
        ArrayList<Player> players = generatePlayers(numberOfPlayers);

            //todo inizializzare i players

        ArrayList<SchoolBoard> schoolBoards = generateSchoolBoards(numberOfPlayers);
        initSchoolBoards(schoolBoards);
        Player firstPlayer = SelectFirstPlayer();
        ArrayList<Integer> tmpOrder = new ArrayList<Integer>(numberOfPlayers);
        for(int i=0;i<numberOfPlayers;i++){
            tmpOrder.add(firstPlayer.getPlayerNumber()+i);
        }
        this.setPlayerOrder(tmpOrder);
        ArrayList<WizardType> wizards = new ArrayList<WizardType>(numberOfPlayers);{
            for(Player p : players) {
                wizards.add(p.getDeck().getWizard());
            }
        }
        if(gamemode.equals(GameMode.expert)){
            ArrayList<CharacterCard> chosenCharacterCards = initChosenCharacterCards();
            this.setChosenCharacterCards(chosenCharacterCards);
            this.setBank(20);
        }
    }
    public void updatePlayerOrder(){
        ArrayList<Player> clonePlayerArray = this.players;
        clonePlayerArray.sort(new Comparator<Player>() {
            @Override
            public int compare(Player i1, Player i2) {
                return i1.getLastAssistantCard().getValue() - i2.getLastAssistantCard().getValue();
            }
        });
        ArrayList<Integer> tmpOrder = new ArrayList<Integer>(numberOfPlayers);
        for(int i=0;i<numberOfPlayers;i++){
            tmpOrder.add(clonePlayerArray.get(i).getPlayerNumber());
        }
        this.setPlayerOrder(tmpOrder);
        //dovrebbe andare ma non sono sicurissimo
    }
//setupGame extractions
    private ArrayList<Island> generateIslands() {
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
        return islands;
    }
    private ArrayList<SchoolBoard> generateSchoolBoards(int numberOfPlayers) {
        ArrayList<SchoolBoard> schoolBoards= new ArrayList<SchoolBoard>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                schoolBoards.add(new SchoolBoard());
            }
        }
        return schoolBoards;
    }
    private void fillCloudTiles(int numberOfPlayers, ArrayList<CloudTile> cloudTiles) {
        for(CloudTile c : cloudTiles){
            for(int i=0;i<3;i++){
                moveFromBagToCloud(c);
            }
            if(numberOfPlayers ==3){
                moveFromBagToCloud(c);
            }
        }
    }
    private ArrayList<CloudTile> generateCloudTiles(int numberOfPlayers) {
        ArrayList<CloudTile> cloudTiles = new ArrayList<CloudTile>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                cloudTiles.add(new CloudTile());
            }
        }
        return cloudTiles;
    }
    private void fillBag() {
        for(PawnColor color : PawnColor.values()){
            this.initBag(color, 26);
        }//setta la bag ai valori di default
    }
    private ArrayList<Player> generatePlayers(int numberOfPlayers) {
        ArrayList<Player> players = new ArrayList<Player>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                players.add(new Player());
            }
        }
        return players;
    }
    private void initSchoolBoards(ArrayList<SchoolBoard> schoolBoards) {
        for(SchoolBoard s: schoolBoards){
            if(this.numberOfPlayers==2){
                s.setTowersNumber(8);
            }
            if(this.numberOfPlayers==3){
                s.setTowersNumber(6);
            }
            if(this.numberOfPlayers==4){
                s.setTowersNumber(4);
            }
            for(int i=0;i<7;i++){
                PawnColor rdColor=PawnColor.randomColor();
                this.bag.replace(rdColor, this.bag.get(rdColor)-1);
                s.addStudentEntrance(rdColor);
            }
            //manca da settare il colore delle torri
        }
    }
    private Player SelectFirstPlayer(){
        int min_val=0;
        int max_val=players.size();
        Random rd=new Random();
        int randNum=min_val+rd.nextInt((max_val - min_val) + 1);
        return players.get(randNum);
    }//genera casualmente il primo giocatore che lancia la carta assistente al primo turno
    private void initBag(PawnColor color, int n){
        this.bag.replace(color,n);
    }
    private ArrayList<CharacterCard> initChosenCharacterCards() {
        return (ArrayList<CharacterCard>) getAllCharacterCards().subList(0,2);
    }
    private ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards(allCharacterCards);
    } /* restituisce tutte le charcards in disordine;
     quando si chiama la init si pescano le prime 3 in disordine;
     dopo la init si accede alle chosen tramite la get*/


}
