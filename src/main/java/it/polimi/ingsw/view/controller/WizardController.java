package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.WizardType;
import it.polimi.ingsw.view.GuiStarter;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;

public class WizardController {

    @FXML
    private FlowPane wizardContainer;

    public void initialize() {
        ArrayList<WizardType> wizards = GuiStarter.getCurrentApplication().getClient().getView().getWizards();

        for (WizardType wizard : wizards) {
            //image capturing
            String path = "assets/Raw/Carte_retro/MAGO_" + WizardType.getIndex(wizard) + "_1.jpg";
            ImageView im2 = new ImageView(path);
            im2.setFitHeight(200);
            im2.setFitWidth(150);
            im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");

            //button creation
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(200);
            button.setPrefWidth(150);
            button.setOnMouseEntered(e -> im2.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)"));
            button.setOnMouseExited(e -> im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)"));
            button.setOnAction(event -> {
                System.out.println(wizard + ") wizard clicked");

                GuiStarter.getCurrentApplication().getClient().getView().getPlayer().setDeck(new Deck(wizard));
                GuiStarter.getCurrentApplication().switchToTowerScene();
            });

            //overlay of image and button
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(im2);
            anchorPane.getChildren().add(button);

            //adding of wizard graphic structure to wizardContainer
            wizardContainer.getChildren().add(anchorPane);
        }

    }
}
