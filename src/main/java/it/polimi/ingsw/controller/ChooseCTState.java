package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;

public class ChooseCTState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi cloud
        for(PawnColor color :PawnColor.values()){
            //cloud scelta
            //gameController.getGame().getSchoolBoards().get(1).addStudentEntrance(color,gameController.getGame().getCloudTiles().get(1).getStudents().get(color));
            //reset cloud scelta
        }
    }
    @Override
    public void updateNextState(GameController gameController) {
        /*
        if(endRound)gameController.setCurrentState(new PlanningState());
        if(endTurn)gameController.setCurrentState(new MovePawnsState());
        */
    }
    @Override
    public void endState() {

    }
}
