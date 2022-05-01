package it.polimi.ingsw.model;

public class CharacterCard6 implements CardBehavior{

    // durante il conteggio dell'influenza su isola le torri non vengono considerate
    @Override
    public void Effect(Parameter parameter) {
        for(SchoolBoard p : parameter.getGame().getSchoolBoards()){
            if(parameter.getIsland().getTowerColor().equals(p.getTowersColor())){
                //parameter.getIsland().calculatePlayerInfluence(p) -= parameter.getIsland().getTowersNumber();
                //credo vada assegnato al player il valore dell'influenza per poter implementare in questo modo la carta
            }
        }
        //??
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }
}

