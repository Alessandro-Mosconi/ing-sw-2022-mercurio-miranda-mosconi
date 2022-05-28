package it.polimi.ingsw.view;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.WizardType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CLI extends View{

    //private String lobby_command;
    private BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));

    @Override
    public Message login() {
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
        ArrayList<String> payloads = new ArrayList<>();
        System.out.println("Inserire username: ");
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUsername(input);
        payloads.add(input);

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
            payloads.add(input);
            System.out.println("Inserire numero di giocatori [2-4]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setPlayerNumber(Integer.parseInt(input));
            payloads.add(input);
            System.out.println("Inserire la difficolta'[easy]/[expert]: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setGamemode(GameMode.valueOf(input));
            payloads.add(input);
            //setMessageType(MessageType.CREATE_MATCH);
            msg_out.setType(MessageType.CREATE_MATCH);
        }
        else if(input.equals("J") || input.equals("j")) {

            System.out.println("Inserire idGame: ");

            try {
                input = stdIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setIdGame(input);
            payloads.add(input);
            //setMessageType(MessageType.JOIN_MATCH);
            msg_out.setType(MessageType.JOIN_MATCH);
        }
        //setMessageType(MessageType.LOGIN);

        msg_out.fill(payloads);
        return msg_out;
    }

    @Override
    public Message settings() {
        Message msg_out = new Message();
        //setMessageType(MessageType.SETTINGS);
        msg_out.setType(MessageType.SETTINGS);
        ArrayList<String> payloads = new ArrayList<>();
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
        System.out.print("Choose a wizard: ");
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
        payloads.add(input);
        //todo rimuovere mago scelto
        System.out.println("Choose a towers color: ");
        for(TowerColor t : getTowerColors())
        {
            System.out.println(t + " - ");
        }
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        payloads.add(input);
        //todo rimuovere colore scelto
        msg_out.fill(payloads);
        return msg_out;
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
    public Message chooseAssistantCard() {
        Message msg_out = new Message(MessageType.AssistantCard);
        String payload = new String();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("Choose an Assistant card: ");
        printDeck();
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        payload = input;
        msg_out.fill(input);
        return msg_out;
        //setMessageType();
    }

    @Override
    public Message choosePawnMove() {
        Message msg_out = new Message(MessageType.PAWN_MOVE);
        ArrayList<String> payloads = new ArrayList<>();
        System.out.print("Choose a student color or type [Character Card] to use if you want to: ");
        showSchoolBoard();
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
            return useCharacterCard();
        }
        payloads.add(input);
        System.out.print("Choose a destination [-1] to indicate your SchoolBoard / [ID] Island: ");
        showIslands();
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        payloads.add(input);
        msg_out.fill(payloads);
        return msg_out;
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
    public Message chooseMNmovement() {
        Message msg_out = new Message(MessageType.MN_SHIFT);
        String payload;
        System.out.println("Choose Mother Nature shift between 1 and " + getPlayer().getMaxShift() + "or type [Character Card] to use one: ");
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
                return useCharacterCard();
        }
        payload = input;
        msg_out.fill(payload);
        return msg_out;
    }

    @Override
    public Message chooseCT() {
        Message msg_out = new Message(MessageType.CHOSEN_CT);
        String payload;
        System.out.println("Choose a Cloud Tile to take or type [Character Card] to use one: ");
        String input = "";
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(input.equals("Character Card")){
            return useCharacterCard();
        }
        payload = input;
        msg_out.fill(payload);
        return msg_out;
    }

    public Message useCharacterCard(){
        Message msg_out = new Message(MessageType.CHOSEN_CHARACTER_CARD);
        ArrayList<String> payloads = new ArrayList<>();
        payloads.add("Character Card");
        System.out.println("Which one do you wanna activate? ");
        showCharacterCards();
        String input = "";
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        payloads.add(input);
        switch (input){
            case ("1"), ("9"), ("11"), ("12") -> {
                System.out.println("Choose a color: ");
                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                payloads.add(input);
            }
            case ("3"), ("5") -> {
                System.out.println("Choose an island: ");
                showIslands();
                try {
                    input = stdIn.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                payloads.add(input);
            }
            case ("7"), ("10") ->{
                //TODO MAPPE
            }
        }
        msg_out.fill(payloads);
        return msg_out;
    }

    @Override
    public void updateView() {
        super.updateView();
        //TODO
    }
}
