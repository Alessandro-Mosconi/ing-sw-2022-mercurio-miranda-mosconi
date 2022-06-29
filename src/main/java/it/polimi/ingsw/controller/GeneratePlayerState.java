package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class GeneratePlayerState implements GameControllerState{

    /**
     * Adds a player (which is initialized with a deck - and a corresponding wizardType - a nickname, a schoolboard and a towers color chosen by the user) to the game.
     * @param gameController is the given controller.
     */
    @Override
    public void startState(GameController gameController) {
        Player playerToAdd = new Player();
        playerToAdd.setDeck(new Deck());
        playerToAdd.setNickName(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getUsername());
        playerToAdd.getDeck().setWizard(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getWizard());
        gameController.getGame().removeWizard(playerToAdd.getDeck().getWizard());
        playerToAdd.getSchoolBoard().setTowersColor(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getTowerColor());
        gameController.getGame().removeTowerColor(playerToAdd.getSchoolBoard().getTowersColor());
        playerToAdd.setPlayerNumber(gameController.getGame().getNumberOfPlayers() - gameController.getPlayersToGo());
        gameController.getGame().addPlayer(playerToAdd);
        gameController.decreasePlayersToGo();
    }

    /**
     * Checks whether each user chose the necessary settings or not. In that case, randomically selects the first player, to which it tells to play. Otherwise, it updates the next VirtualView, corresponding to the next user to select the player settings.
     * @param gameController is the given controller.
     */
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getPlayersToGo()==0){
            gameController.getGame().setupGame();
            System.out.println("inizia planning con players-to-go:" + gameController.getPlayersToGo());
            gameController.resetPlayersToGo();
            gameController.setNextState(new AssistantSelectionState());
            int min = 0;
            int max = gameController.getGame().getNumberOfPlayers()-1;
            int rdNumber = (int) Math.floor(Math.random()*(max-min+1)+min);
            gameController.setVirtualViewsOrderIterator(rdNumber);
            gameController.getVirtualViews().get(rdNumber).getClientHandler().tellToPlay();
        }
        else{
            gameController.setNextState(new GeneratePlayerState());
            gameController.nextVirtualView();
        }
    }

    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {

    }
}
