package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void costructor(){
        Player player = new Player();

        assertNull(player.getNickName());
        assertEquals(player.getWallet(), Integer.valueOf(0));
        assertEquals(player.getPlayerNumber(), Integer.valueOf(0));
        assertNull(player.getLastAssistantCard());

        Deck deck = new Deck();
        SchoolBoard schoolboard = new SchoolBoard();
        Player player2 = new Player("bob", deck, 3, schoolboard);

        assertEquals(player2.getNickName(), "bob");
        assertEquals(player2.getWallet(), Integer.valueOf(0));
        assertEquals(player2.getPlayerNumber(), Integer.valueOf(3));
        assertNull(player2.getLastAssistantCard());
        assertEquals(player2.getSchoolBoard(), schoolboard);
        assertEquals(player2.getDeck(), deck);

        Player player3 = new Player(2, "bob", schoolboard);
        player3.setNickName("franco");
        assertEquals(2, player3.getPlayerNumber());
        assertEquals("franco", player3.getNickName());
        assertEquals(player3.getSchoolBoard(), schoolboard);


    }


    @Test
    void setWallet() {
        Player player = new Player();

        player.setWallet(3);

        assertEquals(player.getWallet(), Integer.valueOf(3));
    }
/*
    @Test
    void collectCoin() {
        Deck deck = new Deck();
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        schoolboard.addStudentEntrance(PawnColor.yellow, 3);
        Player player = new Player("bob", deck, 3, schoolboard);
        player.collectCoin(PawnColor.yellow);
        assertEquals(player.getWallet(), Integer.valueOf(1));
    }

 */

    @Test
    void useAssistantCard() {
        AssistantCard card = new AssistantCard(1, 12);
        assertEquals(card.getId(), "1");
        ArrayList<AssistantCard> cards = new ArrayList<>();
        cards.add(card);
        Deck deck = new Deck(cards);
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        Player player = new Player("bob", deck, 3, schoolboard);

        player.useAssistantCard(card);
        assertEquals(player.getLastAssistantCard(), card);
        assertTrue(player.getDeck().getCards().get(0).isConsumed());
        assertEquals(player.getMaxShift(), 12);

        player.setBonus2Shifts(true);
        player.useAssistantCard(card);
        assertEquals(player.getMaxShift(), 12+2);

    }

    @Test
    void GetSet(){

        ArrayList<AssistantCard> assistantCards = new ArrayList<>();
        assistantCards.add(new AssistantCard());
        Deck deck = new Deck(assistantCards);
        Player player = new Player();
        player.setDeck(deck);
        assertEquals(player.getDeck().getCards().size(), 1);

        player.setMaxShift(2);
        assertEquals(player.getMaxShift(), 2);

        player.setLastAssistantCard(player.getDeck().getCards().get(0));
        assertEquals(player.getDeck().getCards().get(0), player.getLastAssistantCard());

        SchoolBoard sc = new SchoolBoard();
        player.setSchoolBoard(sc);
        assertEquals(sc, player.getSchoolBoard());

        player.setPlayerNumber(3);
        assertEquals(3, player.getPlayerNumber());


    }

    @Test
    void moveFromEntranceToHall() {
        Deck deck = new Deck();
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        schoolboard.addStudentEntrance(PawnColor.yellow, 3);
        Player player = new Player("bob", deck, 3, schoolboard);

        player.moveFromEntranceToHall(PawnColor.yellow);
        player.moveFromEntranceToHall(PawnColor.red);

        assertEquals(player.getSchoolBoard().getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(2));
        assertEquals(player.getSchoolBoard().getStudentHall().get(PawnColor.yellow), Integer.valueOf(1));

    }

    @Test
    void moveFromCloudToEntrance() {
        Deck deck = new Deck();
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        Player player = new Player("bob", deck, 3, schoolboard);

        CloudTile cloud = new CloudTile();
        cloud.setStudents(PawnColor.red, 2);
        cloud.addStudents(PawnColor.blue);

        player.moveFromCloudToEntrance(cloud);

        assertEquals(player.getSchoolBoard().getStudentEntrance().get(PawnColor.red), Integer.valueOf(2));
        assertEquals(player.getSchoolBoard().getStudentEntrance().get(PawnColor.blue), Integer.valueOf(1));

        for (PawnColor color : PawnColor.values())
            assertEquals(cloud.getStudents().get(color), Integer.valueOf(0));

    }

    @Test
    void moveFromEntranceToIsland() {
        Deck deck = new Deck();
        SchoolBoard schoolboard = new SchoolBoard(6, TowerColor.grey, GameMode.expert);
        schoolboard.addStudentEntrance(PawnColor.pink, 2);
        Player player = new Player("bob", deck, 3, schoolboard);
        Map<PawnColor, Integer> map = new HashMap<PawnColor, Integer>();

        for(PawnColor color : PawnColor.values())
            map.put(color, 0);

        Island island = new Island(0, map, TowerColor.grey, 3, false, false);

        player.moveFromEntranceToIsland(island, PawnColor.yellow);
        player.moveFromEntranceToIsland(island, PawnColor.pink);

        assertEquals(player.getSchoolBoard().getStudentEntrance().get(PawnColor.yellow), Integer.valueOf(0));
        assertEquals(player.getSchoolBoard().getStudentEntrance().get(PawnColor.pink), Integer.valueOf(1));
        assertEquals(island.getIslandStudents().get(PawnColor.pink), Integer.valueOf(1));
        assertEquals(island.getIslandStudents().get(PawnColor.yellow), Integer.valueOf(0));

    }
}