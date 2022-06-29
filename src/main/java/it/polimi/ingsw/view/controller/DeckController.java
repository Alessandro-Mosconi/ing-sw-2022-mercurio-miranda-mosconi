package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
        for (Player p : GuiStarter.getCurrentApplication().getClient().getView().getPlayers()) {
            if (p.getLastAssistantCard() != null) {
                VBox vBox = new VBox();
                Text nick = new Text(p.getNickName() + " chose:");
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

        for (int i : array) {
            String path = "assets/Assistenti/2x/Assistente(" + (i) + ").png";
            ImageView im2 = new ImageView(path);
            im2.setFitHeight(200);
            im2.setFitWidth(150);
            im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(200);
            button.setPrefWidth(130);

            button.setOnMouseEntered(e -> im2.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)"));

            button.setOnMouseExited(e -> im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)"));

            button.setOnAction(event -> {
                View view = GuiStarter.getCurrentApplication().getClient().getView();
                boolean invalidInput = false;
                ArrayList<Integer> alreadyUsed = new ArrayList<>();
                for (Player p : view.getPlayers()) {
                    if (p.getLastAssistantCard() != null) {
                        alreadyUsed.add(p.getLastAssistantCard().getValue());
                    }
                }
                ArrayList<Integer> availableOnes = new ArrayList<>();
                for (AssistantCard ac : view.getPlayer().getDeck().getCards()) {
                    if (!ac.isConsumed()) {
                        availableOnes.add(ac.getValue());
                    }
                }
                if ((availableOnes.containsAll(alreadyUsed) && alreadyUsed.containsAll(availableOnes)) || (availableOnes.size() < alreadyUsed.size() && alreadyUsed.containsAll(availableOnes))) {
                    invalidInput = false;
                    System.out.println("dentro l'if strano + - - ");
                } else if (alreadyUsed.contains(i)) {
                    invalidInput = true;
                    System.out.println("fuori dall'if strano + - - ");

                }
                System.out.println("fuori da entrambi gli if else strani");

                if (invalidInput) {
                    GuiStarter.getCurrentApplication().showError("invalid card");
                } else {
                    System.out.println(i + ") card clicked");
                    view.setChosenAssistantCard(view.getPlayer().getDeck().getCards().get(i - 1));
                    view.getPlayer().getDeck().getCards().get(i - 1).setConsumed(true);
                    view.prepareMessage();
                    GuiStarter.getCurrentApplication().closeAssistantStage();
                    GuiStarter.getCurrentApplication().switchToMainBoard();
                }
            });

            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(im2);
            anchorPane.getChildren().add(button);
            View view = GuiStarter.getCurrentApplication().getClient().getView();

            if (!view.getPlayer().getDeck().getCards().get(i - 1).isConsumed()) {
                assistantContainer.getChildren().add(anchorPane);
            }
            GuiStarter.getCurrentApplication().getClient().getView().setMessageType(MessageType.ASSISTANT_CARD);

        }

    }
}


