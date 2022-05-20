package it.polimi.ingsw.model;

import java.util.*;

public class Player {
    private final String nickName;
    private int wallet;
    private final Deck deck;
    private final int playerNumber;
    private final SchoolBoard schoolBoard;
    private AssistantCard lastAssistantCard;
    private int maxShift;
    private boolean bonus2Shifts=false;

    public Player(){
        this.nickName = null;
        this.wallet = 0;
        this.deck = null;
        this.playerNumber = 0;
        this.schoolBoard = null;
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
    public void collectCoin(PawnColor color){
        if(schoolBoard.checkForCoin(color))
            this.wallet = wallet +1;
    }//Increases wallet if needed
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

    public void useAssistantCard(AssistantCard card)
    {
        for(AssistantCard chosenCard:this.deck.getCards())
        {
            if(chosenCard.equals(card) && !chosenCard.isConsumed())
            {
                chosenCard.setConsumed(true);
                this.lastAssistantCard = chosenCard;
                updateMaxShift();
                this.deck.getCards().remove(chosenCard);
            }
        }
    }
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
    public void moveFromEntranceToHall(PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0)
            System.out.println(color + " not present in Entrance");
        else {
            schoolBoard.removeStudentEntrance(color);
            schoolBoard.addStudentHall(color);
            if(schoolBoard.checkForCoin(color))
                this.wallet++;
        }
    }


 //remove all the student from a specific cloud and put them in the entrance
    public void moveFromCloudToEntrance(CloudTile cloud) {

        for (PawnColor color : PawnColor.values()) {
            schoolBoard.addStudentEntrance(color, cloud.getStudents().get(color));
            cloud.reset(color);

        }
    }

    //move a specific student color from the Entrance to a specific island
    public void moveFromEntranceToIsland(Island island, PawnColor color){
        if(schoolBoard.getStudentEntrance().get(color)<=0)
            System.out.println(color + "not present in Entrance");
        else {
            schoolBoard.removeStudentEntrance(color);
            island.addStudent(color);
        }
    }

}
