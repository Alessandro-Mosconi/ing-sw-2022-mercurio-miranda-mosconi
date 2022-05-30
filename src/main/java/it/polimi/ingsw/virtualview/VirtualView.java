package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ErrorType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.VirtualViewListener;

import java.util.*;

public class VirtualView implements Observer {

    private VirtualViewListener gameController;
    private Message msg_in;
    private Message msg_out;
    private MessageType out_type;
    private MessageType next_out_type;
    private ErrorType error_Type_type;
    private String username;
    private String idGame;
    private GameMode gamemode;
    private Integer playersNumber;
    private Player player;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> clouds;
    private IslandManager islandManager;
    private ArrayList<CharacterCard> characterCards; //sono solo le 3 scelte immagino
    private boolean online = true;
    private boolean isMyTurn = false;
    private ArrayList<String> players;
    private WizardType wizard;
    private TowerColor towerColor;
    private int chosenAssistantID;
    private PawnColor studentToMove;
    private int destination;
    private int chosenShift;
    private int chosenCloudID;
    private static Map<String, ArrayList<String>> networkMap = new HashMap<>(); //mappa di gameid e lista di player
    private static Map<String, Game> gameMap = new HashMap<>(); //mappa di gameid e game


    public void setGameController(VirtualViewListener gameController) {
        this.gameController = gameController;
    }

    public VirtualViewListener getGameController() {
        return gameController;
    }

    public Message getMsg_in() {
        return msg_in;
    }

