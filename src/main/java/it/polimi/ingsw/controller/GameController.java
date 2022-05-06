package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.virtualview.VirtualView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private Game game;
    private VirtualView view;
    private GameControllerState currentState;
    private GameControllerState nextState;

    public void setCurrentState(GameControllerState currentState) {
        this.currentState = currentState;
    }


    public Game getGame() {
        return game;
    }

    public VirtualView getView() {
        return view;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }


    public GameController(Game game, VirtualView view) {
        this.game = game;
        this.view = view;
    }

    public GameController() {
            this.game = new Game();
            this.view = new VirtualView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    int main(){
        /*this.game = new Game();
        this.currentState=new GameControllerState();
        this.nextState=new GameControllerState();
        //getPLAYERS somehow
        this.currentState.startState();
        while(true){
            this.currentState=nextState;
            this.currentState.startState();
            this.nextState=this.currentState.updateNextState();
            this.currentState.endState();
            //todo endState sembra abbastanza inutile
       }
       */

    return 0;}
}
