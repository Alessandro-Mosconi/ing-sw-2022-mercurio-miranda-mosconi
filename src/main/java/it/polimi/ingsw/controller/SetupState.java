package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;

public class SetupState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //gameController.getGame().setGameMode(gameController.getView().getGameMode());
        //gameController.getGame().setWizards(/*da client*/);
        gameController.getGame().setupGame();
        //leggermente da modificare per i metodi di init
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new PlanningState());
    }
    @Override
    public void endState() {

    }
}
