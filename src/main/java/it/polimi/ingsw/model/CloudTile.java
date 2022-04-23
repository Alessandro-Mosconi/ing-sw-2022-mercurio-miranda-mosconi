package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CloudTile {

    private Map<PawnColor, Integer> students;

    public CloudTile(Map<PawnColor, Integer> students)
    {
        this.students = new HashMap<PawnColor, Integer>();
        this.students = students;
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

    public void setStudents(Map<PawnColor, Integer> students) {
        this.students = students;
    }

    public void addStudents(PawnColor color) {
        this.students.replace(color, this.students.get(color)+1);
    }

    /*
    public void removeStudents(PawnColor color) {
        if(this.students.get(color)>0)
            this.students.replace(color, this.students.get(color)-1);
    }
    */
    
    public void reset(PawnColor color) {
        this.students.replace(color, 0);
    }

}

