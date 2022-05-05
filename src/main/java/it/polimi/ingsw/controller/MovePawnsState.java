package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;

public class MovePawnsState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        //decidi colore
        //decidi destinazione
        //if(destinazione isIsland)
        //if(destinazione schoolBoard) updateProfessor
    }

    @Override
    public void updateNextState(GameController gameController) {
       gameController.setCurrentState(new MoveMNState());
    }
    @Override
    public void endState() {

    }
}
