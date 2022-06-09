package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class GeneratePlayerState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
       //gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).setGameController(gameController);
        //gameController.getGame().addListener((ModelListener) gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getClientHandler());
        //aggiungo  i listener nel lobbyState
        Player playerToAdd = new Player();
        playerToAdd.setDeck(new Deck());
        playerToAdd.setNickName(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getUsername());
        playerToAdd.getDeck().setWizard(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getWizard());
        gameController.getGame().removeWizard(playerToAdd.getDeck().getWizard());
        playerToAdd.getSchoolBoard().setTowersColor(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getTowerColor());
        gameController.getGame().removeTowerColor(playerToAdd.getSchoolBoard().getTowersColor());
        playerToAdd.setPlayerNumber(gameController.getGame().getNumberOfPlayers() - gameController.getPlayersToGo());
        //gameController.getGame().setPlayers(new ArrayList<>());
        gameController.getGame().addPlayer(playerToAdd);
        gameController.decreasePlayersToGo();
        //gameController.getVirtualViewsOrder().add(playerToAdd.getPlayerNumber());
        //gameController.getGame().addListener(gameController.getClientHandlerArrayList().get(gameController.getVirtualViewsOrderIterator()));
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getPlayersToGo()==0){
            gameController.getGame().setupGame();
            //gameController.setVirtualViewsOrder(gameController.getGame().getPlayerOrder());
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
            //gameController.decreasePlayersToGo();
            gameController.setNextState(new GeneratePlayerState());
            gameController.nextVirtualView();
        }
    }

    @Override
    public void endState(GameController gameController) {

    }
}