package it.polimi.ingsw.model;

import java.util.Map;

//these are the parameters given to the character cards when used
//created in order to give parameters respecting overriding rules
public class Parameter {


    private Game game;
    private Island island;
    private Player player;
    private PawnColor chosenColor;
    private Map<PawnColor,Integer> colorMap1;
    private Map<PawnColor,Integer> colorMap2;

    public Parameter(Game game, Island island) {
        this.game = game;
        this.island = island;
        this.player=null;
    }

    public Parameter(Game game) {
        this.game = game;
        this.island=null;
        this.player=null;
    }

    public Parameter(Island island){
        this.island=island;
        this.game=null;
        this.player=null;
    }

    public Parameter(Player player){
        this.player=player;
        this.game=null;
        this.island=null;
    }

    public Parameter(Game game, Player player){
        this.game=game;
        this.player=player;
        this.island=null;
    }

    public Parameter() {

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PawnColor getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(PawnColor chosenColor) {
        this.chosenColor = chosenColor;
    }

    public Map<PawnColor, Integer> getColorMap1() {
        return colorMap1;
    }

    public void setColorMap1(Map<PawnColor, Integer> colorMap1) {
        this.colorMap1 = colorMap1;
    }

    public Map<PawnColor, Integer> getColorMap2() {
        return colorMap2;
    }

    public void setColorMap2(Map<PawnColor, Integer> colorMap2) {
        this.colorMap2 = colorMap2;
    }
}
