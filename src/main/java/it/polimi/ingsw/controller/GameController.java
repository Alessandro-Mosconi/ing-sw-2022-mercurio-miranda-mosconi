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
    private GameControllerState currentState;
    private GameControllerState nextState  = new LoadingState();
    private GameControllerState previousState;

    private ArrayList<ClientHandler> clientHandlerArrayList; //todo i clientHandler vanno aggiunti qui man mano che vengono creati
    private ArrayList<VirtualView> virtualViews; //da unire ai clientHandler in un'unica classe sooner or later
    private int currentVirtualView=0;
    public int getCurrentVirtualView() {
        return currentVirtualView;
    }
    public void setCurrentVirtualView(int currentVirtualView) {
        this.currentVirtualView = currentVirtualView;
    }
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
    public ArrayList<VirtualView> getVirtualViews() {
        return virtualViews;
    }
    public void setVirtualViews(ArrayList<VirtualView> virtualViews) {
        this.virtualViews = virtualViews;
    }
    public GameControllerState getPreviousState() {
        return previousState;
    }
    public void setPreviousState(GameControllerState previousState) {
        this.previousState = previousState;
    }
    public GameController() {
            this.game = new Game();
            this.virtualViews = new ArrayList<>();
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
            currentState.endState(this);
       }

    }
}
