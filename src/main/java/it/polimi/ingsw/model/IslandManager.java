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
        for(Island island : islandList)
            if(island.isMotherNature())
                currMNPosition= island.getIslandID();
        return currMNPosition;
    }
    public void setCurrMNPosition(int currMNPosition) {
        this.currMNPosition = currMNPosition;
    }

    /**
     * Checks if the previous and/or the next islands starting from the current Mother Nature position have a tower of the same color.
     * In that case, it merges them and resets each island's ID.
     */
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

    /**
     * Checks if the previous and/or the next islands starting from a given island have a tower of the same color.
     * In that case, it merges them and resets each island's ID.
     */
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

    /**
     * Resets each island's ID.
     */
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

    /**
     * Merges two islands into the first one and deletes the second one.
     * @param primaryIsland is the first island.
     * @param secondaryIsland is the second island.
     */
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

    /**
     * Deletes a given island.
     * @param island is the island to be deleted.
     */
    private void deleteIsland(Island island){
        this.islandList.remove(island);
    }

    /**
     * Checks if two islands have a tower of the same color.
     * @param primaryIsland is the first island.
     * @param island2 is the second island.
     * @return returns a boolean value that indicates whether the given islands have a tower of the same color or not.
     */
    public boolean checkIslands(Island primaryIsland, Island island2){
        if(primaryIsland.getTowerColor()==null || island2.getTowerColor()==null) return false;
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }//Checks if both islands given as parameters have the same TowerColor

    /**
     * Moves Mother Nature of a given number of islands starting from its current position.
     * @param shift number of islands.
     */
    public void moveMotherNature(int shift){
        this.islandList.get(currMNPosition).setMotherNature(false);
        this.currMNPosition=((islandList.size()+currMNPosition+shift)% islandList.size());
        this.islandList.get(currMNPosition).setMotherNature(true);
    }//Shifts motherNature

    /**
     * Calls the assignInfluence method on the island where Mother Nature ends its movement.
     * @param schoolBoards contains each player's schoolboard.
     */
    public void assignInfluence(ArrayList<SchoolBoard> schoolBoards) {
        islandList.get(currMNPosition).assignInfluence(schoolBoards);
    }

    public void setIslandList(ArrayList<Island> islandList) {
        this.islandList = islandList;
    }

}
