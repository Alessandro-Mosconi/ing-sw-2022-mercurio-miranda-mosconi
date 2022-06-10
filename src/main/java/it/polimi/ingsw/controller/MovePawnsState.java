package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualview.VirtualView;

import java.util.Map;

public class MovePawnsState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        int currOrder = gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator());
        VirtualView currVV = gameController.getVirtualViews().get(currOrder);
        Player currPlayer = gameController.getGame().getPlayers().get(currOrder);
        PawnColor studentToMove = currVV.getStudentToMove();
        int destination = currVV.getDestination();
        if(destination == -1){
            gameController.getGame().movePawnToHall(currPlayer, studentToMove);
            gameController.decreaseMovesToGo();
        }
        else{
            Island destinationIsland = gameController.getGame().getIslandManager().getIslandList().get(destination);
            gameController.getGame().movePawnToIsland(currPlayer, studentToMove, destinationIsland);
            gameController.decreaseMovesToGo();
        }
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getMovesToGo()==0) {
            gameController.resetMovesToGo();
            gameController.setNextState(new MoveMNState());
        }
        else{
            gameController.setNextState(new MovePawnsState());
        }
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }

}
