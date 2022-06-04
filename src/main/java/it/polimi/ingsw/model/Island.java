package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Island {
    public Island() {

    }

    public int getIslandID() {
        return islandID;
    }

    public void setIslandID(int islandID) {
        this.islandID = islandID;
    }

    private int islandID;

    public void setIslandStudents(Map<PawnColor, Integer> islandStudents) {
        this.islandStudents = islandStudents;
    }

    private Map<PawnColor, Integer> islandStudents;
    private TowerColor towerColor;
    private int towerNumber;
    private boolean noEntryTile;
    private boolean motherNature;
    private boolean towersDoCount=true;
    private PawnColor keptOut=null;

    public Island(Map<PawnColor, Integer> islandStudents, TowerColor towerColor, int towerNumber, boolean noEntryTile, boolean motherNature) {
        this.islandStudents = islandStudents = new HashMap<>();
        for(PawnColor c : PawnColor.values()){
            this.islandStudents.put(c,0);
        }
        this.towerColor = towerColor;
        this.towerNumber = towerNumber;
        this.noEntryTile = noEntryTile;
        this.motherNature = motherNature;
    }
    public boolean isTowersDoCount() {
        return towersDoCount;
    }

    public void setTowersDoCount(boolean towersDoCount) {
        this.towersDoCount = towersDoCount;
    }

    public PawnColor getKeptOut() {
        return keptOut;
    }

    public void setKeptOut(PawnColor keptOut) {
        this.keptOut = keptOut;
    }
    public void setTowerColor(TowerColor color){
        this.towerColor = color;
    }
    public TowerColor getTowerColor(){
        return this.towerColor;
    }

    public void setTowersNumber(int number){
        this.towerNumber = number;
    }
    public int getTowersNumber(){
        return this.towerNumber;
    }

    //We don't set/get the whole map but only the integer related to the key color.
    //The map should be implemented as an HashMap, 'cause Map is an interface.
    public Map<PawnColor, Integer> getIslandStudents() {
        return this.islandStudents;
    }

    /*
    //is this really useful?
    public void setIslandStudents(Map<PawnColor, Integer> islandStudents) {
        // using put method to copy one Map to Other
        islandStudents.putAll(this.islandStudents);
    }

     */

    public int getStudentNumber(PawnColor color){
        return this.islandStudents.get(color);
    }

    public void addStudent(PawnColor color){
        this.islandStudents.replace(color, getStudentNumber(color)+1);
    }

    public void setMotherNature(boolean isPresent){
        this.motherNature = isPresent;
    }
    public boolean isMotherNature(){
        return motherNature;
    }

    public void setNoEntryTile(boolean noEntryTile) {
        this.noEntryTile = noEntryTile;
    }
    public boolean isNoEntryTile() {
        return noEntryTile;
    }

    //We should modify it if there's a 4 player game, as this version does not support it.
    public int calculatePlayerInfluence(SchoolBoard schoolboard){
        int influencePoints = 0;
        for (PawnColor color : schoolboard.getProfessorTable().keySet()){
            if(!color.equals(keptOut)){
               if (schoolboard.getProfessorTable().get(color))
                   influencePoints += getStudentNumber(color);
            }
            if(schoolboard.isBonus2influencepoints()){
                influencePoints=influencePoints+2;
            }
        }
        if (schoolboard.getTowersColor().equals(this.towerColor)&&towersDoCount)
            influencePoints += getTowersNumber();
        return influencePoints;
    }
    public void assignInfluence(ArrayList<SchoolBoard> schoolBoardsArray){
            SchoolBoard currOwner = new SchoolBoard();
            for (SchoolBoard s : schoolBoardsArray) {
                if (this.getTowerColor().equals(s.getTowersColor())) {
                    currOwner = s;
                }
            }
            int maxInfluence = calculatePlayerInfluence(currOwner);
            TowerColor currInfluence = this.getTowerColor();

            for (SchoolBoard schoolBoard : schoolBoardsArray) {
                //if (schoolBoard.getTowersColor().equals(this.getTowerColor()))????????
                if (calculatePlayerInfluence(schoolBoard) > maxInfluence) {
                    maxInfluence = calculatePlayerInfluence(schoolBoard);
                    currOwner.setTowersNumber(currOwner.getTowersNumber() + this.getTowersNumber());
                    for (int i = 0; i < this.getTowersNumber(); i++) {
                        schoolBoard.setTowersNumber(schoolBoard.getTowersNumber() - 1);
                    }
                    this.setTowerColor(schoolBoard.getTowersColor());
                }
            }

        /*for (SchoolBoard schoolBoard : schoolBoardsArray) {
            int currentInfluence = calculatePlayerInfluence(schoolBoard);
            if (currentInfluence > maxInfluence) {
                maxInfluence = currentInfluence;
                influence = schoolBoard.getTowersColor();
            }
        }*/
    }

}
