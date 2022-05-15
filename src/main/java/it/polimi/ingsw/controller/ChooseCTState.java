package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class ChooseCTState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {

        //questo metodo chiede al client la nuvola scelta
        int chosenCloud = gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForCloudTile();
        gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).moveFromCloudToEntrance(gameController.getGame().getCloudTiles().get(chosenCloud));

        //potrebbe essere ottimizzata? Alla fine è un for con 5 valori, stica
        for(PawnColor color : PawnColor.values()){
            gameController.getGame().getCloudTiles().get(chosenCloud).reset(color);
        }
    }
    @Override
    public void updateNextState(GameController gameController) {
        if(!gameController.isCardUsed()&&gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askIfCard()){
            gameController.setPreviousState(this);
            gameController.setNextState(new ChosenCharCardState());
        }
        //this branch lets the following player in order start their turn
        else if (gameController.getCurrentVirtualView()<gameController.getVirtualViews().size()-1){
            gameController.setNextState(new MovePawnsState());
            gameController.setCurrentVirtualView(gameController.getCurrentVirtualView()+1 /*todo % gameController.getVirtualViews().size()*/);
        }
        //this branch is when the players have finished their cards or there are no students left in the bag
        /*else if ((gameController.getGame().getPlayers().get(0).getDeck().getCards().size() < 1) || (calculateBag(gameController) <1)){
            gameController.setNextState(new EndGameState());
        }*/
        else if(gameController.isLastRound()){
            gameController.setNextState(new EndGameState());
        }
        //this branch is for when a new round starts
        else{
            gameController.setNextState(new PlanningState());
            gameController.setCurrentVirtualView(0);
        }

    }
    @Override
    public void endState(GameController gameController) {

    }
    /*
    public int calculateBag(GameController gameController){
        int students = 0;
        for (PawnColor color : PawnColor.values()){
            students += gameController.getGame().getBag().get(color);
        }

        return students;
    }*/
}
