package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
        void setupGame() {
            Game game = new Game();

            assertEquals(game.getBank(), 20);
            assertEquals(game.getNumberOfPlayers(), 0);
            assertEquals(game.getGameID(), "0");
            assertFalse(game.isStarted());
            game.setNumberOfPlayers(2);
            assertEquals(2, game.getNumberOfPlayers());


        game = new Game(3, "g1", GameMode.easy);
        Game game2 = new Game(2, "g2", GameMode.expert);

        assertEquals(game.getGameMode(), GameMode.easy);
        assertEquals(game.getNumberOfPlayers(), 3);
        assertEquals(game.getGameID(), "g1");
        assertFalse(game.isStarted());

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        Player p4 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p5 = new Player("gigi", new Deck(), 2, new SchoolBoard());

        ArrayList<Player> players = new ArrayList<>();
        players.add(p4);
        players.add(p5);
        game2.setPlayers(players);

        game2.setupGame();
        game.setupGame();

        assertEquals(12, game2.getAllCharacterCard().size());
        assertEquals(3, game2.getChosenCharacterCards().size());
        assertEquals(4, game2.getEntryTiles());
        assertEquals(20, game2.getBank());
        assertFalse(game.isBagEmpty());

        for(Player p : game2.getPlayers())
        {
            assertEquals(1, p.getWallet());
        }
//check bag pawn remove
        int counter=0;
        for(PawnColor color : PawnColor.values()){
            counter = counter + game.getBag().get(color);
        }
        assertEquals(counter, Integer.valueOf(93));

//check island pawn add 3 player easy
        counter =0;
        for(Island island : game.getIslandManager().getIslandList()){
            for(PawnColor color : PawnColor.values()){
                counter = counter + island.getIslandStudents().get(color);
            }
        }
        assertEquals(counter, Integer.valueOf(10));
//check island pawn add 2 player expert
        counter=0;
        for(Island island : game2.getIslandManager().getIslandList()){
            for(PawnColor color : PawnColor.values()){
                counter = counter + island.getIslandStudents().get(color);
            }
        }
        assertEquals(counter, Integer.valueOf(10));

        //check schoolboard pawn add 3 player easy
        counter=0;
        for(Player p : game.getPlayers())
            for(PawnColor color : PawnColor.values()){
                counter = p.getSchoolBoard().getStudentEntrance().get(color) + counter;
            }

        assertEquals(counter, Integer.valueOf(27));

        //check schoolboard pawn add 2 player expert
        counter=0;
        for(Player p : game2.getPlayers())
            for(PawnColor color : PawnColor.values()){
                counter = p.getSchoolBoard().getStudentEntrance().get(color) + counter;
            }

        assertEquals(counter, Integer.valueOf(14));
        }

    @Test
    void moveFromBagToCloud() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        game.fillCloudTiles();
        game.moveFromBagToCloud(game.getCloudTiles().get(0));

        int counter = 0;
        for(PawnColor color : PawnColor.values()){
            counter = game.getBag().get(color) + counter;
        }

        assertEquals(counter, Integer.valueOf(80));
        counter=0;
    for(PawnColor color : PawnColor.values()){
        counter = game.getCloudTiles().get(0).getStudents().get(color) + counter;
    }
        assertEquals(counter, Integer.valueOf(5));
    }

    @Test
    void updateProfessor() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        p2.getSchoolBoard().addProfessor(PawnColor.blue);
        p1.getSchoolBoard().addStudentHall(PawnColor.blue);
        game.updateProfessor(PawnColor.blue);

        assertTrue(game.getPlayers().get(0).getSchoolBoard().getProfessorTable().get(PawnColor.blue));
        assertFalse(game.getPlayers().get(1).getSchoolBoard().getProfessorTable().get(PawnColor.blue));
    }
    @Test
    void calculatePlayerOrder() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();

        AssistantCard assistantCard1 = new AssistantCard(1, 5);
        AssistantCard assistantCard2 = new AssistantCard(2, 5);
        AssistantCard assistantCard3 = new AssistantCard(3, 10);
        p1.setLastAssistantCard(assistantCard2);
        p2.setLastAssistantCard(assistantCard3);
        p3.setLastAssistantCard(assistantCard1);

        ArrayList<Integer> newOrder = new ArrayList<>();
        newOrder =  game.calculatePlayerOrder();
        assertEquals(3, newOrder.get(0));
        assertEquals(1, newOrder.get(1));
        assertEquals(2, newOrder.get(2));
    }

    @Test
    void fillCloudTiles() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();

        game.fillCloudTiles();
        int counter=0;
        for(PawnColor color : PawnColor.values()){
            counter = counter + game.getBag().get(color);
        }

        assertEquals(81, counter);

        counter=0;
        for(CloudTile cloud : game.getCloudTiles()){
            for(PawnColor color : PawnColor.values()){
                counter = counter + cloud.getStudents().get(color);
            }
        }
        assertEquals(12, counter);

        //2 player
        game = new Game(2, "g1", GameMode.easy);

        game.addPlayer(p1);
        game.addPlayer(p2);

        game.setupGame();

        game.fillCloudTiles();
        counter=0;
        for(PawnColor color : PawnColor.values()){
            counter = counter + game.getBag().get(color);
        }

        assertEquals(100, counter);

        counter=0;
        for(CloudTile cloud : game.getCloudTiles()){
            for(PawnColor color : PawnColor.values()){
                counter = counter + cloud.getStudents().get(color);
            }
        }
        assertEquals(6, counter);
    }

    @Test
    void useAssistantCard(){
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        AssistantCard card = p1.getDeck().getCards().get(0);
        game.useAssistantCard(p1, card);
        assertEquals(card, p1.getLastAssistantCard());
    }

    @Test
    void movePawnToIsland() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        p1.getSchoolBoard().addStudentEntrance(PawnColor.blue);
         int old = game.getIslandManager().getIslandList().get(0).getIslandStudents().get(PawnColor.blue);
         game.movePawnToIsland(p1, PawnColor.blue, game.getIslandManager().getIslandList().get(0));
        assertEquals(old+1, game.getIslandManager().getIslandList().get(0).getIslandStudents().get(PawnColor.blue));
    }

    @Test
    void movePawnToHall() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        p1.getSchoolBoard().addStudentEntrance(PawnColor.blue);
        int old = p1.getSchoolBoard().getStudentHall().get(PawnColor.blue);
        game.movePawnToHall(p1, PawnColor.blue);
        assertEquals(old+1, p1.getSchoolBoard().getStudentHall().get(PawnColor.blue));
    }

    @Test
    void moveFromCloudToEntrance() {
        Game game = new Game(3, "g1", GameMode.easy);

        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.setupGame();
        game.getCloudTiles().get(0).addStudents(PawnColor.blue);
         int old = p1.getSchoolBoard().getStudentEntrance().get(PawnColor.blue);
         game.moveFromCloudToEntrance(game.getPlayers().get(0), game.getCloudTiles().get(0));

        assertEquals(old+1, p1.getSchoolBoard().getStudentEntrance().get(PawnColor.blue));
        assertEquals(0, game.getCloudTiles().get(0).getStudents().get(PawnColor.blue));


    }

    @Test
    void moveMN() {
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
        int currMN = game.getIslandManager().getCurrMNPosition();
        game.moveMN(3);
        assertTrue(game.getIslandManager().getIslandList().get(currMN+3).isMotherNature());
        currMN = (currMN+3)%12;
        game.getIslandManager().getIslandList().get((currMN+10)%12).setNoEntryTile(true);
        game.moveMN(10);
        assertTrue(game.getIslandManager().getIslandList().get((currMN+10)%12).isMotherNature());

    }

    @Test
    void removeWizard() {
        Game game = new Game(2, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);

        game.setupGame();

        game.removeWizard(WizardType.wizard1);
        boolean flag=false;
        for(WizardType wiz : game.getAvailableWizards())
        {
            flag = wiz.equals(WizardType.wizard1) || flag;
        }
        assertEquals(game.getAvailableWizards().size(), 3);
        assertFalse(flag);

        ArrayList<WizardType> array = new ArrayList<>();
        array.add(WizardType.wizard3);
        game.setAvailableWizards(array);
        assertEquals(game.getAvailableWizards().get(0), WizardType.wizard3);
    }

    @Test
    void removeTowerColor() {
        Game game = new Game(2, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);

        game.setupGame();

        game.removeTowerColor(TowerColor.grey);
        boolean flag=false;
        for(TowerColor color : game.getAvailableTowerColors())
        {
            flag = color.equals(TowerColor.grey) || flag;
        }
        assertEquals(game.getAvailableTowerColors().size(), 2);
        assertFalse(flag);


        ArrayList<TowerColor> array = new ArrayList<>();
        array.add(TowerColor.grey);
        game.setAvailableTowerColors(array);
        assertEquals(game.getAvailableTowerColors().get(0), TowerColor.grey);
    }

    @Test
    void increaseCurrEffectPrice() {
        Game game = new Game(2, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);

        game.setupGame();
        int bank= game.getBank();
        p1.setWallet(5);
        game.setCurrEffect(new CharacterCard (1,3,new CharacterCard1()));
        Parameter par = new Parameter(game, p1);
        par.setIsland(game.getIslandManager().getIslandList().get(0));
        par.setChosenColor(PawnColor.blue);
        game.setCurrParameter(par);
        game.startEffect();
        assertEquals(2, p1.getWallet());
        game.increaseCurrEffectPrice();
        assertEquals(4, game.getCurrEffect().getPrice());
        assertEquals(bank+3-1, game.getBank());
        game.endEffect();
        assertNull(game.getCurrEffect());
    }

    @Test
    void checkForEndGameConditions() {
        Game game = new Game(2, "g1", GameMode.expert);

        Player p1 = new Player("Franco", new Deck(), 0, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 1, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        game.addPlayer(p1);
        game.addPlayer(p2);

        game.setupGame();
        p1.getSchoolBoard().setTowersNumber(0);
        assertTrue(game.checkForEndGameConditions());
        p1.getSchoolBoard().setTowersNumber(1);
        assertFalse(game.checkForEndGameConditions());

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