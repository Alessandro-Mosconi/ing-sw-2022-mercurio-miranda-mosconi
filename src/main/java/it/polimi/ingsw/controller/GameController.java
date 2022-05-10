package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.virtualview.VirtualView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameController implements ActionListener {
    private Game game;
    private VirtualView view;
    private GameControllerState currentState;
    private GameControllerState nextState  = new GeneratePlayersState();
    private ArrayList<ClientHandler> clientHandlerArrayList;

    public ArrayList<ClientHandler> getClientHandlerArrayList() {
        return clientHandlerArrayList;
    }

    public void setClientHandlerArrayList(ArrayList<ClientHandler> clientHandlerArrayList) {
        this.clientHandlerArrayList = clientHandlerArrayList;
    }

    public void setCurrentState(GameControllerState currentState) {
        this.currentState = currentState;
    }
    public void setNextState(GameControllerState nextState){
        this.nextState= nextState;
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
    void manageTurn(){
        game = new Game();
        //currentState=new GameControllerState();
        //nextState=new GameControllerState();
        //getPLAYERS somehow
        //currentState.startState();
        while(true){
            currentState=nextState;
            currentState.startState(this);
            currentState.updateNextState(this);
            currentState.endState();
       }

    }
}
