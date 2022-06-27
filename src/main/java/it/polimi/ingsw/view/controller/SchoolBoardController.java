package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SchoolBoardController {

    @FXML
    private GridPane towerContainer;
    @FXML
    private  GridPane hall;
    @FXML
    private  GridPane entrance;
    @FXML
    private Polygon blueProf;
    @FXML
    private  Polygon redProf;
    @FXML
    private  Polygon greenProf;
    @FXML
    private  Polygon yellowProf;
    @FXML
    private  Polygon pinkProf;
    @FXML
    private  Text playerNick;
    @FXML
    private HBox infoContainer;

    public void initialize(){

        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();
        Player player = view.getPlayerSchoolboard();
        playerNick.setText(player.getNickName());
        if(view.getGamemode().equals(GameMode.expert))
        {
            Text wallet= new Text("  Wallet: " + player.getWallet());
            wallet.setFont(Font.font("System", 15));
            infoContainer.getChildren().add(wallet);
        }
        showSchoolBoard(player);
    }

    public void showSchoolBoard(Player player){

        ArrayList<Shape> towers = new ArrayList<>();

        int col=0, row=0;
        for (int i =0; i<player.getSchoolBoard().getTowersNumber(); i++)
        {
            towers.add(new Rectangle(0.0, 0.0, 40, 40));
            towers.get(i).setFill(TowerColor.getColor(player.getSchoolBoard().getTowersColor()));
            towers.get(i).setStroke(Color.BLACK);
            towerContainer.add(towers.get(i), col, row, 1, 1);
            towerContainer.setAlignment(Pos.CENTER);
            col++;
            if(col==4)
            {
                col=0;
                row++;
            }
        }

        ArrayList<Shape> hallTable = new ArrayList<>();
        Map<PawnColor, Integer> map = player.getSchoolBoard().getStudentHall();

        hall.setHgap(5);
        hall.setVgap(5);
        for (int c=0; c<5; c++)
            for(int r=0; r<10; r++)
            {
                hallTable.add(new Circle(0.0, 0.0, 15));
                hallTable.get((c*10)+r).setFill(PawnColor.getColor(c));
                hallTable.get((c*10)+r).setStroke(Color.WHITE);
                if(r<map.get(PawnColor.getIndex(c)))
                    hall.add(hallTable.get((c*10)+r), c, r, 1, 1);
            }

        col=0;
        row=0;
        int num=0;

        ArrayList<Button> entranceTable = new ArrayList<>();
        map = player.getSchoolBoard().getStudentEntrance();

        for(PawnColor color: PawnColor.values())
        {
            num=map.get(color);
            while(num!=0) {
                entranceTable.add(new Button());
                entranceTable.get((row * 5) + col).setDisable(true);
                entranceTable.get((row * 5) + col).setStyle("-fx-border-color: white; -fx-opacity: 1; -fx-background-color: " + color.toString());
                entranceTable.get((row * 5) + col).setShape(new Circle(25));
                entranceTable.get((row * 5) + col).setPrefWidth(40);
                entranceTable.get((row * 5) + col).setPrefHeight(40);
                entrance.add(entranceTable.get((row * 5) + col), col, row, 1, 1);
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
                num--;
            }
        }



        Map<PawnColor, Boolean> prof = player.getSchoolBoard().getProfessorTable();

        for(PawnColor color : PawnColor.values())
        {
            if (color == PawnColor.blue)
                blueProf.setVisible(prof.get(color));
            if (color == PawnColor.pink)
                pinkProf.setVisible(prof.get(color));
            if (color == PawnColor.red)
                redProf.setVisible(prof.get(color));
            if (color == PawnColor.green)
                greenProf.setVisible(prof.get(color));
            if (color == PawnColor.yellow)
                yellowProf.setVisible(prof.get(color));
        }
    }

}

