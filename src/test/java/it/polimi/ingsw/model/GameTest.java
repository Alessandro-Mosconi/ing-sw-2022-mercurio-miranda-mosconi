package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    //test of setup method of Game class
    @Test
    void setupGame() {

        //Game() constructor
        Game game = new Game();
        assertEquals(game.getBank(), 20);
        assertEquals(game.getNumberOfPlayers(), 0);
        assertEquals(game.getGameID(), "0");
        assertFalse(game.isStarted());

        //test setNumberOfPlayer method
        game.setNumberOfPlayers(2);
        assertEquals(2, game.getNumberOfPlayers());


        //Game(int, string, Gamemode) constructor
        game = new Game(3, "g1", GameMode.easy);
        Game game2 = new Game(2, "g2", GameMode.expert);
        assertEquals(game.getGameMode(), GameMode.easy);
        assertEquals(game.getNumberOfPlayers(), 3);
        assertEquals(game.getGameID(), "g1");
        assertFalse(game.isStarted());

        //add of players to game
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //add of players to game2
        Player p4 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p5 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        ArrayList<Player> players = new ArrayList<>();
        players.add(p4);
        players.add(p5);
        game2.setPlayers(players);

        //setup call
        game2.setupGame();
        game.setupGame();

        //check attributes change
        assertEquals(12, game2.getAllCharacterCard().size());
        assertEquals(3, game2.getChosenCharacterCards().size());
        assertEquals(4, game2.getEntryTiles());
        assertEquals(20, game2.getBank());
        assertFalse(game.isBagEmpty());
        for (Player p : game2.getPlayers()) {
            assertEquals(1, p.getWallet());
        }

        //check bag pawn remove
        int counter = 0;
        for (PawnColor color : PawnColor.values()) {
            counter = counter + game.getBag().get(color);
        }
        assertEquals(counter, Integer.valueOf(93));

        //check island pawn add 3 player easy
        counter = 0;
        for (Island island : game.getIslandManager().getIslandList()) {
            for (PawnColor color : PawnColor.values()) {
                counter = counter + island.getIslandStudents().get(color);
            }
        }
        assertEquals(counter, Integer.valueOf(10));

        //check island pawn add 2 player expert
        counter = 0;
        for (Island island : game2.getIslandManager().getIslandList()) {
            for (PawnColor color : PawnColor.values()) {
                counter = counter + island.getIslandStudents().get(color);
            }
        }
        assertEquals(counter, Integer.valueOf(10));

        //check schoolboard pawn add 3 player easy
        counter = 0;
        for (Player p : game.getPlayers())
            for (PawnColor color : PawnColor.values()) {
                counter = p.getSchoolBoard().getStudentEntrance().get(color) + counter;
            }

        assertEquals(counter, Integer.valueOf(27));

        //check schoolboard pawn add 2 player expert
        counter = 0;
        for (Player p : game2.getPlayers())
            for (PawnColor color : PawnColor.values()) {
                counter = p.getSchoolBoard().getStudentEntrance().get(color) + counter;
            }
        assertEquals(counter, Integer.valueOf(14));
    }

    //test of moveFromBagToCloud method
    @Test
    void moveFromBagToCloud() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //game setup + fill + move method
        game.setupGame();
        game.fillCloudTiles();
        game.moveFromBagToCloud(game.getCloudTiles().get(0));

        //counter used to count all the pawn in bag and check
        int counter = 0;
        for (PawnColor color : PawnColor.values()) {
            counter = game.getBag().get(color) + counter;
        }
        assertEquals(counter, Integer.valueOf(80));

        //cpunter used to count all the pawn on clouds
        counter = 0;
        for (PawnColor color : PawnColor.values()) {
            counter = game.getCloudTiles().get(0).getStudents().get(color) + counter;
        }
        assertEquals(counter, Integer.valueOf(5));
    }

    //test of updateProfessor method
    @Test
    void updateProfessor() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //game setup
        game.setupGame();

        //add blue professor to p2 and add 1 blue studnet to p1 hall
        p2.getSchoolBoard().addProfessor(PawnColor.blue);
        p1.getSchoolBoard().addStudentHall(PawnColor.blue);

        //call of update professor and check
        game.updateProfessor(PawnColor.blue);
        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessorTable().get(PawnColor.blue));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessorTable().get(PawnColor.blue));
    }

    //test of calculatePlayerOrder methos
    @Test
    void calculatePlayerOrder() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //game setup
        game.setupGame();

        //assistant card creation
        AssistantCard assistantCard1 = new AssistantCard(1, 5);
        AssistantCard assistantCard2 = new AssistantCard(2, 5);
        AssistantCard assistantCard3 = new AssistantCard(3, 10);
        p1.setLastAssistantCard(assistantCard2);
        p2.setLastAssistantCard(assistantCard3);
        p3.setLastAssistantCard(assistantCard1);

        //check player reorder
        ArrayList<Integer> newOrder = game.calculatePlayerOrder();
        assertEquals(3, newOrder.get(0));
        assertEquals(1, newOrder.get(1));
        assertEquals(2, newOrder.get(2));
    }

    //test of fillCloudTiles method
    @Test
    void fillCloudTiles() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //game setup
        game.setupGame();

        //method call
        //check bag removes
        game.fillCloudTiles();
        int counter = 0;
        for (PawnColor color : PawnColor.values()) {
            counter = counter + game.getBag().get(color);
        }
        assertEquals(81, counter);
        // check cloud update
        counter = 0;
        for (CloudTile cloud : game.getCloudTiles()) {
            for (PawnColor color : PawnColor.values()) {
                counter = counter + cloud.getStudents().get(color);
            }
        }
        assertEquals(12, counter);

        //2 player case
        game = new Game(2, "g1", GameMode.easy);
        game.addPlayer(p1);
        game.addPlayer(p2);
        //setup
        game.setupGame();

        //method call and previous check
        game.fillCloudTiles();
        counter = 0;
        for (PawnColor color : PawnColor.values()) {
            counter = counter + game.getBag().get(color);
        }
        assertEquals(100, counter);
        counter = 0;
        for (CloudTile cloud : game.getCloudTiles()) {
            for (PawnColor color : PawnColor.values()) {
                counter = counter + cloud.getStudents().get(color);
            }
        }
        assertEquals(6, counter);
    }

    //test of useAssistantCard method
    @Test
    void useAssistantCard() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        //game setup
        game.setupGame();

        //card creation, method call and check
        AssistantCard card = p1.getDeck().getCards().get(0);
        game.useAssistantCard(p1, card);
        assertEquals(card, p1.getLastAssistantCard());
    }

    //test of movePawnToIsland method
    @Test
    void movePawnToIsland() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.setupGame();
        p1.getSchoolBoard().addStudentEntrance(PawnColor.blue);

        //old value of island student saved
        int old = game.getIslandManager().getIslandList().get(0).getIslandStudents().get(PawnColor.blue);

        //method call and check
        game.movePawnToIsland(p1, PawnColor.blue, game.getIslandManager().getIslandList().get(0));
        assertEquals(old + 1, game.getIslandManager().getIslandList().get(0).getIslandStudents().get(PawnColor.blue));
    }

    //test of movePawnToHall method
    @Test
    void movePawnToHall() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.setupGame();
        p1.getSchoolBoard().addStudentEntrance(PawnColor.blue);

        //old value of hall student saved
        int old = p1.getSchoolBoard().getStudentHall().get(PawnColor.blue);

        //method call and check
        game.movePawnToHall(p1, PawnColor.blue);
        assertEquals(old + 1, p1.getSchoolBoard().getStudentHall().get(PawnColor.blue));
    }

    //test of moveFromCloudToEntrance method
    @Test
    void moveFromCloudToEntrance() {
        //game creation
        Game game = new Game(3, "g1", GameMode.easy);
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.setupGame();
        game.getCloudTiles().get(0).addStudents(PawnColor.blue);

        //old value of entrance student saved
        int old = p1.getSchoolBoard().getStudentEntrance().get(PawnColor.blue);

        //method call and check
        game.moveFromCloudToEntrance(game.getPlayers().get(0), game.getCloudTiles().get(0));
        assertEquals(old + 1, p1.getSchoolBoard().getStudentEntrance().get(PawnColor.blue));
        assertEquals(0, game.getCloudTiles().get(0).getStudents().get(PawnColor.blue));
    }

    //test of moveMN method
    @Test
    void moveMN() {
        //game creation
        Game game = new Game(2, "g1", GameMode.expert);
        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.getSchoolBoards().add(p1.getSchoolBoard());
        game.getSchoolBoards().add(p2.getSchoolBoard());
        game.setupGame();

        //method call and check
        int currMN = game.getIslandManager().getCurrMNPosition();
        game.moveMN(3);
        assertTrue(game.getIslandManager().getIslandList().get(currMN + 3).isMotherNature());
        currMN = (currMN + 3) % 12;
        game.getIslandManager().getIslandList().get((currMN + 10) % 12).setNoEntryTile(true);
        game.moveMN(10);
        assertTrue(game.getIslandManager().getIslandList().get((currMN + 10) % 12).isMotherNature());
    }

    //test of removeWizard method
    @Test
    void removeWizard() {
        //game creation
        Game game = new Game(2, "g1", GameMode.expert);
        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setupGame();

        //method call and check
        game.removeWizard(WizardType.wizard1);
        boolean flag = false;
        for (WizardType wiz : game.getAvailableWizards()) {
            flag = wiz.equals(WizardType.wizard1) || flag;
        }
        assertEquals(game.getAvailableWizards().size(), 3);
        assertFalse(flag);
        ArrayList<WizardType> array = new ArrayList<>();
        array.add(WizardType.wizard3);
        game.setAvailableWizards(array);
        assertEquals(game.getAvailableWizards().get(0), WizardType.wizard3);
    }

    //test of removeTowerColor method
    @Test
    void removeTowerColor() {
        //game creation
        Game game = new Game(2, "g1", GameMode.expert);
        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setupGame();

        //method call and check
        game.removeTowerColor(TowerColor.grey);
        boolean flag = false;
        for (TowerColor color : game.getAvailableTowerColors()) {
            flag = color.equals(TowerColor.grey) || flag;
        }
        assertEquals(game.getAvailableTowerColors().size(), 2);
        assertFalse(flag);
        ArrayList<TowerColor> array = new ArrayList<>();
        array.add(TowerColor.grey);
        game.setAvailableTowerColors(array);
        assertEquals(game.getAvailableTowerColors().get(0), TowerColor.grey);
    }

    //test of increaseCurrEffectPrice method
    @Test
    void increaseCurrEffectPrice() {
        //game creation
        Game game = new Game(2, "g1", GameMode.expert);
        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setupGame();
        int bank = game.getBank();
        p1.setWallet(5);
        game.setCurrEffect(new CharacterCard(1, 3, new CharacterCard1()));
        Parameter par = new Parameter(game, p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setChosenColor(PawnColor.blue);
        game.setCurrParameter(par);

        //trigger method with start effect check
        game.startEffect();
        assertEquals(2, p1.getWallet());

        //method call and check
        game.increaseCurrEffectPrice();
        assertEquals(4, game.getCurrEffect().getPrice());
        assertEquals(bank + 3 - 1, game.getBank());
        game.endEffect();
        assertNull(game.getCurrEffect());
    }

    //test of increaseCurrEffectPrice method
    @Test
    void checkForEndGameConditions() {
        //game creation
        Game game = new Game(2, "g1", GameMode.expert);
        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.setupGame();

        //set of 0 tower condition
        p1.getSchoolBoard().setTowersNumber(0);
        assertTrue(game.checkForEndGameConditions());
        p1.getSchoolBoard().setTowersNumber(1);
        assertFalse(game.checkForEndGameConditions());

        //check of 3 island remaining condition
        for (int i = 0; i < 10; i++) {
            game.getIslandManager().getIslandList().get(i).setTowerColor(TowerColor.white);
            game.getIslandManager().getIslandList().get(i).setTowersNumber(1);
        }
        while (game.getIslandManager().getIslandList().size() != 3) {
            game.getIslandManager().checkForMerge(1);
        }
        assertTrue(game.checkForEndGameConditions());
    }

}