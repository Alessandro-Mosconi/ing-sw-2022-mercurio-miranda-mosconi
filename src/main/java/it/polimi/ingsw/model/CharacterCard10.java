package it.polimi.ingsw.model;

public class CharacterCard10 implements CardBehavior{


    //Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.

    /**
     * Switches up to 2 students from the hall and the entrance of the player who activates the effect.
     * @param parameter contains the Game in which the card is activated, the player who activates it, a map indicating the students to be put in the hall and a map indicating the students to be put in the entrance.
     */
    public void startEffect(Parameter parameter) {
        for(PawnColor col : PawnColor.values()){
            int studToHall = parameter.getColorMap1().get(col);
            int studToEntrance = parameter.getColorMap2().get(col);
            parameter.getPlayer().getSchoolBoard().getStudentHall().replace(col, parameter.getPlayer().getSchoolBoard().getStudentHall().get(col) + studToHall - studToEntrance);
            parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(col, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col) - studToHall + studToEntrance);
        }
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card is activated.
     */
    public void initializeCard(Parameter parameter) {
        //none
    }

    /**
     * Does nothing
     * @param parameter contains the Game in which the card was activated, the player who activated it and all the user choices used to start the effect itself.
     */
    public void endEffect(Parameter parameter) {
        //do nothing
    }

}