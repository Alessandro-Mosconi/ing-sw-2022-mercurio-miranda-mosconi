package it.polimi.ingsw.model;

public class CharacterCard3 implements CardBehavior{

/*
    Eff: scegli un'isola e calcola l'infuenza
 */

    public void startEffect(Parameter parameter){
        parameter.getIsland().assignInfluence(parameter.getGame().getSchoolBoards());
        parameter.getGame().getIslandManager().checkForMerge(parameter.getIsland().getIslandID());
    }

    public void initializeCard(Parameter parameter) {
        //none
    }

    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
