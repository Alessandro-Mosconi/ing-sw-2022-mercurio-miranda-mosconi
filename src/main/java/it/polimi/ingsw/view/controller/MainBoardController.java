package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ErrorType;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.Phase;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.GuiStarter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class MainBoardController {
    @FXML
    private GridPane islandContainer;
    @FXML
    private VBox vboxContainer;
    @FXML
    private FlowPane cloudContainer;
    @FXML
    private FlowPane infoContainer;
    @FXML
    private Text effectActive;
    @FXML
    private Text playerNick;
    @FXML
    private Text wallet;
    @FXML
    private GridPane towerContainer;
    @FXML
    private GridPane hall;
    @FXML
    private GridPane entrance;
    @FXML
    private Button hallButton;
    @FXML
    private Polygon blueProf;
    @FXML
    private Polygon redProf;
    @FXML
    private Polygon greenProf;
    @FXML
    private Polygon yellowProf;
    @FXML
    private Polygon pinkProf;

    public void initialize() {
        showSchoolBoard();

        showClouds();

        showIslands();

        showOtherSchoolboard();

        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        playerNick.setText(view.getUsername());
        if (view.getGamemode().equals(GameMode.expert)) {
            wallet.setVisible(true);
            wallet.setText("Wallet: " + view.getPlayer().getWallet());
            showCharacterChard();
        }
        else wallet.setVisible(false);

    }

    public void showCharacterChard() {
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10.0);
        flowPane.setVgap(10.0);
        //character card graphic structure creation
        for (CharacterCard card : view.getCharacterCards()) {
            int cardID = card.getID();

            // image capturing
            String path = "assets/Personaggi/CarteTOT_front" + (cardID) + ".jpg";
            ImageView im2 = new ImageView(path);
            im2.setFitHeight(120);
            im2.setFitWidth(90);
            im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");

            //button creation
            Button button = new Button();
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setPrefHeight(130);
            button.setPrefWidth(90);
            button.setDisable(view.getPhase().equals(Phase.WAITING));
            button.setTooltip(
                    new Tooltip("Card" + cardID + ": " + card.getCaption())
            );
            button.setOnAction(event -> {
                System.out.println(cardID + ") CharacterCard clicked");
                effectActive.setFont(Font.font("System", FontWeight.BOLD, 15));
                effectActive.setFill(Color.WHITE);
                effectActive.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");
                effectActive.setText("Last card used: id " + cardID);
                view.setChosenCharacterCard(card);
                GuiStarter.getCurrentApplication().useCharacterCard();
            });

            //overlay of image and button
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().add(im2);

            // variables for pawn map on character card
            ArrayList<Shape> shapes = new ArrayList<>();
            ArrayList<Text> text = new ArrayList<>();
            ;
            int col, row;

            switch (cardID) {
                case 1 -> {
                    //creation of graphic map for CharacterCard1
                    CharacterCard1 card1 = (CharacterCard1) card.getCardBehavior();
                    GridPane gridPane = new GridPane();
                    col = 0;
                    row = 0;
                    for (PawnColor color : PawnColor.values()) {
                        shapes.add(new Circle(0.0, 0.0, 15));
                        text.add(new Text("0"));
                        Integer x = PawnColor.getColorIndex(color);
                        shapes.get(x).setFill(PawnColor.getColor(x));
                        text.get(x).setText(String.valueOf(card1.getStudents().get(color)));
                        StackPane stack = new StackPane();
                        stack.getChildren().addAll(shapes.get(x), text.get(x));
                        //make not visible if number of pawn = 0
                        stack.setVisible(!text.get(x).getText().equals("0"));
                        gridPane.add(stack, col, row, 1, 1);
                        row++;
                        if (row == 3) {
                            row = 0;
                            col++;
                        }
                    }
                    anchorPane.getChildren().add(gridPane);
                }

                case 7 -> {
                    //creation of graphic map for CharacterCard7
                    CharacterCard7 card7 = (CharacterCard7) card.getCardBehavior();
                    GridPane gridPane2 = new GridPane();
                    shapes = new ArrayList<>();
                    text = new ArrayList<>();
                    col = 0;
                    row = 0;
                    for (PawnColor color : PawnColor.values()) {
                        shapes.add(new Circle(0.0, 0.0, 15));
                        text.add(new Text("0"));
                        int x = PawnColor.getColorIndex(color);
                        shapes.get(x).setFill(PawnColor.getColor(x));
                        text.get(x).setText(String.valueOf(card7.getStudents().get(color)));
                        StackPane stack = new StackPane();
                        stack.getChildren().addAll(shapes.get(x), text.get(x));
                        //make not visible if number of pawn = 0
                        stack.setVisible(!text.get(x).getText().equals("0"));
                        gridPane2.add(stack, col, row, 1, 1);
                        row++;
                        if (row == 3) {
                            row = 0;
                            col++;
                        }
                    }
                    anchorPane.getChildren().add(gridPane2);
                }

                case 11 -> {
                    //creation of graphic map for CharacterCard11
                    CharacterCard11 card11 = (CharacterCard11) card.getCardBehavior();
                    GridPane gridPane3 = new GridPane();
                    shapes = new ArrayList<>();
                    text = new ArrayList<>();
                    col = 0;
                    row = 0;
                    for (PawnColor color : PawnColor.values()) {
                        shapes.add(new Circle(0.0, 0.0, 15));
                        text.add(new Text("0"));
                        int x = PawnColor.getColorIndex(color);
                        shapes.get(x).setFill(PawnColor.getColor(x));
                        text.get(x).setText(String.valueOf(card11.getStudents().get(color)));
                        StackPane stack = new StackPane();
                        stack.getChildren().addAll(shapes.get(x), text.get(x));
                        //make not visible if number of pawn = 0
                        stack.setVisible(!text.get(x).getText().equals("0"));
                        gridPane3.add(stack, col, row, 1, 1);
                        row++;
                        if (row == 3) {
                            row = 0;
                            col++;
                        }
                    }
                    anchorPane.getChildren().add(gridPane3);
                }
            } // -> end of graphic map creation

            //overlay of button and if exist map of the Character card
            anchorPane.getChildren().add(button);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.getChildren().add(anchorPane);
        }
        //adding of graphic character card structure to the character card container
        vboxContainer.getChildren().add(flowPane);
    }

    public void showOtherSchoolboard() {

        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        //creation of an array containing other player in the match
        ArrayList<Player> players = (ArrayList<Player>) view.getPlayers().clone();
        players.removeIf(player -> player.getNickName().equals(view.getPlayer().getNickName()));

        //creation of buttons to see other schoolboard in a modal window
        for (Player player : players) {
            Button scButton = new Button();
            scButton.setFont(Font.font(25));
            scButton.setText(player.getNickName());
            scButton.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent event) {
                    view.setPlayerSchoolboard(player);
                    Stage stage = new Stage();
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/SchoolBoard.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    Scene sc = new Scene(root);
                    stage.setScene(sc);
                    stage.sizeToScene();
                    stage.setTitle(player.getNickName() + " schoolboard");
                    //stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                    stage.show();
                }
            });

            //adding of button to info container
            infoContainer.setVgap(10);
            infoContainer.setHgap(10);
            infoContainer.setAlignment(Pos.CENTER);
            infoContainer.getChildren().add(scButton);
        }
    }

    public void showSchoolBoard() {
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        //creation of towers graphic structure
        ArrayList<Shape> towers = new ArrayList<>();
        int col = 0, row = 0;
        for (int i = 0; i < view.getPlayer().getSchoolBoard().getTowersNumber(); i++) {
            towers.add(new Rectangle(0.0, 0.0, 40, 40));
            towers.get(i).setFill(TowerColor.getColor(view.getPlayer().getSchoolBoard().getTowersColor()));
            towers.get(i).setStroke(Color.BLACK);
            towerContainer.add(towers.get(i), col, row, 1, 1);
            towerContainer.setAlignment(Pos.CENTER);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        //creation of button to Hall
        hallButton.setCursor(Cursor.HAND);
        hallButton.setOnAction(event -> {
            System.out.println("Hall clicked");
            if (view.getColorToMove() != null) {
                view.setDestination(-1);
                view.setMessageType(MessageType.PAWN_MOVE);
                view.prepareMessage();
                view.setColorToMove(null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Chose a pawn to move first", ButtonType.OK);
                alert.showAndWait();
            }
        });
        hallButton.setDisable((!view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE) && !view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE) && !view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE) &&!view.getPhase().equals(Phase.CHOOSING_FOURTH_MOVE)) || view.getPhase().equals(Phase.WAITING));

        //creation of hall graphic view
        ArrayList<Shape> hallTable = new ArrayList<>();
        Map<PawnColor, Integer> map = view.getPlayer().getSchoolBoard().getStudentHall();
        hall.setHgap(5);
        hall.setVgap(5);
        for (int c = 0; c < 5; c++)
            for (int r = 0; r < 10; r++) {
                hallTable.add(new Circle(0.0, 0.0, 15));
                hallTable.get((c * 10) + r).setFill(PawnColor.getColor(c));
                hallTable.get((c * 10) + r).setStroke(Color.WHITE);
                if (r < map.get(PawnColor.getIndex(c)))
                    hall.add(hallTable.get((c * 10) + r), c, r, 1, 1);
            }

        //creation of entrance graphic structure
        ArrayList<Button> entranceTable = new ArrayList<>();
        map = view.getPlayer().getSchoolBoard().getStudentEntrance();
        col = 0;
        row = 0;
        int num;
        for (PawnColor color : PawnColor.values()) {
            num = map.get(color);
            //creation of num buttons
            while (num != 0) {
                entranceTable.add(new Button());
                entranceTable.get((row * 5) + col).setStyle("-fx-border-color: white; -fx-background-color: " + color.toString());
                entranceTable.get((row * 5) + col).setCursor(Cursor.HAND);
                entranceTable.get((row * 5) + col).setShape(new Circle(25));
                entranceTable.get((row * 5) + col).setPrefWidth(40);
                entranceTable.get((row * 5) + col).setPrefHeight(40);
                entranceTable.get((row * 5) + col).setOnAction(event -> {
                    System.out.println(color + ") entrance pawn clicked");
                    view.setColorToMove(color);
                });
                //setting of disable option and prevention of button color change
                if ((!view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE) && !view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE) && !view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE) && !view.getPhase().equals(Phase.CHOOSING_FOURTH_MOVE) || view.getPhase().equals(Phase.WAITING))) {
                    entranceTable.get((row * 5) + col).setDisable(true);
                    entranceTable.get((row * 5) + col).setStyle("-fx-border-color: white; -fx-opacity: 1; -fx-background-color: " + color);
                    entranceTable.get((row * 5) + col).setCursor(Cursor.NONE);
                } else entranceTable.get((row * 5) + col).setDisable(false);
                //adding button to entrance
                entrance.add(entranceTable.get((row * 5) + col), col, row, 1, 1);
                col++;
                if (col == 5) {
                    col = 0;
                    row++;
                }
                num--;
            }
        }

        //setting professors visibility
        Map<PawnColor, Boolean> prof = view.getPlayer().getSchoolBoard().getProfessorTable();
        for (PawnColor color : PawnColor.values()) {
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

    public void showClouds() {
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        //clouds graphic structure creation
        for (CloudTile cloud : view.getClouds()) {
            //image capturing
            String path = "assets/Reame/PNG/nuvola.png";
            ImageView im2 = new ImageView(path);
            im2.setFitHeight(150);
            im2.setFitWidth(200);
            im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0)");

            //graphic map creation
            ArrayList<Shape> shapes = new ArrayList<>();
            ArrayList<Text> text = new ArrayList<>();
            int col = 0, row = 0;
            boolean isEmpty = true;
            GridPane gridPane = new GridPane();
            for (PawnColor color : PawnColor.values()) {
                shapes.add(new Circle(0.0, 0.0, 15));
                text.add(new Text("0"));
                int x = PawnColor.getColorIndex(color);
                shapes.get(x).setFill(PawnColor.getColor(x));
                text.get(x).setText(String.valueOf(cloud.getStudents().get(color)));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(shapes.get(x), text.get(x));
                if (text.get(x).getText().equals("0"))
                    stack.setVisible(false);
                else {
                    isEmpty = false;
                    stack.setVisible(true);
                }
                gridPane.add(stack, col, row, 1, 1);
                row++;
                if (row == 3) {
                    row = 0;
                    col++;
                }
            }
            //map centering
            FlowPane flowPane = new FlowPane();
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setPrefSize(200, 170);
            flowPane.getChildren().add(gridPane);

            //button creation
            Button button2 = new Button();
            button2.setStyle("-fx-background-color: transparent");
            button2.setPrefHeight(200);
            button2.setPrefWidth(200);
            button2.setCursor(Cursor.HAND);
            button2.setDisable(!view.getPhase().equals(Phase.CHOOSING_CT) || view.getPhase().equals(Phase.WAITING));
            button2.setOnAction(event -> {
                System.out.println(cloud.getCloudID() + ") cloud clicked");
                view.setChosenCloudPos(cloud.getCloudID());
                view.setMessageType(MessageType.CHOSEN_CT);
                view.prepareMessage();
            });
            button2.setDisable(isEmpty); //if cloud empty, button disabled

            //overlay of image and button and map
            AnchorPane anchorPane2 = new AnchorPane();
            anchorPane2.getChildren().add(im2);
            anchorPane2.getChildren().add(flowPane);
            anchorPane2.getChildren().add(button2);

            cloudContainer.getChildren().add(anchorPane2);
        }
    }

    public void showIslands() {
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();
        int row = 0;
        int col = 0;

        //clouds graphic structure creation
        for (Island island : view.getIslandManager().getIslandList()) {
            //island map cration
            AnchorPane anchorPane = new AnchorPane();
            GridPane gridPane = new GridPane();
            ArrayList<Shape> shapes = new ArrayList<>();
            ArrayList<Text> text = new ArrayList<>();
            StackPane stack2 = new StackPane();

            //first element is the tower
            shapes.add(new Rectangle(0.0, 0.0, 25, 25));
            text.add(new Text(String.valueOf(island.getTowersNumber())));
            text.get(0).setFill(Color.WHITE);
            stack2.getChildren().addAll(shapes.get(0), text.get(0));
            if (island.getTowerColor() != null) {
                shapes.get(0).setFill(TowerColor.getColor(island.getTowerColor()));
                stack2.setVisible(true);
                if(island.getTowerColor().equals(TowerColor.white))
                    text.get(0).setFill(Color.BLACK);
            } else {
                shapes.get(0).setFill(Color.TRANSPARENT);
                stack2.setVisible(false);
            }

            gridPane.add(stack2, 0, 0, 1, 1);

            //remaining elements are pawn
            int mapCol = 0;
            int mapRow = 1;
            for (PawnColor color : PawnColor.values()) {
                shapes.add(new Circle(0.0, 0.0, 15));
                text.add(new Text("0"));
                int x = PawnColor.getColorIndex(color);
                shapes.get(x + 1).setFill(PawnColor.getColor(x));
                text.get(x + 1).setText(String.valueOf(island.getIslandStudents().get(color)));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(shapes.get(x + 1), text.get(x + 1));
                stack.setVisible(!text.get(x + 1).getText().equals("0"));
                gridPane.add(stack, mapCol, mapRow, 1, 1);
                mapRow++;
                if (mapRow == 3) {
                    mapRow = 0;
                    mapCol++;
                }
            }

            //map centering
            FlowPane flowPane = new FlowPane();
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setPrefSize(110, 110);
            flowPane.getChildren().add(gridPane);

            //button creation
            Button button = new Button();
            button.setPrefHeight(125);
            button.setPrefWidth(125);
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            button.setDisable(view.getPhase().equals(Phase.CHOOSING_CT) || view.getPhase().equals(Phase.WAITING));
            if (view.getPhase().equals(Phase.CHOOSING_MN_SHIFT)) {
                int shift = (view.getIslandManager().getIslandList().size() + island.getIslandID() - view.getIslandManager().getCurrMNPosition()) % view.getIslandManager().getIslandList().size();
                String message = "";
                if (shift > view.getPlayer().getMaxShift() || shift == 0) {
                    message = "invalid MN shift";
                } else {
                    message = shift + " island shift";
                }
                button.setTooltip(
                        new Tooltip(message)
                );
            } //tooltip creation in CHOOSING_MN_SHIFT phase
            //setting of button action
            button.setOnAction(event -> {
                System.out.println(island.getIslandID() + ") island clicked");
                if (view.getChosenCharacterCard() != null) {
                    if (view.getCardUsed() && (view.getChosenCharacterCard().getID().equals(1) || view.getChosenCharacterCard().getID().equals(3) || view.getChosenCharacterCard().getID().equals(5))) {
                        view.getParameter().setIsland(island);
                        view.setMessageType(MessageType.CHOSEN_CHARACTER_CARD);
                        view.prepareMessage();
                        view.setChosenCharacterCard(null);
                        return;
                    }
                }

                if (view.getPhase().equals(Phase.CHOOSING_MN_SHIFT)) {
                    int shift = (view.getIslandManager().getIslandList().size() + island.getIslandID() - view.getIslandManager().getCurrMNPosition()) % view.getIslandManager().getIslandList().size();
                    if (shift > view.getPlayer().getMaxShift()) {
                        GuiStarter.getCurrentApplication().showError(ErrorType.INVALID_MN_SHIFT.toString());
                    } else {
                        if (island.isMotherNature()) {
                            GuiStarter.getCurrentApplication().showError("Minimum MN shift: 1");
                        } else {
                            view.setMessageType(MessageType.MN_SHIFT);
                            view.setMN_shift(shift);
                            view.prepareMessage();
                        }
                    }
                } else if (view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE) || view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE) || view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE) || view.getPhase().equals(Phase.CHOOSING_FOURTH_MOVE)) {
                    if (view.getColorToMove() != null) {
                        view.setDestination(island.getIslandID());
                        view.setMessageType(MessageType.PAWN_MOVE);
                        view.prepareMessage();
                        view.setColorToMove(null);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Chose a pawn to move first", ButtonType.OK);
                        alert.showAndWait();
                    }
                }
            });


            // image capturing
            ImageView im = new ImageView("assets/Reame/PNG/Isola.png");
            im.setFitHeight(110);
            im.setFitWidth(110);
            im.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0)");
            if (island.isMotherNature()) {
                im.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)");
            }// adding of white shadow if MN is present

            // noEntryTile structure creation
            Shape noEntryTile = new Circle(0.0, 0.0, 50);
            noEntryTile.setFill(Color.RED);
            noEntryTile.setStyle("-fx-opacity: 0.5");
            noEntryTile.setStroke(Color.RED);
            noEntryTile.setStrokeWidth(3.0);

            //overlay of all different structures in one anchorpane
            anchorPane.getChildren().add(im);
            if (island.isNoEntryTile()) {
                FlowPane flowPane1 = new FlowPane();
                flowPane1.setPrefSize(110, 110);
                flowPane1.getChildren().add(noEntryTile);
                anchorPane.getChildren().add(flowPane1);
            }
            anchorPane.getChildren().add(flowPane);
            anchorPane.getChildren().add(button);

            // adding of island structure to island container (circular shape)
            islandContainer.add(anchorPane, col, row);
            if ((row == 0 || row == 1 || row == 2) && col == 3)
                row++;
            else if (row == 3 && (col == 3 || col == 2 || col == 1))
                col--;
            else if ((row == 3 || row == 2 || row == 1) && col == 0)
                row--;
            else col++;

        }
    }
}
