package it.polimi.ingsw.model;

public class CharacterCard4 implements CardBehavior{
/*
    Eff: puoi muovere madre natura di 2 isole in piu di quanto scritto sulla carta scelta
 */
    @Override
    public void Effect(Parameter parameter){
        parameter.getPlayer().setBonus2Shifts(true);
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }

    @Override
    public void endEffect(Parameter parameter) {
        parameter.getPlayer().setBonus2Shifts(false);
    }
}
