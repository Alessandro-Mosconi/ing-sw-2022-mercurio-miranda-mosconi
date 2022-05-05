package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    public Game getGame() {
        return game;
    }

    private Game game;


    // private VirtualView view;
        private GameControllerState currentState;

    public void setCurrentState(GameControllerState currentState) {
        this.currentState = currentState;
    }

    private GameControllerState nextState;


    public GameController(Game game/*, VirtualView view*/) {
        this.game = game;
        //this.view = view;
    }

    public GameController() {
            this.game = new Game();
            //this.view = new VirtualView();
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
