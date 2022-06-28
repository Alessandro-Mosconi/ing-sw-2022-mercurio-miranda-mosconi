package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

public interface GameControllerState {

     /**
      * Starts the given controller's state.
      * @param gameController is the given controller.
      */
     void startState(GameController gameController);

     /**
      * Updates the given controller's next state.
      * @param gameController is the given controller.
      */
     void updateNextState(GameController gameController);

     /**
      * Ends the given controller's current state.
      * @param gameController is the given controller.
      */
     void endState(GameController gameController);
}
