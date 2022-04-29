package it.polimi.ingsw.model;
//these are the parameters given to the character cards when used
//created in order to give parameters respecting overriding rules
public class Parameter {

    private Game game;
    private Island island;
    private Player player;

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

}
