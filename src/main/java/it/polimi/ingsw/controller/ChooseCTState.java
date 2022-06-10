package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

public class ChooseCTState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        Player currPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
        int chosenCloudID = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getChosenCloudID();
        CloudTile chosenCT = gameController.getGame().getCloudTiles().get(chosenCloudID);
        gameController.getGame().moveFromCloudToEntrance(currPlayer, chosenCT);
        gameController.decreasePlayersToGo();
    }
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.isCardUsed()){
            gameController.endCardEffect();
        }
        if(gameController.getPlayersToGo()!=0){
            gameController.setNextState(new MovePawnsState());
            gameController.nextVirtualView();
        }
        else if (gameController.isLastRound()){
            EndGameState endGameState = new EndGameState();
            endGameState.startState(gameController);
        }
        else{
            gameController.setNextState(new AssistantSelectionState());
            gameController.resetPlayersToGo();
            gameController.nextVirtualView();
        }
    }
    @Override
    public void endState(GameController gameController) {

        gameController.setPreviousState(this);
    }
}
