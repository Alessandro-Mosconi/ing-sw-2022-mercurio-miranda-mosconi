package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;

public class ChooseCTState implements GameControllerState{
    @Override
    public void startState(GameController gameController) {
        //decidi cloud
        for(PawnColor color : PawnColor.values()){
            //cloud scelta
            //gameController.getGame().getSchoolBoards().get(1).addStudentEntrance(color,gameController.getGame().getCloudTiles().get(1).getStudents().get(color));
            //reset cloud scelta
        }
    }
    @Override
    public void updateNextState(GameController gameController) {

        //todo mettere i check per le carte in mano e per gli studenti nella bag. In tal caso si va nell'endgamestate
        if(gameController.getCurrentVirtualView()<gameController.getVirtualViews().size()-1){
            gameController.setNextState(new MovePawnsState());
            gameController.setCurrentVirtualView(gameController.getCurrentVirtualView()+1);
        }
        else{
            gameController.setNextState(new PlanningState());
            gameController.setCurrentVirtualView(0);
        }

    }
    @Override
    public void endState(GameController gameController) {

    }
}
