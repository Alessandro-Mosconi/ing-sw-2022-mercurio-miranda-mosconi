package it.polimi.ingsw.controller;

public class LoadingState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        while(gameController.getClientHandlerArrayList().size()!=gameController.getGame().getNumberOfPlayers()){
            doNothing();//The controller waits for all the expected clients to join the game
        }
        updateNextState(gameController);
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new SelectWizardState());
    }

    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
    public void doNothing(){

    }
}
