package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.virtualview.VirtualView;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private static Map<String, HashSet<String>> networkMap = new HashMap<>();
    private static Map<String, Game> gameMap = new HashMap<>();

    /**
     * Initializes a new handler using a specific socket connected to
     * a client.
     * @param client The socket connection to the client.
     */
    ClientHandler(Socket client)
    {
        this.client = client;
    }


    /**
     * Connects to the client and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            out = new PrintWriter(this.client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {
            client.close();
        } catch (IOException e) { }
    }


    /**
     * An event loop that receives messages from the client and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection() throws IOException
    {

        GameController controller = null;
        VirtualView virtualView = new VirtualView();

        try {
            while (true) {
                /* read commands from the client, process them, and send replies */
                String input = in.readLine();

                System.out.println("[" + client.getInetAddress() + "] -- " + input);

                Gson gson = new Gson();
                Message msg_in = gson.fromJson(input, Message.class);
                Message msg_out = new Message(msg_in.getUser());

                switch (msg_in.getType()) {
                    case CREATE_MATCH:
                        if (networkMap.containsKey(msg_in.getPayload())) {
                            msg_out.setType(MessageType.ERROR);
                            msg_out.fill(Error.GAME_ALREADY_EXISTING);
                            System.out.println(Error.GAME_ALREADY_EXISTING);
                        } else {
                            msg_out.setType(MessageType.LOBBY_CREATED);
                            HashSet<String> UserList = new HashSet<String>();
                            UserList.add(msg_in.getUser());
                            networkMap.put(msg_in.getPayload(), UserList);
                            System.out.println(networkMap);
                            Game game = new Game();
                            gameMap.put(msg_in.getPayload(), game);
                            controller = new GameController(game);
                        }
                        break;

                    case JOIN_MATCH:
                        if (!networkMap.containsKey(msg_in.getPayload())) {
                            msg_out.setType(MessageType.ERROR);
                            msg_out.fill(Error.GAME_NOT_FOUND);
                            System.out.println(Error.GAME_NOT_FOUND);
                        } else {
                            HashSet<String> UserList = new HashSet<String>();
                            UserList = networkMap.get(msg_in.getPayload());
                            UserList.add(msg_in.getUser());
                            networkMap.replace(msg_in.getPayload(), UserList);
                            msg_out.setType(MessageType.LOBBY_JOINED);
                            msg_out.fill(UserList);
                            System.out.println(networkMap);
                            controller = new GameController(gameMap.get(msg_in.getPayload()));
                        }
                        break;

                    default:
                        msg_out.setType(MessageType.ERROR);
                        msg_in.fill(Error.UNKNOWN_ERROR);
                        break;
                }

                out.println(msg_out.toSend());

            }
        } catch(IOException ex)
        {
            //
        }
        finally
        {
            try
            {
                in.close();
                out.close();
                this.client.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

            System.out.println("[" + client.getInetAddress() + "] " + ">> Connessione terminata <<");
        }
    }

    /*
     * Sends a message to the client.
     * @param answerMsg The message to be sent.
     * @throws IOException If a communication error occurs.
    public void sendAnswerMessage(AnswerMsg answerMsg) throws IOException
    {
    output.writeObject((Object)answerMsg);
    }
     */
}
