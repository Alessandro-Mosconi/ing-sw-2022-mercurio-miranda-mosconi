package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.virtualview.VirtualView;

public class CreateGameState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        //int currentVirtualView = gameController.getCurrentVirtualView();
        Game game = gameController.getGame();
        VirtualView currentView = gameController.getVirtualViews().get(0);
        //game.addListener((ModelListener) currentView.getClientHandler());
        game.setGameID(currentView.getIdGame());
        game.setGameMode(currentView.getGamemode());
        game.setNumberOfPlayers(currentView.getPlayersNumber());
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new GeneratePlayerState());
    }

    @Override
    public void endState(GameController gameController) {

    }
}
