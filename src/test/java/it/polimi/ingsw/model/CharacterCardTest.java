package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    @Test
    void CharacterCard(){
        CharacterCard card = new CharacterCard(0, 2, null, "Caption");

        assertEquals(0, card.getID());
        assertEquals(2, card.getPrice());
        assertEquals("Caption", card.getCaption());

        card.setCaption("ciao");
        card.setPrice(12);
        card.setID(1);
        card.increasePrice();

        assertEquals(1, card.getID());
        assertEquals(13, card.getPrice());
        assertEquals("ciao", card.getCaption());

    }

    @Test
    void CharacterCard1(){
        CharacterCard1 card = new CharacterCard1();

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();

        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);

        card.initializeCard(par);
        for(PawnColor color : PawnColor.values())
            if(card.getStudents().get(color)!=0)
                par.setChosenColor(color);

        int old = par.getIsland().getIslandStudents().get(par.getChosenColor());

        card.startEffect(par);
        card.endEffect(par);
        assertEquals(old+1, par.getIsland().getIslandStudents().get(par.getChosenColor()));

        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color: PawnColor.values())
            map.put(color, 0);
        map.replace(PawnColor.pink, 1);
        par.setChosenColor(PawnColor.pink);
        old = par.getIsland().getIslandStudents().get(par.getChosenColor());
        card.setStudents(map);
        card.startEffect(par);
        card.endEffect(par);
        assertEquals(old+1, par.getIsland().getIslandStudents().get(par.getChosenColor()));
    }

    @Test
    void CharacterCard2(){

        CharacterCard2 card = new CharacterCard2();
        CharacterCard charCard = new CharacterCard (2,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);

        p1.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 4);
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.yellow, 2);
        p2.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 3);
        p2.getSchoolBoard().getStudentHall().replace(PawnColor.yellow, 4);
        p3.getSchoolBoard().getStudentHall().replace(PawnColor.green, 5);
        p1.getSchoolBoard().addProfessor(PawnColor.yellow);
        p2.getSchoolBoard().addProfessor(PawnColor.blue);
        p2.getSchoolBoard().addProfessor(PawnColor.green);

        assertFalse(p1.getSchoolBoard().getProfessorTable().get(PawnColor.blue));

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();
        //assertFalse(p3.getSchoolBoard().getProfessorTable().get(PawnColor.green));
        assertTrue(p1.getSchoolBoard().getProfessorTable().get(PawnColor.blue));
        //assertTrue(p1.getSchoolBoard().getProfessorTable().get(PawnColor.yellow));

    }

    @Test
    void CharacterCard3(){

        CharacterCard3 card = new CharacterCard3();
        CharacterCard charCard = new CharacterCard (3,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 2);
        p1.getSchoolBoard().getStudentEntrance().replace(PawnColor.blue, 1);
        p1.getSchoolBoard().addProfessor(PawnColor.blue);
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        p3.getSchoolBoard().setTowersColor(TowerColor.grey);
        for(PawnColor color : PawnColor.values())
            game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(color, 0);

        par.setIsland(game.getIslandManager().getIslandList().get(0));
        game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(PawnColor.blue, 3);
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();


        assertEquals(game.getIslandManager().getIslandList().get(0).getTowerColor(), TowerColor.white);

    }


    @Test
    void CharacterCard4(){

        CharacterCard4 card = new CharacterCard4();
        CharacterCard charCard = new CharacterCard (4,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 2);
        p1.getSchoolBoard().getStudentEntrance().replace(PawnColor.blue, 1);
        p1.getSchoolBoard().addProfessor(PawnColor.blue);
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        p3.getSchoolBoard().setTowersColor(TowerColor.grey);
        for(PawnColor color : PawnColor.values())
            game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(color, 0);

        par.setIsland(game.getIslandManager().getIslandList().get(0));
        game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(PawnColor.blue, 3);
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        assertTrue(p1.isBonus2Shifts());
        game.endEffect();
        assertFalse(p1.isBonus2Shifts());
    }


    @Test
    void CharacterCard5(){

        CharacterCard5 card = new CharacterCard5();
        CharacterCard charCard = new CharacterCard (5,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();
        par.setPlayer(p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);

        int old = game.getEntryTiles();


        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();
        assertEquals(old-1, game.getEntryTiles());
        assertTrue(game.getIslandManager().getIslandList().get(0).isNoEntryTile());
    }

    @Test
    void CharacterCard6(){

        CharacterCard6 card = new CharacterCard6();
        CharacterCard charCard = new CharacterCard (6,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();
        par.setPlayer(p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        assertFalse(game.isTowersDoCount());
        game.endEffect();
        assertTrue(game.isTowersDoCount());
    }

    @Test
    void CharacterCard7(){

        CharacterCard7 card = new CharacterCard7();

        CharacterCard charCard = new CharacterCard (7,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);

        //test set student
        Map<PawnColor, Integer> map1 = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map1.put(color, 0);
        card.setStudents(map1);
        for(PawnColor color : PawnColor.values())
            assertEquals(card.getStudents().get(color), 0);

        //map 2 : all student of entrance
        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map.put(color, 0);
        map.put(PawnColor.yellow, 2);
        map.put(PawnColor.pink, 1);
        par.setColorMap2(map);

        //map1 : all student on card
        Map<PawnColor, Integer> map2 = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map2.put(color, 0);
        map2.put(PawnColor.red, 2);
        map2.put(PawnColor.blue, 1);
        par.setColorMap1(map2);

        for(PawnColor color : PawnColor.values()) {
            p1.getSchoolBoard().getStudentEntrance().replace(color, map.get(color));
            card.getStudents().replace(color, map2.get(color));
        }

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        for(PawnColor color : PawnColor.values())
        {
            assertEquals(card.getStudents().get(color), par.getColorMap2().get(color));
            assertEquals(p1.getSchoolBoard().getStudentEntrance().get(color), par.getColorMap1().get(color));
        }

    }

    @Test
    void CharacterCard8(){

        CharacterCard8 card = new CharacterCard8();

        CharacterCard charCard = new CharacterCard (8,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        assertTrue(p1.getSchoolBoard().isBonus2influencepoints());
        game.endEffect();
        assertFalse(p1.getSchoolBoard().isBonus2influencepoints());

    }

    @Test
    void CharacterCard9(){

        CharacterCard9 card = new CharacterCard9();

        CharacterCard charCard = new CharacterCard (9,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        par.setPlayer(p1);
        par.setGame(game);
        par.setChosenColor(PawnColor.blue);

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        assertEquals(game.getKeptOut(), PawnColor.blue);
        game.endEffect();
        assertNull(game.getKeptOut());

    }

    @Test
    void CharacterCard10(){

        CharacterCard10 card = new CharacterCard10();

        CharacterCard charCard = new CharacterCard (10,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();

        Map<PawnColor, Integer> map1 = new HashMap<>();
        Map<PawnColor, Integer> map2 = new HashMap<>();

        for(PawnColor color : PawnColor.values())
        {
            map1.put(color, 0);
            map2.put(color, 0);
            p1.getSchoolBoard().getStudentEntrance().replace(color,0);
            p1.getSchoolBoard().getStudentHall().replace(color,0);
        }

        p1.getSchoolBoard().getStudentEntrance().replace(PawnColor.yellow, 2);
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.red, 2);
        map1.replace(PawnColor.yellow, 2);
        map2.replace(PawnColor.red, 2);

        par.setPlayer(p1);
        par.setGame(game);
        par.setColorMap1(map1);
        par.setColorMap2(map2);

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        for(PawnColor color : PawnColor.values()) {
            assertEquals(par.getColorMap1().get(color), p1.getSchoolBoard().getStudentHall().get(color));
            assertEquals(par.getColorMap2().get(color), p1.getSchoolBoard().getStudentEntrance().get(color));
        }

        game.endEffect();

    }

    @Test
    void CharacterCard11(){

        CharacterCard11 card = new CharacterCard11();

        Map<PawnColor, Integer> map = new HashMap<>();

        map.put(PawnColor.green, 1);
        card.setStudents(map);
        assertEquals(card.getStudents().get(PawnColor.green), 1);

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);
        card.setStudents(map);

        CharacterCard charCard = new CharacterCard (11,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();


        par.setPlayer(p1);
        par.setGame(game);

        card.initializeCard(par);

        for(PawnColor color : PawnColor.values())
            if(card.getStudents().get(color)!=null)
                par.setChosenColor(color);

        int old=p1.getSchoolBoard().getStudentHall().get(par.getChosenColor());

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        assertEquals(old+1, p1.getSchoolBoard().getStudentHall().get(par.getChosenColor()));

        game.endEffect();

    }

    @Test
    void CharacterCard12(){

        CharacterCard12 card = new CharacterCard12();

        CharacterCard charCard = new CharacterCard (12,1, card);

        Parameter par = new Parameter();

        Game game = new Game(3, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        for(Player p: game.getPlayers())
            p.setWallet(5);

        game.setupGame();
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.green, 3);
        p2.getSchoolBoard().getStudentHall().replace(PawnColor.green, 4);
        p3.getSchoolBoard().getStudentHall().replace(PawnColor.green, 2);
        par.setChosenColor(PawnColor.green);
        par.setGame(game);
        par.setPlayer(p1);

        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        assertEquals(0, p1.getSchoolBoard().getStudentHall().get(PawnColor.green));
        assertEquals(1, p2.getSchoolBoard().getStudentHall().get(PawnColor.green));
        assertEquals(0, p3.getSchoolBoard().getStudentHall().get(PawnColor.green));

        game.endEffect();

    }

}