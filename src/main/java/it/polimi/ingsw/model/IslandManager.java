package it.polimi.ingsw.model;

import java.util.ArrayList;

public class IslandManager {

    private ArrayList<Island> islandList;
    private int currMNPosition;
    public IslandManager(ArrayList<Island> islandList) {
        this.islandList = islandList;
    }

    public ArrayList<Island> getIslandList(){
        return  this.islandList;
    }
    public int getCurrMNPosition() {
        return currMNPosition;
    }
    public void setCurrMNPosition(int currMNPosition) {
        this.currMNPosition = currMNPosition;
    }

    public void checkForMerge(){

        //this if branch checks the current island with its next
        int islandPos = currMNPosition;

        if(islandList.get(currMNPosition).getTowerColor()==null) return;
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())));
            resetIslandsID();
        }
        islandPos = currMNPosition;
        //this branch checks the current island with its previous
        if (checkIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos))){
            mergeIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos));
            resetIslandsID();
        }
    }//Checks both previous and next island and merge them in case they are owned by the same player/team
    public void checkForMerge(Integer islandID){

        //this if branch checks the current island with its next
        int islandPos = islandID;

        if(islandList.get(currMNPosition).getTowerColor()==null) return;
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get(islandPos), this.islandList.get((islandPos+1)%(this.islandList.size())));
            resetIslandsID();
        }

        if(islandPos>=islandList.size())
            islandPos--;

        //this branch checks the current island with its previous
        if (checkIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos))){
            mergeIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos));
            resetIslandsID();
        }
    }//Checks both previous and next island and merge when characterCard 3 is activated

    private void resetIslandsID() {
        int i=0;
        for(Island island : islandList){
            island.setIslandID(i);
            if(island.isMotherNature()){
                currMNPosition=i;
            }
            i++;
        }
    }

    public void mergeIslands(Island primaryIsland, Island secondaryIsland){

        for (PawnColor color : PawnColor.values()) {
            primaryIsland.getIslandStudents().replace(color, primaryIsland.getIslandStudents().get(color) + secondaryIsland.getIslandStudents().get(color));
        }
        primaryIsland.setTowersNumber(primaryIsland.getTowersNumber() + secondaryIsland.getTowersNumber());
        primaryIsland.setNoEntryTile(secondaryIsland.isNoEntryTile());
        boolean motherNature = primaryIsland.isMotherNature() || secondaryIsland.isMotherNature();
        primaryIsland.setMotherNature(motherNature);
        deleteIsland(secondaryIsland);
    }//Merges 2 islands and deletes the second one


    private void deleteIsland(Island island){
        this.islandList.remove(island);
    }

    public boolean checkIslands(Island primaryIsland, Island island2){
        if(primaryIsland.getTowerColor()==null || island2.getTowerColor()==null) return false;
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }//Checks if both islands given as parameters have the same TowerColor

    public void moveMotherNature(int shift){
        this.islandList.get(currMNPosition).setMotherNature(false);
        this.currMNPosition=((islandList.size()+currMNPosition+shift)% islandList.size());
        this.islandList.get(currMNPosition).setMotherNature(true);
    }//Shifts motherNature

    public void assignInfluence(ArrayList<SchoolBoard> schoolBoards) {
        islandList.get(currMNPosition).assignInfluence(schoolBoards);
    }

    public void setIslandList(ArrayList<Island> islandList) {
        this.islandList = islandList;
    }

}
