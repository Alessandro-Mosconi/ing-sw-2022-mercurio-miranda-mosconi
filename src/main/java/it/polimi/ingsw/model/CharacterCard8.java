package it.polimi.ingsw.model;

public class CharacterCard8 implements CardBehavior{

    // in questo turno hai 2 punti in più per il calcolo dell'influenza
    @Override
    public void Effect(Parameter parameter) {
        //todo come la carta n6, capire come modificare l'influenza del singolo player
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }
}
