package it.polimi.ingsw.model;

public class CharacterCard9 implements CardBehavior{

    //Scegli un colore di Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza.

    @Override
    public void Effect(Parameter parameter) {
        PawnColor chosenColor= chooseColor();
        //todo anche questo da capire
    }


    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
    }
}