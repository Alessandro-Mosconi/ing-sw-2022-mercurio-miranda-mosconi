package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.TowerColor.*;
import static org.junit.jupiter.api.Assertions.*;

class IslandManagerTest {

    @Test
    void mergeIslands() {

        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();
        Map<PawnColor, Integer> map1 = new HashMap<PawnColor, Integer>();
        Map<PawnColor, Integer> map2 = new HashMap<PawnColor, Integer>();
        Map<PawnColor, Integer> map3 = new HashMap<PawnColor, Integer>();
        for(PawnColor color : PawnColor.values()) {
            map.put(color, 1);
            map1.put(color, 1);
            map2.put(color, 1);
            map3.put(color, 1);
        }

        Island island = new Island(0, map, black, 1, false, false);
        Island island1 = new Island(1, map1, black, 1, false, false);
        Island island2 = new Island(2, map2, white, 1, false, false);
        Island island3 = new Island(3, map3, black, 1, false, false);

        ArrayList<Island> islandList = new ArrayList<Island>();
        islandList.add(island);
        islandList.add(island1);
        islandList.add(island2);
        islandList.add(island3);

        IslandManager islandManager = new IslandManager(islandList);

        islandManager.moveMotherNature(1);

        //questo cancella la isola in 0 e la fonda con l'isola in 2 -> isola[0]= old.isola[1], isola[1]=old.isola[merge]
        //isola[0] = old.isola[1], isola[1] = merge isola[0] e isola[2]
        islandManager.checkForMerge();

        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(3));
        assertEquals(islandManager.getIslandList().get(0).getTowersNumber(), Integer.valueOf(2));
        assertEquals(islandManager.getIslandList().get(0).getStudentNumber(PawnColor.blue), Integer.valueOf(2));

        islandManager.setCurrMNPosition(2);
        islandManager.checkForMerge(2);

        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(2));
        assertEquals(islandManager.getIslandList().get(1).getTowersNumber(), Integer.valueOf(3));
        assertEquals(islandManager.getIslandList().get(1).getStudentNumber(PawnColor.blue), Integer.valueOf(3));


        ArrayList<Island> islandList2 = new ArrayList<Island>();
        islandList2.add(island);
        islandList2.add(island1);
        islandList2.add(island2);
        islandList2.add(island3);

        islandManager.setIslandList(islandList2);

        for(Island is : islandManager.getIslandList())
        {
            is.setMotherNature(false);
        }
        islandManager.setCurrMNPosition(0);
        islandManager.checkForMerge();
        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(2));

        for(Island is : islandManager.getIslandList())
        {
            is.setTowerColor(null);
        }
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.white, GameMode.easy);
        schoolboard.addProfessor(PawnColor.blue);

        SchoolBoard schoolboard2 = new SchoolBoard(6, TowerColor.black, GameMode.easy);
        schoolboard2.addProfessor(PawnColor.green);
        schoolboard2.setBonus2influencepoints(true);

        ArrayList<SchoolBoard> schoolBoards = new ArrayList<>();
        schoolBoards.add(schoolboard);
        schoolBoards.add(schoolboard2);

        islandManager.setCurrMNPosition(0);
        islandManager.assignInfluence(schoolBoards);
        assertEquals(islandManager.getIslandList().get(0).getTowerColor(), TowerColor.black);





    }

    @Test
    void assignInfluence(){

    }

    @Test
    void moveMotherNature() {
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();
        Map<PawnColor, Integer> map2 = new HashMap<PawnColor, Integer>();
        Map<PawnColor, Integer> map3 = new HashMap<PawnColor, Integer>();
        for(PawnColor color : PawnColor.values()) {
            map.put(color, 1);
            map2.put(color, 1);
            map3.put(color, 1);
        }

        Island island = new Island(0, map, null, 0, false, false);
        Island island2 = new Island(1, map2, null, 0, false, false);
        Island island3 = new Island(2, map3, null, 0, false, false);

        ArrayList<Island> islandList = new ArrayList<Island>();
        islandList.add(island);
        islandList.add(island2);
        islandList.add(island3);

        IslandManager islandManager = new IslandManager(islandList);

        islandManager.moveMotherNature (5);

        assertTrue(island3.isMotherNature());

    }
}