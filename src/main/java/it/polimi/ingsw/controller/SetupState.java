package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.virtualview.VirtualView;

public class SetupState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        gameController.getGame().setupGame();
        /*for(VirtualView v : gameController.getVirtualViews()){
            v.setOut_type(MessageType.ModelUpdate);
        }
        in teoria non serve: il model cambia e parte la update delle singole virtual view
         */
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new PlanningState());
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }
}
