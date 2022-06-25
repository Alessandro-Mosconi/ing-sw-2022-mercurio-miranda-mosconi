package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CloudTileTest {

    @Test
    public void testConstructor(){
        Map<PawnColor, Integer> map = new HashMap<>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        CloudTile cloud = new CloudTile(map);
        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(0));
        }

        CloudTile cloud2 = new CloudTile();
        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud2.getStudents().get(color), Integer.valueOf(0));
        }
        cloud2.setCloudID(2);
        assertEquals(cloud2.getCloudID(), 2);

        CloudTile cloud3 = new CloudTile(0, map);
        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud3.getStudents().get(color), Integer.valueOf(0));
        }
        assertEquals(cloud3.getCloudID(), 0);
    }

    @Test
    public void testAddStudent(){
        CloudTile cloud = new CloudTile();

        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(0));
        }

        cloud.addStudents(PawnColor.yellow);
        assertEquals(cloud.getStudents().get(PawnColor.yellow), Integer.valueOf(1));
    }

    @Test
    public void testSetReset(){
        CloudTile cloud = new CloudTile();

        for(PawnColor color : PawnColor.values()){
            cloud.setStudents(color, 3);
        }

        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(3));
        }

        for(PawnColor color : PawnColor.values()){
            cloud.reset(color);
        }
        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(0));
        }

        Map<PawnColor, Integer> map = new HashMap<>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 1);

        cloud.setStudentsMap(map);

        for(PawnColor color : PawnColor.values()){
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(1));
        }

    }

}
