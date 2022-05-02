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
        refill(parameter.getGame());
    }

    @Override
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }

    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
    }
    public void refill(Game game){
        PawnColor rdColor=PawnColor.randomColor();
        Map<PawnColor, Integer> clonedBag = game.getBag();
        if(clonedBag.get(rdColor)==0){
            while(clonedBag.get(rdColor)==0){
                rdColor=PawnColor.randomColor();
            }
        }//controllo che la bag abbia disponibilità di studenti del colore random
        clonedBag.replace(rdColor, clonedBag.get(rdColor)-1);
        this.students.replace(rdColor,this.students.get(rdColor)+1);
        game.setBag(clonedBag);
        //game.getBag().replace(rdColor,clonedBag.get(rdColor));
        //a cosa dovrebbe servire l'ultima riga (che ho commentato)?
    }
}// la refill delle carte può essere chiamata con parametro Bag direttamente (senza passare dal game) (?)