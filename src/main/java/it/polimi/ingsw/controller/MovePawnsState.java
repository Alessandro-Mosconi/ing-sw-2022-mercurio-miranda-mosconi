package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.util.Map;

public class MovePawnsState implements GameControllerState{
    //questo attributo lo uso per capire quante pedine sono state spostate dai metodi nel caso in cui ci siano spostamenti misti
    int numUpdates = 0;

    @Override
    public void startState(GameController gameController) {
        //Assumo che il client mi mandi un messaggio con le pedine (secondo me meglio mappa) + destinazione
        //nella VV dovrei fare un controllo che si tratti di Schoolboard o Island e che poi chiami il metodo giusto nel controller.
        //Probabilmente dovremmo fare una synch sulla schoolboard e poi svegliare questo metodo? così da farlo continuare
        gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForMovement();

        while (numUpdates<3){
            //inutile, devo trovare un modo più intelligente
        }
        updateNextState(gameController);
    }

    @Override
    public void updateNextState(GameController gameController) {
        if (gameController.getGame().getGameMode().equals(GameMode.expert)&&!gameController.isCardUsed() && gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askIfCard()) {
            gameController.setPreviousState(this);
            gameController.setNextState(new ChosenCharCardState());
        }
        else gameController.setNextState(new MoveMNState());
    }
    @Override
    public void endState(GameController gameController) {

    }

    //Se modifichiamo il metodo "moveFromEntrancetoHall" potremmo semplificare un po' questo metodo e togliere il for.
    public void movePawnToSchoolboard(GameController gameController, Map<PawnColor, Integer> pawnMap, SchoolBoard schoolBoard){
        for (PawnColor currentColor: pawnMap.keySet()){
            while (pawnMap.get(currentColor) > 0){
                gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).moveFromEntranceToHall(currentColor);
                pawnMap.replace(currentColor, pawnMap.get(currentColor)-1);
                numUpdates ++;
            }
            gameController.getGame().updateProfessor(currentColor);
        }
    }

    //stessa cosa del metodo di sopra.
    public void movePawnToIsland(GameController gameController, Map<PawnColor, Integer> pawnMap, Island island){
        for (PawnColor currentColor: pawnMap.keySet()){
            while (pawnMap.get(currentColor) > 0){
                gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).moveFromEntranceToIsland(island, currentColor);
                pawnMap.replace(currentColor, pawnMap.get(currentColor)-1);
                numUpdates++;
            }
        };
    }
}
