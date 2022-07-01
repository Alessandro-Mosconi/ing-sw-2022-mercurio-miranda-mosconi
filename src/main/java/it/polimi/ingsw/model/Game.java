package it.polimi.ingsw.model;
import java.util.*;

public class Game {


    //attributes
    private ArrayList<Player> players = new ArrayList<>();
    private int numberOfPlayers;
    private String gameID;
    private GameMode gameMode;
    private ArrayList<CloudTile> cloudTiles;
    private ArrayList<SchoolBoard> schoolBoards;
    private IslandManager islandManager;
    private Map<PawnColor, Integer> bag;
    private ArrayList<CharacterCard> allCharacterCards;
    private ArrayList<CharacterCard> chosenCharacterCards;
    private int bank;
    private boolean towersDoCount=true;
    private PawnColor keptOut=null;
    private boolean started;
    private int entryTiles;
    private CharacterCard currEffect;
    private Parameter currParameter;
    private List<ModelListener> clientHandlersListeners = new ArrayList<ModelListener>();
    private ArrayList<WizardType> availableWizards = new ArrayList<>() {{
        addAll(List.of(WizardType.values()));
    }};
    private ArrayList<TowerColor> availableTowerColors = new ArrayList<>(){{
        addAll(List.of(TowerColor.values()));
    }};

    //@Constructors
    public Game() {
        this.numberOfPlayers =0;
        this.gameID = "0";
        this.gameMode =null;
        this.cloudTiles =new ArrayList<>();
        this.schoolBoards=new ArrayList<>();
        this.islandManager = new IslandManager(new ArrayList<Island>());
        this.bag= new HashMap<>();
        this.allCharacterCards=new ArrayList<>();
        this.chosenCharacterCards=new ArrayList<>();
        this.bank=20;
        this.started=false;
        this.entryTiles=4;
    }

    public Game(int numberOfPlayers, String idGame, GameMode gameMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameID = idGame;
        this.gameMode = gameMode;
        this.started = false;
        this.schoolBoards=new ArrayList<SchoolBoard>();
    }

    //methods @Setter @Getter

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

    public ArrayList<CharacterCard> getAllCharacterCard(){
        return this.allCharacterCards;
    }


    public boolean isStarted() {
        return started;
    }
    public void setStarted(boolean started) {
        this.started = started;
    }
    public CharacterCard getCurrEffect() {
        return currEffect;
    }

    public void setCurrEffect(CharacterCard currEffect) {
        this.currEffect = currEffect;
    }

    public Parameter getCurrParameter() {
        return currParameter;
    }

