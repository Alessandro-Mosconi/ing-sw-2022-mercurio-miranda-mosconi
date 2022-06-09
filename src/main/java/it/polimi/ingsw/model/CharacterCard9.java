package it.polimi.ingsw.model;

public class CharacterCard9 implements CardBehavior{

    //Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza.

    public void startEffect(Parameter parameter) {
        parameter.getGame().setKeptOut(parameter.getChosenColor());
    }

    public void initializeCard(Parameter parameter) {
        //none
    }
    public void endEffect(Parameter parameter) {
        parameter.getGame().setKeptOut(null);
    }
}