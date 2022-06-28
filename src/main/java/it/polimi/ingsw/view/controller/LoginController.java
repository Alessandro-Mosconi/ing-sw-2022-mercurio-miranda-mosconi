package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
    public TextField gameMode;
    public TextField numOfPlayers;
    public TextField gameID;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private GuiStarter currentApplication;
    @FXML
    protected TextField username;
    @FXML
    protected TextField serverPort;
    @FXML
    protected TextField serverIP;
    @FXML

    public void checkAndConnect(){
        if ((username.getText().length()==0) || (serverIP.getText().length()==0) || (serverPort.getText().length()==0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setUsername(username.getText());
        view.setServerIP(serverIP.getText());
        view.setServerPort(Integer.parseInt(serverPort.getText()));
        currentApplication.getClient().connectGUI();
    }
    public void createGameButton(ActionEvent actionEvent) throws Exception {
        checkAndConnect();
        currentApplication.getClient().getView().setMessageType(MessageType.CREATE_MATCH);
        currentApplication.switchToCreateSettings();
    }

    public void joinGameButton(ActionEvent actionEvent) {
        checkAndConnect();
        currentApplication.getClient().getView().setMessageType(MessageType.JOIN_MATCH);
        currentApplication.switchToJoinSettings();
    }
}
