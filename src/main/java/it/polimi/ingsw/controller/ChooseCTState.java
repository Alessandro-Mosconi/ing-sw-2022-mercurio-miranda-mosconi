package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
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

        if (gameController.getGame().getGameMode().equals(GameMode.expert)&&!gameController.isCardUsed() && gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askIfCard()) {
            gameController.setNextState(new ChosenCharCardState());
        }
        else{
            gameController.setNextState(new EndActionState());
        }
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
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
