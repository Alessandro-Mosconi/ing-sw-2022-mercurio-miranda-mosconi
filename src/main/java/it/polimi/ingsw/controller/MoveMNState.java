package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Island;

public class MoveMNState implements GameControllerState{

    /**
     * Takes from the current Virtual View the chosen Mother Nature shift and performs the corresponding action on the model. Then, it checks for end game conditions related to towers number or number of islands left.
     * @param gameController is the given controller.
     */
    @Override
    public void startState(GameController gameController) {
        int movement = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getMNShift();
        gameController.getGame().moveMN(movement);
        if(gameController.getGame().checkForEndGameConditions()){
            EndGameState endGameState = new EndGameState();
            endGameState.startState(gameController);
            endGameState.endState(gameController);
        }
    }

    /**
     * Sets the next state to a new ChooseCTState.
     * @param gameController is the given controller.
     */
    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new ChooseCTState());
    }

    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
