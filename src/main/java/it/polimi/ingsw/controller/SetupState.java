package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

public class SetupState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        gameController.getGame().setupGame();
        //serve un metodo che dica ai client che il game è stato settato?
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new PlanningState());
    }
    @Override
    public void endState(GameController gameController) {

    }
}
