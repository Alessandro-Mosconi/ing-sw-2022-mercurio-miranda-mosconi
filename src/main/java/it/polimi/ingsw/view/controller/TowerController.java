package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
            Shape tower = new Rectangle(0.0, 0.0, 100, 100);
            tower.setFill(TowerColor.getColor(towerColor));
            tower.setStroke(Color.BLACK);
            tower.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(100);
            button.setPrefWidth(100);

            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    tower.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)");
                }
            });

            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    tower.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
                }
            });

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    System.out.println(tower + ") tower clicked");
                    View view = GuiStarter.getCurrentApplication().getClient().getView();
                    view.getPlayer().getSchoolBoard().setTowersColor(towerColor);
                    view.setTowerColor(towerColor);
                    //todo perché ci serve questo messagetype?
                    //view.setMessageType(MessageType.SETTINGS);
                    view.prepareMessage();
                }
            });

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(tower);
            anchorPane.getChildren().add(button);

            towerContainer.getChildren().add(anchorPane);
        }
    }
}
