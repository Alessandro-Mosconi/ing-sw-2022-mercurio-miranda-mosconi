package it.polimi.ingsw.model;

import java.util.Map;

public class CharacterCard7 implements CardBehavior {

    // all'inizio della partita peschiamo 6 studenti e li piazziamo qui. Possiamo prendere 3 studenti e scambiarli con 3 studenti
    // nel nostro ingresso
    private Map<PawnColor, Integer> students;

    //inizializzare con 6 studenti dalla bag nel setupGame se viene scelta questa carta
    @Override
    public void Effect(Parameter parameter) {
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

    @Override
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<6;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 6 random students from the bag to the card

    @Override
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
