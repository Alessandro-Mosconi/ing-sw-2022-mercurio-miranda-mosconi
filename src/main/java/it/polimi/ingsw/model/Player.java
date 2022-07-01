package it.polimi.ingsw.model;

public class Player {


    private String nickName;
    private int wallet;

    public Player(int playerNumber, String nickname, SchoolBoard sb) {
        this.playerNumber = playerNumber;
        this.nickName = nickname;
        this.schoolBoard = sb;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setSchoolBoard(SchoolBoard schoolBoard) {
        this.schoolBoard = schoolBoard;
    }

    private Deck deck;

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    private int playerNumber;
    private SchoolBoard schoolBoard;
    private AssistantCard lastAssistantCard;
    private int maxShift;
    private boolean bonus2Shifts=false;

    public Player(){
        this.nickName = null;
        this.wallet = 0;
        this.deck = new Deck();
        this.playerNumber = 0;
        this.schoolBoard = new SchoolBoard();
        this.lastAssistantCard = null;
        this.maxShift=0;
        this.bonus2Shifts=false;
    }
    public Player(String nickName, Deck deck, int playerNumber, SchoolBoard schoolBoard) {
        this.nickName = nickName;
        this.wallet = 0;
        this.deck = deck;
        this.playerNumber = playerNumber;
        this.schoolBoard = schoolBoard;
        this.lastAssistantCard = null;
    }

    public SchoolBoard getSchoolBoard(){
        return schoolBoard;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public int getWallet() {
        return wallet;
    }
    public Deck getDeck() {
        return deck;
    }
    public int getPlayerNumber() {
        return playerNumber;
    }
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    public void setLastAssistantCard(AssistantCard lastAssistantCard) {
        this.lastAssistantCard = lastAssistantCard;
    }
    public int getMaxShift() {
        return maxShift;
    }
    public void setMaxShift(int maxShift) {
        this.maxShift = maxShift;
    }
    public boolean isBonus2Shifts() {
        return bonus2Shifts;
    }
    public void setBonus2Shifts(boolean bonus2Shifts) {
        this.bonus2Shifts = bonus2Shifts;
    }

    /**
     * Sets as consumed the given assistant card, stores it as the player's last used assistant card and updates the maximum Mother Nature shift that the player can use during this turn.
     * @param card chosen assistant card.
     */
    public void useAssistantCard(AssistantCard card)
    {
        int pos = card.getValue()-1;

        this.deck.getCards().get(pos).setConsumed(true);
        this.setLastAssistantCard(card);
        updateMaxShift();
    }

    /**
     * Sets the maximum Mother Nature shift that this player can perform during this turn.
     */
    public void updateMaxShift(){
        if(isBonus2Shifts()){
            this.maxShift=getLastAssistantCard().getMotherMovement()+2;
        }
        else this.maxShift=getLastAssistantCard().getMotherMovement();
    }
    public AssistantCard getLastAssistantCard() {
        return lastAssistantCard;
    }


//move a specific student from the entrance to the hall

    /**
     * Moves a given student from this player's entrance to this player's hall.
     * @param color is the student to be moved.
     */
    public void moveFromEntranceToHall(PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0 && schoolBoard.getStudentHall().get(color)<10)
            System.out.println(color + " not present in Entrance");
        else {
            schoolBoard.removeStudentEntrance(color);
            schoolBoard.addStudentHall(color);
        }
    }



    /**
     *  Removes all the student from a chosen cloud and puts them in this player's entrance.
     * @param cloud is the chosen cloud.
     */
    public void moveFromCloudToEntrance(CloudTile cloud) {
        for (PawnColor color : PawnColor.values()) {
            schoolBoard.addStudentEntrance(color, cloud.getStudents().get(color));
            cloud.reset(color);

        }
    }

    //move a specific student color from the Entrance to a specific island
    /**
     * Moves a given student from this player's entrance to a given island.
     * @param color is the student to be moved.
     * @param island is the island on which the student had to be moved.
     */
    public void moveFromEntranceToIsland(Island island, PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0)
            System.out.println(color + "not present in Entrance");
        else {
            schoolBoard.removeStudentEntrance(color);
            island.addStudent(color);
        }
    }

}
