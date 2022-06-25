package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameMode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public void createGameButton(ActionEvent actionEvent) throws Exception {
        //this is to debug
        System.out.println(username.getText());

        try {
            root = FXMLLoader.load(getClass().getResource("/GameSettings.fxml"));
            //popup = FXMLLoader.load(getClass().getResource("/CreateDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setUsername(username.getText());
        view.setServerIP(serverIP.getText());
        view.setServerPort(Integer.parseInt(serverPort.getText()));

        //final Stage dialog = new Stage();
        primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //this is to check if the username is correct
        Platform.runLater(()->{
            if ((username != null) && (username.getText() != "")) {
                scene = new Scene(root, 660, 370);
                primaryStage.setScene(scene);
                primaryStage.sizeToScene();
                primaryStage.show();
            }
            else{
                actiontarget.setText("Invalid username");
            }
        });

        currentApplication.getClient().connectGUI();
    }

    public void joinGameButton(ActionEvent actionEvent) {
        //qui potrei chiamare direttamente il metodo di GUIStarter che poi cambia la scena
        try {
            root = FXMLLoader.load(getClass().getResource("/GameSettings.fxml"));
            //popup = FXMLLoader.load(getClass().getResource("/CreateDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
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
