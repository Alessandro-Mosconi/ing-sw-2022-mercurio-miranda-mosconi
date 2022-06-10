package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.virtualview.VirtualView;

public class CreateGameState implements GameControllerState{

    @Override
    public void startState(GameController gameController) {
        Game game = new Game();
        VirtualView hostView = gameController.getVirtualViews().get(0);
        game.addListener((ModelListener) gameController.getVirtualViews().get(0).getClientHandler());
        gameController.setVirtualViewsOrderIterator(0);
        game.setGameID(hostView.getIdGame());
        game.setGameMode(hostView.getGamemode());
        game.setNumberOfPlayers(hostView.getPlayersNumber());
        gameController.setGame(game);
        gameController.decreasePlayersToGo();
    }

    @Override
    public void updateNextState(GameController gameController) {
        gameController.setNextState(new LobbyState());
    }

    @Override
    public void endState(GameController gameController) {

    }
}
