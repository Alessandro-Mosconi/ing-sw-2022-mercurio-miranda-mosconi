package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameMode;

public class PreActionState implements GameControllerState{
    //Questo stato dovrebbe essere un intermediario tra planning e action phase per permettere al giocatore di scegliere
    //una carta effetto prima della fase di azione
    @Override
    public void startState(GameController gameController) {
        //do nothing
    }

    @Override
    public void updateNextState(GameController gameController) {
        if (gameController.getGame().getGameMode().equals(GameMode.expert)&&gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askIfCard()) {
//            gameController.setPreviousState(this);
            gameController.setNextState(new ChosenCharCardState());
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
