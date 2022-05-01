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

    //perché assignInfluence di Island prende come input un array normale se abbiamo fatto tutto il resto in arraylist? --todo modificarlo in island
}
