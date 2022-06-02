package it.polimi.ingsw.model;

public class CharacterCard6 extends CharacterCard{

    public CharacterCard6(int id, int price) {
        super(id, price);
    }

    // durante il conteggio dell'influenza su isola le torri non vengono considerate
    @Override
    public void Effect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(false);
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }

    @Override
    public void endEffect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(true);
    }
}

