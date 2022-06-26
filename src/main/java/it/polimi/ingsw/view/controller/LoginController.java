package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.GameMode;
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
    private Text actiontarget;
    @FXML
    protected TextField serverPort;
    @FXML
    protected TextField serverIP;
    @FXML

    public void checkAndConnect(){
        if (username.getText().length()==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //todo add check for serverIP and serverPort
        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setUsername(username.getText());
        view.setServerIP(serverIP.getText());
        view.setServerPort(Integer.parseInt(serverPort.getText()));

        currentApplication.getClient().connectGUI();
    }
    public void createGameButton(ActionEvent actionEvent) throws Exception {
        checkAndConnect();
        currentApplication.switchToCreateSettings();
    }

    public void joinGameButton(ActionEvent actionEvent) {
        checkAndConnect();
        currentApplication.switchToJoinSettings();
    }
    /**
     * Method automatically called when the scene is loaded.
     * Initializes the scene.
     */
    /*
    public void initialize()
    {
        label.setText("");
    }


    public void buttonClicked(ActionEvent actionEvent)
    {
        label.setText(textField.getText());
    }
    */
}
