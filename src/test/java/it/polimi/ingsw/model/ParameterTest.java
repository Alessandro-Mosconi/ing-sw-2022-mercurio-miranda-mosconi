package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParameterTest {

    //test of get set methods of Parameter class
    @Test
    void setting() {
        //settings creation
        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map.put(color, 0);
        Island island = new Island(0, map, TowerColor.white, 1, false, false);
        Game game = new Game();
        Player  player = new Player();
        Map<PawnColor, Integer> map1 = new HashMap<>();
        Map<PawnColor, Integer> map2 = new HashMap<>();

        //parameter creation and check
        Parameter par = new Parameter(player);
        assertEquals(player, par.getPlayer());

        Parameter par1 = new Parameter(game);
        assertEquals(game, par1.getGame());

        Parameter par2 = new Parameter(game, player);
        assertEquals(game, par2.getGame());
        assertEquals(player, par2.getPlayer());

        par.setGame(game);
        assertEquals(game, par.getGame());
        par.setIsland(island);
        assertEquals(island, par.getIsland());

        par1.setPlayer(player);
        assertEquals(player, par1.getPlayer());

        Parameter par3 = new Parameter(island);
        assertEquals(island, par3.getIsland());

        Parameter par4 = new Parameter(game, island);
        assertEquals(island, par4.getIsland());
        assertEquals(game, par4.getGame());

        par = new Parameter();
        par.setColorMap1(map1);
        par.setColorMap2(map2);
        par.setChosenColor(PawnColor.blue);
        assertEquals(map1, par.getColorMap1());
        assertEquals(map2, par.getColorMap2());
        assertEquals(PawnColor.blue, par.getChosenColor());
        assertNull(par.getPlayer());
        assertNull(par.getIsland());
        assertNull(par.getGame());

    }
}