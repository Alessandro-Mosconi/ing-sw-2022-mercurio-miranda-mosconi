package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
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
    private Integer phase;

    public NetworkHandler(PrintWriter out, BufferedReader in, View view) {
        this.out = out;
        this.in = in;
        this.view = view;
        this.phase = 0;
    }

    public synchronized String send_msg() {

        Message msg_out = null;

        if (phase.equals(0)) {
            view.login();
            msg_out = new Message(view.getUsername(), view.getMessageType(), view.getUsername());
        }
        if (phase.equals(1)) {
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
        if(phase.equals(2)) {
            msg_out = new Message(view.getUsername(), MessageType.JOIN_MATCH, view.getIdGame());
        }
        if(phase.equals(3))
        {
            view.lobby();
            msg_out = new Message(view.getUsername(), view.getMessageType());
            msg_out.fill(view.getPlayers());
        }
        if(phase.equals(4))
        {
            view.lobby();
            msg_out = new Message(view.getUsername(), view.getMessageType());
            msg_out.fill(view.getPlayers());
            System.out.println("jump");
            return "jump";
        }
        if(phase.equals(5))
        {
            System.out.println("IL GIOCO PUO' INIZIARE");

            view.login();

        }


            System.out.println("sending... " + msg_out.toSend());
            return msg_out.toSend();

    }

    public synchronized void process (String input)
    {
        System.out.println("receiving... " + input);

        if(input.equals("ACK")) return;

        Gson gson = new Gson();
        Message msg_in = gson.fromJson(input, Message.class);
        Message msg_out = new Message(msg_in.getUser());

        ArrayList<String> payloads;
        String userChange = "";
        Integer position = null;

        switch(msg_in.getType()){
            case ERROR:
                System.out.println("Error:" + msg_in.getPayload());
                this.phase=0;
                break;

            case LOGIN_SUCCESSFUL:
                System.out.println(input);
                this.phase=1;
                break;

            case LOBBY_CREATED:
                System.out.println(input);
                payloads = new ArrayList<>();
                payloads.add(view.getUsername());
                view.setPlayers(payloads);
                this.phase=3;
                break;

            case LOBBY_JOINED:
                System.out.println(input);
                VirtualView vv = gson.fromJson(msg_in.getPayload(), VirtualView.class);
                view.setPlayers(vv.getPlayers());
                view.setPlayerNumber(vv.getPlayerNumber());
                view.setGamemode(vv.getGamemode());
                this.phase=3;
                break;

            case OTHER_USER_JOINED:
                System.out.println(input);
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                view.setPlayers(payloads);
                this.phase=3;
                break;

            case GAME_STARTED:
                System.out.println(input);
                this.phase=5;
                break;

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
                Deck deckk = gson.fromJson(payloads.get(1), Deck.class);
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

            default:
                break;
        }


    }
}
