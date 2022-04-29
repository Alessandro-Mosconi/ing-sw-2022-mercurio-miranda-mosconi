package it.polimi.ingsw.model;

public class CharacterCard6 implements CardBehavior{

    // durante il conteggio dell'influenza su isola le torri non vengono considerate
    @Override
    public void Effect(Parameter parameter) {
        for(SchoolBoard p : parameter.getGame().getSchoolBoards()){
            if(parameter.getIsland().getTowerColor().equals(p.getTowersColor())){
                //parameter.getIsland().calculatePlayerInfluence(p) -= parameter.getIsland().getTowersNumber();
            }
        }
        //??
    }
}

