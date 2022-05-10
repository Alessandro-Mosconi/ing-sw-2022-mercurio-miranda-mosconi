package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;

public class MovePawnsState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        //todo aggiungere questi metodi
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
    public void endState(GameController gameController) {

    }
}
