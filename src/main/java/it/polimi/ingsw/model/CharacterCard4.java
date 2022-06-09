package it.polimi.ingsw.model;

public class CharacterCard4 implements CardBehavior{


    /*
        Eff: puoi muovere madre natura di 2 isole in piu di quanto scritto sulla carta scelta
     */

    public void startEffect(Parameter parameter){
        parameter.getPlayer().setBonus2Shifts(true);
        //TODO mandare update MAXSHIFT!!
    }


    public void initializeCard(Parameter parameter) {
        //none
    }


    public void endEffect(Parameter parameter) {
        parameter.getPlayer().setBonus2Shifts(false);
    }
}
