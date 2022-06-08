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
    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;
    private int playersToGo;
    private int movesToGo;
    private GameControllerState currentState;
    private GameControllerState nextState  = new CreateGameState();
    private GameControllerState previousState;
    private boolean cardUsed = false;
    private boolean lastRound = false;
    private ArrayList<ClientHandler> clientHandlerArrayList = new ArrayList<>();

    //i clientHandler vanno aggiunti qui man mano che vengono creati
    //in realtà non servono i clienthandler sul controller se li attribuiamo alle vvs
    private ArrayList<VirtualView> virtualViews = new ArrayList<>(); //da unire ai clientHandler in un'unica classe sooner or later
    private ArrayList<Integer> virtualViewsOrder = new ArrayList<>()/*{{
        add(0);add(1);
    }}*/;
    private int virtualViewsOrderIterator;
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
        this.playersToGo--;
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
            System.out.println("Inizia la fase di "+ currentState.toString());
            currentState.startState(this);
            currentState.updateNextState(this);
            currentState.endState(this);
            System.out.println("Finisce la fase di "+ currentState.toString());
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
        //game.addListener((ModelListener) virtualView.getClientHandler());
    }

    public void nextVirtualView() {
        //TO TEST mi aspetto che setti a current virtual view la next virtual view nell'array dell'ordine delle VV
        //int size = getVirtualViews().size();
        //clientHandlerArrayList.get(getVirtualViewsOrder().get(size+virtualViewsOrderIterator)) ;
        //clientHandlerArrayList.get(getVirtualViewsOrder().get((getVirtualViews().size()+virtualViewsOrderIterator)%getVirtualViews().size()-1)).tellToWait();
        //virtualViewsOrderIterator = ((virtualViewsOrderIterator +1)%getVirtualViews().size());
        //clientHandlerArrayList.get(getVirtualViewsOrder().get((getVirtualViews().size()+virtualViewsOrderIterator)%getVirtualViews().size()-1)).tellToPlay();
        //clientHandlerArrayList.get(0).tellToWait();
        //clientHandlerArrayList.get(1).tellToPlay();
        //virtualViewsOrderIterator = virtualViewsOrderIterator%(getVirtualViewsOrder().size());
        //this.clientHandlerArrayList.get(this.getVirtualViewsOrder().get(this.virtualViewsOrderIterator)).tellToWait();
        //getVirtualViews().get(getVirtualViewsOrder().get(getVirtualViewsOrderIterator())).getClientHandler().tellToWait();
        int currVV=getVirtualViewsOrder().get(virtualViewsOrderIterator);
        getVirtualViews().get(currVV).getClientHandler().tellToWait();
        int newIt = ((virtualViewsOrderIterator+1)%getVirtualViews().size());
        setVirtualViewsOrderIterator(newIt);
        int newVV = getVirtualViewsOrder().get(virtualViewsOrderIterator);
        //this.virtualViewsOrderIterator = (this.getVirtualViews().size()+this.virtualViewsOrderIterator+1)%(this.getVirtualViews().size());
        System.out.println("ITERATOR VALUE updated : " + newVV);
        //getVirtualViews().get(getVirtualViewsOrderIterator()).getClientHandler().tellToPlay();
        //setVirtualViewsOrderIterator(newIt);
        getVirtualViews().get(newVV).getClientHandler().tellToPlay();
//        this.clientHandlerArrayList.get(this.getVirtualViewsOrder().get(this.virtualViewsOrderIterator)).tellToPlay();


    }
}
