package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.SchoolBoard;

public class EndGameState implements GameControllerState{
    Player winner = new Player();

    @Override
    public void startState(GameController gameController) {
        int minTowers=8;
        int currWinnerProfessors=0;
        int currNumberOfProfessors=0;
        for(Player p : gameController.getGame().getPlayers()){
            if(p.getSchoolBoard().getTowersNumber()<minTowers){
                minTowers=p.getSchoolBoard().getTowersNumber();
                this.winner=p;
                for(PawnColor pawnColor : PawnColor.values()){
                    if(this.winner.getSchoolBoard().getProfessorTable().get(pawnColor)){
                        currWinnerProfessors++;
                    }
                }
            }else if(p.getSchoolBoard().getTowersNumber()==minTowers){
                for(PawnColor pawnColor : PawnColor.values()){
                    if(p.getSchoolBoard().getProfessorTable().get(pawnColor)){
                        currNumberOfProfessors++;
                    }
                }
                if(currNumberOfProfessors>=currWinnerProfessors){
                    this.winner=p;
                    currWinnerProfessors=currNumberOfProfessors;
                }
            }
        }
    }

    @Override
    public void updateNextState(GameController gameController) {
        //do nothing
    }

    @Override
    public void endState(GameController gameController) {
        //todo mostra a video il vincitore presumo
    }
}
