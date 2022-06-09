package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.*;

import java.util.*;

public class VirtualView{

    private VirtualViewListener gameController;

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    private ClientHandler clientHandler;
    private Message msg_in;
    private Message msg_out;
    private MessageType out_type;
    private MessageType next_out_type;
    private ErrorType error_Type_type;
    private String username;
    private String idGame;
    private GameMode gamemode;
    private Integer playersNumber;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player = new Player();
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
    private static Map<String, GameController> gameMap = new HashMap<>(); //mappa di gameid e game


    public void setGameController(VirtualViewListener gameController) {
        this.gameController = gameController;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
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

    public Integer getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(Integer playersNumber) {
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

            default:
                break;
        }

        return msg.toSend();
    } //da finire

    public String read(String input) {

        //System.out.println("receiving..." + input);
        Gson gson = new Gson();
        Message msg = gson.fromJson(input, Message.class);

        //anziché salvare in msg_in usiamo uno switch case e salviamo in determinati attributi
        //Poi più in là vediamo se è la cosa più intelligente da fare.

        ArrayList<String> payloads = new ArrayList<String>();
        this.msg_in = msg;
        payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);

        switch (msg_in.getType()) {
            case CREATE_MATCH -> {
                username = payloads.get(0);
                idGame = payloads.get(1);
                playersNumber = Integer.parseInt(payloads.get(2));
                gamemode = GameMode.valueOf(payloads.get(3));

                GameController gController = new GameController();
                gameController = (VirtualViewListener) gController;
                gController.setPlayersToGo(playersNumber);
                this.player.setPlayerNumber(0);
                gController.addVirtualView(this);
                //gController.getVirtualViews().add(this);
                //gController.getClientHandlerArrayList().add(this.clientHandler);
                //gController.getVirtualViewsOrder().add(0);
                //gController.getVirtualViews().add(this);
                gameController.performAction(); //CREATEGAMESTATE
                //virtualview manda il messaggio di uscita.
                msg_out = new Message();
                msg_out.setUser(username);
                msg_out.setType(MessageType.LOBBY_WAITING);
                ArrayList<String> toPush = new ArrayList<>();
                toPush.add(String.valueOf(gamemode));
                msg_out.fill(toPush);
                ArrayList<String> UsersList = new ArrayList<>();
                UsersList.add(username);
                networkMap.put(idGame, UsersList);
                gameMap.put(idGame,gController);
                System.out.println(networkMap);
                //Game game = new Game(virtualView.getPlayerNumber(), virtualView.getIdGame(), virtualView.getGamemode());
                //gameMap.put(virtualView.getIdGame(), game);
                return msg_out.toSend();
                //out.println(msg_out);
            }
            case JOIN_MATCH -> {
                //username = payloads.get(0);
                if(!networkMap.containsKey(payloads.get(1))){
                    msg_out = new Message();
                    msg_out.setUser(payloads.get(0));
                    msg_out.setType(MessageType.ERROR);
                    msg_out.fill(ErrorType.GAME_NOT_FOUND);
                    System.out.println(ErrorType.GAME_NOT_FOUND);
                    return msg_out.toSend();
                }
                else if(networkMap.get(payloads.get(1)).contains(msg_in.getUser())) {
                    //setUsername(payloads.get(0));
                    //setIdGame(payloads.get(1));
                    msg_out = new Message();
                    msg_out.setUser(payloads.get(0));
                    msg_out.setType(MessageType.ERROR);
                    msg_out.fill(ErrorType.USERNAME_ALREADY_IN_LOBBY);
                    System.out.println(ErrorType.USERNAME_ALREADY_IN_LOBBY);
                    return msg_out.toSend();
                }
                else {
                    username = payloads.get(0);
                    idGame = payloads.get(1);
                    playersNumber = (gameMap.get(getIdGame()).getGame().getNumberOfPlayers());
                    gamemode = (gameMap.get(getIdGame()).getGame().getGameMode());

                    GameController gController = gameMap.get(idGame);
                    gameController = (VirtualViewListener) gController;
                    ArrayList<String> UsersList = new ArrayList<>();
                    UsersList = networkMap.get(getIdGame());
                    UsersList.add(msg_in.getUser());
                    networkMap.replace(getIdGame(), UsersList);
                    setPlayers(UsersList);
                    this.player.setPlayerNumber(UsersList.size()-1);
                    gController.addVirtualView(this);
                    msg_out = new Message();
                    msg_out.setUser(username);
                    ArrayList<String> toPush = new ArrayList<>();
                    toPush.add(String.valueOf(gamemode));
                    msg_out.fill(toPush);
                    //msg_out.fill(String.valueOf(gamemode));
                    //gController.getClientHandlerArrayList().add(this.clientHandler);
                    //gController.getVirtualViews().add(this);
                    //gameController.addVirtualView(this);
                    //gController.addVirtualView(this);
                    gameController.performAction();
                    msg_out.setType(MessageType.LOBBY_WAITING);
                    return msg_out.toSend();
                }
            }
            case SETTINGS -> {
                wizard = WizardType.valueOf(payloads.get(0));
                towerColor = TowerColor.valueOf(payloads.get(1));
                msg_out = new Message();
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                player.getDeck().setWizard(wizard);
                player.getSchoolBoard().setTowersColor(towerColor);
                gameController.performAction();
                return msg_out.toSend();
            }
            case AssistantCard -> {
                chosenAssistantID = Integer.parseInt(payloads.get(0));
                msg_out = new Message();
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                //player.getDeck().getCards().get(chosenAssistantID-1).setConsumed(true);
                //player.setLastAssistantCard(player.getDeck().getCards().get(chosenAssistantID-1));
                gameController.performAction();
                return msg_out.toSend();
            }
            case PAWN_MOVE -> {
                studentToMove = PawnColor.valueOf(payloads.get(0));
                destination = Integer.parseInt(payloads.get(1));
                msg_out = new Message();
                msg_out.setType(MessageType.IS_YOUR_TURN);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case MN_SHIFT -> {
                chosenShift = Integer.parseInt(payloads.get(0));
                msg_out = new Message();
                msg_out.setType(MessageType.IS_YOUR_TURN);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case CHOSEN_CT -> {
                chosenCloudID = Integer.parseInt(payloads.get(0));
                msg_out = new Message();
                msg_out.setType(MessageType.WAIT);
                msg_out.setUser(username);
                gameController.performAction();
                return msg_out.toSend();
            }
            case CHOSEN_CHARACTER_CARD -> {
                String playerID = payloads.get(0);
                Integer characterCardID = Integer.parseInt(payloads.get(1));
                int payloadsIterator = 2;
                //Parameter parameter = new Parameter(player);
                PawnColor color = null;
                Integer islandID = null;
                Map<PawnColor,Integer> map1 = null;
                Map<PawnColor,Integer> map2 = null;
                switch (characterCardID){
                    case 1 ->{
                        //parameter.setChosenColor(PawnColor.valueOf(payloads.get(payloadsIterator)));
                        color = PawnColor.valueOf(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        //parameter.setIsland(islandManager.getIslandList().get(Integer.parseInt(payloads.get(payloadsIterator))));
                        islandID = Integer.parseInt(payloads.get(payloadsIterator));
                    }
                    case 3, 5 ->{
                       //parameter.setIsland(islandManager.getIslandList().get(Integer.parseInt(payloads.get(payloadsIterator))));
                        islandID = Integer.parseInt(payloads.get(payloadsIterator));
                    }
                    case 7, 10 ->{
                        map1 = new HashMap<>();
                        map2 = new HashMap<>();
                        for(int j=0; j<PawnColor.values().length; j++){
                            PawnColor currColor = PawnColor.valueOf(payloads.get(payloadsIterator));
                            payloadsIterator++;
                            Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                            payloadsIterator++;
                            map1.put(currColor,num);
                        }
                        for(int j=0; j<PawnColor.values().length; j++){
                            PawnColor currColor = PawnColor.valueOf(payloads.get(payloadsIterator));
                            payloadsIterator++;
                            Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                            payloadsIterator++;
                            map2.put(currColor,num);
                        }
                        //parameter.setColorMap1(map1);
                        //parameter.setColorMap2(map2);
                    }
                    case 9,11,12 ->{
                        //parameter.setChosenColor(PawnColor.valueOf(payloads.get(payloadsIterator)));
                        color = PawnColor.valueOf(payloads.get(payloadsIterator));
                    }
                }
                gameController.activateCardEffect(characterCardID, username, color, islandID, map1, map2);
                msg_out = new Message();
                msg_out.setType(MessageType.CARD_ACTIVATED);
                return msg_out.toSend();
            }
        }
        return null;
    }



    //private ArrayList<Player> players;
    //private GameMode chosenGameMode;

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
        return player;
    }

    public AssistantCard askForAssistantCard() {
        AssistantCard chosenAssistantCard = new AssistantCard();
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
    public int askForMNMovement() {
        return 1;
    }

    public int askForCloudTile() {
        return 1;
    }
*/

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