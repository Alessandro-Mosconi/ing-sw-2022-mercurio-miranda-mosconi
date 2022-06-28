package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class DeckController {
    @FXML
    private FlowPane assistantContainer;
    @FXML
    private FlowPane otherAssistantContainer;

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

                    View view = GuiStarter.getCurrentApplication().getClient().getView();
                    view.setChosenAssistantCard(view.getPlayer().getDeck().getCards().get(i));
                    view.prepareMessage();
                    GuiStarter.getCurrentApplication().closeAssistantStage();
                    GuiStarter.getCurrentApplication().switchToMainBoard();
                }
            });

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(im2);
            anchorPane.getChildren().add(button);

            assistantContainer.getChildren().add(anchorPane);

            }


        for(Player p : GuiStarter.getCurrentApplication().getClient().getView().getPlayers()){
            if(p.getLastAssistantCard()!=null) {
                VBox vBox = new VBox();
                Text nick = new Text(p.getNickName() + " chosed:");
                nick.setFont(Font.font("System", FontWeight.BOLD, 20));
                nick.setFill(Color.WHITE);
                nick.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");

                String path2 = "assets/Assistenti/2x/Assistente(" + p.getLastAssistantCard().getValue() + ").png";
                ImageView im = new ImageView(path2);
                im.setFitHeight(120);
                im.setFitWidth(80);
                im.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(nick);
                vBox.getChildren().add(im);
                vBox.setMargin(nick, new Insets(0, 0, 10, 0));
                otherAssistantContainer.getChildren().add(vBox);
            }
            }
    }
}


