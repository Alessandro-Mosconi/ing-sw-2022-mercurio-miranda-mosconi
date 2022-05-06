package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public class PlanningState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        for(Player p : gameController.getGame().getPlayers()){
            //decidiAssistantCard

        }
        gameController.getGame().updatePlayerOrder();
        gameController.getGame().fillCloudTiles();
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new MovePawnsState());
    }
    @Override
    public void endState() {

    }
}
