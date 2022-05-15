package it.polimi.ingsw.model;

public class CharacterCard10 implements CardBehavior{

    //Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.
    @Override
    public void Effect(Parameter parameter) {
        //max 2 volte
        PawnColor chosenColor = chooseColor();
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(chosenColor)-1);
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentHall().get(chosenColor)+1);
        //from entrance to hall
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentHall().get(chosenColor)-1);
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(chosenColor)+1);
        //from hall to entrance
        for(PawnColor col : PawnColor.values()){
            int studToHall = parameter.getColorMap1().get(col);
            int studToEntrance = parameter.getColorMap2().get(col);
            parameter.getPlayer().getSchoolBoard().getStudentHall().replace(col, parameter.getPlayer().getSchoolBoard().getStudentHall().get(col)+studToHall-studToEntrance);
            parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(col, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col)-studToHall+studToEntrance);
        }//todo effettuare controlli sulle disponibilità
    }

    @Override
    public void initializeCard(Parameter parameter) {
        //none
    }

    @Override
    public void endEffect(Parameter parameter) {
        //do nothing
    }

    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
    }
}