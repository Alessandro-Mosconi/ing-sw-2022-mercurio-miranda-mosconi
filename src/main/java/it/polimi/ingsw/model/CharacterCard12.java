package it.polimi.ingsw.model;

public class CharacterCard12 extends CharacterCard{

    public CharacterCard12(int id, int price) {
        super(id, price);
    }

    // Scegli un colore di Studente; ogni giocatore (incluso te) deve rimettere nel sacchetto 3 Studenti
    // di quel colore presenti nella sua Sala. Chi avesse meno di 3 Studenti di quel colore, rimetterà tutti quelli che ha.
//TODO considerare il caso in cui la bag è vuota ma viene riempita da questo effetti (va ripetuto il check sulla bag)
    @Override
    public void Effect(Parameter parameter) {
        PawnColor chosenColor = chooseColor();
        for(SchoolBoard s : parameter.getGame().getSchoolBoards()){
            for(int i=0;i<3;i++) {
                if(s.getStudentHall().get(chosenColor)>0) {
                    s.getStudentHall().replace(chosenColor, s.getStudentHall().get(chosenColor) - 1);
                    parameter.getGame().getBag().replace(chosenColor, parameter.getGame().getBag().get(chosenColor)+1);
                }
            }
        }//Each player puts 3 students (or all of them if he has less than 3) of a chosen color from its Hall back to the bag.
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