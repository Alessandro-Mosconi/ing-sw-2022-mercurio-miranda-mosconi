package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class PlanningState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).useAssistantCard(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForAssistantCard());
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getCurrentVirtualView()<gameController.getVirtualViews().size()-1){
            gameController.setNextState(new PlanningState());
            gameController.setCurrentVirtualView((gameController.getCurrentVirtualView()+1));
        }
        else{
            gameController.getGame().updatePlayerOrder();
            //todo updateVirtualViewsOrder
            gameController.getGame().fillCloudTiles();
            gameController.setNextState(new MovePawnsState());
            gameController.setCurrentVirtualView(0);
        }
    }
    @Override
    public void endState(GameController gameController) {

    }
}
