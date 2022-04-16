package it.polimi.ingsw.model;

public class CharacterCard5 implements CardBehavior{

    private int entryTile = 4;

    // ad inizio partita mettiamo le 4 tessere divieto su questa carta. Quando si usa la carta si sposta una tessera divieto su
    // un'isola e resta lì finché non ci va madre natura.
    // Se madre natura finisce su un'isola con una tessera divieto allora non si calcola l'influenza todo
    @Override
    public void Effect() {

    }
}

