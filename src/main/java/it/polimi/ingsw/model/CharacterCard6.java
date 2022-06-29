package it.polimi.ingsw.model;

public class CharacterCard6 implements CardBehavior{


    /**
     * Sets as false a boolean attribute of the Game indicating that towers do not count while calculating the influence on an island.
     * @param parameter contains the Game in which the card is activated and the player who activates.
     */
    public void startEffect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(false);
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }

    /**
     * Sets as true the boolean attribute of the Game indicating that towers did not count while calculating the influence during this turn.
     * @param parameter contains the Game in which the card was activated and the player who activated it.
     */
    public void endEffect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(true);
    }
}

