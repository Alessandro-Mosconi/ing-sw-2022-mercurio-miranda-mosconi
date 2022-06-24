package it.polimi.ingsw.view;

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
        view.connect();


        //final Stage dialog = new Stage();
        primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        /*
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);

        Scene dialogScene = new Scene(popup, 300, 200);
        dialog.setScene(dialogScene);*/

        //this is to check if the username is correct
        if ((username != null) && (username.getText() != "")) {
            //todo non capisco perché mi dia null come risultato..
            scene = new Scene(root, 660, 370);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();
        }
        else{
            actiontarget.setText("Invalid username");
            }



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
