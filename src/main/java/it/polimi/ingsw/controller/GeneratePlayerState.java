package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;

public class GeneratePlayerState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        Player playerToAdd = new Player();
        playerToAdd.setNickName(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getUsername());
        playerToAdd.getDeck().setWizard(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getWizard());
        playerToAdd.getSchoolBoard().setTowersColor(gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).getTowerColor());
        gameController.getGame().getPlayers().add(playerToAdd);
        gameController.decreasePlayersToGo();

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
