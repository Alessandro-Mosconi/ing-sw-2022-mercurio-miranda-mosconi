package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    //test of constructor and method of CharacterCard class
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

    //test of CharacterCard1 class method and effects
    @Test
    void CharacterCard1(){
        //creation of a game and the card
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

        //setting of the parameters the card need
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);
        card.initializeCard(par);
        for(PawnColor color : PawnColor.values())
            if(card.getStudents().get(color)!=0)
                par.setChosenColor(color);

        //ol variable used to checkl the old value of the student number on that island
        int old = par.getIsland().getIslandStudents().get(par.getChosenColor());

        //start of the effect
        card.startEffect(par);
        card.endEffect(par);

        //check if the student moved
        assertEquals(old+1, par.getIsland().getIslandStudents().get(par.getChosenColor()));

        //creation of a map with only 1 pink student
        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color: PawnColor.values())
            map.put(color, 0);
        map.replace(PawnColor.pink, 1);

        //parameter setting
        par.setChosenColor(PawnColor.pink);
        old = par.getIsland().getIslandStudents().get(par.getChosenColor());

        //used the map created to check if the student moved
        card.setStudents(map);
        card.startEffect(par);
        card.endEffect(par);
        assertEquals(old+1, par.getIsland().getIslandStudents().get(par.getChosenColor()));
    }

    //test of CharacterCard2 class method and effects
    @Test
    void CharacterCard2(){

        //creation of a game and the card
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
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 4);
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.yellow, 2);
        p2.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 3);
        p2.getSchoolBoard().getStudentHall().replace(PawnColor.yellow, 4);
        p3.getSchoolBoard().getStudentHall().replace(PawnColor.green, 5);
        p1.getSchoolBoard().addProfessor(PawnColor.yellow);
        p2.getSchoolBoard().addProfessor(PawnColor.blue);
        p2.getSchoolBoard().addProfessor(PawnColor.green);

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);

        //check if the standard professor is blue
        assertFalse(p1.getSchoolBoard().getProfessorTable().get(PawnColor.blue));

        //start the effect
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();

        //check if the professor is changed
        assertTrue(p1.getSchoolBoard().getProfessorTable().get(PawnColor.blue));

    }

    //test of CharacterCard3 class method and effects
    @Test
    void CharacterCard3(){

        //creation of a game and the card
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
        game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(PawnColor.blue, 3);

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        card.initializeCard(par);

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();

        //check the result
        assertEquals(game.getIslandManager().getIslandList().get(0).getTowerColor(), TowerColor.white);
    }

    //test of CharacterCard4 class method and effects
    @Test
    void CharacterCard4(){

        //creation of a game and the card
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
        p1.getSchoolBoard().getStudentHall().replace(PawnColor.blue, 2);
        p1.getSchoolBoard().getStudentEntrance().replace(PawnColor.blue, 1);
        p1.getSchoolBoard().addProfessor(PawnColor.blue);
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        p3.getSchoolBoard().setTowersColor(TowerColor.grey);
        for(PawnColor color : PawnColor.values())
            game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(color, 0);
        game.getIslandManager().getIslandList().get(0).getIslandStudents().replace(PawnColor.blue, 3);

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);
        par.setIsland(game.getIslandManager().getIslandList().get(0));

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        //check the result
        assertTrue(p1.isBonus2Shifts());

        //effect end and result check
        game.endEffect();
        assertFalse(p1.isBonus2Shifts());
    }

    //test of CharacterCard4 class method and effects
    @Test
    void CharacterCard5(){

        //creation of a game and the card
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

        //parameter settings
        par.setPlayer(p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);

        //old value of number of entryTiles saved
        int old = game.getEntryTiles();

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();

        //check if old number of entryTile decreased and island noEntryTile is true
        assertEquals(old-1, game.getEntryTiles());
        assertTrue(game.getIslandManager().getIslandList().get(0).isNoEntryTile());
    }

    //test of CharacterCard6 class method and effects
    @Test
    void CharacterCard6(){

        //creation of a game and the card
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

        //parameter settings
        par.setPlayer(p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setGame(game);

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        //check effect
        assertFalse(game.isTowersDoCount());

        //effect end and chek effect undo
        game.endEffect();
        assertTrue(game.isTowersDoCount());
    }

    //test of CharacterCard7 class method and effects
    @Test
    void CharacterCard7(){

        //creation of a game and the card
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

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);

        //test setStudent method
        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map.put(color, 0);
        card.setStudents(map);
        for(PawnColor color : PawnColor.values())
            assertEquals(card.getStudents().get(color), 0);

        //map 2 : all student of entrance
        Map<PawnColor, Integer> map2 = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map2.put(color, 0);
        map2.put(PawnColor.yellow, 2);
        map2.put(PawnColor.pink, 1);

        //map1 : all student on card
        Map<PawnColor, Integer> map1 = new HashMap<>();
        for(PawnColor color : PawnColor.values())
            map1.put(color, 0);
        map1.put(PawnColor.red, 2);
        map1.put(PawnColor.blue, 1);

        //copy of map2 in students card
        for(PawnColor color : PawnColor.values()) {
            p1.getSchoolBoard().getStudentEntrance().replace(color, map2.get(color));
            card.getStudents().replace(color, map1.get(color));
        }

        //parameter settings
        par.setColorMap1(map1);
        par.setColorMap2(map2);

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        //check swap success
        for(PawnColor color : PawnColor.values())
        {
            assertEquals(card.getStudents().get(color), par.getColorMap2().get(color));
            assertEquals(p1.getSchoolBoard().getStudentEntrance().get(color), par.getColorMap1().get(color));
        }

    }

    //test of CharacterCard8 class method and effects
    @Test
    void CharacterCard8(){

        //creation of a game and the card
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

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);

        //effect start and check
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        assertTrue(p1.getSchoolBoard().isBonus2influencepoints());

        //effect end and check
        game.endEffect();
        assertFalse(p1.getSchoolBoard().isBonus2influencepoints());
    }

    //test of CharacterCard9 class method and effects
    @Test
    void CharacterCard9(){

        //creation of a game and the card
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

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        par.setChosenColor(PawnColor.blue);

        //effect start and check
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        assertEquals(game.getKeptOut(), PawnColor.blue);

        //effect end and check
        game.endEffect();
        assertNull(game.getKeptOut());
    }

    //test of CharacterCard9 class method and effects
    @Test
    void CharacterCard10(){

        //creation of a game and the card
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

        //map1 - entrance and map2 - hall created
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

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        par.setColorMap1(map1);
        par.setColorMap2(map2);

        //start effect
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();

        //check effect
        for(PawnColor color : PawnColor.values()) {
            assertEquals(par.getColorMap1().get(color), p1.getSchoolBoard().getStudentHall().get(color));
            assertEquals(par.getColorMap2().get(color), p1.getSchoolBoard().getStudentEntrance().get(color));
        }


    }

    //test of CharacterCard9 class method and effects
    @Test
    void CharacterCard11(){

        //creation of a game and the card and check set students method
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

        //parameter settings
        par.setPlayer(p1);
        par.setGame(game);
        card.initializeCard(par);
        for(PawnColor color : PawnColor.values())
            if(card.getStudents().get(color)!=null)
                par.setChosenColor(color);

        //saving old chosen color student number of the hall
        int old=p1.getSchoolBoard().getStudentHall().get(par.getChosenColor());

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();
        game.endEffect();

        //check student move in hall
        assertEquals(old+1, p1.getSchoolBoard().getStudentHall().get(par.getChosenColor()));


    }

    //test of CharacterCard9 class method and effects
    @Test
    void CharacterCard12(){

        //creation of a game and the card and check set students method
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

        //parameter settings
        par.setChosenColor(PawnColor.green);
        par.setGame(game);
        par.setPlayer(p1);

        //effect start
        game.setCurrEffect(charCard);
        game.setCurrParameter(par);
        game.startEffect();

        //check effect
        assertEquals(0, p1.getSchoolBoard().getStudentHall().get(PawnColor.green));
        assertEquals(1, p2.getSchoolBoard().getStudentHall().get(PawnColor.green));
        assertEquals(0, p3.getSchoolBoard().getStudentHall().get(PawnColor.green));

        game.endEffect();

    }

}