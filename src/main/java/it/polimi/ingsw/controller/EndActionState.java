package it.polimi.ingsw.controller;

public class EndActionState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //do nothing
    }

    @Override
    public void updateNextState(GameController gameController) {
        if (gameController.getCurrentVirtualView()<gameController.getVirtualViews().size()-1){
            gameController.setNextState(new MovePawnsState());
            gameController.setCurrentVirtualView((gameController.getVirtualViewsOrder().get((gameController.getCurrentVirtualView())+1)% gameController.getVirtualViews().size()));
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
        //do nothing
    }
}
