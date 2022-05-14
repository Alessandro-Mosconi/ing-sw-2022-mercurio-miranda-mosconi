package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class MoveMNState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi movimento
        int movement = gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForMNMovement();
        int motherPosition = gameController.getGame().getIslandManager().getMNPosition();

        //this method can be rewritten receiving only 1 parameter which would be the sum of motherPosition + movement
        gameController.getGame().getIslandManager().moveMotherNature(motherPosition, movement);
        gameController.getGame().getIslandManager().checkForMerge(motherPosition);
        gameController.getGame().getIslandManager().getIslandList().get(motherPosition+movement).assignInfluence(gameController.getGame().getSchoolBoards());

        updateNextState(gameController);
        //if the user clicks on the card then we start another state?
    }

    @Override
    public void updateNextState(GameController gameController) {

        //questo if controlla che le torri non siano finite e che ci siano più di 3 isole
        if ((gameController.getGame().getIslandManager().getIslandList().size() <= 3) || (gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).getSchoolBoard().getTowersNumber() == 0)) {
            gameController.setCurrentState(new EndGameState());
        }
        else
            gameController.setCurrentState(new ChooseCTState());
    }
    @Override
    public void endState(GameController gameController) {

    }
}
