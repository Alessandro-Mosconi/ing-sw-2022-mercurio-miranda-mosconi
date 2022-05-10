package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public class MoveMNState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi movimento
        //gameController.getGame().getIslandManager().moveMotherNature();
        //gameController.getGame().getIslandManager().checkForMerge();
        //if the user clicks on the card then we start
    }

    @Override
    public void updateNextState(GameController gameController) {

        //todo bisogna aggiungere il check sulle torri
        if (gameController.getGame().getIslandManager().getIslandList().size() <= 3) {
            gameController.setCurrentState(new EndGameState());
        }
        else
            gameController.setCurrentState(new ChooseCTState());
    }
    @Override
    public void endState(GameController gameController) {

    }
}
