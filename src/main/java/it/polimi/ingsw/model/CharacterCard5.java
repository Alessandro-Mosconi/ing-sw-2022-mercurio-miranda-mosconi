package it.polimi.ingsw.model;

public class CharacterCard5 implements CardBehavior{



    // ad inizio partita mettiamo le 4 tessere divieto su questa carta. Quando si usa la carta si sposta una tessera divieto su
    // un'isola e resta lì finché non ci va madre natura.
    // Se madre natura finisce su un'isola con una tessera divieto allora non si calcola l'influenza

    /**
     * Puts a No Entry Tile on the island chosen by the user.
     * @param parameter contains the Game in which the card is activated, the player who activates it and island on which the effect has to be applied.
     */
    public void startEffect(Parameter parameter) {
        if(parameter.getGame().getEntryTiles()>0) {
            parameter.getIsland().setNoEntryTile(true);
            parameter.getGame().setEntryTiles(parameter.getGame().getEntryTiles()-1);
        }
    }//Moves a noEntryTile from the card to the chosen Island

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //do nothing
    }

    /**
     * Does nothing.
     * @param parameter contains the Game in which the card was activated.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}

