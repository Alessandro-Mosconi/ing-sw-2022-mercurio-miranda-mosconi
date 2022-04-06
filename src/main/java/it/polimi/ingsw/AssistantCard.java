package it.polimi.ingsw;

public class AssistantCard {
    private final int value;
    private final int motherMovement;
    private boolean consumed;

    public AssistantCard(int val, int mov)
    {
        this.value = val;
        this.motherMovement = mov;
        this. consumed = false;
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
