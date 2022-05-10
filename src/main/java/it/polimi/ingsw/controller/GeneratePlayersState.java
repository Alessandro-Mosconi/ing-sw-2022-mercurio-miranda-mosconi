package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

public class GeneratePlayersState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.getGame().setPlayers(gameController.getView().getPlayers());
        updateNextState(gameController);
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new SetupState());
    }

    @Override
    public void endState() {

    }
}
