package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.CloudTile;
import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.view.View;

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

    public NetworkHandler(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    public synchronized String send_msg(){

        /*
        Message msg_out = new Message(view.getUser());
        msg_out.setType(MessageType.LOGIN);
        msg_out.fill(view.getUser());
         */

        BufferedReader stdIn =new BufferedReader(new InputStreamReader(System.in));

        System.out.println("inserire Game id: ");
        String payload = null;
        try {
            payload = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg_out = new Message("Franco", MessageType.CREATE_MATCH, payload);
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

        ArrayList<String> payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
        String userChange = "";
        Integer position = null;

        switch(msg_in.getType()){
            case CloudChanged:
                position = gson.fromJson(payloads.get(0), Integer.class);
                CloudTile cloud = gson.fromJson(payloads.get(1), CloudTile.class);
                //view.update(position, cloud)
                break;

            case HallChanged:
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Integer> hall = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChange, hall)
                break;

            case EntranceChanged:
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Integer> entrance = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChanged, entrance)
                break;

            case ProfTableChanged:
                userChange = gson.fromJson(payloads.get(0), String.class);
                HashMap<PawnColor, Boolean> profTable = gson.fromJson(payloads.get(1), HashMap.class);
                //view.update(userChanged, hall)
                break;

            case IslandChanged:
                position = gson.fromJson(payloads.get(0), Integer.class);
                Island island = gson.fromJson(payloads.get(1), Island.class);
                //view.update(position, island)
                break;

            case CharacterCardChanged:
                Integer newPrice = gson.fromJson(payloads.get(0), Integer.class);
                String idCard = gson.fromJson(payloads.get(1), String.class);
                //view.updateCloud(position, island)
                break;

            case WalletChanged:
                userChange = gson.fromJson(payloads.get(0), String.class);
                Integer wallet = gson.fromJson(payloads.get(1), Integer.class);
                //view.updateProf(userChanged, hall)
                break;

            case DeckChanged:
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
