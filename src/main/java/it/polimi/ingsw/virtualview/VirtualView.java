package it.polimi.ingsw.virtualview;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ErrorType;
import it.polimi.ingsw.network.Message;
import it.polimi.ingsw.network.MessageType;

import java.util.*;

public class VirtualView implements Observer {

    private Message msg_in;
    private MessageType out_type;
    private MessageType next_out_type;
    private ErrorType error_Type_type;
    private String username;
    private String idGame;
    private GameMode gamemode;
    private Integer playerNumber;
    private Player player;
    private ArrayList<SchoolBoard> schoolBoards;
    private ArrayList<CloudTile> clouds;
    private IslandManager islandManager;
    private ArrayList<CharacterCard> characterCards; //sono solo le 3 scelte immagino
    private boolean online = true;
    private ArrayList<String> players;

    public void update(Object o) {
        Game model = (Game) o;

        this.schoolBoards = model.getSchoolBoards();
        this.clouds = model.getCloudTiles();
        this.islandManager = model.getIslandManager();
        this.characterCards = model.getAllCharacterCard();
        this.player = model.getPlayerByUsername(this.username);
    }

    public Message getMsg_in() {
        return msg_in;
    }

    public void setMsg_in(Message msg_in) {
        this.msg_in = msg_in;
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
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
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

    public void read(String input) {

        Gson gson = new Gson();
        Message msg = gson.fromJson(input, Message.class);

        this.msg_in = msg;

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

    @Override
    public void update(Observable o, Object arg) {

    }

    public boolean askIfCard() {
        String payload = new String();
        Gson gson = new Gson();
        payload = gson.fromJson(msg_in.getPayload(), String.class);
        return payload.equals("YES");
    }

    public PawnColor askForColor() {
        if (msg_in.getType().equals(MessageType.Color)) {
            String payload = new String();
            Gson gson = new Gson();
            payload = gson.fromJson(msg_in.getPayload(), String.class);
            payload = payload.toLowerCase();
            return PawnColor.valueOf(payload);
        } else {
            setError_type(ErrorType.INVALID_COLOR);
            return null;
        }
    }

    public Island askForIsland() {
        if (msg_in.getType().equals(MessageType.Island)) {
            String payload = new String();
            Gson gson = new Gson();
            payload = gson.fromJson(msg_in.getPayload(), String.class);
            return this.islandManager.getIslandList().get(Integer.parseInt(payload));
        } else {
            setError_type(ErrorType.INVALID_ISLAND);
            return null;
        }

    }

    public Map<PawnColor, Integer> askForStudToTake() {
        Map<PawnColor, Integer> payload = new HashMap<>();
        Gson gson = new Gson();
        payload = gson.fromJson(msg_in.getPayload(), HashMap.class);
        return payload;
    }

    public Map<PawnColor, Integer> askForStudToGive() {
        Map<PawnColor, Integer> payload = new HashMap<>();
        Gson gson = new Gson();
        payload = gson.fromJson(msg_in.getPayload(), HashMap.class);
        return payload;
    }

    public CharacterCard askForCharCard() {
        if(msg_in.getType().equals(MessageType.ChosenCharacterCard)){
            String payload = new String();
            Gson gson = new Gson();
            payload = gson.fromJson(msg_in.getPayload(), String.class);
            for(CharacterCard characterCard : characterCards){
               if(Integer.parseInt(payload)==(characterCard.getID())){
                   return characterCard;
               }
            }
        }
        setError_type(ErrorType.INVALID_CARD);
        return null;
    }
}