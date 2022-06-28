package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CharacterCard2 implements CardBehavior{
/*
    Eff: durante il turno, prendi il controllo dei prof anche se nella tua sala hai lo stesso numero di studenti del giocatore che controlla i prof in quel momento
 */

    /**
     * Sets as true a boolean attribute on the schoolboard of the player who activates the effect to indicate that,
     * in case of ties while assigning the professor table, that player gets the professor.
     * @param parameter contains the Game in which the card is activated and the player who activated it.
     */
    public void startEffect(Parameter parameter){

        parameter.getPlayer().getSchoolBoard().setEffectProf(true);
        for(PawnColor c : PawnColor.values()) {
            parameter.getGame().updateProfessor(c);
        }
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }


    /**
     * Resets the professor tables as they were before the card was activated.
     * @param parameter contains the Game in which the card was activated and the player who activated it.
     */
    public void endEffect(Parameter parameter) {
        parameter.getPlayer().getSchoolBoard().setEffectProf(false);
    }


}
