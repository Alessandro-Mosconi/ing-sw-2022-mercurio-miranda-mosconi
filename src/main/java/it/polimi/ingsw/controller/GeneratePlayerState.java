package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public class GeneratePlayerState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        Player playerToAdd = new Player();
        playerToAdd.setDeck(new Deck());
        playerToAdd.setNickName(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getUsername());
        playerToAdd.getDeck().setWizard(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getWizard());
        gameController.getGame().removeWizard(playerToAdd.getDeck().getWizard());
        playerToAdd.getSchoolBoard().setTowersColor(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getTowerColor());
        gameController.getGame().removeTowerColor(playerToAdd.getSchoolBoard().getTowersColor());
        gameController.getGame().setPlayers(new ArrayList<>());
        gameController.getGame().getPlayers().add(playerToAdd);
        gameController.decreasePlayersToGo();
        //TODO il listener va inserito quando si joina o va bene qui?
        gameController.getGame().addListener(gameController.getClientHandlerArrayList().get(gameController.getVirtualViewsOrderIterator()));
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getPlayersToGo()==0){
            gameController.getGame().setupGame();
            gameController.setVirtualViewsOrder(gameController.getGame().getPlayerOrder());
            gameController.setNextState(new AssistantSelectionState());
            gameController.setVirtualViewsOrderIterator(0);
        }
        else{
            gameController.setNextState(new GeneratePlayerState());
            gameController.nextVirtualView();
        }
    }

    @Override
    public void endState(GameController gameController) {

    }
}
