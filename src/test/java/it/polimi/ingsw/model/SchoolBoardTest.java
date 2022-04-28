package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {

    @Test
    void constructor(){
         SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);

         assertEquals(schoolboard.getTowersNumber(), Integer.valueOf(6));
         assertEquals(schoolboard.getTowersColor(), TowerColor.grey);
         assertEquals(schoolboard.getGameMode(), GameMode.easy);

         for (PawnColor color : PawnColor.values()) {
            assertEquals(schoolboard.getProfessorTable().get(color), false);
            assertEquals(schoolboard.getStudentEntrance().get(color), Integer.valueOf(0));
            assertEquals(schoolboard.getStudentHall().get(color), Integer.valueOf(0));
         }

        SchoolBoard schoolboard2 = new SchoolBoard();

        assertEquals(schoolboard2.getTowersNumber(), Integer.valueOf(0));
        assertNull(schoolboard2.getTowersColor());
        assertNull(schoolboard2.getGameMode());

        for (PawnColor color : PawnColor.values()) {
            assertEquals(schoolboard.getProfessorTable().get(color), false);
            assertEquals(schoolboard.getStudentEntrance().get(color), Integer.valueOf(0));
            assertEquals(schoolboard.getStudentHall().get(color), Integer.valueOf(0));
        }
    }
    @Test
    void addRemoveStudentEntrance() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.addStudentEntrance(PawnColor.blue);
        schoolboard.addStudentEntrance(PawnColor.yellow, 3);

        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.blue), Integer.valueOf(1));
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(3));

        schoolboard.removeStudentEntrance(PawnColor.yellow);
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(3-1));
    }

    @Test
    void addStudentHall() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.addStudentHall(PawnColor.blue);

        assertEquals(schoolboard.getStudentHall().get(PawnColor.blue), Integer.valueOf(1));

        schoolboard.removeStudentHall(PawnColor.blue);
        assertEquals(schoolboard.getStudentHall().get(PawnColor.yellow), Integer.valueOf(0));
    }

    @Test
    void setTowersNumber() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.setTowersNumber(schoolboard.getTowersNumber()-1);

        assertEquals(schoolboard.getTowersNumber(), Integer.valueOf(5));
    }

    @Test
    void illegalRemove(){
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        //schoolboard.removeProfessor(PawnColor.blue);
        //assertEquals(schoolboard.getProfessorTable().get(PawnColor.blue), false);
        schoolboard.removeStudentHall(PawnColor.blue);
        assertEquals(schoolboard.getStudentHall().get(PawnColor.blue), 0);
        schoolboard.removeStudentEntrance(PawnColor.blue);
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.blue), 0);
    }

    @Test
    void addProfessor() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.addProfessor(PawnColor.blue);
        assertEquals(schoolboard.getProfessorTable().get(PawnColor.blue), true);

        schoolboard.removeProfessor(PawnColor.blue);
        assertEquals(schoolboard.getProfessorTable().get(PawnColor.blue), false);
    }

    @Test
    void checkForCoin() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        assertEquals(schoolboard.checkForCoin(PawnColor.blue), false);

        SchoolBoard schoolboard2 = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        schoolboard2.addStudentEntrance(PawnColor.blue, 6);
        assertEquals(schoolboard2.checkForCoin(PawnColor.blue), true);

    }
}