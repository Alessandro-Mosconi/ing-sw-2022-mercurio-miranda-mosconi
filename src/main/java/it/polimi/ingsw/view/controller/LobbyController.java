package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.view.GuiStarter;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class LobbyController {
    @FXML
    private VBox playersContainer;
    @FXML
    private Text titleWaiting;

    public void initialize() {

        titleWaiting.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                titleWaiting.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 15, 0.2, 0, 0)");
            }
        });

        titleWaiting.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                titleWaiting.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
            }
        });

        ArrayList<String> array = GuiStarter.getCurrentApplication().getClient().getView().getPlayersUsername();
        for (String player : array) {
            Text nickname = new Text();

            nickname.setText(player);
            nickname.setFill(Color.WHITE);
            nickname.setFont(Font.font("System", FontWeight.BOLD, 20));
            playersContainer.getChildren().add(nickname);
        }


        //GuiStarter.getCurrentApplication().getClient().getNet().prepare_msg();
    }
}