    public void setMsg_in(Message msg_in) {
        this.msg_in = msg_in;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public MessageType getNext_out_type() {
        return next_out_type;
    }

    public void setNext_out_type(MessageType next_out_type) {
        this.next_out_type = next_out_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public Integer getPlayerNumber() {
        return playersNumber;
    }

    public void setPlayerNumber(Integer playersNumber) {
        this.playersNumber = playersNumber;
    }

    public MessageType getOut_type() {
        return out_type;
    }

    public void setOut_type(MessageType out_type) {
        this.out_type = out_type;
    }

    public ErrorType getError_type() {
        return error_Type_type;
    }

    public void setError_type(ErrorType error_Type_type) {
        this.error_Type_type = error_Type_type;
    }

    public String sendAnswer() {
        Message msg = new Message(username, out_type);

        switch (out_type) {
            case ERROR:
                msg.fill(error_Type_type);
                break;

            case CloudChanged:
                msg.fill(clouds);
                break;

            case HallChanged:
                msg.fill(player.getSchoolBoard().getStudentHall());
                break;

            case EntranceChanged:
                msg.fill(player.getSchoolBoard().getStudentEntrance());
                break;

            case ProfTableChanged:
                msg.fill(player.getSchoolBoard().getProfessorTable());
                break;

            case GAME_STARTED:
                break;

            case WalletChanged:
                //todo gestire switch case

            default:
                break;
        }

        return msg.toSend();
    } //da finire

    public String read(String input) {

        System.out.println("receiving..." + input);
        Gson gson = new Gson();
        Message msg = gson.fromJson(input, Message.class);

        //anziché salvare in msg_in usiamo uno switch case e salviamo in determinati attributi
        //Poi più in là vediamo se è la cosa più intelligente da fare.

        ArrayList<String> payloads = new ArrayList<String>();
        this.msg_in = msg;
        payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);

        //todo Inserire switch case che analizza il tipo di messaggio
        switch (msg_in.getType()) {
            case CREATE_MATCH -> {
                username = payloads.get(0);
                idGame = payloads.get(1);
                playersNumber = Integer.parseInt(payloads.get(2));
                gamemode = GameMode.valueOf(payloads.get(3));

                GameController gameController = new GameController();
                gameController.getVirtualViews().add(this);

                //virtualview manda il messaggio di uscita.
                msg_out.setUser(username);
                msg_out.setType(MessageType.ASK_FOR_SETTINGS);
                ArrayList<String> UsersList = new ArrayList<>();
                UsersList.add(username);
                networkMap.put(idGame, UsersList);
                System.out.println(networkMap);
                //Game game = new Game(virtualView.getPlayerNumber(), virtualView.getIdGame(), virtualView.getGamemode());
                //gameMap.put(virtualView.getIdGame(), game);
                return msg_out.toSend();
                //out.println(msg_out);
            }
            case JOIN_MATCH -> {
                //username = payloads.get(0);
                if(!networkMap.containsKey(payloads.get(1))){
                    msg_out.setUser(payloads.get(0));
                    msg_out.setType(MessageType.ERROR);
                    msg_out.fill(ErrorType.GAME_NOT_FOUND);
                    System.out.println(ErrorType.GAME_NOT_FOUND);
                    return msg_out.toSend();
                }
                else if(networkMap.get(payloads.get(1)).contains(msg_in.getUser())) {
                    //setUsername(payloads.get(0));
                    //setIdGame(payloads.get(1));
                    msg_out.setUser(payloads.get(0));
                    msg_out.setType(MessageType.ERROR);
                    msg_out.fill(ErrorType.USERNAME_ALREADY_IN_LOBBY);
                    System.out.println(ErrorType.USERNAME_ALREADY_IN_LOBBY);
                    return msg_out.toSend();
                }
                else {
                    username = payloads.get(0);
                    idGame = payloads.get(1);
                    setPlayerNumber(gameMap.get(getIdGame()).getNumberOfPlayers());
                    setGamemode(gameMap.get(getIdGame()).getGameMode());
                    ArrayList<String> UsersList = new ArrayList<>();
                    UsersList = networkMap.get(getIdGame());
                    UsersList.add(msg_in.getUser());
                    networkMap.replace(getIdGame(), UsersList);
                    setPlayers(UsersList);
                    msg_out.setUser(username);
                    msg_out.setType(MessageType.ASK_FOR_SETTINGS);
                    return msg_out.toSend();
                }
            }
            case SETTINGS -> {
                wizard = WizardType.valueOf(payloads.get(0));
                towerColor = TowerColor.valueOf(payloads.get(1));
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case AssistantCard -> {
                chosenAssistantID = Integer.parseInt(payloads.get(0));
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case PAWN_MOVE -> {
                studentToMove = PawnColor.valueOf(payloads.get(0));
                destination = Integer.parseInt(payloads.get(1));
                msg_out.setType(MessageType.IS_YOUR_TURN);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case MN_SHIFT -> {
                chosenShift = Integer.parseInt(payloads.get(0));
                msg_out.setType(MessageType.IS_YOUR_TURN);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case CHOSEN_CT -> {
                chosenCloudID = Integer.parseInt(payloads.get(0));
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
        }
        return null;
    }



    //private ArrayList<Player> players;
    //private GameMode chosenGameMode;

    //todo compito della virtual view è di prendere i player dal network handler.
    /*public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
*/
    /*
    public Player askForPlayerData() {
        Player player = new Player();
        //todo deve chiedere ad ogni client di inserire tutti i dati richiesti (username, colore e mago)
        return player;
    }

    public AssistantCard askForAssistantCard() {
        AssistantCard chosenAssistantCard = new AssistantCard();
        //todo chiede, riceve e dejsonizza la scelta
        if (msg_in.getType().equals(MessageType.AssistantCard)) {
            ArrayList<String> payloads = new ArrayList<>();
            Gson gson = new Gson();
            payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
            chosenAssistantCard = player.getDeck().getCards().get(Integer.parseInt(payloads.get(0)));
            return chosenAssistantCard;
        } else {
            setError_type(ErrorType.INVALID_CARD);
            return null;
        }
    }

    public void askForMovement() {
    }

    //todo modificare appena facciamo la virtualView, ofc
    public int askForMNMovement() {
        return 1;
    }

    //todo modificare appena facciamo la virtualView, ofc 2
    public int askForCloudTile() {
        return 1;
    }
*/
    //todo tutti i tipi di modelUpdate vanno qui
    @Override
    public void update(Observable o, Object arg) {

    }

    public WizardType getWizard() {
        return wizard;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public int getChosenAssistantID() {
        return chosenAssistantID;
    }

    public PawnColor getStudentToMove() {
        return studentToMove;
    }

    public int getDestination() {
        return destination;
    }

    public int getMNShift() {
        return chosenShift;
    }

    public int getChosenCloudID() {
        return chosenCloudID;
    }

    public Parameter getParameter() {
        return null; //TODO
    }

    public int getChosenCharacterCardID() {
        return 1; //TODO
    }
}