    public void setCurrParameter(Parameter currParameter) {
        this.currParameter = currParameter;
    }

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
        this.towersDoCount = towersDoCount;
        for(Island i: this.getIslandManager().getIslandList()){
            i.setTowersDoCount(towersDoCount);
        }
    }
    public PawnColor getKeptOut() {
        return keptOut;
    }
    public void setKeptOut(PawnColor keptOut) {
        this.keptOut = keptOut;
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
    /**
     * Fills a given cloud with students from this game's bag.
     * @param cloud is the given cloud.
     */
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

    /**
     * Checks what schoolboard owns a given professor.
     * @param color is the given professor's color.
     */
    public void updateProfessor(PawnColor color){
        int currentMax=0;

        for(SchoolBoard s : schoolBoards){
            if(s.getProfessorTable().get(color)){
                currentMax=s.getStudentHall().get(color);
            }
        }
        for (SchoolBoard s : schoolBoards){
            if(s.getStudentHall().get(color)>currentMax || (currentMax > 0 && s.isEffectProf() && s.getStudentHall().get(color)>=currentMax)){
                currentMax=s.getStudentHall().get(color);
                for(SchoolBoard f : schoolBoards){
                    f.getProfessorTable().replace(color,false);
                }
                s.getProfessorTable().replace(color,true);
            }
        }
    }

    /**
     * Shuffles all the character cards.
     * @return a shuffled array containing all the character cards.
     */
    public ArrayList<CharacterCard> shuffleCharacterCards() {
        Collections.shuffle(allCharacterCards);
        return allCharacterCards;
    }

    /**
     * Initializes each component of the game-board.
     */
    public void setupGame(){
        for(ModelListener l : clientHandlersListeners){
            l.updateSetupPlayers(players);
        }
        bag = new HashMap<>();
        fillBag();
        ArrayList<Island> islands = generateIslands(); //generates and initialises the islands
        IslandManager islandManager= new IslandManager(islands);
        this.setIslandManager(islandManager);
        for(ModelListener l : clientHandlersListeners){
            l.updateIslandList(islands);
        }
        ArrayList<SchoolBoard> schoolBoards = generateSchoolBoards();
        this.setSchoolBoards(schoolBoards);
        initSchoolBoards();
        for(ModelListener l : clientHandlersListeners){
            for(Player p : players){
                l.updateSchoolBoardEntrance(p);
                l.updateNumTowers(p);
            }
        }
        this.cloudTiles = generateCloudTiles();
        for(ModelListener l : clientHandlersListeners){
            l.updateCTs(cloudTiles);
        }

        for(ModelListener l : clientHandlersListeners){
            l.modelCreated();
        }
        if(gameMode.equals(GameMode.expert)){
            initAllCharacterCards(); //Allocates all the CharacterCards
            ArrayList<CharacterCard> chosenCharacterCards = initChosenCharacterCards(); //Takes a sublist from the randomized CharacterCards list
            this.setChosenCharacterCards(chosenCharacterCards);
            for(CharacterCard cc: chosenCharacterCards){
                cc.getCardBehavior().initializeCard(new Parameter(this));
            }//Initializes the chosen card
            this.setEntryTiles(4);
            this.setBank(20);
            for(Player p : players){
                p.setWallet(1);
                for(ModelListener l : clientHandlersListeners){
                    l.updateWallet(p);
                }
            }
            for(ModelListener l : clientHandlersListeners){
                l.initializedCharacterCards(chosenCharacterCards);
            }
        }
    }


    /**
     * Sorts the players based on their last used assistant card's value.
     * @return a sorted array containing the ordered player numbers.
     */
    public ArrayList<Integer> calculatePlayerOrder() {
        ArrayList<Player> tmpPlayerArray = new ArrayList<>() {{
            addAll(players);
        }};
        tmpPlayerArray.sort(new Comparator<>() {
            public int compare(Player i1, Player i2) {
                return i1.getLastAssistantCard().getValue() - i2.getLastAssistantCard().getValue();
            }
        });
        ArrayList<Integer> tmpOrder = new ArrayList<>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            tmpOrder.add(tmpPlayerArray.get(i).getPlayerNumber());
        }
        return tmpOrder;
    }


    //setupGame extractions

    /**
     * Allocates and initializes 12 islands.
     * @return an arrayList of the initialized islands.
     */
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
                    islands.add(new Island(i,null,0,false,false));
                    if(i!=0&&i!=5){
                        PawnColor rdColor=PawnColor.randomColor();
                        if(islandInitializationBag.get(rdColor)==0){
                           while(islandInitializationBag.get(rdColor)==0){
                               rdColor = PawnColor.randomColor();
                           }
                        }
                        islandInitializationBag.replace(rdColor, islandInitializationBag.get(rdColor)-1);
                        islands.get(i).addStudent(rdColor);
                    }//initializes islands with students
                    else if(i==0){
                        islands.get(i).setMotherNature(true);
                    }//initializes mother nature
                }
            }
        }
        return islands;
    }

    /**
     * Allocates a schoolboard for each player.
     * @return an arrayList of the just created schoolboards - not initialized yet.
     */
    private ArrayList<SchoolBoard> generateSchoolBoards() {
        ArrayList<SchoolBoard> schoolBoards = new ArrayList<>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                schoolBoards.add(new SchoolBoard());
            }
        }
        return schoolBoards;
    }

    /**
     * Moves to each cloud 3 students (when players are 2) or 4 students (when players are 3).
     */
    public void fillCloudTiles() {
        for(CloudTile c : this.cloudTiles){
            for(int i=0;i<3;i++){
                moveFromBagToCloud(c);
            }
            if(this.numberOfPlayers == 3){
                moveFromBagToCloud(c);
            }
        }
        for(ModelListener l : clientHandlersListeners){
            l.updateCTs(cloudTiles);
        }
    }

    /**
     * Allocates cloud tile for each player.
     * @return an arrayList of the cloud tiles - not filled yet.
     */
    private ArrayList<CloudTile> generateCloudTiles() {
        ArrayList<CloudTile> cloudTiles = new ArrayList<>(numberOfPlayers);
        {
            for(int i = 0; i< numberOfPlayers; i++){
                cloudTiles.add(new CloudTile(i));
            }
        }
        return cloudTiles;
    }

    /**
     * Initializes this game bag with 26 students for each color.
     */
    private void fillBag() {
        for(PawnColor color : PawnColor.values()){
            this.initBag(color,26);
        }//setta la bag ai valori di default
    }


    /**
     * Initializes each schoolboard with the appropriate amount of towers and students.
     */
    private void initSchoolBoards() {
        int j=0;
         for(SchoolBoard s: schoolBoards){
             s.setTowersColor(players.get(j).getSchoolBoard().getTowersColor());
            if(this.numberOfPlayers==2){
                s.setTowersNumber(8);
            }
            if(this.numberOfPlayers==3){
                s.setTowersNumber(6);
            }
            for(int i=0;i<7;i++){
                PawnColor rdColor=PawnColor.randomColor();
                this.bag.replace(rdColor, this.bag.get(rdColor)-1);
                s.addStudentEntrance(rdColor);
            }if(numberOfPlayers==3){
                for(int i = 0 ; i<2 ; i++){
                    PawnColor rdColor=PawnColor.randomColor();
                    this.bag.replace(rdColor, this.bag.get(rdColor)-1);
                    s.addStudentEntrance(rdColor);
                }
             }
            players.get(j).setSchoolBoard(s);
            j++;
        }
    }

    /**
     * Sets to a given number this game bag's amount of students of a given color.
     * @param color is the given color.
     * @param n is the given number.
     */
    private void initBag(PawnColor color,int n){
        this.bag.put(color, n);
    }
