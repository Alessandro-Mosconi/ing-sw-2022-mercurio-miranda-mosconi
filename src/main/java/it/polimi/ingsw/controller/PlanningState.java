package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.MessageType;

public class PlanningState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.setErrorFlag(false);
        AssistantCard chosenCard = gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForAssistantCard();
        if (chosenCard != null) {
            gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).useAssistantCard(chosenCard);
        }
        else gameController.setErrorFlag(true);
    }
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.isErrorFlag()){
            gameController.setNextState(new PlanningState());
        }
        else if(gameController.getCurrentVirtualView()<gameController.getVirtualViews().size()-1){
            gameController.setNextState(new PlanningState());
            gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).setMyTurn(false);
            gameController.setCurrentVirtualView((gameController.getVirtualViewsOrder().get((gameController.getCurrentVirtualView())+1)% gameController.getVirtualViews().size()));
            gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).setMyTurn(true);
        }
        else{
            gameController.getGame().updatePlayerOrder();
            gameController.setVirtualViewsOrder(gameController.getGame().calculatePlayerOrder());
            gameController.getGame().fillCloudTiles();
            if(gameController.getGame().isBagEmpty()){
                gameController.setLastRound(true);
            }
            for (int i = 0; i < gameController.getGame().getNumberOfPlayers(); i++) {
                if (gameController.getGame().getPlayers().get(i).getDeck().getCards().size() == 0) {
                    gameController.setLastRound(true);
                }
            }
            gameController.setNextState(new PreActionState());
            gameController.setCurrentVirtualView(0);
        }
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
