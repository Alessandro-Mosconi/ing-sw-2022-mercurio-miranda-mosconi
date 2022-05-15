package it.polimi.ingsw.model;

public class CharacterCard3 implements CardBehavior{
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
