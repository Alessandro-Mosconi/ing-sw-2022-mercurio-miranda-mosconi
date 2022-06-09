package it.polimi.ingsw.model;
import java.util.HashMap;
import java.util.Map;

public class CharacterCard1 implements CardBehavior{

    private Map<PawnColor,Integer> students = new HashMap<>(){{
        for(PawnColor color : PawnColor.values()){
            put(color,0);
        }
    }}; //da inizializzare a 4 nel setup game
    public Map<PawnColor, Integer> getStudents() {
        return students;
    }
    public void setStudents(Map<PawnColor, Integer> students) {
        this.students = students;
    }
    /*
        all'inizio della partita 4 studenti vengono piazzati sopra questa carta
        Eff: prendi 1 studente e piazzalo su un'isola a scelta; pesca uno studente e mettilo sulla carta
     */
    public void startEffect(Parameter parameter){
        /*if(this.students.get(parameter.getChosenColor())==0){
            //TODO manda errore e chiede di reinserire --  direi che i check sulla validità dell'input andrebbero fatti lato client
        }
        else */{
            moveToIsland(parameter.getChosenColor(), parameter.getIsland());
            refill(parameter.getGame());
        }
    }
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }//Places 4 random students on the card
    public void endEffect(Parameter parameter) {
        //do nothing
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
        }//Checks that the bag (its clone) has students of the random color
        clonedBag.replace(rdColor, clonedBag.get(rdColor)-1); //Takes the chosen student from the bag
        this.students.replace(rdColor,this.students.get(rdColor)+1);//Places the chosen student on the card
        game.setBag(clonedBag);
        game.getBag().replace(rdColor,clonedBag.get(rdColor));
    }
}
