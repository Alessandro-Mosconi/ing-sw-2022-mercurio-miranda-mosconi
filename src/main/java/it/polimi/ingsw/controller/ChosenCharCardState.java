package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.*;

public class ChosenCharCardState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        gameController.setCardUsed(true);
        Player currPlayer = gameController.getGame().getPlayers().get(gameController.getGame().getCurrPlayer());
        Parameter parameter = new Parameter(gameController.getGame(), currPlayer);
        CharacterCard chosenCard = gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForCard();
        if(chosenCard.getPrice()>gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).getWallet()){
            //todo send error
            this.endState(gameController);
        }else{
            gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).setWallet(gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).getWallet()-chosenCard.getPrice());
            for(CharacterCard c: gameController.getGame().getChosenCharacterCards()){
                if(c.equals(chosenCard)){
                    c.increasePrice();
                }
            }
        }
        String T = chosenCard.getClass().getSimpleName();
        switch (T) {
            case ("CharacterCard1"), ("CharacterCard9"),("CharacterCard11"), ("CharacterCard12") -> parameter.setChosenColor(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForColor());
            /*case("CharacterCard2"):
                break;*/
            case ("CharacterCard3"),("CharacterCard5") -> parameter.setIsland(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForIsland());
            /*case("CharacterCard4"):
                break;*/
            //case ("CharacterCard5") -> parameter.setIsland(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForIsland());
            /*case("CharacterCard6"):
                break;*/
            case ("CharacterCard7"), ("CharacterCard10") -> {
                parameter.setColorMap1(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForStudToTake());
                parameter.setColorMap2(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForStudToGive());
            }
            /*case("CharacterCard8"):
                break;*/
            //case ("CharacterCard9") -> parameter.setChosenColor(gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForColor());
        }
        parameter = gameController.getVirtualViews().get(gameController.getCurrentVirtualView()).askForParameter();
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
            if ((gameController.getGame().getIslandManager().getIslandList().size() <= 3) || (gameController.getGame().getPlayers().get(gameController.getCurrentVirtualView()).getSchoolBoard().getTowersNumber() == 0)) {
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
    }
    @Override
    public void updateNextState(GameController gameController) {

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
