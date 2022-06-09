package it.polimi.ingsw.model;

public class CharacterCard8 implements CardBehavior{


    // in questo turno hai 2 punti in più per il calcolo dell'influenza
    public void startEffect(Parameter parameter) {
        parameter.getGame().getSchoolBoards().get(parameter.getGame().getCurrPlayer()).setBonus2influencepoints(true);
    }

    public void initializeCard(Parameter parameter) {
        //none
    }
    public void endEffect(Parameter parameter) {
        parameter.getGame().getSchoolBoards().get(parameter.getGame().getCurrPlayer()).setBonus2influencepoints(false);
    }
}
