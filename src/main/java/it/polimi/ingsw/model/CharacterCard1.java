package it.polimi.ingsw.model;
import java.util.Map;

public class CharacterCard1 implements CardBehavior{
/*
    all'inizio della partita 4 studenti vengono piazzati sopra questa carta
    Eff: prendi 1 studente e piazzalo su un'isola a scelta; pesca uno studente e mettilo sulla carta
 */
    private Map<PawnColor,Integer> students; //da inizializzare a 4 nel setup game

    @Override
    public void Effect(Parameter parameter){
        PawnColor color = chooseColor();
        moveToIsland(color, parameter.getIsland());
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

    public void moveToIsland(PawnColor color, Island destination){
        this.students.replace(color, this.students.get(color)-1);
        destination.addStudent(color);
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
        game.getBag().replace(rdColor,clonedBag.get(rdColor));
    }
    public PawnColor chooseColor(){
        //credo vada chiesto al controller il valore del colore da ritornare
       return null;
       //return chosenColor;
    }


}
