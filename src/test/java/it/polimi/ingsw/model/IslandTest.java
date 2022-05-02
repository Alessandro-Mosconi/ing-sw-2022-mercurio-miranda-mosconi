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

        Island island = new Island(map, null, 0, false, false);

        island.addStudent(PawnColor.blue);
        island.addStudent(PawnColor.blue);
        assertEquals(island.getIslandStudents().get(PawnColor.blue), Integer.valueOf(2));

    }

    @Test
    void setTowerColor(){
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(map, null, 0, false, false);

        island.setTowerColor(TowerColor.grey);
        assertEquals(island.getTowerColor(), TowerColor.grey);

    }

    @Test
    void assignInfluence() {
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(map, TowerColor.white, 1, false, false);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.green);
        island.addStudent(PawnColor.blue);

        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.white, GameMode.easy);
        schoolboard.addProfessor(PawnColor.blue);
        SchoolBoard schoolboard2 = new SchoolBoard(6, TowerColor.black, GameMode.easy);
        schoolboard2.addProfessor(PawnColor.green);

        assertEquals(island.calculatePlayerInfluence(schoolboard), Integer.valueOf(2));
        assertEquals(island.calculatePlayerInfluence(schoolboard2), Integer.valueOf(3));

        ArrayList<SchoolBoard> array = new ArrayList<SchoolBoard>();
        array.add(schoolboard);
        array.add(schoolboard2);

        island.assignInfluence(array);

        assertEquals(island.getTowerColor(), TowerColor.black);
    }
}