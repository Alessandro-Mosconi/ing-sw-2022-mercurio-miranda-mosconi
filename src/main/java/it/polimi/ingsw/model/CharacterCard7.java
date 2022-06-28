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

    /**
     * Switches up to 3 students from this card and as many from the entrance of the player who activated it
     * @param parameter contains the Game in which the card is activated, the player who activates it, a map indicating the students to be taken and a map indicating the students to be given.
     */
    public void startEffect(Parameter parameter) {
        for(PawnColor col : PawnColor.values()){
            int studToTake = parameter.getColorMap1().get(col);
            int studToGive = parameter.getColorMap2().get(col);
            parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(col, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col) + studToTake - studToGive);
            this.students.replace(col, this.students.get(col) - studToTake + studToGive);
        }
    }

    /**
     * Initialized this card with 6 students.
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<6;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 6 random students from the bag to the card

    /**
     * Does nothing
     * @param parameter contains the Game in which the card was activated.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }
}
