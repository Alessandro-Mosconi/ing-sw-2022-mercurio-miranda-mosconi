package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualview.VirtualView;

public class AssistantSelectionState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        gameController.setErrorFlag(false);
         int currOrder = gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator());
         VirtualView currVV = gameController.getVirtualViews().get(currOrder);
         Player currentPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
         AssistantCard chosenCard = currentPlayer.getDeck().getCards().get(currVV.getChosenAssistantID()-1);//currVV.getPlayer().getDeck().getCards().get(currVV.getChosenAssistantID()-1);
       // AssistantCard chosenCard = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).getDeck().getCards().get(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getChosenAssistantID());
        if (chosenCard != null) {
            //Player currPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
            gameController.getGame().useAssistantCard(currentPlayer, chosenCard);
            gameController.decreasePlayersToGo();
        }
        else gameController.setErrorFlag(true);
    }
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.isErrorFlag()){
            gameController.setNextState(new AssistantSelectionState()); //il client riceve l'error e non updata la sua fase quindi può ripetere la scelta
        }
        else if(gameController.getPlayersToGo()!=0){
            gameController.setNextState(new AssistantSelectionState());
            //gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).setMyTurn(false);
            gameController.nextVirtualView();
            //gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).setMyTurn(true);
        }
        else if (gameController.getPlayersToGo()==0){
            //gameController.getGame().updatePlayerOrder();
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
            gameController.resetMovesToGo();
            gameController.resetPlayersToGo();
            gameController.setNextState(new MovePawnsState());
            gameController.setVirtualViewsOrderIterator(0);
            gameController.getClientHandlerArrayList().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).tellToPlay();
        }
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
