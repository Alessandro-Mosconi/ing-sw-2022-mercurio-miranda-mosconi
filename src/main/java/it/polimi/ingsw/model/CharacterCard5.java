package it.polimi.ingsw.model;

public class CharacterCard5 implements CardBehavior{


    // ad inizio partita mettiamo le 4 tessere divieto su questa carta. Quando si usa la carta si sposta una tessera divieto su
    // un'isola e resta lì finché non ci va madre natura.
    // Se madre natura finisce su un'isola con una tessera divieto allora non si calcola l'influenza
    /* Direi di fare un controllo quando si sposta madre natura:
        si potrebbe fare il controllo quando si sposta madre natura e togliere, eventualmente, la tile
        di conseguenza andrebbe risettato il valore delle entryTiles su questa carta con una nuova Set
    */
    @Override
    public void Effect(Parameter parameter) {
        if(parameter.getGame().getEntryTiles()>0) {
            parameter.getIsland().setNoEntryTile(true);
            parameter.getGame().setEntryTiles(parameter.getGame().getEntryTiles()-1);
        }
    }//Moves a noEntryTile from the card to the chosen Island

    @Override
    public void initializeCard(Parameter parameter) {
        //do nothing
    }

    @Override
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}

