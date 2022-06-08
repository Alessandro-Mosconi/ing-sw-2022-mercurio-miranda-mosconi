package it.polimi.ingsw.model;

public class CharacterCard10 extends CharacterCard{

    public CharacterCard10(int id, int price) {
        super(id, price);
    }

    //Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.
    @Override
    public void Effect(Parameter parameter) {
        for(PawnColor col : PawnColor.values()) {
            int studToHall = parameter.getColorMap1().get(col);
            int studToEntrance = parameter.getColorMap2().get(col);
            if (studToHall < parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col) || studToEntrance > parameter.getPlayer().getSchoolBoard().getStudentHall().get(col)) {
                //todo manda errore e chiede di reinserire - fare check lato client
            }
        }
        for(PawnColor col : PawnColor.values()){
            int studToHall = parameter.getColorMap1().get(col);
            int studToEntrance = parameter.getColorMap2().get(col);
            parameter.getPlayer().getSchoolBoard().getStudentHall().replace(col, parameter.getPlayer().getSchoolBoard().getStudentHall().get(col) + studToHall - studToEntrance);
            parameter.getPlayer().getSchoolBoard().getStudentEntrance().replace(col, parameter.getPlayer().getSchoolBoard().getStudentEntrance().get(col) - studToHall + studToEntrance);
        }
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