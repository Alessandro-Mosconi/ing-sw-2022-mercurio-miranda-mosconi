package it.polimi.ingsw.model;

public class CharacterCard{


    // stackOverflow suggests to use Integer type if we need to assign null value
    private int ID;
    private int price;
    private final CardBehavior cardBehavior;

    public CharacterCard(int id, int price, CardBehavior characterCard) {
        this.ID=id;
        this.price=price;
        this.cardBehavior=characterCard;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public void increasePrice(){
        this.setPrice(this.getPrice()+1);
    }//After using the card, increases the price and decreases game.bank
    public CardBehavior getCardBehavior() {
        return cardBehavior;
    }
}
