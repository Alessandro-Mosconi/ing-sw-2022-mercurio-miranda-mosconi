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
        if (!gameController.getGame().getIslandManager().getIslandList().get(gameController.getGame().getIslandManager().getCurrMNPosition()).isNoEntryTile()) {
            gameController.getGame().getIslandManager().getIslandList().get(motherPosition + movement).assignInfluence(gameController.getGame().getSchoolBoards());
            if (gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).getSchoolBoard().getTowersNumber() == 0) {
                EndGameState endGameState = new EndGameState();
                endGameState.startState(gameController);
            }
            gameController.getGame().getIslandManager().checkForMerge(motherPosition);
            if (gameController.getGame().getIslandManager().getIslandList().size() <= 3) {
                EndGameState endGameState = new EndGameState();
                endGameState.startState(gameController);
            }
        }
        else {
            gameController.getGame().getIslandManager().getIslandList().get(gameController.getGame().getIslandManager().getCurrMNPosition()).setNoEntryTile(false);
            gameController.getGame().setEntryTiles(gameController.getGame().getEntryTiles()+1);
        }//This else branch takes the entryTiles back and does not let MN change the influence on this island
        updateNextState(gameController);
        //if the user clicks on the card then we start another state?
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(!gameController.isCardUsed()&&gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askIfCard()){
            gameController.setPreviousState(this);
            gameController.setNextState(new ChosenCharCardState());
        }
        else
            gameController.setCurrentState(new ChooseCTState());
    }
    @Override
    public void endState(GameController gameController) {

    }
}
