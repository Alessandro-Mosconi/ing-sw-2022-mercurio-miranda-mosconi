package it.polimi.ingsw.view;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.NetworkHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiStarter extends Application{
    private static GuiStarter currentApplication;
    private Client client;
    private Stage primaryStage;

    public static GuiStarter getCurrentApplication() {
        return currentApplication;
    }
    public Client getClient() {
        return client;
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/

    @Override
    public void start(Stage primaryStage) {
        this.client = new Client();
        GUI gui = new GUI();
        client.setView(gui);
        gui.setClient(client);
        gui.setGuiStarter(this);
        currentApplication = this;

        primaryStage.setTitle("Eriantys");
        this.primaryStage = primaryStage;
        switchToLoginScene();
        primaryStage.show();
    }

    public void switchToLoginScene()
    {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Login2");
        primaryStage.sizeToScene();
    }

    public void switchToCreateSettings()
    {
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
    }

    public void switchToJoinSettings()
    {
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
    }

    public void switchToWizardsScene() {

        Platform.runLater(() ->{
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/WizardChoise.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene sc = new Scene(root);
        primaryStage.setScene(sc);
        primaryStage.sizeToScene();

        });

    }

    public void switchToLobbyScene()
    {
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

    public void showError(String error){
        System.out.println("mmmh");
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.ERROR, "This error occured: " + error, ButtonType.OK);
            alert.showAndWait();
        });
    }
}
