package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    @Test
    void addStudent() {
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(0, map, null, 0, false, false);

        island.addStudent(PawnColor.blue);
        island.addStudent(PawnColor.blue);
        assertEquals(island.getIslandStudents().get(PawnColor.blue), Integer.valueOf(2));

    }

    @Test
    void GetSet(){
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(0, map, null, 0, false, false);

        island.setIslandID(1);
        assertEquals(1, island.getIslandID());

        island.setTowerColor(TowerColor.grey);
        assertEquals(island.getTowerColor(), TowerColor.grey);

        island.setTowersNumber(2);
        assertEquals(2, island.getTowersNumber());

        island.setNoEntryTile(true);
        assertTrue(island.isNoEntryTile());

        island.setTowersDoCount(false);
        assertFalse(island.isTowersDoCount());
        island.setKeptOut(PawnColor.red);
        assertEquals(island.getKeptOut(), PawnColor.red);

        Map<PawnColor, Integer> map2 = new HashMap<PawnColor, Integer>();
        island.setIslandStudents(map2);
        assertEquals(map2, island.getIslandStudents());


    }

    @Test
    void assignInfluence() {
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(0, map, TowerColor.white, 1, false, false);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.blue);

        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.white, GameMode.easy);
        schoolboard.addProfessor(PawnColor.blue);

        SchoolBoard schoolboard2 = new SchoolBoard(6, TowerColor.black, GameMode.easy);
        schoolboard2.addProfessor(PawnColor.green);
        schoolboard2.setBonus2influencepoints(true);

        assertEquals(island.calculatePlayerInfluence(schoolboard), Integer.valueOf(2));

        assertEquals(island.calculatePlayerInfluence(schoolboard2), Integer.valueOf(5));

        ArrayList<SchoolBoard> array = new ArrayList<SchoolBoard>();
        array.add(schoolboard);
        array.add(schoolboard2);

        island.assignInfluence(array);

        assertEquals(island.getTowerColor(), TowerColor.black);

        island.addStudent(PawnColor.blue);
        island.addStudent(PawnColor.blue);
        island.setTowerColor(TowerColor.white);
        schoolboard2.setBonus2influencepoints(false);

        island.assignInfluence(array);

        assertEquals(island.getTowerColor(), TowerColor.white);

        array.add(schoolboard);
        island.setTowerColor(null);
        island.assignInfluence(array);

        assertNull(island.getTowerColor());


    }
}