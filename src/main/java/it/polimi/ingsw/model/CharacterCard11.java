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

    /**
     * Moves a student from this card to the hall of the player who uses this card.
     * @param parameter contains the Game in which the card is activated, the player who activates it and the student that has to be moved.
     */
    public void startEffect(Parameter parameter) {
        this.students.replace(parameter.getChosenColor(), this.students.get(parameter.getChosenColor())-1);
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(parameter.getChosenColor(),parameter.getPlayer().getSchoolBoard().getStudentHall().get(parameter.getChosenColor())+1);
        parameter.getGame().setBag(refill(parameter.getGame().getBag()));
    }

    /**
     * Initialized this card with 4 students taken from the game bag.
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 4 students on the card

    /**
     * Does nothing.
     * @param parameter contains the Game in which the card was activated, the player who activated it and all the user choices used to start the effect itself.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }

    /**
     * Refills this card with a new student taken from the game bag.
     * @param gameBag the bag from which the student is taken.
     * @return returns the modified bag to be set in the game.
     */
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