package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameMode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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

    public TextField getUsername() {
        return username;
    }

    public void submitGame(ActionEvent actionEvent) {
        System.out.println("suca");
        currentApplication = GuiStarter.getCurrentApplication();
        View view = currentApplication.getClient().getView();
        view.setGamemode(GameMode.valueOf(gameMode.getText()));
        view.setIdGame(gameID.getText());
        view.setPlayerNumber(Integer.valueOf(numOfPlayers.getText()));

        view.preparelogin();
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
