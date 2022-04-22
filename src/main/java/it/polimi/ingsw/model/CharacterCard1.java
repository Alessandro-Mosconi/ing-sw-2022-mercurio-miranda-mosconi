package it.polimi.ingsw.model;
import java.util.Map;

public class CharacterCard1 implements CardBehavior{
/*
    all'inizio della partita 4 studenti vengono piazzati sopra questa carta
    Eff: prendi 1 studente e piazzalo su un'isola a scelta; pesca uno studente e mettilo sulla carta
 */
    private Map<PawnColor,Integer> students;

    Island island= new Island();
    Game game= new Game();

   public void initCard(){
       for(int i=0;i<4;i++){
           refill();
       }
   }

    @Override
    public void Effect(){
        PawnColor color = chooseColor();
        moveToIsland(color, island);
        refill();

    }
    //
    public void moveToIsland(PawnColor color, Island destination){
        this.students.replace(color, this.students.get(color)-1);
        destination.addStudent(color);
    }
    public void refill(){
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
        game.bag.replace(rdColor,clonedBag.get(rdColor));
    }
    public PawnColor chooseColor(){
       //TODO
       return chosenColor;
    }
}
