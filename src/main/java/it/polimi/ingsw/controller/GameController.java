package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.VirtualViewListener;
import it.polimi.ingsw.network.VirtualView;

import java.util.ArrayList;
import java.util.Map;

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
    private ArrayList<VirtualView> virtualViews = new ArrayList<>(); //da unire ai clientHandler in un'unica classe sooner or later
    private ArrayList<Integer> virtualViewsOrder = new ArrayList<>();
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
            currentState=nextState;
            System.out.println("Inizia la fase di "+ currentState.toString());
            currentState.startState(this);
            currentState.updateNextState(this);
            currentState.endState(this);
            System.out.println("Finisce la fase di "+ currentState.toString());
    }

    /**
     * Manages a state of the controller by starting and ending it and setting the next one.
     */
    @Override
    public void performAction() {
        manageState();
    }

    /**
     * Sets to true the boolean attribute of the controller that indicates when a character card has been used,
     * generates the parameter to pass to the chosen character cardand manages the effect on the model.
     * It also sends a message to each client telling that a player activated an effect card.
     */
    @Override
    public void activateCardEffect(Integer ID, String playerUsername, PawnColor color, Integer islandID, Map<PawnColor,Integer> map1, Map<PawnColor,Integer> map2) {
        cardUsed = true;
        for(CharacterCard cc : game.getChosenCharacterCards()){
            if(ID.equals(cc.getID())){
                game.setCurrEffect(cc);
            }
        }
        Parameter parameter = new Parameter(this.game);
        Player player = new Player();
        for(Player p : game.getPlayers()){
            if(p.getNickName().equals(playerUsername))
                player=p;
        }
        parameter.setPlayer(player);
        parameter.setChosenColor(color);
        if(islandID!=null) {
            parameter.setIsland(this.game.getIslandManager().getIslandList().get(islandID));
        }
        parameter.setColorMap1(map1);
        parameter.setColorMap2(map2);
        game.setCurrParameter(parameter);
        game.startEffect();
        game.increaseCurrEffectPrice();
        for(VirtualView vv : virtualViews){
            vv.getClientHandler().tellAPlayerActivatedACard(virtualViews.get(virtualViewsOrder.get(virtualViewsOrderIterator)).getUsername(), ID);
        }

    }

    /**
     * Ends the effect of the currently active character card on the model, sends a message to inform each client and set
     * the controller's boolean flag indicating that a card was active to false.
     */
    public void endCardEffect() {
        game.endEffect();
        for(VirtualView vv : virtualViews){
            vv.getClientHandler().tellTheEffectEnded();
        }
        cardUsed=false;
    }

    /**
     * Makes the controller listener of a new virtual view.
     * @param virtualView virtual view to be listened to.
     */
    @Override
    public void addVirtualView(VirtualView virtualView) {
        this.virtualViews.add(virtualView);
        //this.getClientHandlerArrayList().add(virtualView.getClientHandler());
        this.virtualViewsOrder.add(virtualView.getPlayer().getPlayerNumber());
    }

    /**
     * Updates the currently playing virtual view.
     */
    public void nextVirtualView() {
        int currVV=getVirtualViewsOrder().get(virtualViewsOrderIterator);
        getVirtualViews().get(currVV).getClientHandler().tellToWait();
        System.out.println("dico alla vv " + currVV + "di aspettare");
        int newIt = ((getVirtualViews().size()+virtualViewsOrderIterator+1)%getVirtualViews().size());
        setVirtualViewsOrderIterator(newIt);
        int newVV = getVirtualViewsOrder().get(virtualViewsOrderIterator);
        System.out.println("ITERATOR VALUE updated : " + newVV);
        getVirtualViews().get(newVV).getClientHandler().tellToPlay();
        System.out.println("dico alla vv " + newVV + "di giocare");

    }
}
