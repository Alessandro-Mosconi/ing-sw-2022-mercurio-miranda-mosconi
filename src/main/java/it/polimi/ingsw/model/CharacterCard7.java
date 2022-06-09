package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CharacterCard7 implements CardBehavior {

    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public void setStudents(Map<PawnColor, Integer> students) {
        this.students = students;
    }

    // all'inizio della partita peschiamo 6 studenti e li piazziamo qui. Possiamo prendere fino a 3 studenti e scambiarli con altrettanti studenti
    // nel nostro ingresso
    private Map<PawnColor, Integer> students = new HashMap<>(){{
        for(PawnColor color : PawnColor.values()){
            put(color,0);
        }
    }};

    //inizializzare con 6 studenti dalla bag nel setupGame se viene scelta questa carta
    public void startEffect(Parameter parameter) {
        /*PawnColor studentToTake = chooseColor();
        PawnColor studentToGive = chooseColor();
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(studentToGive, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(studentToGive) - 1);
        this.students.replace(studentToGive, this.students.get(studentToGive) + 1);
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(studentToTake, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(studentToTake) + 1);
        this.students.replace(studentToGive, this.students.get(studentToGive) - 1);*/
        for(PawnColor col : PawnColor.values()){
            int studToTake = parameter.getColorMap1().get(col);
            int studToGive = parameter.getColorMap2().get(col);
            if(studToTake>this.students.get(col) || studToGive<parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col)){
                //todo manda errore e chiede di reinserire
            }
        }
        for(PawnColor col : PawnColor.values()){
            int studToTake = parameter.getColorMap1().get(col);
            int studToGive = parameter.getColorMap2().get(col);
            parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(col, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col) + studToTake - studToGive);
            this.students.replace(col, this.students.get(col) - studToTake + studToGive);
        }
    }

    public void initializeCard(Parameter parameter) {
        for(int i=0;i<6;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 6 random students from the bag to the card

    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
