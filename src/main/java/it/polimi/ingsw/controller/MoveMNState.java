package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Island;

public class MoveMNState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi movimento
        int movement = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getMNShift();
        //int motherPosition = gameController.getGame().getIslandManager().getMNPosition();

        //this method can be rewritten receiving only 1 parameter which would be the sum of motherPosition + movement
        gameController.getGame().moveMN(movement);
        //Island newCurrIsland = gameController.getGame().getIslandManager().getIslandList().get(gameController.getGame().getIslandManager().getCurrMNPosition());
        //getIslandManager().moveMotherNature(motherPosition, movement);
        /*if (newCurrIsland.isNoEntryTile()) {
            gameController.getGame().assignInfluence(newCurrIsland);
            //gameController.getGame().getIslandManager().getIslandList().get(motherPosition + movement).assignInfluence(gameController.getGame().getSchoolBoards());
            if (gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).getSchoolBoard().getTowersNumber() == 0) {
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
        }
        *///This else branch takes the entryTiles back and does not let MN change the influence on this island

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
