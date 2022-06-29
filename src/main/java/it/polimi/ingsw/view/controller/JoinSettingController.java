package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class JoinSettingController {

    public TextField gameID;

    public void joinGame(ActionEvent actionEvent) throws Exception {

        if ((gameID.getText().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insert parameters!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        GuiStarter currentApplication = GuiStarter.getCurrentApplication();
        View view = currentApplication.getClient().getView();
        view.setIdGame(gameID.getText());
        view.setMessageType(MessageType.JOIN_MATCH);
        view.prepareMessage();
    }

}
