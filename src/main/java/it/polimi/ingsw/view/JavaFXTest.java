package it.polimi.ingsw.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.awt.*;

import java.io.IOException;

public class JavaFXTest extends Application implements Runnable {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Eriantys");

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 660, 370);


        primaryStage.setScene(scene);

        primaryStage.sizeToScene();
        primaryStage.show();
    }

    @Override
    public void run() {
        main(null);
    }
}
