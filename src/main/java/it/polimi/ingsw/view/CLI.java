package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CLI extends View{

    private BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void login() {
        player = new Player();
        Message msg_out = new Message();
        System.out.print("\033[H\033[2J");
        System.out.flush();

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
        //ArrayList<String> payloads = new ArrayList<>();
        System.out.println("Inserire username: ");
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUsername(input);
        player.setNickName(input);
        //payloads.add(input);

        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean commandFail = false;

        do {
            System.out.flush();
            commandFail=false;
            System.out.println("CREATE OR JOIN MATCH? [C]/[J]: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!input.equals("C") && !input.equals("c") && !input.equals("j") && !input.equals("J"))
            {
                System.out.println("Errore ");
                commandFail = true;
            }
        } while(commandFail);

        if(input.equals("C") || input.equals("c")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setIdGame(input);
            //payloads.add(input);
            System.out.println("Inserire numero di giocatori [2-4]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setPlayerNumber(Integer.parseInt(input));
            //payloads.add(input);
            System.out.println("Inserire la difficolta'[easy]/[expert]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setGamemode(GameMode.valueOf(input));
            //payloads.add(input);
            setMessageType(MessageType.CREATE_MATCH);
            //msg_out.setType(MessageType.CREATE_MATCH);
        }
        else if(input.equals("J") || input.equals("j")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIdGame(input);
            //payloads.add(input);
            setMessageType(MessageType.JOIN_MATCH);
            msg_out.setType(MessageType.JOIN_MATCH);
        }
        //setMessageType(MessageType.LOGIN);
    }

    @Override
    public void settings() {
        /*
        System.out.print("\033[H\033[2J");
        System.out.flush();

        String input = "" ;
        boolean commandFail = false;

        do {
            System.out.flush();
            commandFail=false;
            System.out.println("CREATE OR JOIN MATCH? [C]/[J]: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!input.equals("C") && !input.equals("c") && !input.equals("j") && !input.equals("J"))
            {
                System.out.println("Errore ");
                commandFail = true;
            }
        } while(commandFail);

        if(input.equals("C") || input.equals("c")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIdGame(input);

            System.out.println("Inserire numero di giocatori [2-4]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setPlayerNumber(Integer.parseInt(input));

            System.out.println("Inserire la difficolta'[easy]/[expert]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setGamemode(GameMode.valueOf(input));

            setMessageType(MessageType.CREATE_MATCH);
        } else if(input.equals("J") || input.equals("j")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIdGame(input);

            setMessageType(MessageType.JOIN_MATCH);
        }*/
        boolean invalidInput = true;
        String input = "";

        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.print("Choose a wizard:\n ");
            for (WizardType wizard : getWizards()) {
                System.out.println(wizard);
            }
            //String input = "";
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //boolean accettable = false;
            for(WizardType w : getWizards()){
                if(String.valueOf(w).equals(input)){
                    invalidInput = false;
                }
            }
            if(invalidInput){
                System.out.println("Error: invalid wizard");
            }
        }while(invalidInput);
        player.setDeck(new Deck(WizardType.valueOf(input)));
        //getPlayer().getDeck().setWizard(WizardType.valueOf(input));

        invalidInput = true;
        do {
            System.out.println("Choose a towers color: ");
            for (TowerColor towerColor : getTowerColors()) {
                System.out.println(towerColor);
            }
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //boolean accettable = false;
            for(TowerColor tc : getTowerColors()){
                if(String.valueOf(tc).equals(input)){
                    invalidInput=false;
                }
            }
            if(invalidInput){
                System.out.println("Error: invalid towers color");
                //invalidInput=true;
            }
        }while(invalidInput);
        player.getSchoolBoard().setTowersColor(TowerColor.valueOf(input));
        setTowerColor(TowerColor.valueOf(input));

    }

    public void printDeck(){
        //TODO inserire check sulle consumed
        for(AssistantCard card : player.getDeck().getCards())
            System.out.print("-------------\t\t");
        System.out.println("");
        for(AssistantCard card : player.getDeck().getCards())
            System.out.print("|\t\t\t|\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("|\t v: "+ card.getValue() + "\t|\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("|\t\t\t|\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("|\t m: "+ card.getMotherMovement() + "\t|\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("|\t\t\t|\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("-------------\t\t");
    }

    @Override
    public void chooseAssistantCard() {

        String input = "" ;
        boolean invalidInput = true;
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.print("Choose an Assistant card by ID: ");
            //printDeck();
            for (AssistantCard card : getPlayer().getDeck().getCards()) {
                if (!card.isConsumed())
                    System.out.println(card.getId());
            }

            //String input = "" ;
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int availableCards = 0;
            boolean lastCard = true;
            for(int i = 1; i<11; i++){
                if (input.equals(String.valueOf(i))) {
                    invalidInput = false;
                    break;
                }
            }
            if(!invalidInput) {
                for (AssistantCard card : getPlayer().getDeck().getCards()) {
                    if (lastCard) {
                        if (!card.isConsumed()) {
                            availableCards++;
                        }
                        if (availableCards > 1) {
                            lastCard = false;
                        }
                    }
                }
                for (AssistantCard card : getPlayer().getDeck().getCards()) {
                    if (input.equals(card.getId()) && !card.isConsumed()) {
                        invalidInput = false;
                        for (Player p : players) {
                            if (p.getLastAssistantCard() != null) {
                                if (card.getValue().equals(p.getLastAssistantCard().getValue()) && !lastCard) {
                                    invalidInput = true;
                                }
                            }
                        }
                    }
                }
            }
            if (invalidInput) {
                System.out.println("Error: invalid input");
            }
        }while(invalidInput);
        player.getDeck().getCards().get(Integer.parseInt(input)-1).setConsumed(true);
        setChosenAssistantCard(getPlayer().getDeck().getCards().get(Integer.parseInt(input)-1));
    }

    public void showTable(){

        showSchoolBoards();
        showYourSchoolBoard();
        showCloudTiles();
        showIslands();
        //showCharacterCards();
        //showDeck();
        showUsedAssistantCards();
    }
    public void showUsedAssistantCards() {
        System.out.println("Already used cards:");
        for(Player p : players){
            if(p.getLastAssistantCard()!=null) {
                System.out.println(p.getNickName() + " used card " + p.getLastAssistantCard().getValue());
                //TODO resettare le used cards alla fine del round
            }
        }
    }
    private void showDeck() {
        System.out.println("\n");
        System.out.println("Here's your deck:");
        System.out.println("|");
        for(AssistantCard ac : this.player.getDeck().getCards()){
            if(!ac.isConsumed()){
                System.out.println("Card  " + ac.getValue() + ", Max Shift " + ac.getMotherMovement() + "|");
            }
        }
    }
    private void showCloudTiles() {
        System.out.println("CloudTiles: ");
        for(CloudTile cloud : clouds)
        {
            System.out.print("Cloud ID: "+cloud.getCloudID() + " - " + cloud.getStudents());
            System.out.print("\t");
        }
    }
    private void showYourSchoolBoard() {
        System.out.println(this.player.getNickName() + " is your turn, this is your table:");
        System.out.println("Schoolboard: ");
        System.out.println("Entrance: " + this.player.getSchoolBoard().getStudentEntrance());
        System.out.println("Hall: " + this.player.getSchoolBoard().getStudentHall());
        System.out.print("Professor owned: " + this.player.getSchoolBoard().getProfessorTable());
        for (PawnColor color : this.player.getSchoolBoard().getProfessorTable().keySet()) {
            if (this.player.getSchoolBoard().getProfessorTable().get(color))
                System.out.print(color + " ");

        }
        System.out.println("\n");
    }
    private void showSchoolBoards() {
        for(Player p : players){
            //if(player.equals(getPlayer())) {
            {
                System.out.println(p.getNickName());
                System.out.println("Schoolboard: ");
                System.out.println("Entrance: " + p.getSchoolBoard().getStudentEntrance());
                System.out.println("Hall: " + p.getSchoolBoard().getStudentHall());
                System.out.print("Professor owned: " + p.getSchoolBoard().getProfessorTable());
                for (PawnColor color : p.getSchoolBoard().getProfessorTable().keySet()) {
                    if (p.getSchoolBoard().getProfessorTable().get(color))
                        System.out.print(color + " ");

                }
                System.out.println("\n");
            }
        }
    }
    @Override
    public void choosePawnMove() {

        ArrayList<String> payloads = new ArrayList<>();
        showTable();
        String input = "";
        boolean invalidInput = true;
        do {
            System.out.print("Choose a student color or type [Character Card] to use if you want to: ");

            //String input = "" ;
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(input.equals("Character Card")) {
                useCharacterCard();
                return;
            }else{
                for(PawnColor pc: PawnColor.values()){
                    if(input.equals(String.valueOf(pc))){
                        invalidInput = false;
                    }
                }
                if(!invalidInput) {
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
            if(invalidInput){
                System.out.println("Error: invalid input");
            }
        }while(invalidInput);
        setMessageType(MessageType.PAWN_MOVE);
        setColorToMove(PawnColor.valueOf(input));
        invalidInput = true;
        do {
            System.out.print("Choose a destination [-1] to indicate your SchoolBoard / [ID] Island: ");
            //showIslands();
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i=-1;i<islandManager.getIslandList().size();i++){
                if(input.equals(String.valueOf(i))){
                    invalidInput = false;
                }
            }
            if(invalidInput){
                System.out.println("Error: invalid input");
            }
        }while(invalidInput);
        setDestination(Integer.valueOf(input));

    }
    private void showCharacterCards() {
        System.out.println("\n");
        System.out.println("CharacterCards: ");
        /*for(CharacterCard characterCard : getCharacterCards())
        {
            System.out.print("ID: " + characterCard.getID() + "price: " + characterCard.getPrice() + "\t");
        }*/
    }
    private void showIslands() {
        System.out.println("\n");
        System.out.println("Islands: ");
        for(Island island : getIslandManager().getIslandList())
        {
            System.out.print("|");
            System.out.print(island.isMotherNature()? " MotherNature ":"");
            System.out.print("id" + island.getIslandID() + " towerNumber " + island.getTowersNumber());
            if(island.getTowerColor()!=null) System.out.println("towerColor" + island.getTowerColor());
            System.out.print(" map:" + island.getIslandStudents());
            System.out.print(island.isNoEntryTile()? " NoEntry ": "");
            System.out.print("|\t");
        }
    }
    private void showSchoolBoard() {
        //TODO
    }
    /*@Override
    public Message chooseWizard() {
        return null;
    }*/
    /*public Message waiting() {
        super.waiting();
    }*/
    /*public void mainboard() {
        super.mainboard();
    }*/

    /*
    @Override
    public void lobby() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("User in this lobby: " + getPlayers());
        System.out.println("waiting for " + (getPlayerNumber()-getPlayers().size()) + " player to join");
        this.setMessageType(MessageType.LOBBY_WAITING);

        /*;
        if( (getPlayerNumber() - getPlayers().size()) == 0) System.out.println("Press enter to continue");
        else System.out.println("refresh[r]/quit[q]:");

        this.setMessageType(MessageType.LOBBY_WAITING);
        try {
            String input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    //}

    /*@Override
    public Message chooseWizard() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print("Chose a wizard: ");
        for(WizardType wizard : getWizards())
        {
            System.out.print(wizard + " - ");
        }

        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMessageType(MessageType.ChoseWizard);
        return null;
    }*/

    @Override
    public void chooseMNmovement() {
        String input = "";
        boolean invalidInput = true;
        do {
            System.out.println("Choose Mother Nature shift between 1 and " + getChosenAssistantCard().getMotherMovement()/*player.getMaxShift()*/ + "or type [Character Card] to use one: ");
            //String input = "";
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("Character Card")) {
                useCharacterCard();
                return;
            }
            else{
                for(int i=1; i<=getChosenAssistantCard().getMotherMovement();i++){
                    if(input.equals(String.valueOf(i))){
                        invalidInput=false;
                    }
                }
                /*if(Integer.parseInt(input)>0 && !(Integer.parseInt(input) > getChosenAssistantCard().getMotherMovement())){ //TODO sistemare maxShift nella gestione dei messaggi va inserito il boolean che indica il bonus effetto in caso
                    invalidInput = false;
                }*/
            }
            if(invalidInput){
                System.out.println("Error: invalid input");
            }
        }while (invalidInput);
        setMessageType(MessageType.MN_SHIFT);
        setMN_shift(Integer.valueOf(input));

    }

    @Override
    public void chooseCT() {

        showTable();
        String input = "";
        boolean invalidInput = true;
        do {
            System.out.println("Choose a Cloud Tile by ID to take or type [Character Card] to use one: ");
            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.equals("Character Card")) {
                useCharacterCard();
                return;
            }
            else{
                for(int i=0; i<players.size();i++){
                    if(input.equals(String.valueOf(i))){
                        invalidInput=false;
                    }
                }
                if(!invalidInput){
                    invalidInput=true;
                    for(PawnColor pc : PawnColor.values()){
                        if(clouds.get(Integer.parseInt(input)).getStudents().get(pc)!=0){
                            invalidInput = false;
                        }
                    }
                }
            }
            if(invalidInput){
                System.out.println("Error: invalid input");
            }
        }while(invalidInput);
        setChosenCloudPos(Integer.parseInt(input));
        setMessageType(MessageType.CHOSEN_CT);

    }

    public void useCharacterCard(){
        Message msg_out = new Message(MessageType.CHOSEN_CHARACTER_CARD);

        System.out.println("Which one do you wanna activate? ");
        showCharacterCards();
        String input = "";
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setChosenCharacterCard(getCharacterCards().get(Integer.parseInt(input)));

        setColorToMove(null);
        setChosenIslandPos(null);

        switch (input){
            case ("1"), ("9"), ("11"), ("12") -> {
                System.out.println("Choose a color: ");
                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setColorToMove(PawnColor.valueOf(input));
            }
            case ("3"), ("5") -> {
                System.out.println("Choose an island: ");
                showIslands();
                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setChosenIslandPos(Integer.parseInt(input));
            }
            case ("7"), ("10") ->{
                //TODO MAPPE
            }
        }

        setMessageType(MessageType.CHOSEN_CHARACTER_CARD);
    }

    @Override
    public void updateView() {
        super.updateView();
        //TODO
    }
}
