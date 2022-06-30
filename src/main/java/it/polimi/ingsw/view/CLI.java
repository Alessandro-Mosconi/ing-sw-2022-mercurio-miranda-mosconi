package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CLI extends View {

    private BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Method clearScreen flushes terminal's screen.
     */
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error in clean console method: \n" + e);
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Manages ro show the logo text version.
     */
    public void showLogo() {

        System.out.println("""
                                                                                                                                                                   \s
                     %@@@@@@@@@@@@@@@@@@@@@@@@@@@@                       @@@@@@                                                                                      \s
                  %@@@@      @@@@@            @@@@@                     .@@@@@@%                                              %@                                     \s
                 @@@@       @@@@@@              #@@@                     @@@@@                                               @@@                                     \s
                @@@@       *@@@@@@     @@@@@@.     @%   (#                                                                 @@@@@                  ,%%                \s
                @@@@       @@@@@@@ .@@@@@@@@@@@      @@@@@@@  @@@@@@@& @@@@@@@@        @@@@@@@@@@@@    @@@@@%  @@@@@@@/ @@@@@@@@@@@( #@@@@@@%   @@@@@@@  /@@@@@@@@@@@@
                @@@@       @@@@@@@@@@@@     @@@    &@@@@@@@@@@@@@@@@@,(@ @@@@@@      @@@@@   #@@@@@ &@@@@@@@@@@@@@@@@@@    @@@@@   @@@@@@@@@@@  %@@@@@@@ @@@@     @@@\s
                 @@@@      @@@@@@@@@@                 @@@@@@@   @@@@     @@@@@@    @@@@@@    #@@@@@    @@@@@@@@  @@@@@@    @@@@@  /@    @@@@@@@    @@@@@ @@@@@@@@    \s
                  @@@@@    @@@@@@@@.                  @@@@@@   @@#       @@@@@@   %@@@@@     %@@@@@    @@@@@@    @@@@@@    @@@@@          @@@@@@,  @@@@  #@@@@@@@@@@@\s
                    @@@@@@.@@@@@@@@                @& @@@@@@             @@@@@@   @@@@@@.   @@@@@@@    @@@@@@    @@@@@@    @@@@@           @@@@@@@@@@%  #@  /@@@@@@@@@
                           @@@@@@@               @@@  @@@@@@             @@@@@@   @@@@@@@@@@@@@@@@@    @@@@@@    @@@@@&    @@@@@  &@@       @@@@@@@@    @@@      @@@@@
                           @@@@@@@&           @@@@@   @@@@@@             @@@@@@@@  @@@@@@@@@ @@@@@@@@@ @@@@@@    @@@@@@@@  @@@@@@@@          @@@@@&     /@@@@@   #@@@@
                        @@@@@@@@@@@@@@@@@@@@@@@@@@    &@(                  @@@%      @@@@      %@@@    @@@&       @@@@@     @@@@&            (@@@         @@@@@@@@@/ \s
                                                                                                                                            @@@@                     \s
                                                                                                                                          @@@@@                      \s
                                                                                                                                         @@@@@@                      \s
                                                                                                                                         @@@@@@@     @@%             \s
                                                                                                                                          @@@@@@@@@@@@&              \s""");
    }

    /**
     * Manages the login phase and takes all the input necessary to send the first request to the server.
     */
    @Override
    public void login() {
        player = new Player();
        Message msg_out = new Message();
        clearConsole();
        showLogo();
        System.out.println("Inserire username: ");
        String input = "";
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUsername(input);
        player.setNickName(input);

        boolean commandFail = false;
        do {
            System.out.flush();
            commandFail = false;
            System.out.println("CREATE OR JOIN MATCH? [C]/[J]: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!input.equals("C") && !input.equals("c") && !input.equals("j") && !input.equals("J")) {
                System.out.println("Errore ");
                commandFail = true;
            }
        } while (commandFail);

        if (input.equals("C") || input.equals("c")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setIdGame(input);
            boolean invalidInput = true;

            do {
                System.out.println("Inserire numero di giocatori [2-3]: ");

                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Integer.parseInt(input) > 1 && Integer.parseInt(input) < 4) {
                    invalidInput = false;
                }
            } while (invalidInput);
            setPlayerNumber(Integer.parseInt(input));
            invalidInput = true;
            do {
                System.out.println("Inserire la difficolta'[easy]/[expert]: ");

                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (input.equals("easy") || input.equals("expert")) {
                    invalidInput = false;
                }
            } while (invalidInput);
            setGamemode(GameMode.valueOf(input));
            setMessageType(MessageType.CREATE_MATCH);
        } else if (input.equals("J") || input.equals("j")) {

            System.out.println("Inserire idGame: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIdGame(input);
            setMessageType(MessageType.JOIN_MATCH);
            msg_out.setType(MessageType.JOIN_MATCH);
        }
    }

    /**
     * Shows the available settings and takes the input from the user.
     */
    @Override
    public void settings() {

        clearConsole();
        showLogo();

        boolean invalidInput = true;
        String input = "";

        do {
            System.out.println("Choose a wizard: ");
            for (WizardType wizard : getWizards()) {
                System.out.print(wizard + "\t");
            }
            System.out.println();
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (WizardType w : getWizards()) {
                if (String.valueOf(w).equals(input)) {
                    invalidInput = false;
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid wizard");
            }
        } while (invalidInput);
        player.setDeck(new Deck(WizardType.valueOf(input)));
        invalidInput = true;
        do {
            System.out.println("Choose a towers color: ");
            for (TowerColor towerColor : getTowerColors()) {
                System.out.print(towerColor + "\t");
            }
            System.out.println();
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (TowerColor tc : getTowerColors()) {
                if (String.valueOf(tc).equals(input)) {
                    invalidInput = false;
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid towers color");
            }
        } while (invalidInput);
        player.getSchoolBoard().setTowersColor(TowerColor.valueOf(input));
        setTowerColor(TowerColor.valueOf(input));

    }

    /**
     * Shows the available assistant cards and takes the input from the user.
     */
    @Override
    public void chooseAssistantCard() {
        showTable();
        String input = "";
        boolean invalidInput = false;
        ArrayList<Integer> alreadyUsed = new ArrayList<>();
        for (Player p : players) {
            if (p.getLastAssistantCard() != null) {
                alreadyUsed.add(p.getLastAssistantCard().getValue());
            }
        }
        ArrayList<Integer> availableOnes = new ArrayList<>();
        for (AssistantCard ac : player.getDeck().getCards()) {
            if (!ac.isConsumed()) {
                availableOnes.add(ac.getValue());
            }
        }

        do {
            invalidInput = false;

            System.out.print("\nChoose an Assistant card by ID: \n");
            int i=0;
            for (AssistantCard card : getPlayer().getDeck().getCards()) {
                if (!card.isConsumed()) {
                    System.out.print("[value: " + card.getId() + " MN shift: " + card.getMotherMovement() + "] ");
                    if(i!=0&&i%3==0) System.out.println();
                    else System.out.print("\t");
                    i++;
                }
            }
            System.out.println();
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ((availableOnes.containsAll(alreadyUsed) && alreadyUsed.containsAll(availableOnes)) || (availableOnes.size() < alreadyUsed.size() && alreadyUsed.containsAll(availableOnes))) {
                invalidInput = false;
            } else if (alreadyUsed.contains(Integer.valueOf(input))) {
                invalidInput = true;
            }
        } while (invalidInput);
        player.getDeck().getCards().get(Integer.parseInt(input) - 1).setConsumed(true);
        setChosenAssistantCard(getPlayer().getDeck().getCards().get(Integer.parseInt(input) - 1));
        player.setMaxShift(chosenAssistantCard.getMotherMovement());
    }

    /**
     * Shows the whole game board.
     */
    public void showTable() {
        clearConsole();
        showSchoolBoards();
        showYourSchoolBoard();
        showCloudTiles();
        showIslands();
        if (gamemode.equals(GameMode.expert)) {
            showCharacterCards();
            if (cardUsed) {
                System.out.println("Currently active effect: \n" + activeEffect);
            }
        }
        showUsedAssistantCards();
    }

    /**
     * Shows the assistant cards already used in this turn by the other players.
     */
    public void showUsedAssistantCards() {
        System.out.println("\nAlready used cards:");
        for (Player p : players) {
            if (p.getLastAssistantCard() != null) {
                System.out.println(p.getNickName() + " used card " + p.getLastAssistantCard().getValue());
            }
        }
    }

    /**
     * Shows the cloud tiles.
     */
    private void showCloudTiles() {
        System.out.println("CloudTiles: ");
        for (CloudTile cloud : clouds) {
            System.out.print("Cloud ID: " + cloud.getCloudID() + " - " + cloud.getStudents());
            System.out.print("\t");
        }
    }

    /**
     * Shows this user's schoolboard.
     */
    private void showYourSchoolBoard() {
        clearConsole();
        System.out.println(this.player.getNickName() + " is your turn, this is your table:");
        System.out.println("Schoolboard: ");
        System.out.println("Towers: " + this.player.getSchoolBoard().getTowersColor() + " n. " + this.player.getSchoolBoard().getTowersNumber());

        System.out.println("Entrance: " + this.player.getSchoolBoard().getStudentEntrance());
        System.out.println("Hall: " + this.player.getSchoolBoard().getStudentHall());
        System.out.print("Professor owned: " + this.player.getSchoolBoard().getProfessorTable());
        for (PawnColor color : this.player.getSchoolBoard().getProfessorTable().keySet()) {
            if (this.player.getSchoolBoard().getProfessorTable().get(color))
                System.out.print(color + " ");

        }
        if (gamemode.equals(GameMode.expert)) {
            System.out.println("\nWallet: " + player.getWallet());
        }
        System.out.println("\n");
    }

    /**
     * Shows each user's schoolboard.
     */
    private void showSchoolBoards() {
        for (Player p : players) {
            {
                System.out.println(p.getNickName());
                System.out.println("Schoolboard: ");
                System.out.println("Towers: " + p.getSchoolBoard().getTowersColor() + " n. " + p.getSchoolBoard().getTowersNumber());
                System.out.println("Entrance: " + p.getSchoolBoard().getStudentEntrance());
                System.out.println("Hall: " + p.getSchoolBoard().getStudentHall());
                System.out.print("Professor owned: " + p.getSchoolBoard().getProfessorTable());
                for (PawnColor color : p.getSchoolBoard().getProfessorTable().keySet()) {
                    if (p.getSchoolBoard().getProfessorTable().get(color))
                        System.out.print(color + " ");

                }
                if (gamemode.equals(GameMode.expert)) {
                    System.out.println("\nWallet: " + p.getWallet());
                }
                System.out.println("\n");
            }
        }
    }

    /**
     * Shows the table and takes the input to prepare the pawn moves request to send to the server.
     */
    @Override
    public void choosePawnMove() {

        ArrayList<String> payloads = new ArrayList<>();
        showTable();
        String input = "";
        boolean invalidInput = true;
        do {
            if (gamemode.equals(GameMode.expert) && !cardUsed)
                System.out.println("Choose a student color or type [Character Card] to use if you want to: ");
            else
                System.out.println("Choose a student color: ");
            //String input = "" ;
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("Character Card")) {
                cardUsed = useCharacterCard();
                if (cardUsed) {
                    return;
                }
            } else {
                for (PawnColor pc : PawnColor.values()) {
                    if (input.equals(String.valueOf(pc))) {
                        invalidInput = false;
                        break;
                    }
                }
                if (!invalidInput) {
                    invalidInput = true;
                    for (PawnColor pc : PawnColor.values()) {
                        if (pc.equals(PawnColor.valueOf(input))) {
                            if (player.getSchoolBoard().getStudentEntrance().get(pc) != 0) {
                                invalidInput = false;
                            }
                        }
                    }
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid input");
            }
        } while (invalidInput);
        setMessageType(MessageType.PAWN_MOVE);
        setColorToMove(PawnColor.valueOf(input));
        invalidInput = true;
        do {
            System.out.print("Choose a destination [-1] to indicate your SchoolBoard / [ID] Island: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = -1; i < islandManager.getIslandList().size(); i++) {
                if (input.equals(String.valueOf(i))) {
                    invalidInput = false;
                    break;
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid input");
            }
        } while (invalidInput);
        setDestination(Integer.valueOf(input));
    }

    /**
     * Shows the table and takes the input to prepare the mother nature shift request to send to the server.
     */
    @Override
    public void chooseMNmovement() {
        String input = "";
        boolean invalidInput = true;
        do {
            if (gamemode.equals(GameMode.expert) && !cardUsed)
                System.out.println("Choose Mother Nature shift between 1 and " + player.getMaxShift()/*player.getMaxShift()*/ + "or type [Character Card] to use one: ");
                // se viene usata la carta che modifica il maxShift del player, viene mandato un messaggio specifico che modifica maxShift;
                // non serve poi risettare a fine effetto -> al prossimo turno, l'utilizzo di una nuova carta sovrascriverà il maxShift corretto.
            else
                System.out.println("Choose Mother Nature shift between 1 and " + player.getMaxShift() + ":");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("Character Card")) {
                cardUsed = useCharacterCard();
                if (cardUsed) {
                    return;
                }
            } else {
                for (int i = 1; i <= player.getMaxShift(); i++) {
                    if (input.equals(String.valueOf(i))) {
                        invalidInput = false;
                        break;
                    }
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid input");
            }
        } while (invalidInput);
        setMessageType(MessageType.MN_SHIFT);
        setMN_shift(Integer.valueOf(input));

    }

    /**
     * Shows the table and takes the input to prepare the cloud tile choice request to send to the server.
     */
    @Override
    public void chooseCT() {

        showTable();
        String input = "";
        boolean invalidInput = true;
        do {
            if (gamemode.equals(GameMode.expert) && !cardUsed)
                System.out.println("Choose a Cloud Tile by ID to take or type [Character Card] to use one: ");

            else
                System.out.println("Choose a Cloud Tile by ID: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("Character Card")) {
                cardUsed = useCharacterCard();
                if (cardUsed) {
                    return;
                }
            } else {
                for (int i = 0; i < players.size(); i++) {
                    if (input.equals(String.valueOf(i))) {
                        invalidInput = false;
                        break;
                    }
                }
                if (!invalidInput) {
                    invalidInput = true;
                    for (PawnColor pc : PawnColor.values()) {
                        if (clouds.get(Integer.parseInt(input)).getStudents().get(pc) != 0) {
                            invalidInput = false;
                        }
                    }
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid input");
            }
        } while (invalidInput);
        setChosenCloudPos(Integer.parseInt(input));
        setMessageType(MessageType.CHOSEN_CT);

    }

    /**
     * Shows the available character cards and their attributes (if the have any).
     */
    private void showCharacterCards() {
        System.out.println("CharacterCards: ");
        for (CharacterCard characterCard : characterCards) {
            System.out.print("[ID: " + characterCard.getID() + " price: " + characterCard.getPrice() + "] ");
            if (characterCard.getCardBehavior() instanceof CharacterCard1) {
                System.out.println("Map : " + ((CharacterCard1) characterCard.getCardBehavior()).getStudents());
            }
            if (characterCard.getCardBehavior() instanceof CharacterCard7) {
                System.out.println("Map : " + ((CharacterCard7) characterCard.getCardBehavior()).getStudents());
            }
            if (characterCard.getCardBehavior() instanceof CharacterCard11) {
                System.out.println("Map : " + ((CharacterCard11) characterCard.getCardBehavior()).getStudents());
            }
            System.out.println("\t");
        }
    }

    /**
     * Shows the whole island list.
     */
    private void showIslands() {
        System.out.println("\n");
        System.out.println("Islands: ");
        for (Island island : getIslandManager().getIslandList()) {
            System.out.print("(");
            System.out.print("ID: " + island.getIslandID());
            if (island.getTowerColor() != null)
                System.out.println(" Tower: " + island.getTowersNumber() + " " + island.getTowerColor());
            System.out.print(" Map:" + island.getIslandStudents());
            System.out.print(island.isMotherNature() ? " MotherNature " : "");
            System.out.print(island.isNoEntryTile() ? " NoEntry " : "");
            System.out.print(")\n");
        }
    }

    /**
     * Shows the available character cards, their caption and their price and starts asking for possible parameters.
     *
     * @return true if the character card choice has been successful; false otherwise.
     */
    public boolean useCharacterCard() {
        parameter.setPlayer(player);
        boolean invalidInput = true;
        System.out.println("Which one do you wanna activate? ");
        showCharacterCards();
        showCharacterCardsCaptions();
        CharacterCard chosenCard = null;
        String input = "";
        do {
            System.out.println("Choose the card you wanna activate or type [exit] to cancel.");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("exit")) return false;
            for (CharacterCard cc : characterCards) {
                if (input.equals(String.valueOf(cc.getID())) && player.getWallet() >= cc.getPrice()) {
                    chosenCard = cc;
                    invalidInput = false;
                }
            }
        } while (invalidInput);

        setChosenCharacterCard(chosenCard);
        switch (input) {
            case ("1") -> {
                choosePawnColor();
                chooseIsland();
            }
            case ("9"), ("11"), ("12") -> {
                choosePawnColor();
            }
            case ("3"), ("5") -> {
                chooseIsland();
            }
            case ("7") -> {
                int chosenStudents = 0;
                Map<PawnColor, Integer> map1 = new HashMap<>() {{
                    for (PawnColor color : PawnColor.values()) {
                        put(color, 0);
                    }
                }};
                do {
                    System.out.println("Choose a student from this card or type [stop]: ");
                    try {
                        input = stdIn.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //todo check che sia stato inserito un input = color
                    map1.replace(PawnColor.valueOf(input), map1.get(PawnColor.valueOf(input)) + 1);
                    chosenStudents++;
                    //if non colore allora non incrementare chosenStudents
                } while (chosenStudents < 3 && !input.equals("stop"));
                Map<PawnColor, Integer> map2 = new HashMap<>() {{
                    for (PawnColor color : PawnColor.values()) {
                        put(color, 0);
                    }
                }};
                do {
                    System.out.println("Choose a student from your entrance: ");
                    try {
                        input = stdIn.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //todo check che sia stato inserito un input = color
                    map2.replace(PawnColor.valueOf(input), map2.get(PawnColor.valueOf(input)) + 1);
                    chosenStudents--;
                    //if non colore allora non incrementare chosenStudents
                } while (chosenStudents > 0);
                parameter.setColorMap1(map1);
                parameter.setColorMap2(map2);
            }
            case ("10") -> {
                int chosenStudents = 0;
                Map<PawnColor, Integer> map1 = new HashMap<>() {{
                    for (PawnColor color : PawnColor.values()) {
                        put(color, 0);
                    }
                }};
                do {
                    System.out.println("Choose a student from your Entrance: ");
                    try {
                        input = stdIn.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //todo check che sia stato inserito un input = color
                    map1.replace(PawnColor.valueOf(input), map1.get(PawnColor.valueOf(input)) + 1);
                    chosenStudents++;
                    //if non colore allora non incrementare chosenStudents
                } while (chosenStudents < 2 && !input.equals("stop"));
                Map<PawnColor, Integer> map2 = new HashMap<>() {{
                    for (PawnColor color : PawnColor.values()) {
                        put(color, 0);
                    }
                }};
                do {
                    System.out.println("Choose a student from your Hall: ");
                    try {
                        input = stdIn.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //todo check che sia stato inserito un input = color
                    map2.replace(PawnColor.valueOf(input), map2.get(PawnColor.valueOf(input)) + 1);
                    chosenStudents--;
                    //if non colore allora non incrementare chosenStudents
                } while (chosenStudents > 0);
                parameter.setColorMap1(map1);
                parameter.setColorMap2(map2);
            }
            case ("exit") -> {
                return false;
            }
            //todo check consistenza input
        }

        setMessageType(MessageType.CHOSEN_CHARACTER_CARD);
        return true;
    }

    /**
     * Shows the island list and sets a character card's parameter's island if that card needs one.
     */
    private void chooseIsland() {
        String input = "";
        boolean invalidInput = true;
        do {
            System.out.println("Choose an island: ");
            showIslands();
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < islandManager.getIslandList().size(); i++) {
                if (input.equals(String.valueOf(i))) {
                    invalidInput = false;
                    break;
                }
            }
        } while (invalidInput);
        parameter.setIsland(islandManager.getIslandList().get(Integer.parseInt(input)));
    }

    /**
     * Shows the student map of a character card and sets that character card's parameter's students if that card needs to.
     */
    private void choosePawnColor() {
        String input = "";
        boolean invalidInput = true;
        do {
            System.out.println("Choose a color: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (PawnColor pc : PawnColor.values()) {
                if (input.equals(String.valueOf(pc))) {
                    invalidInput = false;
                    break;
                }
            }
            if (!invalidInput) {
                if (chosenCharacterCard.getCardBehavior() instanceof CharacterCard1) {
                    Map<PawnColor, Integer> cardMap = ((CharacterCard1) chosenCharacterCard.getCardBehavior()).getStudents();
                    if (cardMap.get(PawnColor.valueOf(input)) < 1) {
                        invalidInput = true;
                    }
                }
                if (chosenCharacterCard.getCardBehavior() instanceof CharacterCard11) {
                    Map<PawnColor, Integer> cardMap = ((CharacterCard1) chosenCharacterCard.getCardBehavior()).getStudents();
                    if (cardMap.get(PawnColor.valueOf(input)) < 1 || player.getSchoolBoard().getStudentHall().get(PawnColor.valueOf(input)) > 9) {
                        invalidInput = true;
                    }
                }
            }
        } while (invalidInput);
    }

    /**
     * Shows, for each available character card, a description of its effect.
     */
    private void showCharacterCardsCaptions() {
        for (CharacterCard cc : characterCards) {
            System.out.println("Card: " + cc.getID());
            System.out.println("Effect: " + cc.getCaption());
        }
    }

    /**
     * Shows the endgame window.
     *
     * @param winnerID the winner's username.
     */
    public void showEndGameWindow(String winnerID) {
        clearConsole();
        System.out.println("\n\nEnd Game: player " + winnerID + " won the game!\n\n");
    }
}
