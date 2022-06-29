package it.polimi.ingsw.model;
import java.util.HashMap;
import java.util.Map;

public class CharacterCard1 implements CardBehavior{

    private Map<PawnColor,Integer> students = new HashMap<>(){{
        for(PawnColor color : PawnColor.values()){
            put(color,0);
        }
    }};
    public Map<PawnColor, Integer> getStudents() {
        return students;
    }
    public void setStudents(Map<PawnColor, Integer> students) {
        this.students = students;
    }

    /**
     * Moves a student from this card to an island and refills this card with a student taken from the game bag.
     * @param parameter contains the Game in which the card is activated, the player who activated it,
     *                  the student color and the destination island chosen by the user
     */
    public void startEffect(Parameter parameter){
        moveToIsland(parameter.getChosenColor(), parameter.getIsland());
        refill(parameter.getGame());
    }

    /**
     * Initializes this card with 4 random students
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        for(int i=0;i<4;i++){
            PawnColor rdColor = PawnColor.randomColor();
            parameter.getGame().getBag().replace(rdColor, parameter.getGame().getBag().get(rdColor)-1);
            this.students.replace(rdColor, this.students.get(rdColor)+1);
        }
    }

    /**
     * In this specific character card, this method does nothing since its effect doesn't persist.
     * @param parameter contains the Game in which the card is activated.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }

    /**
     * Moves a student from this card to an island.
     * @param color represents the student to be moved.
     * @param destination represents the island on which the student has to be put.
     */
    public void moveToIsland(PawnColor color, Island destination){
        this.students.replace(color, this.students.get(color)-1);
        destination.addStudent(color);
    }

    /**
     * Takes another student pawn from the game bag and puts it on this card.
     * @param game represents the current game, from whose bag the student has to be taken.
     */
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
