package it.polimi.ingsw.network;

import com.google.gson.Gson;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                view.getPlayer().setNickName(view.getUsername());
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
            }
            case SETTINGS -> {
                view.settings();
                msg_out.setType(MessageType.SETTINGS);
                payloads.add(view.getPlayer().getDeck().getWizard().toString());
                payloads.add(view.getTowerColor().toString());
                nextPhase = Phase.PLANNING;
            }
            case PLANNING -> {
                view.chooseAssistantCard();
                msg_out.setType(MessageType.ASSISTANT_CARD);
                payloads.add(view.getChosenAssistantCard().getValue().toString());
                view.setCardUsed(false);
                nextPhase = Phase.CHOOSING_FIRST_MOVE;
            }
            case CHOOSING_FIRST_MOVE -> {
                view.choosePawnMove();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_SECOND_MOVE;
                } else if (view.getMessageType().equals(MessageType.CHOSEN_CHARACTER_CARD)){
                    return characterCardToSend();
                }

            }
            case CHOOSING_SECOND_MOVE -> {
                view.choosePawnMove();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_THIRD_MOVE;
                } else if (view.getMessageType().equals(MessageType.CHOSEN_CHARACTER_CARD)){
                    return characterCardToSend();
                }
            }
            case CHOOSING_THIRD_MOVE -> {
                view.choosePawnMove();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.PAWN_MOVE)) {
                    payloads.add(view.getColorToMove().toString());
                    payloads.add(view.getDestination().toString());
                    nextPhase = Phase.CHOOSING_MN_SHIFT;
                }else if (view.getMessageType().equals(MessageType.CHOSEN_CHARACTER_CARD)){
                    return characterCardToSend();
                }

            }
            case CHOOSING_MN_SHIFT -> {
                view.chooseMNmovement();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.MN_SHIFT)) {
                    payloads.add(view.getMN_shift().toString());
                    nextPhase = Phase.CHOOSING_CT;
                } else if (view.getMessageType().equals(MessageType.CHOSEN_CHARACTER_CARD)){
                        return characterCardToSend();
                    }
            }
            case CHOOSING_CT -> {
                view.chooseCT();
                msg_out.setType(view.getMessageType());
                if (view.getMessageType().equals(MessageType.CHOSEN_CT)) {
                    payloads.add(view.getChosenCloudPos().toString());
                    nextPhase = Phase.PLANNING;
                } else if (view.getMessageType().equals(MessageType.CHOSEN_CHARACTER_CARD)){
                    return characterCardToSend();
                }
                for(Player p : view.getPlayers()){
                    p.setLastAssistantCard(null);
                }//resetta le lastAssistantCards usate dai player
            }
            case WAITING -> {
                msg_out.setType(MessageType.WAITING);
                msg_out.fill("WAITING");
            }
        }
        phase = Phase.WAITING;
        msg_out.fill(payloads);
        System.out.println("sending... " + msg_out.toSend());
        return msg_out.toSend();
    }

    private String characterCardToSend() {
        Message cardMsg = new Message();
        ArrayList<String> payloads = new ArrayList<>();
        cardMsg.setType(MessageType.CHOSEN_CHARACTER_CARD);
        payloads.add((view.getPlayer().getNickName()));
        payloads.add(String.valueOf(view.getChosenCharacterCard().getID()));
        switch (view.getChosenCharacterCard().getID()){
            case 1 ->{
                payloads.add(String.valueOf(view.getParameter().getChosenColor()));
                payloads.add(String.valueOf(view.getParameter().getIsland().getIslandID()));
            }
            case 3, 5 ->{
                payloads.add(String.valueOf(view.getParameter().getIsland().getIslandID()));
            }
            case 7, 10 ->{
                Map<PawnColor,Integer> map1 = view.getParameter().getColorMap1();
                Map<PawnColor,Integer> map2 = view.getParameter().getColorMap2();
                for(PawnColor pc : PawnColor.values()){
                    payloads.add(String.valueOf(pc));
                    payloads.add(String.valueOf(map1.get(pc)));
                }
                for(PawnColor pc : PawnColor.values()){
                    payloads.add(String.valueOf(pc));
                    payloads.add(String.valueOf(map2.get(pc)));
                }
            }
            case 9, 11, 12 ->{
                payloads.add(String.valueOf(view.getParameter().getChosenColor()));
            }
            //casi senza parametro 2-4-6-8
        }
        cardMsg.fill(payloads);
        nextPhase = phase;
        phase = Phase.WAITING;
        return cardMsg.toSend();
    }

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
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                GameMode gm = GameMode.valueOf(String.valueOf(payloads.get(0)));
                view.setGamemode(gm);
                phase = Phase.WAITING;
                nextPhase = Phase.SETTINGS;
            }
            case LOBBY_UPDATED -> {
                System.out.println("Lobby updated ok");
            }
            case ASK_FOR_SETTINGS -> {
                phase = Phase.SETTINGS;
            }
            case WAIT -> {
                phase = Phase.WAITING;
                System.out.println("ok aspetto\n");
            }
            case IS_YOUR_TURN, ACK, CARD_ACTIVATED -> {
                phase = nextPhase;
            }
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
                String playerID = payloads.get(0);
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
            case GAME_ENDED -> {
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                String winnerID = payloads.get(0);
                phase = Phase.END_GAME;
                view.showEndGameWindow(winnerID);
                //TODO mandare al server il messaggio che la partita finisce e chiudere le socket?
            }
            case UPDATE_WALLET ->{
                payloads = gson.fromJson(msg_in.getPayload(), ArrayList.class);
                String playerID = payloads.get(0);
                Integer coins = Integer.parseInt(payloads.get(1));
                for(Player p : view.getPlayers()){
                    if(p.getNickName().equals(playerID)){
                        p.setWallet(coins);
                        if(p.getNickName().equals(view.getUsername())){
                            view.getPlayer().setWallet(coins);
                        }
                    }
                }
            }
            case UPDATE_MAX_SHIFT ->{
                payloads = gson.fromJson(msg_in.getPayload(),ArrayList.class);
                if(payloads.get(0).equals(view.getPlayer().getNickName())){
                    view.getPlayer().setMaxShift(view.getPlayer().getMaxShift()+2);
                }
            }
            case PRICE_INCREASE -> {
                payloads = gson.fromJson(msg_in.getPayload(),ArrayList.class);
                Integer cardID = Integer.parseInt(payloads.get(0));
                for(CharacterCard cc : view.getCharacterCards()){
                    if(cc.getID().equals(cardID)){
                        cc.increasePrice();
                    }
                }
            }
            case INIT_CHARACTER_CARDS -> {
                payloads = gson.fromJson(msg_in.getPayload(),ArrayList.class);
                int payloadsIterator = 0;
                while(payloadsIterator<payloads.size()){
                    Integer id = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Integer price = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    switch (id){
                        case 1 ->{
                            Map<PawnColor,Integer> map = new HashMap<>();
                            for(int j=0; j<PawnColor.values().length; j++){
                                PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                map.put(c,num);
                            }
                            CharacterCard1 cc1 = new CharacterCard1();
                            cc1.setStudents(map);
                            String caption = "Take 1 Student from this card and place it on an Island of your choice.\nThen, draw a new Student from the Bag and place it on this card.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc1,caption));
                        }
                        case 2 ->{
                            CharacterCard2 cc2 = new CharacterCard2();
                            String caption = "During this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc2,caption));
                        }
                        case 3 ->{
                            CharacterCard3 cc3 = new CharacterCard3();
                            String caption = "Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\nMother Nature will still move and the Island where she ends her movement will also be resolved.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc3,caption));
                        }
                        case 4 ->{
                            CharacterCard4 cc4 = new CharacterCard4();
                            String caption = "You may move Mother Nature up to 2 additional Islands than is indicated by the Assistant Card you've played.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc4,caption));
                        }
                        case 5 ->{
                            CharacterCard5 cc5 = new CharacterCard5();
                            String caption = "Place a No Entry Tile on an Island of your choice.\nThe first time Mother Nature ends her movement there, put the No Entry Tile back onto this card DO NOT calculate influence on that Island, or place any Towers.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc5,caption));
                        }
                        case 6 ->{
                            CharacterCard6 cc6 = new CharacterCard6();
                            String caption = "When resolving a Conquering on an Island, Towers do not count towards influence.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc6,caption));
                        }
                        case 7 ->{
                            Map<PawnColor,Integer> map = new HashMap<>();
                            for(int j=0; j<PawnColor.values().length; j++){
                                PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                map.put(c,num);
                            }
                            CharacterCard7 cc7 = new CharacterCard7();
                            cc7.setStudents(map);
                            String caption = "You may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc7,caption));
                        }
                        case 8 ->{
                            CharacterCard8 cc8 = new CharacterCard8();
                            String caption = "During the influence calculation this turn, you count as having 2 more influence.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc8,caption));
                        }
                        case 9 ->{
                            CharacterCard9 cc9 = new CharacterCard9();
                            String caption = "Choose a color of Student: during the influence calculation this turn, that color adds no influence.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc9,caption));
                        }
                        case 10 ->{
                            CharacterCard10 cc10 = new CharacterCard10();
                            String caption = "You may exchange up to 2 Students between your Entrance and your Dining Room.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc10,caption));
                        }
                        case 11 ->{
                            Map<PawnColor,Integer> map = new HashMap<>();
                            for(int j=0; j<PawnColor.values().length; j++){
                                PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                                payloadsIterator++;
                                map.put(c,num);
                            }
                            CharacterCard11 cc11 = new CharacterCard11();
                            cc11.setStudents(map);
                            String caption = "Take 1 Student from this card and place it in your Dining Room.\nThen, draw a new Student from the Bag and place it on this card.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc11,caption));
                        }
                        case 12 ->{
                            CharacterCard12 cc12 = new CharacterCard12();
                            String caption = "Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the Bag.\n If any player has fewer than 3 Students of that type, return as many Students as they have.";
                            view.getCharacterCards().add(new CharacterCard(id,price,cc12,caption));
                        }
                    }
                }
            }
            case UPDATE_CARD_STUDENTS -> {
                payloads = gson.fromJson(msg_in.getPayload(),ArrayList.class);
                int payloadsIterator = 0;
                Integer idCard = Integer.parseInt(payloads.get(payloadsIterator));
                payloadsIterator++;
                Map<PawnColor,Integer> map = new HashMap<>();
                for(int j=0; j<PawnColor.values().length; j++){
                    PawnColor c = PawnColor.valueOf(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    Integer num = Integer.parseInt(payloads.get(payloadsIterator));
                    payloadsIterator++;
                    map.put(c,num);
                }
                for(CharacterCard cc : view.getCharacterCards()){
                    if(cc.getID().equals(idCard)){
                        if (cc.getCardBehavior() instanceof CharacterCard1){
                            ((CharacterCard1) cc.getCardBehavior()).setStudents(map);
                        }
                        else if(cc.getCardBehavior() instanceof CharacterCard7){
                            ((CharacterCard7) cc.getCardBehavior()).setStudents(map);
                        }
                        else if(cc.getCardBehavior() instanceof CharacterCard11){
                            ((CharacterCard11) cc.getCardBehavior()).setStudents(map);
                        }
                    }
                }
            }
            case SOMEONE_ACTIVATED_AN_EFFECT -> {
                payloads = gson.fromJson(msg_in.getPayload(),ArrayList.class);
                String currUser = payloads.get(0);
                Integer cardID = Integer.parseInt(payloads.get(1));
                view.setCardUsed(true);
                view.setActiveEffect(currUser + " used the character card n." + cardID);
            }
            case EFFECT_ENDED -> {
                view.setCardUsed(false);
            }
        }
    }

}