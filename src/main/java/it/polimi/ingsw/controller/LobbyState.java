package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.network.VirtualView;

public class LobbyState implements GameControllerState{

    /**
     * Decreases the number of players that still need to join.
     * @param gameController is the given controller.
     */
    @Override
    public void startState(GameController gameController) {
        int totPlayers=gameController.getGame().getNumberOfPlayers();
        int pToGo= gameController.getPlayersToGo();
        gameController.getGame().addListener((ModelListener) gameController.getVirtualViews().get(totPlayers-pToGo).getClientHandler());
        gameController.decreasePlayersToGo();
        for(VirtualView vv : gameController.getVirtualViews()){
            vv.getClientHandler().tellAPlayerJoined(gameController.getPlayersToGo());
        }
    }

    /**
     * Checks whether the lobby is full or not and sets the next state to a new GeneratePlayerState (in the case the lobby is full) or a new LobbyState.
     * @param gameController is the given controller.
     */
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getPlayersToGo()==0){
            gameController.resetPlayersToGo();
            gameController.getVirtualViews().get(0).getClientHandler().tellToPlay();
            gameController.setVirtualViewsOrderIterator(0);
            gameController.setNextState(new GeneratePlayerState());
        }
        else {
            gameController.setNextState(new LobbyState());
        }
    }

    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {

    }
}
