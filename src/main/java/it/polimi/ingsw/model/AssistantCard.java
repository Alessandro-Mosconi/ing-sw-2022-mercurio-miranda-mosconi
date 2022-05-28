package it.polimi.ingsw.model;

public class AssistantCard {


    private String id;
    private Integer value;
    private Integer motherMovement;
    private boolean consumed;

    public String getId() {
        return id;
    }


    public AssistantCard(int val, int mov)
    {
        this.value = val;
        this.motherMovement = mov;
        this. consumed = false;
    }

    public AssistantCard() {

    }

    public Integer getValue() {
        return value;
    }

    public Integer getMotherMovement() {
        return motherMovement;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }
}
