package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

import java.util.Map;

public class ChosenCharCardState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        gameController.setCardUsed(true);
        Player currPlayer = gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator()));
        Parameter parameter = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getParameter();
        parameter.setPlayer(currPlayer);
        int cardID = gameController.getVirtualViews().get(gameController.getVirtualViewsOrder().get(gameController.getVirtualViewsOrderIterator())).getChosenCharacterCardID();
        CharacterCard chosenCard = gameController.getGame().getAllCharacterCard().get(cardID);
        /*if(chosenCard.getPrice()>gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).getWallet()){
            String prvState = gameController.getPreviousState().getClass().getSimpleName();
            switch (prvState){
                case ("PreActionState") -> gameController.setNextState(new MovePawnsState());
                case ("MovePawnsState") -> gameController.setNextState(new MoveMNState());
                case ("MoveMNState") -> gameController.setNextState(new ChooseCTState());
                case ("ChooseCTState") -> gameController.setNextState(new EndActionState());
            }
            this.endState(gameController);
        }else{
            gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).setWallet(gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).getWallet()-chosenCard.getPrice());
            for(CharacterCard c: gameController.getGame().getChosenCharacterCards()){
                if(c.equals(chosenCard)){
                    c.increasePrice();
                }
            }
        }
        String T = chosenCard.getClass().getSimpleName();
        switch (T) {
            case ("CharacterCard1"), ("CharacterCard9"),("CharacterCard11"), ("CharacterCard12") -> {
                PawnColor chosenColor = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForColor();
                if(chosenColor!=null) {
                    parameter.setChosenColor(chosenColor);
                }
                else{
                    gameController.setErrorFlag(true);
                    while(gameController.isErrorFlag()){
                        chosenColor = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForColor();
                        if(chosenCard!=null){
                            parameter.setChosenColor(chosenColor);
                            gameController.setErrorFlag(false);
                        }
                    }
                }
            }
            case ("CharacterCard3"),("CharacterCard5") -> {
                Island chosenIsland = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForIsland();
                if(chosenIsland!=null){
                    parameter.setIsland(chosenIsland);
                }
                else{
                    gameController.setErrorFlag(true);
                    while (gameController.isErrorFlag()){
                        chosenIsland = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForIsland();
                        if(chosenIsland!=null){
                            parameter.setIsland(chosenIsland);
                            gameController.setErrorFlag(false);
                        }
                    }
                }
            }
            case ("CharacterCard7"), ("CharacterCard10") -> {
                Map<PawnColor,Integer> studToTake  = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForStudToTake();
                Map<PawnColor,Integer> studToGive = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForStudToGive();
                if(studToTake!=null&&studToGive!=null){
                    parameter.setColorMap1(studToTake);
                    parameter.setColorMap2(studToGive);
                }
                else{
                    gameController.setErrorFlag(true);
                    while(gameController.isErrorFlag()){
                        studToTake  = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForStudToTake();
                        studToGive = gameController.getVirtualViews().get(gameController.getVirtualViewsOrderIterator()).askForStudToGive();
                        if(studToTake!=null&&studToGive!=null){
                            parameter.setColorMap1(studToTake);
                            parameter.setColorMap2(studToGive);
                            gameController.setErrorFlag(false);
                        }
                    }
                }
            }
        }
        chosenCard.getCardBehavior().Effect(parameter);
        if(gameController.getPreviousState() instanceof PreActionState){
            MovePawnsState movePawnsState = new MovePawnsState();
            movePawnsState.startState(gameController);
            movePawnsState.endState(gameController);
        }
        if(gameController.getPreviousState() instanceof PreActionState || gameController.getPreviousState() instanceof MovePawnsState){
            MoveMNState moveMNState = new MoveMNState();
            moveMNState.startState(gameController);
            moveMNState.endState(gameController);
            if ((gameController.getGame().getIslandManager().getIslandList().size() <= 3) || (gameController.getGame().getPlayers().get(gameController.getVirtualViewsOrderIterator()).getSchoolBoard().getTowersNumber() == 0)) {
                EndGameState endGameState = new EndGameState();
                endGameState.startState(gameController);
            }
        }
        if(gameController.getPreviousState() instanceof PreActionState || gameController.getPreviousState() instanceof MovePawnsState || gameController.getPreviousState() instanceof MoveMNState){
            ChooseCTState chooseCTState = new ChooseCTState();
            chooseCTState.startState(gameController);
            chosenCard.getCardBehavior().endEffect(parameter);
            chooseCTState.endState(gameController);
            if ((gameController.getGame().getPlayers().get(0).getDeck().getCards().size() < 1) || (calculateBag(gameController) <1)){
                EndGameState endGameState = new EndGameState();
                endGameState.startState(gameController);
            }
        }
    */
        gameController.setCurrEffect(chosenCard);
        gameController.setCurrParameter(parameter);
        //chosenCard.getCardBehavior().Effect(parameter);
    }
    @Override
    public void updateNextState(GameController gameController) {
        GameControllerState prvState = gameController.getPreviousState();
        prvState.updateNextState(gameController); //Dovrebbe funzionare
    }
    @Override
    public void endState(GameController gameController) {
        gameController.setCardUsed(false);
    }
    public int calculateBag(GameController gameController){
        int students = 0;
        for (PawnColor color : PawnColor.values()){
            students += gameController.getGame().getBag().get(color);
        }
        return students;
    }
}
