package it.polimi.ingsw.model;

public class CharacterCard6 implements CardBehavior{


    // durante il conteggio dell'influenza su isola le torri non vengono considerate
    public void startEffect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(false);
    }

    public void initializeCard(Parameter parameter) {
        //none
    }

    public void endEffect(Parameter parameter) {
        parameter.getGame().setTowersDoCount(true);
    }
}

