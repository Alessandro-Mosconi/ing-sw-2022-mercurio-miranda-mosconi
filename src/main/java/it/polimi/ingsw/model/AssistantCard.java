package it.polimi.ingsw.model;

public class AssistantCard {

    private int value;
    private int motherMovement;
    private boolean consumed;

    public AssistantCard(int val, int mov)
    {
        this.value = val;
        this.motherMovement = mov;
        this. consumed = false;
    }

    public AssistantCard() {

    }

    public int getValue() {
        return value;
    }

    public int getMotherMovement() {
        return motherMovement;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }
}