//gestione init character cards

    /**
     * Takes the first 3 character cards from an array containing all of them in a shuffled order.
     * @return an array list containing the 3 random character cards.
     */
    private ArrayList<CharacterCard> initChosenCharacterCards() {
        ArrayList<CharacterCard> chosenCards = new ArrayList<>(getAllCharacterCards().subList(0,3));
        return chosenCards;
    }

    /**
     * @return an array list containing all the character cards in an already shuffled order.
     */
    private ArrayList<CharacterCard> getAllCharacterCards() {
        return shuffleCharacterCards();
    }

    /**
     * Allocates and adds to this game's character cards array a card for each kind.
     */
    private void initAllCharacterCards(){
        this.allCharacterCards = new ArrayList<CharacterCard>();
        this.allCharacterCards.add(new CharacterCard (1,1,new CharacterCard1()));
        this.allCharacterCards.add(new CharacterCard (2,2,new CharacterCard2()));
        this.allCharacterCards.add(new CharacterCard (3,3,new CharacterCard3()));
        this.allCharacterCards.add(new CharacterCard (4,1,new CharacterCard4()));
        this.allCharacterCards.add(new CharacterCard (5,2,new CharacterCard5()));
        this.allCharacterCards.add(new CharacterCard (6,3,new CharacterCard6()));
        this.allCharacterCards.add(new CharacterCard (7,1,new CharacterCard7()));
        this.allCharacterCards.add(new CharacterCard (8,2,new CharacterCard8()));
        this.allCharacterCards.add(new CharacterCard (9,3,new CharacterCard9()));
        this.allCharacterCards.add(new CharacterCard (10,1,new CharacterCard10()));
        this.allCharacterCards.add(new CharacterCard (11,2,new CharacterCard11()));
        this.allCharacterCards.add(new CharacterCard (12,3,new CharacterCard12()));
    }

    /**
     * Adds a player to this game's players array.
     * @param playerToAdd player to be added.
     */
    public void addPlayer(Player playerToAdd) {
        players.add(playerToAdd);
    }

    /**
     * @return true if the bag has no students, false otherwise.
     */
    public boolean isBagEmpty() {
        for(PawnColor c : PawnColor.values()){
            if(bag.get(c)!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * Calls the corresponding method on the given player and updates each client by sending a message.
     * @param cPlayer given player.
     * @param assistantCard assistant card chosen by the user.
     */
    public void useAssistantCard(Player cPlayer, AssistantCard assistantCard){
        cPlayer.useAssistantCard(assistantCard);
        for(ModelListener l: clientHandlersListeners){
            l.updateLastAssistantCard(cPlayer);
        }
    }

    /**
     * Calls the corresponding method on the given player and updates each client by sending a message.
     * @param currPlayer given player.
     * @param student student chosen by the user.
     * @param destination island chosen by the user.
     */
    public void movePawnToIsland(Player currPlayer, PawnColor student, Island destination){
        currPlayer.moveFromEntranceToIsland(destination, student);
        for(ModelListener l : clientHandlersListeners){
            l.updateIsland(destination);
            l.updateSchoolBoardEntrance(currPlayer);
        }
    }

    /**
     * Calls the corresponding method on the given player and updates each client by sending a message.
     * @param currPlayer given player.
     * @param student student chosen by the user.
     */
    public void movePawnToHall(Player currPlayer, PawnColor student){
        currPlayer.moveFromEntranceToHall(student);
        updateProfessor(student);
        if(currPlayer.getSchoolBoard().checkForCoin(student) && this.bank>0){
            currPlayer.setWallet(currPlayer.getWallet()+1);
            for(ModelListener l : clientHandlersListeners){
                l.updateWallet(currPlayer);
            }
        }
        for(ModelListener l : clientHandlersListeners){
            l.updateSchoolBoardEntrance(currPlayer);
            l.updateSchoolBoardHall(currPlayer);
            l.updateProfessorTables(players);
        }
    }

    /**
     * Calls the corresponding method on the given player.
     * @param currPlayer given player.
     * @param ct cloud tile chosen by the user.
     */
    public void moveFromCloudToEntrance(Player currPlayer, CloudTile ct){
        currPlayer.moveFromCloudToEntrance(ct);
        for(ModelListener l : clientHandlersListeners){
            l.updateCTs(this.cloudTiles);
            l.updateSchoolBoardEntrance(currPlayer);
        }
    }

    /**
     * Calls the corresponding method on this game's island manager, it also calls assignInfluence and checkForMerge methods on the island manager. Finally, updates each client by sending a message.
     * @param shift mother nature movement chosen by the user.
     */
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
            for(Player p : players){
                l.updateNumTowers(p);
            }
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

    /**
     * Calls the corresponding method on the currently active character card and updates each client by sending a message.
     */
    public void increaseCurrEffectPrice() {
        this.currEffect.increasePrice();
        for(ModelListener l : clientHandlersListeners){
            l.updatePrice(currEffect);
        }
    }

    /**
     * Activates the effect of a character card using every necessary parameter chosen by the user and updates each client by sending a message.
     */
    public void startEffect() {
        this.currEffect.getCardBehavior().startEffect(this.currParameter);
        currParameter.getPlayer().setWallet(currParameter.getPlayer().getWallet()-currEffect.getPrice());
        this.bank+=(currEffect.getPrice()-1);
        for(ModelListener l : clientHandlersListeners) {
            l.updateWallet(currParameter.getPlayer());
        }
        switch(currEffect.getID()){
            case 1->{
                for(ModelListener l : clientHandlersListeners){
                    for(Player p : players){
                        l.updateNumTowers(p);
                    }
                    l.updateIslandList(islandManager.getIslandList());
                    l.updateCardStudents(currEffect);
                }
            }
            case 2 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateProfessorTables(players);
                }
            }
            case 3, 5 ->{
                for(ModelListener l : clientHandlersListeners){
                    for(Player p : players){
                        l.updateNumTowers(p);
                    }
                    l.updateIslandList(islandManager.getIslandList());
                }
            }
            case 4 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateMaxShift(currParameter.getPlayer());
                }
            }
            case 6 ->{
                for(ModelListener l : clientHandlersListeners){
                    for(Player p : players)
                    l.updateTowersDoCount(towersDoCount);
                }
            }
            case 7 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateSchoolBoardEntrance(currParameter.getPlayer());
                    l.updateCardStudents(currEffect);
                }
            }
            case 8 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateBonus2InfluencePoints(currParameter.getPlayer());
                }
            }
            case 9 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateKeptOut(currParameter.getChosenColor());
                }
            }
            case 10 ->{
                for(PawnColor color : PawnColor.values()){
                    updateProfessor(color);
                }
                for(ModelListener l : clientHandlersListeners){
                    l.updateSchoolBoardEntrance(currParameter.getPlayer());
                    l.updateSchoolBoardHall(currParameter.getPlayer());
                    l.updateProfessorTables(players);
                }
            }
            case 11 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateSchoolBoardHall(currParameter.getPlayer());
                    l.updateCardStudents(currEffect);
                }
            }
            case 12 ->{
                for(SchoolBoard s : schoolBoards){
                    s.getProfessorTable().replace(currParameter.getChosenColor(),false);
                }
                updateProfessor(currParameter.getChosenColor());
                for(ModelListener l : clientHandlersListeners){
                    for(Player p : players){
                        l.updateSchoolBoardHall(p);
                    }
                    l.updateProfessorTables(players);
                }

            }
        }
    }

    /**
     * Ends the effect of the currently active character card using the currently set parameter, both previously chosen by the user and updates each client by sending a message.
     */
    public void endEffect() {
        this.currEffect.getCardBehavior().endEffect(this.currParameter);
        switch(currEffect.getID()){
            case 2 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateProfessorTables(players);
                }
            }
            case 6 ->{
                for(ModelListener l : clientHandlersListeners){
                    for(Player p : players)
                        l.updateTowersDoCount(towersDoCount);
                }
            }
            case 8 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateBonus2InfluencePoints(currParameter.getPlayer());
                }
            }
            case 9 ->{
                for(ModelListener l : clientHandlersListeners){
                    l.updateKeptOut(currParameter.getChosenColor());
                }
            }
        }
        this.currEffect=null;
        this.currParameter=null;
    }

    /**
     * @return true if there's a schoolboard with no towers left or if the remaining islands are 3 or less; false otherwise.
     */
    public boolean checkForEndGameConditions() {
        for(SchoolBoard s : schoolBoards){
            if(s.getTowersNumber()==0) return true;
        }
        if(islandManager.getIslandList().size()<=3) return true;
        return false;
    }
}
