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

    public void checkForMerge(int islandPos){

        //this if branch checks the current island with its next
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())));
            deleteIsland(this.islandList.get((islandPos+1)%(this.islandList.size())));
        }

        //this branch checks the current island with its previous
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((islandPos-1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get((islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos));
            deleteIsland(this.islandList.get(islandPos));
        }
    }

    //this method merges 2 islands and is supposed to delete the second one.
    public void mergeIslands(Island primaryIsland, Island secondaryIsland){

        for (PawnColor color : PawnColor.values()) {
            primaryIsland.getIslandStudents().replace(color, primaryIsland.getIslandStudents().get(color) + secondaryIsland.getIslandStudents().get(color));
        }
        primaryIsland.setTowersNumber(primaryIsland.getTowersNumber() + secondaryIsland.getTowersNumber());
        primaryIsland.setNoEntryTile(secondaryIsland.isNoEntryTile());
        primaryIsland.setMotherNature(true);

        deleteIsland(secondaryIsland);
    }

    private void deleteIsland(Island island){
        this.islandList.remove(island);
    }

    public boolean checkIslands(Island primaryIsland, Island island2){
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }

    public void moveMotherNature(int motherPosition, int cells){
        this.islandList.get(motherPosition).setMotherNature(false);
        this.islandList.get((motherPosition+cells)% islandList.size()).setMotherNature(true);
    }
}
