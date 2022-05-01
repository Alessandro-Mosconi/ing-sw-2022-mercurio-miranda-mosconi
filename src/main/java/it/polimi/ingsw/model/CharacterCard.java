package it.polimi.ingsw.model;

public class CharacterCard{
    // stackOverflow suggests to use Integer type if we need to assign null value
    private int price;
    private CardBehavior cardBehavior;

    public CharacterCard(int price, Object characterCard) {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
