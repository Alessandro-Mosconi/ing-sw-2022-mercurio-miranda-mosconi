package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.virtualview.VirtualView;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private VirtualView virtualView;
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private static Map<String, ArrayList<String>> networkMap = new HashMap<>(); //mappa di gameid e lista di player
    private static Map<String, Game> gameMap = new HashMap<>(); //mappa di gameid e game

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
            Pinger pinger = new Pinger(out, client, "Server to "+ client.getInetAddress());
            Thread thread = new Thread(pinger, "serverPing_" + client.getInetAddress());
            thread.start();
            handleClientConnection(pinger);
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }

        try {

            client.close();

        } catch (IOException e) {
            System.out.println("Error with the socket closing");
        }
    }

    /**
     * An event loop that receives messages from the client and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection(Pinger pinger) throws IOException
    {

        GameController controller = null;

        System.out.println("sending ack");
        out.println("ACK");

        try {
            while (true) {

                String input = in.readLine();
                System.out.println("[" + client.getInetAddress() + "]" + " receiving... " + input);

                if(!input.equals("ping")) {
                    if(!initialize(input))
                        System.out.println("processing...");
                        //virtualView.update(input)

                }

            }
        } catch (SocketTimeoutException e) {
            System.err.println("Client no more reachable ");
        } catch(IOException ex)
        {
            //
        }
        finally
        {

            //prima di chiudere una connessione se l'utente è entrato in qualche lobby o ha creato qualche lobby con solo lui, si cancellano

            try
            {
                pinger.stop();
                in.close();
                out.close();
                this.client.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }

            synchronized (networkMap) {
                if (virtualView.getIdGame() != null)
                    if (gameMap.containsKey(virtualView.getIdGame()))
                        if (gameMap.get(virtualView.getIdGame()) != null)
                            if (!gameMap.get(virtualView.getIdGame()).isStarted()) {
                                ArrayList<String> UserList = networkMap.get(virtualView.getIdGame());
                                UserList.remove(virtualView.getUsername());
                                if (UserList.isEmpty()) {
                                    networkMap.remove(virtualView.getIdGame());
                                    System.out.println("deleted");
                                    System.out.println(networkMap);
                                } else {
                                    networkMap.replace(virtualView.getIdGame(), UserList);
                                    System.out.println("removed");
                                    System.out.println(networkMap);
                                }
                            }

            }

            System.out.println("[" + client.getInetAddress() + "] " + ">> Connessione terminata <<");
        }
    }

    public synchronized boolean initialize (String input){

        synchronized (networkMap) {
            System.out.println("initializing...");
            Gson gson = new Gson();
            Message msg_in = gson.fromJson(input, Message.class);
            Message msg_out = new Message();
            ArrayList<String> payloads = new ArrayList<String>();

            switch (msg_in.getType()) {
                case LOGIN:
                    if (msg_in.getPayload().equals("")) {
                        msg_out.setUser(null);
                        msg_out.setType(MessageType.ERROR);
                        msg_out.fill(Error.INVALID_USERNAME);
                        System.out.println(Error.INVALID_USERNAME);
                    } else {
                        virtualView = new VirtualView();
                        virtualView.setUsername(msg_in.getUser());
                        msg_out.setUser(virtualView.getUsername());
                        msg_out.setType(MessageType.LOGIN_SUCCESSFUL);
                        System.out.println("login successful");
                    }
                    break;

                case CREATE_MATCH:
                    payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                    if (networkMap.containsKey(payloads.get(0))) {
                        msg_out.setUser(virtualView.getUsername());
                        msg_out.setType(MessageType.ERROR);
                        msg_out.fill(Error.GAME_ALREADY_EXISTING);
                        System.out.println(Error.GAME_ALREADY_EXISTING);
                    } else {
                        virtualView.setIdGame(payloads.get(0));
                        virtualView.setPlayerNumber(Integer.parseInt(payloads.get(1)));
                        virtualView.setGamemode(GameMode.valueOf(payloads.get(2)));

                        msg_out.setUser(virtualView.getUsername());
                        msg_out.setType(MessageType.LOBBY_CREATED);

                        ArrayList<String> UserList = new ArrayList<String>();
                        UserList.add(virtualView.getUsername());
                        networkMap.put(virtualView.getIdGame(), UserList);
                        System.out.println(networkMap);
                        Game game = new Game(virtualView.getPlayerNumber(), virtualView.getIdGame(), virtualView.getGamemode());
                        gameMap.put(virtualView.getIdGame(), game);
                        //controller = new GameController(game);
                    }
                    break;

                case JOIN_MATCH:
                    payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                    if (!networkMap.containsKey(payloads.get(0))) {
                        msg_out.setUser(virtualView.getUsername());
                        msg_out.setType(MessageType.ERROR);
                        msg_out.fill(Error.GAME_NOT_FOUND);
                        System.out.println(Error.GAME_NOT_FOUND);
                    } else if (networkMap.get(payloads.get(0)).contains(msg_in.getUser())) {
                        msg_out.setUser(virtualView.getUsername());
                        msg_out.setType(MessageType.ERROR);
                        msg_out.fill(Error.USERNAME_ALREADY_IN_LOBBY);
                        System.out.println(Error.USERNAME_ALREADY_IN_LOBBY);
                    } else {
                        virtualView.setIdGame(payloads.get(0));
                        virtualView.setPlayerNumber(gameMap.get(virtualView.getIdGame()).getNumberOfPlayers());
                        virtualView.setGamemode(gameMap.get(virtualView.getIdGame()).getGameMode());
                        ArrayList<String> UserList = new ArrayList<>();
                        UserList = networkMap.get(virtualView.getIdGame());
                        UserList.add(msg_in.getUser());
                        networkMap.replace(virtualView.getIdGame(), UserList);
                        virtualView.setPlayers(UserList);
                        msg_out.setType(MessageType.LOBBY_JOINED);
                        msg_out.fill(virtualView);
                        System.out.println(networkMap);
                        networkMap.notifyAll();
                        System.out.println("notified");
                        //controller = new GameController(gameMap.get(msg_in.getPayload()));
                    }
                    break;

                case LOBBY_WAITING:
                    payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);

                    String inputCheck = "ping";

                    while (networkMap.get(virtualView.getIdGame()).size() != virtualView.getPlayerNumber()) {

                        try {
                                System.out.println("waiting...");
                                networkMap.wait(15000);
                                inputCheck = in.readLine();
                            } catch (IOException e) {
                                System.out.println("Client disconnesso al momento del risveglio");
                                return false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return false;
                            }



                    System.out.println("svegliato...");


                    System.out.println("waiting, while reciving inputCheck");

                            if (networkMap.get(virtualView.getIdGame()).size() != virtualView.getPlayerNumber() && networkMap.get(virtualView.getIdGame()).size() != payloads.size()) {
                                msg_out.setType(MessageType.OTHER_USER_JOINED);
                                msg_out.fill(networkMap.get(virtualView.getIdGame()));
                                out.println(msg_out.toSend());
                                return false;
                            }

                    }

                    gameMap.get(virtualView.getIdGame()).setStarted(true);
                    msg_out.setUser(virtualView.getUsername());
                    msg_out.setType(MessageType.GAME_STARTED);
                    msg_out.fill(networkMap.get(virtualView.getIdGame()));

                    System.out.println("sending... " + msg_out.toSend());
                    out.println(msg_out.toSend());
                    return true;
                //break;


                default:
                    msg_out.setType(MessageType.ERROR);
                    msg_in.fill(Error.UNKNOWN_ERROR);
                    out.println(msg_out.toSend());
                    System.out.println("sending... " + msg_out.toSend());
                    return false;
            }


            System.out.println("sending... " + msg_out.toSend());
            out.println(msg_out.toSend());
            return false;
        }
    }

}