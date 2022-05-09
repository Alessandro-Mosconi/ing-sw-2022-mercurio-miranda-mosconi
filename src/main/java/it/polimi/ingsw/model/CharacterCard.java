package it.polimi.ingsw.model;

public class CharacterCard{
    // stackOverflow suggests to use Integer type if we need to assign null value
    private int price;
    private final CardBehavior cardBehavior;

    public CharacterCard(int price, CardBehavior characterCard) {
        this.price=price;
        this.cardBehavior=characterCard;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void increasePrice(){
        this.setPrice(this.getPrice()+1);
    }//After using the card, increases the price and decreases game.bank
    public CardBehavior getCardBehavior() {
        return cardBehavior;
    }
}
