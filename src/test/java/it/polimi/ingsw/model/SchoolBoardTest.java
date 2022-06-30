package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {

    //test of constructor methods
    @Test
    void constructor(){
        //schoolvoard(int, TowerColor, GameMode)
         SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
         assertEquals(schoolboard.getTowersNumber(), Integer.valueOf(6));
         assertEquals(schoolboard.getTowersColor(), TowerColor.grey);
         assertEquals(schoolboard.getGameMode(), GameMode.easy);

        //schoolboard()
        SchoolBoard schoolboard2 = new SchoolBoard();
        assertEquals(schoolboard2.getTowersNumber(), Integer.valueOf(0));
        assertNull(schoolboard2.getTowersColor());
        assertNull(schoolboard2.getGameMode());
        for (PawnColor color : PawnColor.values()) {
            assertEquals(schoolboard2.getProfessorTable().get(color), false);
            assertEquals(schoolboard2.getStudentEntrance().get(color), Integer.valueOf(0));
            assertEquals(schoolboard2.getStudentHall().get(color), Integer.valueOf(0));
        }

        //schoolboard(int, TowerColor, entranceMap, GameMode)
        SchoolBoard sc = new SchoolBoard(6, TowerColor.grey, new HashMap<PawnColor, Integer>(),GameMode.expert);
        assertEquals(sc.getTowersNumber(), 6);
        assertEquals(sc.getTowersColor(), TowerColor.grey);
        assertEquals(sc.getGameMode(), GameMode.expert);
        for (PawnColor color : PawnColor.values()) {
            assertEquals(sc.getProfessorTable().get(color), false);
            assertEquals(sc.getStudentEntrance().get(color), Integer.valueOf(0));
            assertEquals(sc.getStudentHall().get(color), Integer.valueOf(0));
        }

    }

    //Test of add and remove Entrance student methods
    @Test
    void addRemoveStudentEntrance() {
        //schoolboard creation
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);

        //add
        schoolboard.addStudentEntrance(PawnColor.blue);
        schoolboard.addStudentEntrance(PawnColor.yellow, 3);
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.blue), Integer.valueOf(1));
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(3));

        //remove
        schoolboard.removeStudentEntrance(PawnColor.yellow);
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(3-1));
    }

    //Test of add and remove Entrance student methods
    @Test
    void addRemoveStudentHall() {
        //schoolboard creation
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);

        //add
        schoolboard.addStudentHall(PawnColor.blue);
        assertEquals(schoolboard.getStudentHall().get(PawnColor.blue), Integer.valueOf(1));

        //remove
        schoolboard.removeStudentHall(PawnColor.blue);
        assertEquals(schoolboard.getStudentHall().get(PawnColor.yellow), Integer.valueOf(0));
    }

    //test of setTowerNumber method
    @Test
    void setTowersNumber() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.setTowersNumber(schoolboard.getTowersNumber()-1);
        assertEquals(schoolboard.getTowersNumber(), Integer.valueOf(5));
    }

    //test of illegal remove from hall and entrance
    @Test
    void illegalRemove(){
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        //illegal hall remove
        schoolboard.removeStudentHall(PawnColor.blue);
        assertEquals(schoolboard.getStudentHall().get(PawnColor.blue), 0);
        //illegal entrnace remove
        schoolboard.removeStudentEntrance(PawnColor.blue);
        assertEquals(schoolboard.getStudentEntrance().get(PawnColor.blue), 0);
    }

    //test add professor method
    @Test
    void addProfessor() {
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.easy);
        schoolboard.addProfessor(PawnColor.blue);
        assertEquals(schoolboard.getProfessorTable().get(PawnColor.blue), true);
        schoolboard.removeProfessor(PawnColor.blue);
        assertEquals(schoolboard.getProfessorTable().get(PawnColor.blue), false);
    }

    //test checkForCoin method
    @Test
    void checkForCoin() {
        SchoolBoard schoolboard2 = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        schoolboard2.addStudentEntrance(PawnColor.blue, 6);
        assertTrue(schoolboard2.checkForCoin(PawnColor.blue));
    }

    //test of getter and setter
    @Test
    void GetSet(){
        SchoolBoard sc = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        HashMap<PawnColor, Integer> map = new HashMap<>();
        map.put(PawnColor.blue, 2);
        HashMap<PawnColor, Boolean> profMap = new HashMap<>();
        profMap.put(PawnColor.pink, true);

        sc.setTowersColor(TowerColor.white);
        assertEquals(TowerColor.white, sc.getTowersColor());

        sc.setTowersNumber(8);
        assertEquals(8, sc.getTowersNumber());

        sc.setBonus2influencepoints(true);
        assertTrue(sc.isBonus2influencepoints());

        sc.setStudentHall(map);
        assertEquals(map, sc.getStudentHall());

        sc.setStudentEntrance(map);
        assertEquals(map, sc.getStudentEntrance());

        sc.setProfessorTable(profMap);
        assertEquals(profMap, sc.getProfessorTable());
    }

}