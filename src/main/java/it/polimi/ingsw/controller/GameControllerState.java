package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public interface GameControllerState {
     void startState(GameController gameController);
     void updateNextState(GameController gameController);
     void endState(GameController gameController);
}
