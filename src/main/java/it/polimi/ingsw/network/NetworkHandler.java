package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.virtualview.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkHandler {


    private PrintWriter out;
    private BufferedReader in;
    private View view;
    private Phase previousPhase;
    private Phase phase;
    private Phase nextPhase;

    public NetworkHandler(PrintWriter out, BufferedReader in, View view) {
        this.out = out;
        this.in = in;
        this.view = view;
        this.phase = Phase.LOGIN;
    }

    public synchronized String send_msg() {
        Message msg_out = new Message(view.getUsername());

        Gson gson = new Gson();
        ArrayList<String> payloads = new ArrayList<>();


        switch (phase) {
            case LOGIN -> {
                view.login();

                msg_out.setUser(view.getUsername());
                if (view.getMessageType().equals(MessageType.CREATE_MATCH)) {
                    payloads.add(view.getIdGame());
                    payloads.add(view.getPlayerNumber().toString());
                    payloads.add(view.getGamemode().toString());
                } else {
                    payloads.add(view.getIdGame());
                }

                msg_out.setType(view.getMessageType());
                nextPhase = Phase.SETTINGS;
                phase = Phase.WAITING;
            }
            case SETTINGS -> {
                view.settings();
                msg_out.setType(MessageType.SETTINGS);

                payloads.add(view.getPlayer().getDeck().getWizard().toString());
                payloads.add(view.getTowerColor().toString());

                nextPhase = Phase.PLANNING;
                phase = Phase.WAITING;
            }
            case PLANNING -> {
                view.chooseAssistantCard();
                msg_out.setType(MessageType.AssistantCard);

                payloads.add(view.getChosenAssistantCard().getValue().toString());

                nextPhase = Phase.CHOOSING_FIRST_MOVE;
                phase = Phase.WAITING;
            }
            case CHOOSING_FIRST_MOVE -> {
                view.choosePawnMove();
                msg_out.setType(view.getMessageType());
                if(view.getMessageType().equals(MessageType.PAWN_MOVE))
                {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_SECOND_MOVE;
                }
                else{
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    payloads.add(view.getChosenCharacterCard().getID().toString());

                    if(view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if(view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }

            }
            case CHOOSING_SECOND_MOVE -> {
                //TODO ALESSANDRO msg_out = view.choosePawnMove();
                previousPhase = phase;
                nextPhase = Phase.CHOOSING_THIRD_MOVE;
                phase = Phase.WAITING;
            }
            case CHOOSING_THIRD_MOVE -> {
                //TODO ALESSANDRO msg_out = view.choosePawnMove();
                previousPhase = phase;
                nextPhase = Phase.CHOOSING_MN_SHIFT;
                phase = Phase.WAITING;
            }
            case CHOOSING_MN_SHIFT -> {
                view.chooseMNmovement();
                msg_out.setType(view.getMessageType());
                if(view.getMessageType().equals(MessageType.MN_SHIFT))
                {
                    payloads.add(view.getMN_shift().toString());
                    nextPhase = Phase.CHOOSING_CT;
                }
                else{
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    if(view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if(view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }
            }
            case CHOOSING_CT -> {
                view.chooseCT();
                msg_out.setType(view.getMessageType());
                if(view.getMessageType().equals(MessageType.CHOSEN_CT))
                {
                    payloads.add(view.getChosenCloudPos().toString());
                    nextPhase = Phase.PLANNING;
                }
                else{
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    if(view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if(view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }

            }
            case WAITING -> {
                msg_out.setType(MessageType.WAITING);
                msg_out.fill("WAITING");
            }
            case CHOOSING_PARAMETERS -> {
                //todo in base alla carta che viene scelta cambiano i parametri richiesti
            }
        }

        previousPhase = phase;
        phase = Phase.WAITING;
        msg_out.fill(payloads);
        System.out.println("sending... " + msg_out.toSend());

        return msg_out.toSend();
    }
        /*
        if (phase==Phase.LOGIN) {
            view.login();
            msg_out = new Message(view.getUsername(), view.getMessageType(), view.getUsername());
        }
        if (phase==Phase.SETTINGS) {
            view.settings();
            ArrayList<String> payloads = new ArrayList<>();
            payloads.add(view.getIdGame());
            if(view.getPlayerNumber() != null)
                payloads.add(view.getPlayerNumber().toString());
            if(view.getGamemode() != null)
                payloads.add(view.getGamemode().toString());

            msg_out = new Message(view.getUsername(), view.getMessageType());
            msg_out.fill(payloads);
        }
        if(phase==Phase.JOINING) {
            msg_out = new Message(view.getUsername(), MessageType.JOIN_MATCH, view.getIdGame());
        }
        if(phase==Phase.LOBBY)
        {
            view.lobby();
            msg_out = new Message(view.getUsername(), view.getMessageType());
            msg_out.fill(view.getPlayers());
        }

        if(phase==Phase.CHOSING_SCHOOLBOARD)
        {
            //System.out.println("IL GIOCO PUO' INIZIARE");

            view.chooseWizard();



            //todo scegliere anche colore torri e -potenzialmente- squadra
        }
        if(phase == Phase.CHOSING_ASSISTANT_CARD){
            System.out.println("scegli assistant card:");
            view.chooseAssistantCard();
        }
        if(phase == Phase.CHOOSING_PAWS_TO_MOVE){

        }
        if(phase == Phase.CHOOSING_MN_MOVE){

        }
        if(phase == Phase.CHOOSING_CLOUDTILE){

        }
        if(phase == Phase.WAITING){
            msg_out = new Message(MessageType.WAITING);
        }
            System.out.println("sending... " + msg_out.toSend());
            return msg_out.toSend();
 }
*/

    public synchronized void process (String input) {
        System.out.println("receiving... " + input);
        if(input.equals("ACK")) return;
        Gson gson = new Gson();
        Message msg_in = gson.fromJson(input, Message.class);
        Message msg_out = new Message(msg_in.getUser());
        ArrayList<String> payloads;
        switch(msg_in.getType()){
            case ERROR -> {
                System.out.println("Error:" + msg_in.getPayload());
                phase = previousPhase;
            }

            case ASK_FOR_SETTINGS -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<WizardType> wizards = gson.fromJson(payloads.get(0), ArrayList.class);
                ArrayList<TowerColor> towers = gson.fromJson(payloads.get(1), ArrayList.class);
                view.setWizards(wizards);
                view.setTowerColors(towers);
                phase = nextPhase;
            }
            /*
            case LOGIN_SUCCESSFUL:
                System.out.println(input);
                this.phase=Phase.SETTINGS;
                break;

            case LOBBY_CREATED:
                System.out.println(input);
                payloads = new ArrayList<>();
                payloads.add(view.getUsername());
                view.setPlayers(payloads);
                this.phase=Phase.LOBBY;
                break;

            case LOBBY_JOINED:
                System.out.println(input);
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<String> userList = gson.fromJson(payloads.get(0), ArrayList.class);
                view.setPlayers(userList);
                view.setPlayerNumber(gson.fromJson(payloads.get(1), Integer.class));
                view.setGamemode(gson.fromJson(payloads.get(2), GameMode.class));
                this.phase=Phase.LOBBY;
                break;

            case OTHER_USER_JOINED:
                System.out.println(input);
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                view.setPlayers(payloads);
                this.phase=Phase.LOBBY;
                break;

            case GAME_STARTED:
                System.out.println(input);
                this.phase=Phase.WAITING;
                break;

            case ModelUpdate:
                //aggiorni view
                break;

            case NOW_IS_YOUR_TURN:
                System.out.println(input);
                this.phase=nextPhase;
                break;

            case OK_ASSISTANT_CARD:
                System.out.println(input);
                this.nextPhase=Phase.CHOOSING_PAWS_TO_MOVE;
                this.phase=Phase.WAITING;
                break;

            case OK_PAWNS_MOVED:
                System.out.println(input);
                //this.nextPhase=Phase.CHOOSING_MN_MOVE;
                //this.phase=Phase.WAITING;
                this.phase=Phase.CHOOSING_MN_MOVE;
                break;
            case OK_MN_MOVED:
                System.out.println(input);
                //this.nextPhase=Phase.CHOOSING_CLOUDTILE;
                //this.phase=Phase.WAITING;
                this.phase=Phase.CHOOSING_CLOUDTILE;
                break;
            case OK_CLOUTILE_COLLECTED:
                System.out.println(input);
                this.nextPhase = Phase.CHOSING_ASSISTANT_CARD;
                this.phase = Phase.WAITING;
                break;
            case OK_EFFECT_CARD:
                System.out.println(input);
                this.phase = Phase.CHOOSING_PARAMETER;

*/
            /*
            case CloudChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                position = gson.fromJson(payloads.get(0), Integer.class);
                CloudTile cloud = gson.fromJson(payloads.get(1), CloudTile.class);
                //view.update(position, cloud)
                break;

            case HallChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Integer> hall = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChange, hall)
                break;

            case EntranceChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Integer> entrance = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChanged, entrance)
                break;

            case ProfTableChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Boolean> profTable = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChanged, hall)
                break;

            case IslandChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                position = gson.fromJson(payloads.get(0), Integer.class);
                Island island = gson.fromJson(payloads.get(1), Island.class);
                //view.update(position, island)
                break;

            case CharacterCardChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                Integer newPrice = gson.fromJson(payloads.get(0), Integer.class);
                String idCard = gson.fromJson(payloads.get(1), String.class);
                //view.updateCloud(position, island)
                break;

            case WalletChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                userChange = gson.fromJson(payloads.get(0), String.class);
                Integer wallet = gson.fromJson(payloads.get(1), Integer.class);
                //view.updateProf(userChanged, hall)
                break;

            case DeckChanged:
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                userChange = gson.fromJson(payloads.get(0), String.class);
                Deck deck = gson.fromJson(payloads.get(1), Deck.class);
                //view.updateProf(userChanged, hall)
                break;

                /*
            case MoveToIsland:
                ArrayList<String> payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                PawnColor colorToIsland = gson.fromJson(payloads.get(0), PawnColor.class);
                Integer position = gson.fromJson(payloads.get(1), Integer.class);
                break;

            case MoveToHall:
                PawnColor colorToHall = gson.fromJson(msg_in.getPayload(), PawnColor.class);
                break;

            case MoveMN:
                Integer shift = gson.fromJson(msg_in.getPayload(), Integer.class);
                break;
                */
            case WAIT -> {
                previousPhase = phase;
                phase = Phase.WAITING;
                //nextPhase = nextPhase;
                System.out.println("ok aspetto\n");
            }
            case NOW_IS_YOUR_TURN, ACK -> {
                phase = nextPhase;
            }
            case MODEL_UPDATE -> {
                view.updateView();
            }
        }


    }
}
