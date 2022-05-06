package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

public class GeneratePlayersState implements GameControllerState{
    @Override /*riceve un game new ma vuoto*/
    public void startState(GameController gameController) {
        //man mano che riceve players li aggiunge alla ArrayList di players
        //per ogni player che riceve setta i dati del player (nickName , mago, colore torri etc)
        updateNextState(gameController);
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setCurrentState(new SetupState());
    }

    @Override
    public void endState() {

    }
}
