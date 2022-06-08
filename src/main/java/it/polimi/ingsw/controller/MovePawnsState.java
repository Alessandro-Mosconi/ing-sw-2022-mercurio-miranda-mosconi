package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.virtualview.VirtualView;

import java.util.Map;

public class MovePawnsState implements GameControllerState{
    //questo attributo lo uso per capire quante pedine sono state spostate dai metodi nel caso in cui ci siano spostamenti misti
    @Override
    public void startState(GameController gameController) {
        //Assumo che il client mi mandi un messaggio con le pedine (secondo me meglio mappa) + destinazione
        //nella VV dovrei fare un controllo che si tratti di Schoolboard o Island e che poi chiami il metodo giusto nel controller.
        //Probabilmente dovremmo fare una synch sulla schoolboard e poi svegliare questo metodo? così da farlo continuare
        //Player currPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
        int currOrder = gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator());
        VirtualView currVV = gameController.getVirtualViews().get(currOrder);
        Player currPlayer = /*currVV.getPlayer();*/ gameController.getGame().getPlayers().get(currOrder);
        //currPlayer.setNickName(currVV.getUsername());
        PawnColor studentToMove = currVV.getStudentToMove();//gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getStudentToMove();
        int destination = currVV.getDestination();//gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getDestination();
        if(destination == -1){
            gameController.getGame().movePawnToHall(currPlayer, studentToMove);
            //getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).moveFromEntranceToHall(studentToMove);
            gameController.decreaseMovesToGo();
        }
        else{
            Island destinationIsland = gameController.getGame().getIslandManager().getIslandList().get(destination);
            gameController.getGame().movePawnToIsland(currPlayer, studentToMove, destinationIsland);
            //getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).moveFromEntranceToIsland(destinationIsland,studentToMove);
            gameController.decreaseMovesToGo();
        }
    }

    @Override
    public void updateNextState(GameController gameController) {
        if(gameController.getMovesToGo()==0) {
            gameController.resetMovesToGo();
            gameController.setNextState(new MoveMNState());
        }
        else{
            gameController.setNextState(new MovePawnsState());
        }
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setPreviousState(this);
    }

    //Se modifichiamo il metodo "moveFromEntrancetoHall" potremmo semplificare un po' questo metodo e togliere il for.
    /*public void movePawnToSchoolboard(GameController gameController, Map<PawnColor, Integer> pawnMap, SchoolBoard schoolBoard){
        for (PawnColor currentColor: pawnMap.keySet()){
            while (pawnMap.get(currentColor) > 0){
                gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).moveFromEntranceToHall(currentColor);
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
                gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).moveFromEntranceToIsland(island, currentColor);
                pawnMap.replace(currentColor, pawnMap.get(currentColor)-1);
                numUpdates++;
            }
        };
    }*/
}
