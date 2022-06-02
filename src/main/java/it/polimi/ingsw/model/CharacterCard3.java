package it.polimi.ingsw.model;

public class CharacterCard3 extends CharacterCard{
    public CharacterCard3(int id, int price) {
        super(id, price);
    }
/*
    Eff: scegli un'isola e calcola l'infuenza
 */

    @Override
    public void Effect(Parameter parameter){
        parameter.getIsland().assignInfluence(parameter.getGame().getSchoolBoards());
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }

    @Override
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
