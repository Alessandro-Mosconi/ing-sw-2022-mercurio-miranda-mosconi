package it.polimi.ingsw.model;

import java.util.Map;

public class CharacterCard7 implements CardBehavior {

    // all'inizio della partita peschiamo 6 studenti e li piazziamo qui. Possiamo prendere 3 studenti e scambiarli con 3 studenti
    // nel nostro ingresso
    private Map<PawnColor, Integer> students;

    //inizializzare con 6 studenti dalla bag nel setupGame se viene scelta questa carta
    @Override
    public void Effect(Parameter parameter) {
        //da ripetere per max3 volte
        PawnColor studentToTake = chooseColor();
        PawnColor studentToGive = chooseColor();//eseguire dei controlli per verificare la presenza delle pedine scelte
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(studentToGive, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(studentToGive) - 1);
        this.students.replace(studentToGive, this.students.get(studentToGive) + 1);
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(studentToTake, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(studentToTake) + 1);
        this.students.replace(studentToGive, this.students.get(studentToGive) - 1);
    }

    @Override
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<6;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }

    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
    }
}
