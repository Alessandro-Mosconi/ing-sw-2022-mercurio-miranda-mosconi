package it.polimi.ingsw.model;

import java.util.ArrayList;

public class IslandManager {
    private ArrayList<Island> islandList;

    public ArrayList<Island> getIslandList(){
        return  this.islandList;
    }

    //how am I supposed to do this?
    //TODO - at the end
    public void setupIslands(){}

    //Todo
    public void mergeIslands(Island island, Island island2){}

    //todo
    public void deleteIsland(Island island){}

    //todo
    public boolean checkNext(Island island){
        return true;
    }

    //todo
    public boolean checkPrevious(Island island){ return true; }

    //todo
    public void moveMotherNature(int cells){}


}
