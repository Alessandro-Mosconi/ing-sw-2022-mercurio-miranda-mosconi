package it.polimi.ingsw.controller;

public class SetupState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        gameController.getGame().setupGame();
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new AssistantSelectionState());
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
