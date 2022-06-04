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

            gameController.setVirtualViewsOrderIterator(0);//TODO IN REALTA DEVE ESSERE RANDOM E NON 0
            gameController.getVirtualViews().get(0).getClientHandler().tellToPlay();
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
