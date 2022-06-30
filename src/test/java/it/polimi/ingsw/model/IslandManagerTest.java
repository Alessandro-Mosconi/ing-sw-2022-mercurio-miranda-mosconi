package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.model.TowerColor.black;
import static it.polimi.ingsw.model.TowerColor.white;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IslandManagerTest {

    @Test
    void mergeIslands() {

        //island list and islandManager creation
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
        //move mn
        islandManager.moveMotherNature(1);

        //delete island 0 and put their attribute to island 2 -> isola[0]= old.isola[1], isola[1]=old.isola[merge]
        //isola[0] = old.isola[1], isola[1] = merge isola[0] e isola[2]
        islandManager.checkForMerge();
        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(3));
        assertEquals(islandManager.getIslandList().get(0).getTowersNumber(), Integer.valueOf(2));
        assertEquals(islandManager.getIslandList().get(0).getStudentNumber(PawnColor.blue), Integer.valueOf(2));

        //check second method to merge the island in i position
        islandManager.setCurrMNPosition(2);
        islandManager.checkForMerge(2);
        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(2));
        assertEquals(islandManager.getIslandList().get(1).getTowersNumber(), Integer.valueOf(3));
        assertEquals(islandManager.getIslandList().get(1).getStudentNumber(PawnColor.blue), Integer.valueOf(3));


        //island list 2 creation
        ArrayList<Island> islandList2 = new ArrayList<Island>();
        islandList2.add(island);
        islandList2.add(island1);
        islandList2.add(island2);
        islandList2.add(island3);
        islandManager.setIslandList(islandList2);
        for(Island is : islandManager.getIslandList())
            is.setMotherNature(false);
        islandManager.setCurrMNPosition(0);

        //check of multi merge
        islandManager.checkForMerge();
        assertEquals(islandManager.getIslandList().size(), Integer.valueOf(2));

        //check of assign ingluence method call
        for(Island is : islandManager.getIslandList())
            is.setTowerColor(null);
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

    //test of moveMotherNature method
    @Test
    void moveMotherNature() {
        //island list and islandManager creation
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

        //mehtod call and check
        islandManager.moveMotherNature (5);
        assertTrue(island3.isMotherNature());
    }
}