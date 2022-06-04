package it.polimi.ingsw.model;
import java.util.*;

public class Game {


    //attributes
    private ArrayList<Player> players = new ArrayList<>();
    private int currPlayer;
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
    private boolean towersDoCount=true;
    private PawnColor keptOut=null;
    private boolean started;
    private int entryTiles;
    private List<ModelListener> clientHandlersListeners = new ArrayList<ModelListener>();

    public ArrayList<WizardType> getAvailableWizards() {
        return availableWizards;
    }

    public void setAvailableWizards(ArrayList<WizardType> availableWizards) {
        this.availableWizards = availableWizards;
    }

    public ArrayList<TowerColor> getAvailableTowerColors() {
        return availableTowerColors;
    }

    public void setAvailableTowerColors(ArrayList<TowerColor> availableTowerColors) {
        this.availableTowerColors = availableTowerColors;
    }

    private ArrayList<WizardType> availableWizards = new ArrayList<>() {{
        addAll(List.of(WizardType.values()));
    }};

    private ArrayList<TowerColor> availableTowerColors = new ArrayList<>(){{
        addAll(List.of(TowerColor.values()));
    }};

    public ArrayList<CharacterCard> getAllCharacterCard(){
        return this.allCharacterCards;
    }

    public Player getPlayerByUsername(String username){
        for (Player player : getPlayers())
        {
            if(player.getNickName().equals(username))
                return player;
        }

        System.out.println("player non trovato");
        return null;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Game() {
        this.players = new ArrayList<>();
        this.currPlayer = 0;
        this.numberOfPlayers =0;
        this.gameID = "0";
        this.gameMode =null;
        this.cloudTiles =new ArrayList<>();
        this.schoolBoards=new ArrayList<>();
        this.islandManager = new IslandManager(new ArrayList<Island>());
        this.bag= new HashMap<>();
        this.allCharacterCards=new ArrayList<>();
        this.players=new ArrayList<>();
        this.wizards=new ArrayList<>();
        this.chosenCharacterCards=new ArrayList<>();
        this.bank=20;
        this.started=false;
        this.entryTiles=4;
        this.clientHandlersListeners=new ArrayList<>();
    }

    public Game(int numberOfPlayers, String idGame, GameMode gameMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameID = idGame;
        this.gameMode = gameMode;
        this.started = false;
    }

    //methods @Setter @Getter

    public int getEntryTiles() {
        return entryTiles;
    }

    public void setEntryTiles(int entryTiles) {
        this.entryTiles = entryTiles;
    }

    public boolean isTowersDoCount() {
        return towersDoCount;
    }
    public void setTowersDoCount(boolean towersDoCount) {
        for(Island i: this.getIslandManager().getIslandList()){
            i.setTowersDoCount(towersDoCount);
        }
    }
    public PawnColor getKeptOut() {
        return keptOut;
    }
    public void setKeptOut(PawnColor keptOut) {
        for(Island i : this.getIslandManager().getIslandList()){
            i.setKeptOut(keptOut);
        }
    }
    public void setGameMode(GameMode gamemode){
        this.gameMode=gamemode;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public int getCurrPlayer() {
        return currPlayer;
    }
    public void setCurrPlayer(int currPlayer) {
        this.currPlayer = currPlayer;
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
    public ArrayList<Player> getPlayers() {
        return players;
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
        if(!isBagEmpty()){
            PawnColor rdColor=PawnColor.randomColor();
            if(this.bag.get(rdColor)==0){
                while(this.bag.get(rdColor)==0){
                    rdColor=PawnColor.randomColor();
                }
            }//controllo che la bag abbia disponibilità di studenti del colore random
            this.bag.replace(rdColor, this.bag.get(rdColor)-1);
            cloud.getStudents().replace(rdColor, cloud.getStudents().get(rdColor)+1);
        }
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
    public ArrayList<CharacterCard> shuffleCharacterCards() {
        Collections.shuffle(allCharacterCards);
        return allCharacterCards;
    }
    public void setupGame(){
        //players = new ArrayList<>(); //qui vengono aggiunti man mano dal controller
        for(ModelListener l : clientHandlersListeners){
            l.updatePlayers(players);
        }
        bag = new HashMap<>();
        fillBag();
        //availableTowerColors.addAll(List.of(TowerColor.values()));
        //availableWizards.addAll(List.of(WizardType.values()));
        ArrayList<Island> islands = generateIslands(); //generates and initialises the islands
        IslandManager islandManager= new IslandManager(islands);
        this.setIslandManager(islandManager);
        for(ModelListener l : clientHandlersListeners){
            l.updateIslandList(islands);
        }
        //ArrayList<Player> players = generatePlayers(this.numberOfPlayers);
        //this.setPlayers(players);
        //inizializzare i players - probabilmente verrà fatto dal controller che darà in input al setupGame l'array di players da settare
        ArrayList<SchoolBoard> schoolBoards = generateSchoolBoards();
        initSchoolBoards();
        this.setSchoolBoards(schoolBoards);
        for(ModelListener l : clientHandlersListeners){
            for(Player p : players){
                l.updateSchoolBoardEntrance(p);
            }
        }
        ArrayList<CloudTile> cloudTiles = generateCloudTiles();
        for(ModelListener l : clientHandlersListeners){
            for(CloudTile ct: cloudTiles) {
                l.updateCT(ct);
            }
        }
        //Clouds are filled after the player order is set
        //Game board parts are allocated and initialized so far
        /*Player firstPlayer = SelectFirstPlayer(); //Randomically select the first player to choose an AssistantCard
        ArrayList<Integer> tmpOrder = new ArrayList<>(this.numberOfPlayers);
        for(int i=0;i<this.numberOfPlayers;i++){
            tmpOrder.add(firstPlayer.getPlayerNumber()+i % this.numberOfPlayers);
        }
        this.setPlayerOrder(tmpOrder);*/
        //During the first phase, the player order follows a clockwise order
        /*ArrayList<WizardType> wizards = new ArrayList<>(numberOfPlayers);{
            for(Player p : players) {
                wizards.add(p.getDeck().getWizard());
            }
        }*/
       // this.setWizards(wizards);//il mago viene scelto nel setup del controller
        //fillCloudTiles();  vengono riempite nel SETUPSTATE dopo il playerOrder
        if(gameMode.equals(GameMode.expert)){
            initAllCharacterCards(); //Allocates all of the CharacterCards
            ArrayList<CharacterCard> chosenCharacterCards = initChosenCharacterCards(); //Takes a sublist from the randomized CharacterCards list
            this.setChosenCharacterCards(chosenCharacterCards);
            for(CharacterCard cc: chosenCharacterCards){
                cc.initializeCard(new Parameter(this));
            }//Initializes the chosen card
            this.setEntryTiles(4);
            this.setBank(20);
        }
        /*for(ModelListener l : clientHandlersListeners){
            l.updateModel(this);
        }*/
    }
    public void updatePlayerOrder(){
        ArrayList<Integer> tmpOrder = calculatePlayerOrder();
        this.setPlayerOrder(tmpOrder);

    }
    public ArrayList<Integer> calculatePlayerOrder(){
        ArrayList<Player> clonePlayerArray = this.players;
        clonePlayerArray.sort(new Comparator<>() {
            public int compare(Player i1, Player i2) {
                return i1.getLastAssistantCard().getValue() - i2.getLastAssistantCard().getValue();
            }
        });
        ArrayList<Integer> tmpOrder = new ArrayList<>(numberOfPlayers);
        for(int i=0;i<numberOfPlayers;i++){
            tmpOrder.add(clonePlayerArray.get(i).getPlayerNumber());
        }
        return tmpOrder;
    }//dovrebbe andare ma non sono sicurissimo
//setupGame extractions
    private ArrayList<Island> generateIslands() {
        ArrayList<Island> islands = new ArrayList<>(12);
        {
            {
                Map<PawnColor,Integer> islandInitializationBag = new HashMap<>();
                for(PawnColor col : PawnColor.values()){
                    islandInitializationBag.put(col,2);
                    this.bag.replace(col,this.bag.get(col)-2);
                }
                for(int i=0;i<12;i++){
                    islands.add(new Island(null,null,0,false,false));
                    if(i!=0&&i!=5){
                        PawnColor rdColor=PawnColor.randomColor();
                        islandInitializationBag.replace(rdColor, islandInitializationBag.get(rdColor)-1);
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
    private ArrayList<SchoolBoard> generateSchoolBoards() {
        ArrayList<SchoolBoard> schoolBoards = new ArrayList<>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                schoolBoards.add(new SchoolBoard());
            }
        }
        return schoolBoards;
    }
    public void fillCloudTiles() {
        for(CloudTile c : this.cloudTiles){
            for(int i=0;i<3;i++){
                moveFromBagToCloud(c);
            }
            if(this.numberOfPlayers == 3){
                moveFromBagToCloud(c);
            }
            for(ModelListener l : clientHandlersListeners){
                l.updateCT(c);
            }
        }

    }
    private ArrayList<CloudTile> generateCloudTiles() {
        ArrayList<CloudTile> cloudTiles = new ArrayList<>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                cloudTiles.add(new CloudTile());
            }
        }
        return cloudTiles;
    }
    private void fillBag() {
        for(PawnColor color : PawnColor.values()){
            this.initBag(color,26);
        }//setta la bag ai valori di default
    }
    /*
    private ArrayList<Player> generatePlayers(int numberOfPlayers) {
        ArrayList<Player> players = new ArrayList<>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                players.add(new Player());
            }
        }
        return players;
    } da gestire nel controller*/
    private void initSchoolBoards() {
         for(SchoolBoard s: schoolBoards){
            /*if(this.numberOfPlayers==2){
                s.setTowersNumber(8);
            }
            if(this.numberOfPlayers==3){
                s.setTowersNumber(6);
            }
            if(this.numberOfPlayers==4){
                s.setTowersNumber(4);}*/
            //qui va modificato -- todo valutare di aggiungere un attributo che indichi squadra al player e ricordarsi di cambiare le torri qunado si gioca in 4
            for(int i=0;i<7;i++){
                PawnColor rdColor=PawnColor.randomColor();
                this.bag.replace(rdColor, this.bag.get(rdColor)-1);
                s.addStudentEntrance(rdColor);
            }//todo se si gioca in 3 se ne mettono 9
            //manca da settare il colore delle torri todo se lo sceglie il giocatore deve settarlo il controller

        }
    }
    private Player SelectFirstPlayer(){
        int min_val = 0;
        int max_val = players.size();
        Random rd = new Random();
        int randNum = min_val+rd.nextInt((max_val - min_val) + 1);
        return players.get(randNum);
    }//genera casualmente il primo giocatore che lancia la carta assistente al primo turno
    private void initBag(PawnColor color,int n){
        this.bag.put(color, n);
    }
//gestione init character cards
    private ArrayList<CharacterCard> initChosenCharacterCards() {
        return (ArrayList<CharacterCard>) getAllCharacterCards().subList(0,2);
    }
    private ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards();
    } /* restituisce tutte le charcards in disordine;
     quando si chiama la init si pescano le prime 3 in disordine;
     dopo la init si accede alle chosen tramite la get*/
    private void initAllCharacterCards(){//TODO ereditarietà instead of interfaccia
        this.allCharacterCards.add(new CharacterCard1(1,1));
        this.allCharacterCards.add(new CharacterCard2 (2,2));
        this.allCharacterCards.add(new CharacterCard3 (3,3));
        this.allCharacterCards.add(new CharacterCard (4,1));
        this.allCharacterCards.add(new CharacterCard (5,2));
        this.allCharacterCards.add(new CharacterCard (6,3));
        this.allCharacterCards.add(new CharacterCard (7,1));
        this.allCharacterCards.add(new CharacterCard (8,2));
        this.allCharacterCards.add(new CharacterCard (9,3));
        this.allCharacterCards.add(new CharacterCard (10,1));
        this.allCharacterCards.add(new CharacterCard (11,2));
        this.allCharacterCards.add(new CharacterCard (12,3));
    }

    public void addPlayer(Player playerToAdd) {
        players.add(playerToAdd);
        for(ModelListener l : clientHandlersListeners){
            l.updatePlayers(this.players);
        }
    }

    public boolean isBagEmpty() {
        for(PawnColor c : PawnColor.values()){
            if(bag.get(c)!=0){
                return false;
            }
        }
        return true;
    }
    public void useAssistantCard(Player currPlayer, AssistantCard assistantCard){
        //players.get(this.currPlayer).useAssistantCard(assistantCard);
        currPlayer.useAssistantCard(assistantCard);
        for(ModelListener l: clientHandlersListeners){
            l.updateLastAssistantCard(currPlayer);
        }
    }
    public void movePawnToIsland(PawnColor student, Island destination){
        players.get(currPlayer).moveFromEntranceToIsland(destination, student);
        for(ModelListener l : clientHandlersListeners){
            l.updateIsland(destination);
            l.updateSchoolBoardEntrance(players.get(currPlayer));
        }
    }
    public void movePawnToHall(PawnColor student){
        players.get(currPlayer).moveFromEntranceToHall(student);
        updateProfessor(student);
        for(ModelListener l : clientHandlersListeners){
            l.updateSchoolBoardHall(players.get(currPlayer));
            l.updateProfessorTables(players);
        }
    }
    public void moveFromCloudToEntrance(CloudTile ct){
        players.get(currPlayer).moveFromCloudToEntrance(ct);
        for(ModelListener l : clientHandlersListeners){
            l.updateCT(ct);
            l.updateSchoolBoardEntrance(players.get(currPlayer));
        }
    }
    public void moveMN(int shift){
        islandManager.moveMotherNature(shift);
        if(islandManager.getIslandList().get(islandManager.getCurrMNPosition()).isNoEntryTile()){
            islandManager.getIslandList().get(islandManager.getCurrMNPosition()).setNoEntryTile(false);
            entryTiles += 1;
        }else{
            islandManager.assignInfluence(schoolBoards);
            islandManager.checkForMerge();
        }
        for(ModelListener l : clientHandlersListeners){
            l.updateIslandList(islandManager.getIslandList());
        }
    }
    public void addListener(ModelListener l){
        this.clientHandlersListeners.add(l);
    }

    public void removeWizard(WizardType wizard) {
        this.availableWizards.remove(wizard);
        for(ModelListener l : clientHandlersListeners){
            l.updateAvailableWizards(availableWizards);
        }
    }

    public void removeTowerColor(TowerColor towersColor) {
        this.availableTowerColors.remove(towersColor);
        for(ModelListener l : clientHandlersListeners){
            l.updateAvailableTowerColors(availableTowerColors);
        }
    }
}
