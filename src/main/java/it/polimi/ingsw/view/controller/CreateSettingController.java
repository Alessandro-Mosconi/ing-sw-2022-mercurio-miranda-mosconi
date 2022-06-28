package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateSettingController {
    public TextField gameMode;
    public TextField numOfPlayers;
    public TextField gameID;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private GuiStarter currentApplication;

    public void createGame(ActionEvent actionEvent) throws Exception {

        if ((gameID.getText().length() == 0) || (gameMode.getText().length() == 0) || (numOfPlayers.getText().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (Integer.parseInt(numOfPlayers.getText())>3){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Max number of players allowed is 3", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        String game = gameMode.getText();
        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setIdGame(gameID.getText());
        view.setPlayerNumber(Integer.parseInt(numOfPlayers.getText()));
        view.setGamemode(GameMode.valueOf(gameMode.getText()));

        primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        view.prepareMessage();
    }

}
