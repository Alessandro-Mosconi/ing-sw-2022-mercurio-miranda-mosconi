package it.polimi.ingsw.model;

public class CharacterCard8 implements CardBehavior{


    /**
     * Sets as true a boolean attribute of the player who activates the card, indicating that that player has 2 additional influence points during this turn.
     * @param parameter contains the Game in which the card is activated and the player who activates it.
     */
    // in questo turno hai 2 punti in più per il calcolo dell'influenza
    public void startEffect(Parameter parameter) {
        parameter.getPlayer().getSchoolBoard().setBonus2influencepoints(true);
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }

    /**
     * Sets as false the boolean attribute of the player who activated the card, indicating that that player had 2 additional influence points.
     * @param parameter contains the Game in which the card was activated, the player who activated it and all the user choices used to start the effect itself.
     */
    public void endEffect(Parameter parameter) {
        parameter.getPlayer().getSchoolBoard().setBonus2influencepoints(false);
    }
}
