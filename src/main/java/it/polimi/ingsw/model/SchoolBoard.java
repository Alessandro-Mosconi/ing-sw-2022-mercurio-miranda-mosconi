package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;


public class SchoolBoard {
    private int towersNumber;

    public boolean isEffectProf() {
        return effectProf;
    }

    public void setEffectProf(boolean effectProf) {
        this.effectProf = effectProf;
    }

    private boolean effectProf = false;

    public SchoolBoard(int towersNumber, TowerColor color, GameMode gameMode) {
        this.towersNumber = towersNumber;
        this.towersColor = color;
        this.gameMode=gameMode;
        this.studentHall = new HashMap<PawnColor, Integer>();
        this.studentEntrance = new HashMap<PawnColor, Integer>();
        this.professorTable = new HashMap<PawnColor, Boolean>();

        for(PawnColor c : PawnColor.values()){
            this.studentHall.put(c, 0);
            this.studentEntrance.put(c, 0);
            this.professorTable.put(c, false);
        }
    }

    public void setTowersColor(TowerColor towersColor) {
        this.towersColor = towersColor;
    }

    private TowerColor towersColor;

    public void setStudentHall(Map<PawnColor, Integer> studentHall) {
        this.studentHall = studentHall;
    }

    private Map<PawnColor, Integer> studentHall;

    public void setStudentEntrance(Map<PawnColor, Integer> studentEntrance) {
        this.studentEntrance = studentEntrance;
    }

    public void setProfessorTable(Map<PawnColor, Boolean> professorTable) {
        this.professorTable = professorTable;
    }

    private Map<PawnColor, Integer> studentEntrance;
    private Map<PawnColor, Boolean> professorTable;
    private GameMode gameMode;
    private boolean bonus2influencepoints=false;


    public SchoolBoard() {
        this.towersNumber = 0;
        this.towersColor = null;
        this.gameMode = null;
        this.studentHall = new HashMap<PawnColor, Integer>();
        this.studentEntrance = new HashMap<PawnColor, Integer>();
        this.professorTable = new HashMap<PawnColor, Boolean>();

        for(PawnColor color : PawnColor.values()){
            this.studentHall.put(color, 0);
            this.studentEntrance.put(color, 0);
            this.professorTable.put(color, false);
        }
    }
    public SchoolBoard(int numTower, TowerColor colTower, Map<PawnColor,Integer> entrance, GameMode gameMode) {
        this.towersNumber = numTower;
        this.towersColor = colTower;
        this.gameMode = gameMode;
        this.studentHall = new HashMap<PawnColor, Integer>();
        this.studentEntrance = entrance;
        this.professorTable = new HashMap<PawnColor, Boolean>();

        for(PawnColor color : PawnColor.values()){
            this.studentHall.put(color, 0);
            this.studentEntrance.put(color, 0);
            this.professorTable.put(color, false);
        }

    }

    public int getTowersNumber() {
        return towersNumber;
    }
    public TowerColor getTowersColor() {
        return towersColor;
    }
    public GameMode getGameMode() {
        return gameMode;
    }
    public Map<PawnColor, Boolean> getProfessorTable() {
        return professorTable;
    }
    public Map<PawnColor, Integer> getStudentEntrance() {
        return studentEntrance;
    }
    public Map<PawnColor, Integer> getStudentHall() {
        return studentHall;
    }
    public boolean isBonus2influencepoints() {
        return bonus2influencepoints;
    }
    public void setBonus2influencepoints(boolean bonus2influencepoints) {
        this.bonus2influencepoints = bonus2influencepoints;
    }


    public void addStudentEntrance(PawnColor color) {
        this.studentEntrance.replace(color, this.studentEntrance.get(color)+1);
    }
    public void addStudentEntrance(PawnColor color, int n) {
        this.studentEntrance.replace(color, this.studentEntrance.get(color)+n);
    }
    public void removeStudentEntrance(PawnColor color) {
        if(this.studentEntrance.get(color)>0)
            this.studentEntrance.replace(color, this.studentEntrance.get(color)-1);
        else System.out.println("studente non presente");
    }
    public void addStudentHall(PawnColor color) {
        this.studentHall.replace(color, this.studentHall.get(color)+1);
    }
    public void removeStudentHall(PawnColor color) {
        if(this.studentHall.get(color)>0)
            this.studentHall.replace(color, this.studentHall.get(color)-1);
        else System.out.println("studente non presente");
    }

    public void setTowersNumber(int towersNumber) {
        this.towersNumber = towersNumber;
    }

    /**
     * Sets as true the boolean value indicating that this schoolboard controls a given professor.
     * @param color is the controlled professor.
     */
    public void addProfessor(PawnColor color){this.professorTable.replace(color, true); }

    /**
     * Sets as false the boolean value indicating that this schoolboard does not control a given professor anymore.
     * @param color is the professor to be set as false.
     */
    public void removeProfessor(PawnColor color){this.professorTable.replace(color, false); }

    /**
     * Checks if the owner of this schoolboard has to get a coin.
     * @param color is the color of the student that has been moved to this schoolboard's hall.
     * @return true if the owner has to get a coin, false otherwise.
     */
    public boolean checkForCoin(PawnColor color){
        return ((studentHall.get(color) % 3) == 0);
    }


}
