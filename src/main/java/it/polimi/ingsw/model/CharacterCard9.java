package it.polimi.ingsw.model;

public class CharacterCard9 extends CharacterCard{
    public CharacterCard9(int id, int price) {
        super(id, price);
    }

    //Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza.

    @Override
    public void Effect(Parameter parameter) {
        parameter.getGame().setKeptOut(parameter.getChosenColor());
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }

    @Override
    public void endEffect(Parameter parameter) {
        parameter.getGame().setKeptOut(null);
    }
}