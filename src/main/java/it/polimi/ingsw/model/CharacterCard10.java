package it.polimi.ingsw.model;

public class CharacterCard10 implements CardBehavior{

    //Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.
    @Override
    public void Effect(Parameter parameter) {
        //max 2 volte
        PawnColor chosenColor= chooseColor();
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(chosenColor)-1);
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentHall().get(chosenColor)+1);
        //da entrance to hall
        parameter.getPlayer().getSchoolBoard().getStudentHall().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentHall().get(chosenColor)-1);
        parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(chosenColor,parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(chosenColor)+1);
        //da hall to entrance
    }
    public PawnColor chooseColor() {
        //credo vada chiesto al controller il valore del colore da ritornare
        return null;
    }
}