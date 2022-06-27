package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class DeckController {
    @FXML
    private FlowPane assistantContainer;

    public void initialize() {
        ArrayList<Integer> array = new ArrayList<>();

        for (AssistantCard card : GuiStarter.getCurrentApplication().getClient().getView().getPlayer().getDeck().getCards())
            array.add(card.getValue());

        for (int i : array) {
            String path = "assets/Assistenti/2x/Assistente("+(i)+").png";
            ImageView im2 = new ImageView(path);
            im2.setFitHeight(200);
            im2.setFitWidth(150);
            im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(200);
            button.setPrefWidth(130);

            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    im2.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)");
                }
            });

            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
                }
            });

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(i + ") card clicked");

                    //todo perché è prima i e poi i-1?
                    View view = GuiStarter.getCurrentApplication().getClient().getView();
                    view.setChosenAssistantCard(view.getPlayer().getDeck().getCards().get(i-1));
                    view.prepareMessage();

                    GuiStarter.getCurrentApplication().switchToMainBoard();
                }
            });

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(im2);
            anchorPane.getChildren().add(button);

            assistantContainer.getChildren().add(anchorPane);
        }
    }
}


