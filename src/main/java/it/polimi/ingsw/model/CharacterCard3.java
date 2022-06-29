package it.polimi.ingsw.model;

public class CharacterCard3 implements CardBehavior{

/*
    Eff: scegli un'isola e calcola l'infuenza
 */

    /**
     * Calculates the influence on an island even if Mother Nature doesn't end its movement on it.
     * @param parameter contains the Game in which the card is activated and the island on which the effect has to be applied.
     */
    public void startEffect(Parameter parameter){
        parameter.getIsland().assignInfluence(parameter.getGame().getSchoolBoards());
        parameter.getGame().getIslandManager().checkForMerge(parameter.getIsland().getIslandID());
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }
    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
