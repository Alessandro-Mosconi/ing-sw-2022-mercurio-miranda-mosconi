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
        //this branch checks the current island with its previous
        if (checkIslands(this.islandList.get(islandPos), this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())))){
            mergeIslands(this.islandList.get((this.islandList.size()+islandPos-1)%(this.islandList.size())), this.islandList.get(islandPos));
            resetIslandsID();
        }
    }//Checks both previous and next island and merge them in case they are owned by the same player/team

    private void resetIslandsID() {
        int i=0;
        for(Island island : islandList){
            island.setIslandID(i);
            i++;
        }
    }

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
        if(primaryIsland.getTowerColor()==null || island2.getTowerColor()==null) return false;
        return primaryIsland.getTowerColor().equals(island2.getTowerColor());
    }//Checks if both islands given as parameters have the same TowerColor

    public void moveMotherNature(int shift){
        this.islandList.get(currMNPosition).setMotherNature(false);
        this.currMNPosition=((currMNPosition+shift)% islandList.size());
        this.islandList.get(currMNPosition).setMotherNature(true);
        //this.currMNPosition=((currMNPosition+shift)% islandList.size());
    }//Shifts motherNature

    public void assignInfluence(ArrayList<SchoolBoard> schoolBoards) {
        islandList.get(currMNPosition).assignInfluence(schoolBoards);
    }

    public void setIslandList(ArrayList<Island> islandList) {
        this.islandList = islandList;
    }

/*    public int getMNPosition(){
        for (int i = 0; i < islandList.size(); i++){
            if (islandList.get(i).isMotherNature())
                return i;
        }
        //bisogna trattare il caso in cui non ritorni nulla? Probabilmente dobbiamo inserire un errore
        return 0;
    }*/ //diventa inutile questo metodo se salviamo il valore della posizione dell'isola su cui è MN
}
