package it.polimi.ingsw.model;

public class CharacterCard9 implements CardBehavior{

    //Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza.

    /**
     * Sets the PawnColor attribute of the Game, indicating that, during this turn, while calculating the influence on an island, that color doesn't count.
     * @param parameter contains the Game in which the card is activated, the player who activates it and the PawnColor to be kept out.
     */
    public void startEffect(Parameter parameter) {
        parameter.getGame().setKeptOut(parameter.getChosenColor());
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
     * @param parameter contains the Game in which the card was activated, the player who activated it and all the user choices used to start the effect itself.
     */
    public void endEffect(Parameter parameter) {
        parameter.getGame().setKeptOut(null);
    }
}