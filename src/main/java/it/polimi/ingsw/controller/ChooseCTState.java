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
        //getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).moveFromCloudToEntrance(chosenCT);
        /*for(PawnColor color : PawnColor.values()){
            gameController.getGame().getCloudTiles().get(chosenCloudID).reset(color);
        }  è gestito sul model quando viene presa la cloud*/
        gameController.decreasePlayersToGo();
    }
    @Override
    public void updateNextState(GameController gameController) {
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
            //gameController.setVirtualViewsOrderIterator(0);
            gameController.resetPlayersToGo();
            gameController.nextVirtualView();
        }
    }
    @Override
    public void endState(GameController gameController) {
        if(gameController.isCardUsed()){
            gameController.endCardEffect();
        }
        gameController.setPreviousState(this);
    }
    /*
    public int calculateBag(GameController gameController){
        int students = 0;
        for (PawnColor color : PawnColor.values()){
            students += gameController.getGame().getBag().get(color);
        }

        return students;
    }*/
}
