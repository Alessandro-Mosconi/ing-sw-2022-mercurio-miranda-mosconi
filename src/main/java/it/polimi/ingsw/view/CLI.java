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

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print("Choose a wizard: ");
        for(WizardType wizard : getWizards())
        {
            System.out.println(wizard);
        }
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getPlayer().setDeck(new Deck(WizardType.valueOf(input)));
        //getPlayer().getDeck().setWizard(WizardType.valueOf(input));


        System.out.println("Choose a towers color: ");
        for(TowerColor towerColor : getTowerColors())
        {
            System.out.println(towerColor);
        }
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTowerColor(TowerColor.valueOf(input));

    }

    public void printDeck(){

        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.print("-------------\t\t");
        System.out.println("");
        for(AssistantCard card : getPlayer().getDeck().getCards())
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


        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print("Choose an Assistant card by ID: ");
        //printDeck();
        for(AssistantCard card : getPlayer().getDeck().getCards())
            System.out.println(card.getId());


        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setChosenAssistantCard(getPlayer().getDeck().getCards().get(Integer.parseInt(input)-1));

    }

    public void showTable(){

        for(Player p : players)
        {
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

        System.out.println(getPlayer().getNickName() + " is your turn, this is your table:");

        System.out.println("Schoolboard: ");
        System.out.println("Entrance: " + this.player.getSchoolBoard().getStudentEntrance());
        System.out.println("Hall: " + this.player.getSchoolBoard().getStudentHall());
        System.out.print("Professor owned: " + this.player.getSchoolBoard().getProfessorTable());
        for (PawnColor color : this.player.getSchoolBoard().getProfessorTable().keySet()) {
            if (this.player.getSchoolBoard().getProfessorTable().get(color))
                System.out.print(color + " ");

        }
        System.out.println("\n");

        System.out.println("CloudTiles: ");
        for(CloudTile cloud : getClouds())
        {
            System.out.print(cloud.getStudents());
            System.out.print("\t");
        }

        System.out.println("\n");
        System.out.println("Islands: ");
        for(Island island : getIslandManager().getIslandList())
        {
            System.out.print("|");
            System.out.print(island.getIslandStudents());
            System.out.print(island.isNoEntryTile()? "NoEntry": "");
            System.out.print("|\t");
        }

        System.out.println("\n");
        System.out.println("CharacterCards: ");
        /*for(CharacterCard characterCard : getCharacterCards())
        {
            System.out.print("ID: " + characterCard.getID() + "price: " + characterCard.getPrice() + "\t");
        }*/

    }

    @Override
    public void choosePawnMove() {

        ArrayList<String> payloads = new ArrayList<>();
        showTable();
        System.out.print("Choose a student color or type [Character Card] to use if you want to: ");

        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
            useCharacterCard();
            return;
        }

        setMessageType(MessageType.PAWN_MOVE);
        setColorToMove(PawnColor.valueOf(input));

        System.out.print("Choose a destination [-1] to indicate your SchoolBoard / [ID] Island: ");
        //showIslands();
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setDestination(Integer.valueOf(input));

    }

    private void showCharacterCards() {
        //TODO
    }

    private void showIslands() {
        //TODO
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

        System.out.println("Choose Mother Nature shift between 1 and " + getChosenAssistantCard().getMotherMovement() + "or type [Character Card] to use one: ");
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
            useCharacterCard();
            return;
        }

        setMessageType(MessageType.MN_SHIFT);
        setMN_shift(Integer.valueOf(input));

    }

    @Override
    public void chooseCT() {

        showTable();

        System.out.println("Choose a Cloud Tile by ID to take or type [Character Card] to use one: ");
        String input = "";
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
            useCharacterCard();
            return;
        }

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
