package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLI extends View{

    //private String lobby_command;
    private BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void login() {

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
        System.out.println("Inserire username: ");
        String input = "" ;
        try {
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUsername(input);
        setMessageType(MessageType.LOGIN);
    }

    @Override
    public void settings() {


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
        }

    }

    @Override
    public void chooseAssistantCard() {
        super.chooseAssistantCard();
    }

    @Override
    public void waiting() {
        super.waiting();
    }

    @Override
    public void mainboard() {
        super.mainboard();
    }

    @Override
    public void lobby() {

        System.out.flush();

        System.out.println("User in this lobby: " + getPlayers());
        System.out.println("waiting for " + (getPlayerNumber()-getPlayers().size()) + "player to join");
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

    }

    @Override
    public void chooseWizard() {
        super.chooseWizard();
    }

    @Override
    public void chooseMNmovement() {
        super.chooseMNmovement();
    }
}
