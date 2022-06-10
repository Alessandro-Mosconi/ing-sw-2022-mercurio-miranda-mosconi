package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Island;

public class MoveMNState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        int movement = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getMNShift();
        gameController.getGame().moveMN(movement);
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new ChooseCTState());
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
