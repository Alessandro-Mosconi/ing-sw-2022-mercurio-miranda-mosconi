package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class Island {
    private Map<PawnColor, Integer> islandStudents;
    private TowerColor towerColor;
    private int towerNumber;
    private boolean noEntryTile;
    private boolean motherNature;

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

    //is this really useful?
    private void setIslandStudents(Map<PawnColor, Integer> islandStudents) {
        for (Map.Entry<PawnColor, Integer> entry : this.islandStudents.entrySet()) {
            // using put method to copy one Map to Other
            islandStudents.put(entry.getKey(),
                    entry.getValue());
        }
    }

    public int getStudentNumber(PawnColor color){
        return this.islandStudents.get(color);
    }

    public void addStudent(PawnColor color){
        this.islandStudents.put(color, getStudentNumber(color)+1);
    }

    //Come gestiamo il null? Bisogna fare un try/catch?
    public void removeStudent(PawnColor color){
        if (getStudentNumber(color) > 0) {
            this.islandStudents.put(color, getStudentNumber(color)-1);
        }
    }

    public void setMotherNature(boolean isPresent){
        this.motherNature = isPresent;
    }
    public boolean isMotherNature(){
        return motherNature;
    }

    public boolean isNoEntryTile() {
        return noEntryTile;
    }
    public void setNoEntryTile(boolean noEntryTile) {
        this.noEntryTile = noEntryTile;
    }

    //We should modify it if there's a 4 player game, as this version does not support it.
    public int calculatePlayerInfluence(SchoolBoard schoolboard){
        int totStudents = 0;
        for (PawnColor color : schoolboard.ProfessorTable.keySet()){
               if (schoolboard.ProfessorTable.get(color))
                   totStudents += getStudentNumber(color);
        }
        if (schoolboard.TowerColor.equals(this.towerColor))
            totStudents += getTowersNumber();
        return totStudents;
    }

    public void assignInfluence(SchoolBoard[] schoolBoardsArray){
        int maxInfluence = 0;
        TowerColor influence = null;

        for (SchoolBoard schoolBoard : schoolBoardsArray) {
            int currentInfluence = calculatePlayerInfluence(schoolBoard);
            if (currentInfluence > maxInfluence) {
                maxInfluence = currentInfluence;
                influence = schoolBoard.TowerColor;
            }
        }
        this.towerColor = influence;
    }
}
