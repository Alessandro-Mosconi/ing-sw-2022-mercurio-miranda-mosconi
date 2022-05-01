package it.polimi.ingsw.model;

public class CharacterCard5 implements CardBehavior{

    public int getEntryTile() {
        return entryTile;
    }

    public void setEntryTile(int entryTile) {
        this.entryTile = entryTile;
    }

    private int entryTile = 4;

    // ad inizio partita mettiamo le 4 tessere divieto su questa carta. Quando si usa la carta si sposta una tessera divieto su
    // un'isola e resta lì finché non ci va madre natura.
    // Se madre natura finisce su un'isola con una tessera divieto allora non si calcola l'influenza todo
    /* Direi di fare un controllo quando si sposta madre natura:
        si potrebbe fare il controllo quando si sposta madre natura e togliere, eventualmente, la tile
        di conseguenza andrebbe risettato il valore delle entryTiles su questa carta con una nuova Set
    */
    @Override
    public void Effect(Parameter parameter) {
        if(this.entryTile>0) {
            parameter.getIsland().setNoEntryTile(true);
            this.setEntryTile( this.entryTile - 1 );
        }
    }
}

