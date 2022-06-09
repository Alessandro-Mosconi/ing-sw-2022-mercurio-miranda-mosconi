package it.polimi.ingsw.model;

public class CharacterCard3 implements CardBehavior{
    /*public CharacterCard3(int id, int price) {
        super(id, price);
    }
/*
    Eff: scegli un'isola e calcola l'infuenza
 */

    public void startEffect(Parameter parameter){
        parameter.getIsland().assignInfluence(parameter.getGame().getSchoolBoards());
    }

    public void initializeCard(Parameter parameter) {
        //none
    }

    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
