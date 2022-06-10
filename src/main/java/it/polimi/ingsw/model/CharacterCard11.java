package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CharacterCard11 implements CardBehavior{
    public Map<PawnColor, Integer> getStudents() {
        return students;
    }

    public void setStudents(Map<PawnColor, Integer> students) {
        this.students = students;
    }

    private Map<PawnColor, Integer> students = new HashMap<>(){{
        for(PawnColor color : PawnColor.values()){
            put(color,0);
        }
    }};


    //All'inizio della partita, pescate 4 Studenti e piazzateli su questa carta. EFFETTO:
    //Prendi 1 Studente da questa carta e piazzalo nella tua Sala.
    //Poi pesca un nuovo Studente dal sacchetto e posizionalo su questa carta.
    public void startEffect(Parameter parameter) {
        this.students.replace(parameter.getChosenColor(), this.students.get(parameter.getChosenColor())-1);
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(parameter.getChosenColor(),parameter.getPlayer().getSchoolBoard().getStudentHall().get(parameter.getChosenColor())+1);
        parameter.getGame().setBag(refill(parameter.getGame().getBag()));
    }

    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 4 students on the card

    public void endEffect(Parameter parameter) {
        //do nothing
    }
    public Map<PawnColor,Integer> refill(Map<PawnColor,Integer> gameBag){
        PawnColor rdColor=PawnColor.randomColor();
        Map<PawnColor, Integer> clonedBag = gameBag;
        if(clonedBag.get(rdColor)==0){
            while(clonedBag.get(rdColor)==0){
                rdColor=PawnColor.randomColor();
            }
        }//controllo che la bag abbia disponibilità di studenti del colore random
        clonedBag.replace(rdColor, clonedBag.get(rdColor)-1);
        this.students.replace(rdColor,this.students.get(rdColor)+1);
        return clonedBag;

    }
}