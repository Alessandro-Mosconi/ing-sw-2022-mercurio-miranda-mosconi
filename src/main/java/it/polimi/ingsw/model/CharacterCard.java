package it.polimi.ingsw.model;

import java.util.Map;

public class CharacterCard{


    // stackOverflow suggests to use Integer type if we need to assign null value
    protected Integer ID;
    protected int price;
    //private final CardBehavior cardBehavior;
    protected Map<PawnColor,Integer> students = null;

    public CharacterCard(int id, int price/*, CardBehavior cardBehavior*/) {
        this.ID=id;
        this.price=price;
        //this.cardBehavior = cardBehavior;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
    public void increasePrice(){
        this.setPrice(this.getPrice()+1);
    }//After using the card, increases the price and decreases game.bank
    //public CardBehavior getCardBehavior() {return cardBehavior;}

    public void Effect(Parameter parameter) {

    };
    public void initializeCard(Parameter parameter){

    };
    public void endEffect(Parameter parameter){

    };

}
