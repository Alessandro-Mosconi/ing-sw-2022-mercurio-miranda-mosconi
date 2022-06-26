package it.polimi.ingsw.view;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.MessageType;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinSettingController {
    public TextField gameID;


    private GuiStarter currentApplication;


    public void joinGame(ActionEvent actionEvent) throws Exception {

            if ((gameID.getText().length()==0)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

        currentApplication = GuiStarter.getCurrentApplication();
        View view = (GUI) currentApplication.getClient().getView();
        view.setIdGame(gameID.getText());
        //view.setPlayerNumber(Integer.parseInt(numOfPlayers.getText()));
        //view.setGamemode(GameMode.valueOf(gameMode.getText()));
        view.setPlayer(new Player());
        view.setMessageType(MessageType.JOIN_MATCH);
        //primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        currentApplication.getClient().getNet().prepare_msg();

    }

}
