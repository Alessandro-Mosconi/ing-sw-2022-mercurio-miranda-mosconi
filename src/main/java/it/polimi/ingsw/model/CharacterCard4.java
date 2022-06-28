package it.polimi.ingsw.model;

public class CharacterCard4 implements CardBehavior{


    /*
        Eff: puoi muovere madre natura di 2 isole in piu di quanto scritto sulla carta scelta
     */

    /**
     * Sets as true a boolean attribute of the player who uses it, indicating that that player can move Mother Nature up to 2 extra islands.
     * @param parameter contains the Game in which the card is activated and the player who activates it.
     */
    public void startEffect(Parameter parameter){
        parameter.getPlayer().setBonus2Shifts(true);
    }


    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }


    /**
     * Sets as false the boolean attribute of the player who used the card that indicated that that player could move Mother Nature up to 2 extra islands.
     * @param parameter contains the Game in which the card was activated and the player who activated it.
     */
    public void endEffect(Parameter parameter) {
        parameter.getPlayer().setBonus2Shifts(false);
    }
}
