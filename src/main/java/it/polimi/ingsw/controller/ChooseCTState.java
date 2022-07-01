package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

public class ChooseCTState implements GameControllerState{

    /**
     * Takes from the current Virtual View the chosen Cloud Tile and performs the corresponding action on the model.
     * @param gameController is the given controller.
     */
    @Override
    public void startState(GameController gameController) {
        Player currPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
        int chosenCloudID = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getChosenCloudID();
        CloudTile chosenCT = gameController.getGame().getCloudTiles().get(chosenCloudID);
        gameController.getGame().moveFromCloudToEntrance(currPlayer, chosenCT);
        gameController.decreasePlayersToGo();
    }

    /**
     * Checks if, during this turn, a character card was activated. In that case, it ends its effect. Then, if the currently playing user was not the last of the round,
     * the virtual view is updated and the next state is set as a new MovePawnsState; otherwise, if this was the last round (empty bag or no assistant cards left) the next state is
     * a new EndGameState. In any other case, it starts a new round by resetting the number of players to go and setting the next state to a new AssistantSelectionState.
     * @param gameController is the given controller.
     */
    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.isCardUsed()){
            gameController.endCardEffect();
        }
        if(gameController.getPlayersToGo()!=0){
            gameController.setNextState(new MovePawnsState());
            gameController.nextVirtualView();
        }
        else if (gameController.isLastRound()){
            EndGameState endGameState = new EndGameState();
            endGameState.startState(gameController);
            endGameState.endState(gameController);
        }
        else{
            gameController.getGame().fillCloudTiles();
            if(gameController.getGame().isBagEmpty()){
                gameController.setLastRound(true);
            }
            gameController.setNextState(new AssistantSelectionState());
            gameController.resetPlayersToGo();
            gameController.nextVirtualView();
        }
    }
    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {

        gameController.setPreviousState(this);
    }
}
