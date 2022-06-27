package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateSettingController {
    public TextField gameMode;
    public TextField numOfPlayers;
    public TextField gameID;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private GuiStarter currentApplication;

    public void createGame(ActionEvent actionEvent) throws Exception {

            if ((gameID.getText().length()==0) || (gameMode.getText().length()==0) || (numOfPlayers.getText().length()==0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setIdGame(gameID.getText());
        view.setPlayerNumber(Integer.parseInt(numOfPlayers.getText()));
        view.setGamemode(GameMode.valueOf(gameMode.getText()));

        primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        view.prepareLogin();
    }

}
