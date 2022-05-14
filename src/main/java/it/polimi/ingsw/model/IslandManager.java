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
        }

        //this branch checks the current island with its previous
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos));
        }
    }//Checks both previous and next island and merge them in case they are owned by the same player/team

    public void mergeIslands(Island primaryIsland, Island secondaryIsland){

        for (PawnColor color : PawnColor.values()) {
            primaryIsland.getIslandStudents().replace(color, primaryIsland.getIslandStudents().get(color) + secondaryIsland.getIslandStudents().get(color));
        }
        primaryIsland.setTowersNumber(primaryIsland.getTowersNumber() + secondaryIsland.getTowersNumber());
        primaryIsland.setNoEntryTile(secondaryIsland.isNoEntryTile());
        primaryIsland.setMotherNature(true);

        deleteIsland(secondaryIsland);
    }//Merges 2 islands and deletes the second one


    private void deleteIsland(Island island){
        this.islandList.remove(island);
    }

    public boolean checkIslands(Island primaryIsland, Island island2){
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }//Checks if both islands given as parameters have the same TowerColor

    public void moveMotherNature(int motherPosition, int shift){
        this.islandList.get(motherPosition).setMotherNature(false);
        this.islandList.get((motherPosition+shift)% islandList.size()).setMotherNature(true);
    }//Shifts motherNature

    public int getMNPosition(){
        for (int i = 0; i < islandList.size(); i++){
            if (islandList.get(i).isMotherNature())
                return i;
        }
        //bisogna trattare il caso in cui non ritorni nulla? Probabilmente dobbiamo inserire un errore
        return 0;
    }
}
