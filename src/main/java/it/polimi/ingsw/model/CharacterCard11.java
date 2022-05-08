package it.polimi.ingsw.model;

import java.util.Map;

public class CharacterCard11 implements CardBehavior{
    private Map<PawnColor, Integer> students; //da settare nel setupgame se viene scelta questa carta
    //All'inizio della partita, pescate 4 Studenti e piazzateli su questa carta. EFFETTO:
    //Prendi 1 Studente da questa carta e piazzalo nella tua Sala.
    //Poi pesca un nuovo Studente dal sacchetto e posizionalo su questa carta.
    @Override
    public void Effect(Parameter parameter) {
        PawnColor chosenColor = chooseColor();
        this.students.replace(chosenColor, this.students.get(chosenColor)-1);
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentHall().get(chosenColor)+1);
        parameter.getGame().setBag(refill(parameter.getGame().getBag()));
    }

    @Override
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 4 students on the card

    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
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
        //game.setBag(clonedBag);
        //game.getBag().replace(rdColor,clonedBag.get(rdColor));
        //a cosa dovrebbe servire l'ultima riga (che ho commentato)?
    }
}// la refill delle carte può essere chiamata con parametro Bag direttamente (senza passare dal game) (?)