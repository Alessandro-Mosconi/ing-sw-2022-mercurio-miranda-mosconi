package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WizardType;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class WinnerController {
    @FXML
    private Text winnerText;
    @FXML
    private FlowPane wizardWinnerContainer;

    public void initialize(){
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        winnerText.setText(view.getWinnerUsername());
        winnerText.setStyle("-fx-effect:  dropshadow(three-pass-box,  yellow, 100, 0.3, 0, 0)");

        Player player = null;
        for(Player p : view.getPlayers())
            if(p.getNickName().equals(view.getWinnerUsername()))
                player=p;

        String path = "assets/Raw/Carte_retro/MAGO_" + WizardType.getIndex(player.getDeck().getWizard()) + "_1.jpg";
        javafx.scene.image.ImageView im = new ImageView(path);
        im.setFitHeight(200);
        im.setFitWidth(150);
        im.setStyle("-fx-effect:  dropshadow(three-pass-box,  yellow, 100, 0.5, 0, 0)");
        wizardWinnerContainer.getChildren().add(im);
    }
}
