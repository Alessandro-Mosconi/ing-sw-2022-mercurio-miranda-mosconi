package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.virtualview.VirtualView;

public class LobbyState implements GameControllerState{
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

    @Override
    public void endState(GameController gameController) {

    }
}
