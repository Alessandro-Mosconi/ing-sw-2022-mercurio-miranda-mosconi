package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.VirtualView;

public class EndGameState implements GameControllerState{
    Player winner = new Player();

    /**
     * Checks for the winning conditions and sets the winner.
     * @param gameController is the given controller.
     */
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
    /**
     * Does nothing.
     * @param gameController is the given controller.
     */
    @Override
    public void updateNextState(GameController gameController) {
        //do nothing
    }

    /**
     * Tells who won the game to each client by sending them a message.
     * @param gameController is the given controller.
     */
    @Override
    public void endState(GameController gameController) {
        for(VirtualView vv : gameController.getVirtualViews()){
            vv.getClientHandler().tellWhoWon(winner);
        }
    }
}
