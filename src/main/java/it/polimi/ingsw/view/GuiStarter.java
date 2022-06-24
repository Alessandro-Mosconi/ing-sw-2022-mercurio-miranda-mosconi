package it.polimi.ingsw.view;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.NetworkHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.awt.*;

import java.io.IOException;

public class GuiStarter extends Application{
    private static GuiStarter currentApplication;
    private Client client;

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
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginScene.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root, 660, 370);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
