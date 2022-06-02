package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelListener;
import it.polimi.ingsw.model.Parameter;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.VirtualViewListener;
import it.polimi.ingsw.virtualview.VirtualView;

import java.util.ArrayList;

public class GameController implements VirtualViewListener {
    private Game game = new Game();
    private int playersToGo;
    private int movesToGo;
    private GameControllerState currentState;
    private GameControllerState nextState  = new CreateGameState();
    private GameControllerState previousState;
    private boolean cardUsed = false;
    private boolean lastRound = false;
    private ArrayList<ClientHandler> clientHandlerArrayList = new ArrayList<>(); //todo i clientHandler vanno aggiunti qui man mano che vengono creati
    private ArrayList<VirtualView> virtualViews = new ArrayList<>(); //da unire ai clientHandler in un'unica classe sooner or later
    private ArrayList<Integer> virtualViewsOrder = new ArrayList<>();
    private int virtualViewsOrderIterator = 0;
    private CharacterCard currEffect;
    private Parameter currParameter;
    private boolean errorFlag=false;


    public Parameter getCurrParameter() {
        return currParameter;
    }

    public void setCurrParameter(Parameter currParameter) {
        this.currParameter = currParameter;
    }
    public CharacterCard getCurrEffect() {
        return currEffect;
    }

    public void setCurrEffect(CharacterCard currEffect) {
        this.currEffect = currEffect;
    }
    public int getPlayersToGo() {
        return playersToGo;
    }
    public void setPlayersToGo(int playersToGo) {
        this.playersToGo = playersToGo;
    }
    public void decreasePlayersToGo(){
        this.playersToGo=playersToGo-1;
    }
    public void resetPlayersToGo(){
        this.playersToGo=game.getNumberOfPlayers();
    }
    public int getMovesToGo() {
        return movesToGo;
    }
    public void setMovesToGo(int movesToGo) {
        this.movesToGo = movesToGo;
    }
    public void decreaseMovesToGo(){
        this.movesToGo=movesToGo-1;
    }
    public void resetMovesToGo(){
        this.movesToGo=3;
    }

    public boolean isErrorFlag() {
        return errorFlag;
    }
    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }
    public boolean isLastRound() {
        return lastRound;
    }
    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
    public int getVirtualViewsOrderIterator() {
        return virtualViewsOrderIterator;
    }
    public void setVirtualViewsOrderIterator(int virtualViewsOrderIterator) {
        this.virtualViewsOrderIterator = virtualViewsOrderIterator;
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
    public void setCardUsed(boolean cardUsed){this.cardUsed=cardUsed;}
    public boolean isCardUsed() {
        return cardUsed;
    }
    public ArrayList<Integer> getVirtualViewsOrder() {
        return virtualViewsOrder;
    }
    public void setVirtualViewsOrder(ArrayList<Integer> virtualViewsOrder) {
        this.virtualViewsOrder = virtualViewsOrder;
    }

    public void manageState(){
        //currentState=new GameControllerState();
        //nextState=new GameControllerState();
        //getPLAYERS somehow
        //currentState.startState();
        //while(true){
            currentState=nextState;
            currentState.startState(this);
            currentState.updateNextState(this);
            currentState.endState(this);


       //}

    }

    @Override
    public void performAction() {
        manageState();
    }

    @Override
    public void addVirtualView(VirtualView virtualView) {
        this.virtualViews.add(virtualView);
        this.getClientHandlerArrayList().add(virtualView.getClientHandler());
        this.virtualViewsOrder.add(virtualView.getPlayer().getPlayerNumber());
        game.addListener(virtualView.getClientHandler());
    }

    public void nextVirtualView() {
        //TO TEST mi aspetto che setti a current virtual view la next virtual view nell'array dell'ordine delle VV
        clientHandlerArrayList.get(getVirtualViewsOrder().get(virtualViewsOrderIterator)).tellToWait();
        virtualViewsOrderIterator = ((virtualViewsOrderIterator +1)%getVirtualViews().size());
        clientHandlerArrayList.get(getVirtualViewsOrder().get(virtualViewsOrderIterator)).tellToPlay();
    }
}
