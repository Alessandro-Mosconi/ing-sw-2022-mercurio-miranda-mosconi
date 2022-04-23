package it.polimi.ingsw.model;

import java.util.ArrayList;

public class IslandManager {
    private ArrayList<Island> islandList;

    public IslandManager(ArrayList<Island> islandList) {
        this.islandList = islandList;
    }

    public ArrayList<Island> getIslandList(){
        return  this.islandList;
    }

    //qui devo capire come fare.. la merge deve sommare gli studenti (ed inoltre dovrebbe tenere conto
    //graficamente dell'unione delle due isole. Non mi sembra una buona soluzione.
    //Todo
    public void checkMergeIslands(int islandPos){
        if (checkNext(this.islandList.get(islandPos), this.islandList.get(islandPos+1))){
            mergeIslands(this.islandList.get(islandPos), this.islandList.get(islandPos+1));
            deleteIsland(this.islandList.get(islandPos+1));
        }
        if (checkPrevious(this.islandList.get(islandPos), this.islandList.get(islandPos-1))){
            mergeIslands(this.islandList.get(islandPos-1), this.islandList.get(islandPos));
            deleteIsland(this.islandList.get(islandPos));
        }
    }

    //todo
    public void mergeIslands(Island primaryIsland, Island secondaryIsland){
    }

    //todo
    public void deleteIsland(Island island){}

    public boolean checkNext(Island primaryIsland, Island island2){
        //if (this.islandList.get(islandPos).getTowerColor().equals(this.islandList.get(islandPos+1).getTowerColor()))
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }

    public boolean checkPrevious(Island primaryIsland, Island island2){
        //if (this.islandList.get(islandPos).getTowerColor().equals(this.islandList.get(islandPos-1).getTowerColor()))
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }

    public void moveMotherNature(int motherPosition, int cells){
        this.islandList.get(motherPosition).setMotherNature(false);
        this.islandList.get(motherPosition+cells).setMotherNature(true);
    }


}
