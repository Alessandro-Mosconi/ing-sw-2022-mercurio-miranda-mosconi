package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualview.VirtualView;

import java.util.Map;

public class MovePawnsState implements GameControllerState{

    /**
     * Takes from the current Virtual View a student color and a destination and performs the corresponding action on the model. It also updates the number of moves left.
     * @param gameController is the given controller.
     */
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

    /**
     * Checks if there are any move left. In that case, it sets the next state to a new MovePawnsState. Otherwise, it sets the next state to a new MoveMNState.
     * @param gameController is the given controller.
     */
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

    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }

}
