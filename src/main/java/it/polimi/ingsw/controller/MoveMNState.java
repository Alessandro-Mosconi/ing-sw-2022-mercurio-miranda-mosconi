package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class MoveMNState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi movimento
        //gameController.getGame().getIslandManager().moveMotherNature();
        //gameController.getGame().getIslandManager().checkForMerge();
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new ChooseCTState());
    }
    @Override
    public void endState() {

    }
}
