package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CloudTile {

    public CloudTile(Map<PawnColor, Integer> map) {
        this.students=map;
    }

    public int getCloudID() {
        return cloudID;
    }

    public void setCloudID(int cloudID) {
        this.cloudID = cloudID;
    }

    private int cloudID;
    private Map<PawnColor, Integer> students;

    public CloudTile(int ID, Map<PawnColor, Integer> students)
    {
        this.cloudID=ID;
        this.students = students;
    }
    public CloudTile(int ID) {
        this.cloudID = ID;
        this.students = new HashMap<PawnColor, Integer>();

        for (PawnColor color : PawnColor.values())
            this.students.put(color, 0);
    }
    public CloudTile()
    {
        this.students = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            this.students.put(color, 0);
    }
    public Map<PawnColor, Integer> getStudents() {
        return students;
    }
    public void setStudentsMap(Map<PawnColor,Integer> map) {
        this.students = map;
    }
    public void setStudents(PawnColor color, int n) {
        this.students.replace(color,n);
    }

    /**
     * Adds to this cloud tile a student of a given color.
     * @param color is the color of the student that has to be added on this cloud.
     */
    public void addStudents(PawnColor color) {
        this.students.replace(color, this.students.get(color)+1);
    }

    /**
     * Sets to 0 the number of pawns of a given color.
     * @param color PawnColor of which this cloud's students number has to be set to 0.
     */
    public void reset(PawnColor color) {
        this.students.replace(color, 0);
    }

}

