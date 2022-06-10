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

    public Island(int id, TowerColor towerColor, int towerNumber, boolean noEntryTile, boolean motherNature) {
        this.islandID=id;
        this.islandStudents = new HashMap<>(){{
            for(PawnColor c : PawnColor.values()){
                put(c,0);
            }
        }
        };
        this.towerColor = towerColor;
        this.towerNumber = towerNumber;
        this.noEntryTile = noEntryTile;
        this.motherNature = motherNature;
    }
    public Island(int id, Map<PawnColor,Integer> islandMap, TowerColor towerColor, int towerNumber, boolean noEntryTile, boolean motherNature) {
        this.islandID=id;
        this.islandStudents = islandMap;
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

    public Map<PawnColor, Integer> getIslandStudents() {
        return this.islandStudents;
    }


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
            int maxInfluence=0;
            SchoolBoard currOwner = new SchoolBoard();
            if(towerColor==null){
                for (SchoolBoard schoolBoard : schoolBoardsArray) {
                    int currPlayerInfluence=calculatePlayerInfluence(schoolBoard);
                    if (currPlayerInfluence > maxInfluence){
                        maxInfluence = currPlayerInfluence;
                        currOwner=schoolBoard;
                    }
                    else if (currPlayerInfluence == maxInfluence){
                        currOwner = new SchoolBoard();
                    }
                }
                if(maxInfluence!=0 && currOwner.getTowersColor()!=null){
                    this.towerColor=currOwner.getTowersColor();
                    this.towerNumber=1;
                    currOwner.setTowersNumber(currOwner.getTowersNumber()-1);
                }
            } else {
                SchoolBoard prevOwner = new SchoolBoard();
                for (SchoolBoard s : schoolBoardsArray) {
                    if (towerColor.equals(s.getTowersColor())) {
                        prevOwner = s;
                    }
                }
                maxInfluence = calculatePlayerInfluence(prevOwner);
                TowerColor prevInfluence = towerColor; //todo serve?

                for (SchoolBoard schoolBoard : schoolBoardsArray) {
                    int currPlayerInfluence = calculatePlayerInfluence(schoolBoard);
                    if (currPlayerInfluence > maxInfluence) {
                        maxInfluence = currPlayerInfluence;
                        currOwner = schoolBoard;
                    }
                }
                prevOwner.setTowersNumber(prevOwner.getTowersNumber() + this.towerNumber);
                currOwner.setTowersNumber(currOwner.getTowersNumber() - this.towerNumber);
                this.towerColor = currOwner.getTowersColor();
            }
    }

}
