package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class TowerController {

    @FXML
    private FlowPane towerContainer;

    public void initialize() {
        ArrayList<TowerColor> towers = GuiStarter.getCurrentApplication().getClient().getView().getTowerColors();

        for (TowerColor towerColor : towers) {
            //tower shape creation
            Shape tower = new Rectangle(0.0, 0.0, 100, 100);
            tower.setFill(TowerColor.getColor(towerColor));
            tower.setStroke(Color.BLACK);
            tower.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
            //button creation
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(100);
            button.setPrefWidth(100);
            button.setOnMouseEntered(e -> tower.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)"));
            button.setOnMouseExited(e -> tower.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)"));
            button.setOnAction(event -> {
                System.out.println(towerColor + ") tower clicked");

                View view = GuiStarter.getCurrentApplication().getClient().getView();
                view.getPlayer().getSchoolBoard().setTowersColor(towerColor);
                view.setTowerColor(towerColor);
                view.prepareMessage();
            });

            //shape and button overlay
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(tower);
            anchorPane.getChildren().add(button);

            //adding structure to tower container
            towerContainer.getChildren().add(anchorPane);
            GuiStarter.getCurrentApplication().getClient().getView().setMessageType(MessageType.SETTINGS);
        }
    }
}
