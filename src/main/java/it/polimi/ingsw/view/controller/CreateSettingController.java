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
    public TextField gameID;
    private Stage primaryStage;

    private GuiStarter currentApplication=GuiStarter.getCurrentApplication();

    public void createGame(ActionEvent actionEvent) throws Exception {

        View view = (GUI) currentApplication.getClient().getView();
        System.out.println("1" + gameID.getText());
        System.out.println(" 2 " + view.getGamemode());
        System.out.println(" 23 " + view.getPlayerNumber());
        if ((gameID.getText().length() == 0) || view.getGamemode()==null || view.getPlayerNumber()==null || (view.getPlayerNumber()!=3 && view.getPlayerNumber()!=2)) {
            currentApplication.showError("missing or invalid parameter");
            return;
        }

        currentApplication = GuiStarter.getCurrentApplication();
        view.setIdGame(gameID.getText());
        view.prepareMessage();
    }

    public void easyButton(ActionEvent actionEvent) throws Exception {
        View view = (GUI) currentApplication.getClient().getView();
        view.setGamemode(GameMode.easy);
    }

    public void expertButton(ActionEvent actionEvent) throws Exception {
        View view = (GUI) currentApplication.getClient().getView();
        view.setGamemode(GameMode.expert);
    }

    public void twoPlayer(ActionEvent actionEvent) throws Exception {
        View view = (GUI) currentApplication.getClient().getView();
        view.setPlayerNumber(2);
    }

    public void threePlayer(ActionEvent actionEvent) throws Exception {
        View view = (GUI) currentApplication.getClient().getView();
        view.setPlayerNumber(3);
    }


}
