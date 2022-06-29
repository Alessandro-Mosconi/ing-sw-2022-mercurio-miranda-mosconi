package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.regex.Pattern;

public class LoginController {

    private GuiStarter currentApplication;
    @FXML
    protected TextField username;
    @FXML
    protected TextField serverPort;
    @FXML
    protected TextField serverIP;

    @FXML

    public boolean checkAndConnect() {

        currentApplication = GuiStarter.getCurrentApplication();
        int serverPortNum;
        Pattern pattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        String error = "";


        if ((username.getText().length() == 0) || (serverIP.getText().length() == 0) || (serverPort.getText().length() == 0)) {
            currentApplication.showError("Insert parameters!");
            return false;
        }

        if (!pattern.matcher(serverIP.getText()).matches()) {
            error = "Not valid IPv4 address\n";
        }
        try {
            serverPortNum = Integer.parseInt(serverPort.getText());
            if (serverPortNum >= 65535 || serverPortNum <= 0) {
                error = error + "Port must be an Integer in the range [1, 65535]";
            }
        } catch (IllegalArgumentException e) {
            currentApplication.showError("Port must be a number");
            return false;
        }

        if (!error.equals("")) {
            currentApplication.showError(error);
            return false;
        }

        View view = currentApplication.getClient().getView();
        view.setUsername(username.getText());
        view.setServerIP(serverIP.getText());
        view.setServerPort(Integer.parseInt(serverPort.getText()));
        currentApplication.getClient().connectGUI();
        return true;
    }

    public void createGameButton(ActionEvent actionEvent) {
        if (checkAndConnect()) {
            currentApplication.getClient().getView().setMessageType(MessageType.CREATE_MATCH);
            currentApplication.switchToCreateSettings();
        }
    }

    public void joinGameButton(ActionEvent actionEvent) {
        if (checkAndConnect()) {
            currentApplication.getClient().getView().setMessageType(MessageType.JOIN_MATCH);
            currentApplication.switchToJoinSettings();
        }
    }
}
