package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        previousPhase = phase;
        switch (phase) {
            case LOGIN -> {
                view.login();
                msg_out.setUser(view.getUsername());
                //
                view.getPlayer().setNickName(view.getUsername());
                //
                payloads.add(view.getUsername());
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
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_SECOND_MOVE;
                    //phase = Phase.WAITING;
                } else {
                    //phase = Phase.WAITING;
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    payloads.add(view.getChosenCharacterCard().getID().toString());

                    if (view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if (view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }

            }
            case CHOOSING_SECOND_MOVE -> {
                //TODO ALESSANDRO msg_out = view.choosePawnMove();
                view.choosePawnMove();
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_THIRD_MOVE;
                    //phase = Phase.WAITING;
                } else {
                    //phase = Phase.WAITING;
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    payloads.add(view.getChosenCharacterCard().getID().toString());

                    if (view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if (view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }
                msg_out.setType(view.getMessageType());
                //nextPhase = Phase.CHOOSING_THIRD_MOVE;
                //phase = Phase.WAITING;
            }
            case CHOOSING_THIRD_MOVE -> {
                //TODO ALESSANDRO msg_out = view.choosePawnMove();
                view.choosePawnMove();
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_MN_SHIFT;
                    //phase = Phase.WAITING;
                }else{
                    //phase = Phase.WAITING;
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    payloads.add(view.getChosenCharacterCard().getID().toString());

                    if (view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if (view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }
                msg_out.setType(view.getMessageType());
                //nextPhase = Phase.CHOOSING_MN_SHIFT;
                //phase = Phase.WAITING;
            }
            case CHOOSING_MN_SHIFT -> {
                view.chooseMNmovement();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.MN_SHIFT)) {
                    payloads.add(view.getMN_shift().toString());
                    nextPhase = Phase.CHOOSING_CT;
                } else {
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    if (view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if (view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }
            }
            case CHOOSING_CT -> {
                view.chooseCT();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.CHOSEN_CT)) {
                    payloads.add(view.getChosenCloudPos().toString());
                    nextPhase = Phase.PLANNING;
                } else {
                    nextPhase = Phase.CHOOSING_CharacterCard;
                    if (view.getColorToMove() != null)
                        payloads.add(view.getColorToMove().toString());
                    else if (view.getChosenIslandPos() != null)
                        payloads.add(view.getColorToMove().toString());
                }
                for(Player p : view.getPlayers()){
                    p.setLastAssistantCard(null);
                }//resetta le lastAssistantCards usate dai player
            }
            case WAITING -> {
                msg_out.setType(MessageType.WAITING);
                msg_out.fill("WAITING");
            }
            case CHOOSING_PARAMETERS -> {
                //todo in base alla carta che viene scelta cambiano i parametri richiesti
            }
        }

        //previousPhase = phase;
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

    public synchronized void process(String input) {
        System.out.println("receiving... " + input);
        if (input.equals("ACK")) return;
        Gson gson = new Gson();
        Message msg_in = gson.fromJson(input, Message.class);
        Message msg_out = new Message(msg_in.getUser());
        ArrayList<String> payloads;
        switch (msg_in.getType()) {
            case ERROR -> {
                System.out.println("Error:" + msg_in.getPayload());
                phase = previousPhase;
            }
            case LOBBY_WAITING -> {
                phase = Phase.WAITING;
                nextPhase = Phase.SETTINGS;
            }
            case LOBBY_UPDATED -> {
                System.out.println("Lobby updated ok");
            }
            case ASK_FOR_SETTINGS -> {
                //payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                //ArrayList<WizardType> wizards = gson.fromJson(payloads.get(0), ArrayList.class);
                //ArrayList<TowerColor> towers = gson.fromJson(payloads.get(1), ArrayList.class);
                //view.setWizards(wizards);
                //view.setTowerColors(towers);
                phase = Phase.SETTINGS;
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

            case IS_YOUR_TURN:
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
            case IS_YOUR_TURN, ACK -> {
                phase = nextPhase;
            }
            /*case GAME_MODEL_UPDATE -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                int numPlayers = Integer.parseInt(payloads.get(0));
                GameMode gameMode = GameMode.valueOf(payloads.get(1));
                ArrayList<Player> players = new ArrayList<>();
                ArrayList<CloudTile> clouds = new ArrayList<>();
                ArrayList<Island> islands = new ArrayList<>();
                int payloadsIterator = 2;
                for (int i = 0; i < numPlayers; i++) {
                    Map<PawnColor, Integer> entrance = new HashMap<>();
                    String nickname = payloads.get(i + payloadsIterator);
                    payloadsIterator++;
                    TowerColor towerColor = TowerColor.valueOf(payloads.get(i + payloadsIterator));
                    payloadsIterator++;
                    Integer towersNumber = Integer.parseInt(payloads.get(i + payloadsIterator));
                    payloadsIterator++;
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        Integer number = Integer.parseInt(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        entrance.put(c, number);
                    }
                    SchoolBoard sb = new SchoolBoard(towersNumber, towerColor, entrance, gameMode);
                    players.add(new Player(i, nickname, sb));
                }
                view.setPlayers(players);
                for (int i = 0; i < numPlayers; i++) {
                    Map<PawnColor, Integer> cloudStudents = new HashMap<>();
                    Integer cloudID = Integer.parseInt(payloads.get(i + payloadsIterator));
                    payloadsIterator++;
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        Integer number = Integer.parseInt(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        cloudStudents.put(c, number);
                    }
                    clouds.add(new CloudTile(i, cloudStudents));
                }
                view.setClouds(clouds);
                for (int i = 0; i < 12; i++) {
                    Map<PawnColor, Integer> islandStudents = new HashMap<>();
                    Integer islandID = Integer.parseInt(payloads.get(payloadsIterator));
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        Integer number = Integer.parseInt(payloads.get(i + payloadsIterator));
                        payloadsIterator++;
                        islandStudents.put(c, number);
                    }
                    if (i == 0) {
                        islands.add(new Island(islandStudents, null, 0, false, true));
                    } else {
                        islands.add(new Island(islandStudents, null, 0, false, false));
                    }
                }
                IslandManager islandManager = new IslandManager(islands);
                islandManager.setCurrMNPosition(0);
                view.setIslandManager(islandManager);
                //TODO MANCA GESTIONE CASO EXPERT MODE
            }*/
            case AVAILABLE_WIZARDS -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<WizardType> availableWizards = new ArrayList<>();
                for (int i = 0; i < payloads.size(); i++) {
                    availableWizards.add(WizardType.valueOf(payloads.get(i)));
                }
                view.setWizards(availableWizards);
            }
            case AVAILABLE_TOWER_COLORS -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<TowerColor> availableColors = new ArrayList<>();
                for (int i = 0; i < payloads.size(); i++) {
                    availableColors.add(TowerColor.valueOf(payloads.get(i)));
                }
                view.setTowerColors(availableColors);
            }
            case UPDATE_ASSISTANT_CARD -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                String nickname = payloads.get(0);
                String idAssistantCard = payloads.get(1);
                for (Player p : view.getPlayers()) {
                    if (p.getNickName().equals(nickname)) {
                        for (AssistantCard ac : p.getDeck().getCards()) {
                            if (ac.getId().equals(idAssistantCard)) {
                                ac.setConsumed(true);
                                p.setLastAssistantCard(ac);
                                if(p.equals(view.getPlayer())){
                                    view.getPlayer().setLastAssistantCard(ac);
                                }
                            }
                        }
                    }
                }
                view.showUsedAssistantCards();
                /*bisogna che il client non posssa selezionare carte dello stesso valore di quelle usate dai client
                 precedenti + gestire caso in cui l'ultima carta è necessariamente uguale (if deck.size()==1)-> salta check else ->check */
            }
            case UPDATE_ISLAND -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                Integer idIsland = Integer.parseInt(payloads.get(0));
                Map<PawnColor, Integer> islandStudents = new HashMap<>();
                int payloadsIterator = 1;
                for (int j = 0; j < PawnColor.values().length; j++) {
                    PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Integer number = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    islandStudents.put(c, number);
                }
                for (Island i : view.getIslandManager().getIslandList()) {
                    if (i.getIslandID() == idIsland) {
                        i.setIslandStudents(islandStudents);
                    }
                }
            }
            case UPDATE_SCHOOL_BOARD_ENTRANCE -> {
                System.out.println("Ho ricevuto "+ input);
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                String playerID = payloads.get(0); //TODO si potrebbe mandare il playernumber al posto dell'id
                int payloadsIterator = 1;
                Map<PawnColor, Integer> entrance = new HashMap<>();
                for (int j = 0; j < PawnColor.values().length; j++) {
                    PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Integer number = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    entrance.put(c, number);
                }
                for(Player p : view.getPlayers()){
                    if(playerID.equals(p.getNickName())){
                        p.getSchoolBoard().setStudentEntrance(entrance);
                        if(playerID.equals(view.getUsername())){
                            view.getPlayer().getSchoolBoard().setStudentEntrance(entrance);
                        }
                    }
                }
                /*for (Player p : view.getPlayers()) {
                    if (p.getNickName().equals(playerID)) {
                        p.getSchoolBoard().setStudentEntrance(entrance);
                        view.getPlayers().get(p.getPlayerNumber()).getSchoolBoard().setStudentEntrance(entrance);
                    }
                }*/
            }
            case UPDATE_SCHOOL_BOARD_HALL -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                String playerID = payloads.get(0);
                int payloadsIterator = 1;
                Map<PawnColor, Integer> hall = new HashMap<>();
                for (int j = 0; j < PawnColor.values().length; j++) {
                    PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Integer number = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    hall.put(c, number);
                }
                for (Player p : view.getPlayers()) {
                    if (p.getNickName().equals(playerID)) {
                        p.getSchoolBoard().setStudentHall(hall);
                        if(playerID.equals(view.getUsername())){
                            view.getPlayer().getSchoolBoard().setStudentHall(hall);
                        }
                    }
                }
            }
            case UPDATE_PROFESSORS -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                int payloadsIterator = 0;
                for (int i = 0; i < view.getPlayers().size(); i++) {
                    String nickname = payloads.get(payloadsIterator);
                    payloadsIterator++;
                    Map<PawnColor, Boolean> professorTable = new HashMap<>();
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        Boolean prof = Boolean.parseBoolean(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        professorTable.put(c, prof);
                    }
                    for (Player p : view.getPlayers()) {
                        if (p.getNickName().equals(nickname)) {
                            p.getSchoolBoard().setProfessorTable(professorTable);
                        }
                    }
                }
            }
            case UPDATE_ISLAND_LIST -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<Island> islandList = new ArrayList<>();
                int payloadsIterator = 0;
                while (payloadsIterator < payloads.size()) {
                    String islandID = payloads.get(payloadsIterator);
                    payloadsIterator++;
                    Boolean isMN = Boolean.parseBoolean(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Boolean isNET = Boolean.parseBoolean(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    TowerColor tc=null;
                    if(!(payloads.get(payloadsIterator).equals("null"))){
                        tc = TowerColor.valueOf(payloads.get(payloadsIterator));
                    }
                    payloadsIterator++;
                    Integer tn = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Map<PawnColor, Integer> islandMap = new HashMap<>();
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        islandMap.put(c, num);
                    }
                    islandList.add(new Island(Integer.parseInt(islandID), islandMap, tc, tn, isNET, isMN));
                }
                view.getIslandManager().setIslandList(islandList);
            }
            case UPDATE_CLOUDTILES -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<CloudTile> clouds = new ArrayList<>();
                //Map<PawnColor, Integer> map = new HashMap<>();
                int payloadsIterator = 0;
                while (payloadsIterator < payloads.size()) {
                    Map<PawnColor, Integer> map = new HashMap<>(){{
                        for(PawnColor c : PawnColor.values()){
                            put(c,0);
                        }
                    }};
                    Integer ctID = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    for (int j = 0; j < PawnColor.values().length; j++) {
                        PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                        payloadsIterator++;
                        map.replace(c, num);
                    }
                    clouds.add(new CloudTile(ctID,map));
                }
                view.setClouds(clouds);
            }
            case SETUP_PLAYERS -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                ArrayList<Player> players = new ArrayList<>();
                for (int i = 0; i < payloads.size(); i++) {
                    String nickname = payloads.get(i);
                    i++;
                    Integer playerNumber = Integer.parseInt(payloads.get(i));
                    i++;
                    WizardType wt = WizardType.valueOf(payloads.get(i));
                    i++;
                    TowerColor tc = TowerColor.valueOf(payloads.get(i));
                    Player p = new Player();
                    p.setNickName(nickname);
                    p.setPlayerNumber(playerNumber);
                    p.setDeck(new Deck(wt));
                    p.getSchoolBoard().setTowersColor(tc);
                    players.add(p);
                    if(p.getNickName().equals(view.getUsername())){
                        view.setPlayer(p);
                    }
                }
                view.setPlayers(players);
            }
            case MODEL_CREATED -> {
                view.showTable();
            }

        }
    }
}