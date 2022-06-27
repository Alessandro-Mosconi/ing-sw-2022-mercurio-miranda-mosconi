package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Phase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiStarter extends Application{
    private static GuiStarter currentApplication;
    private Client client;
    private Stage primaryStage;
    private Stage characterStage;

    public static GuiStarter getCurrentApplication() {
        return currentApplication;
    }
    public Client getClient() {
        return client;
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/

    //usati per testare la gui, andranno cancellati
/*
    public void test1(){
        GUI gui = (GUI) currentApplication.getClient().getView();
        gui.setPhase(Phase.CHOOSING_CT);
        Game game = new Game();

        game.setNumberOfPlayers(3);


        game = new Game(3, "g1", GameMode.expert);
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.grey);
        p3.getSchoolBoard().setTowersColor(TowerColor.black);
        p1.setDeck(new Deck(WizardType.wizard1));
        p2.setDeck(new Deck(WizardType.wizard2));
        p3.setDeck(new Deck(WizardType.wizard3));
        players.add(p1);
        players.add(p2);
        players.add(p3);

        game.setPlayers(players);

        game.setupGame();

        game.setPlayers(players);
        gui.setPlayers(game.getPlayers());

        gui.setPlayer(game.getPlayers().get(0));
        gui.setGamemode(game.getGameMode());
        gui.setClouds(game.getCloudTiles());
        gui.setIslandManager(game.getIslandManager());
        gui.setPlayerNumber(3);
        gui.setUsername(game.getPlayers().get(0).getNickName());
    }
    public void test2(){
        GUI gui = (GUI) currentApplication.getClient().getView();
        gui.setPhase(Phase.CHOOSING_CT);
        Game game = new Game();

        game.setNumberOfPlayers(3);


        game = new Game(3, "g1", GameMode.expert);
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        p3.getSchoolBoard().setTowersColor(TowerColor.grey);
        p1.setDeck(new Deck(WizardType.wizard1));
        p2.setDeck(new Deck(WizardType.wizard2));
        p3.setDeck(new Deck(WizardType.wizard3));
        players.add(p1);
        players.add(p2);
        players.add(p3);

        game.setPlayers(players);

        game.setupGame();

        game.setPlayers(players);
        gui.setPlayers(game.getPlayers());

for(CharacterCard card : game.getChosenCharacterCards())
{
    card.setCaption("questa sarà la descrizione");
}
        gui.setCharacterCards(game.getChosenCharacterCards());
        gui.setPlayer(game.getPlayers().get(0));
        gui.setGamemode(game.getGameMode());
        gui.setClouds(game.getCloudTiles());
        gui.setIslandManager(game.getIslandManager());
        gui.setPlayerNumber(3);
        gui.setUsername(game.getPlayers().get(0).getNickName());
    }
    public void test3(){
        GUI gui = (GUI) currentApplication.getClient().getView();
        gui.setPhase(Phase.CHOOSING_FIRST_MOVE);
        Game game = new Game();

        game.setNumberOfPlayers(3);


        game = new Game(3, "g1", GameMode.expert);
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Franco", new Deck(), 1, new SchoolBoard());
        Player p2 = new Player("gigi", new Deck(), 2, new SchoolBoard());
        Player p3 = new Player("pol", new Deck(), 3, new SchoolBoard());
        p1.getSchoolBoard().setTowersColor(TowerColor.white);
        p2.getSchoolBoard().setTowersColor(TowerColor.black);
        p3.getSchoolBoard().setTowersColor(TowerColor.grey);
        p1.setDeck(new Deck(WizardType.wizard1));
        p2.setDeck(new Deck(WizardType.wizard2));
        p3.setDeck(new Deck(WizardType.wizard3));
        players.add(p1);
        players.add(p2);
        players.add(p3);

        game.setPlayers(players);

        game.setupGame();



        game.setPlayers(players);
        gui.setPlayers(game.getPlayers());

        for(CharacterCard card : game.getChosenCharacterCards())
        {
            card.setCaption("questa sarà la descrizione");
        }
        AssistantCard card = new AssistantCard(3, 5);
        p2.setLastAssistantCard(card);
        AssistantCard card2 = new AssistantCard(5, 4);
        p3.setLastAssistantCard(card2);

        CharacterCard7 card7 = new CharacterCard7();
        Map<PawnColor, Integer> map = new HashMap<>();
        for(PawnColor color: PawnColor.values())
            map.put(color, 3);
        p1.getSchoolBoard().setStudentHall(map);

        Map<PawnColor, Integer> map3 = new HashMap<>();
        for(PawnColor color: PawnColor.values())
            map3.put(color, 2);

        card7.setStudents(map3);

        //map.replace(PawnColor.green, 2);
        //map.replace(PawnColor.yellow, 1);
        //map.replace(PawnColor.red, 1);
        //card12.setStudents(map);
        CharacterCard charCard = new CharacterCard (7,1, card7);
        gui.setChosenCharacterCard(charCard);
        game.getIslandManager().getIslandList().get(9).setNoEntryTile(true);
        gui.setWinnerUsername("pol");
        gui.setCharacterCards(game.getChosenCharacterCards());
        gui.setPlayer(game.getPlayers().get(0));
        gui.setGamemode(game.getGameMode());
        gui.setClouds(game.getCloudTiles());
        gui.setIslandManager(game.getIslandManager());
        gui.setPlayerNumber(3);
        gui.setUsername(game.getPlayers().get(0).getNickName());
    }

 */




    @Override
    public void start(Stage primaryStage) {
        this.client = new Client();
        GUI gui = new GUI();
        client.setView(gui);
        gui.setClient(client);
        gui.setGuiStarter(this);
        currentApplication = this;


        //usati per testare la gui, andranno cancellati
        //test3();

        primaryStage.getIcons().add(new Image("/assets/logo-cranio-creation.png"));
        primaryStage.setTitle("Eriantys");
        this.primaryStage = primaryStage;
        switchToLoginScene();
        primaryStage.show();
    }

    public void switchToLoginScene()
    {

        Platform.runLater(() ->{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        //primaryStage.setTitle("Login");
        primaryStage.sizeToScene();

    });
    }

    public void switchToCreateSettings()
    {

        Platform.runLater(() ->{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/CreateSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        //primaryStage.setTitle("Login2");
        primaryStage.sizeToScene();

        });
    }

    public void switchToJoinSettings()
    {
        Platform.runLater(() ->{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/JoinSettings.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        //primaryStage.setTitle("Login2");
        primaryStage.sizeToScene();
        });
    }

    public void switchToWizardsScene() {
        Platform.runLater(() ->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/WizardChoice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();
        });

    }

    public void switchToTowerScene() {

        Platform.runLater(() ->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/TowerChoice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });

    }

    public void switchToDeckScene() {

        Platform.runLater(() ->{
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/AssistantDeck.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene sc = new Scene(root);
        stage.setScene(sc);
        stage.sizeToScene();
        //stage.setTitle("Deck");
        stage.initModality(Modality.WINDOW_MODAL);
        //stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();

        });
    }

    public void switchToLobbyScene() {
        Platform.runLater(() ->{

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/LobbyWaiting.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();
    });
    }

    public void switchToMainBoard()
    {

        Platform.runLater(() ->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/MainBoard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });

    }

    public void showError(String error){
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.ERROR, "This error occured: " + error, ButtonType.OK);
            alert.showAndWait();
        });
    }


    public void choseCT(){
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nChose a cloud and click on it", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/nuvola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    public void chooseMNmovement() {
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nClick on the island Mother Nature should go\nMax " + getClient().getView().getPlayer().getMaxShift() + " shift", ButtonType.OK);
//TODO vedere se ci va isola o island
            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    public void choseIsland() {
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.NONE, "NOW CHOSE AN ISLAND\nClick on the island you want to apply the effect", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    public void choosePawnMove() {
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nChoose a destination click on your SchoolBoard Hall or on the Island you choose:", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    public void showEndGameWindow(){
        Platform.runLater(() ->{
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/WinnerScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });
    }

    public void useCharacterCard() {
        Platform.runLater(() ->{
            Stage stage = new Stage();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/CharacterCardScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene sc = new Scene(root);
            stage.setScene(sc);
            characterStage = stage;
            stage.sizeToScene();
            stage.setTitle("CharacterCard");
            stage.initModality(Modality.WINDOW_MODAL);
            //stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        });
    }

    public void closeCharacterStage() {

        Platform.runLater(() ->{
            characterStage.close();
        });
    }
}
