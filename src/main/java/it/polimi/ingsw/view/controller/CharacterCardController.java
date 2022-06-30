package it.polimi.ingsw.view.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.view.GuiStarter;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CharacterCardController {
    @FXML
    private Text caption;
    @FXML
    private FlowPane effectContainer;

    private boolean check = false;

    public void initialize() {
        View view = GuiStarter.getCurrentApplication().getClient().getView();

        //caption showing
        caption.setText(view.getChosenCharacterCard().getCaption());
        caption.setWrappingWidth(600);

        //updated cost setting
        Text cost = new Text("Updated cost: " + view.getChosenCharacterCard().getPrice());
        cost.setFont(Font.font("System", FontPosture.ITALIC, 20));
        cost.setUnderline(true);
        cost.setFill(Color.WHITE);

        //container for cost + action + submit buttons
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        vBox.setFillWidth(true);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(cost);

        //default question (if question is different, override it)
        Text question1 = new Text("Chose a color:");
        question1.setFont(Font.font("System", 20));
        question1.setFill(Color.WHITE);

        //action container
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);

        //reset of effect container
        effectContainer.getChildren().removeAll();
        check = false;

        switch (view.getChosenCharacterCard().getID()) {
            case 1, 9, 11, 12 -> {
                vBox.getChildren().add(question1);
                ArrayList<Button> colorButtons = new ArrayList<>();

                //acquiring the map using the right cast
                Map<PawnColor, Integer> map = new HashMap<>();
                if (view.getChosenCharacterCard().getID().equals(1)) {
                    CharacterCard1 card1 = (CharacterCard1) view.getChosenCharacterCard().getCardBehavior();
                    map = card1.getStudents();
                } else if (view.getChosenCharacterCard().getID().equals(11)) {
                    CharacterCard11 card11 = (CharacterCard11) view.getChosenCharacterCard().getCardBehavior();
                    map = card11.getStudents();
                } else if (view.getChosenCharacterCard().getID().equals(12) || view.getChosenCharacterCard().getID().equals(9)) {
                    for (PawnColor color : PawnColor.values())
                        map.put(color, 1); //creating a map with 1 pawn of all color for color chose
                }

                //show of every pawn like a button if present
                for (PawnColor color : map.keySet()) {
                    if (!map.get(color).equals(0)) {
                        Button button = new Button();
                        button.setShape(new Circle(25));
                        button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect: null");
                        button.setCursor(Cursor.HAND);
                        button.setPrefWidth(40);
                        button.setPrefHeight(40);
                        colorButtons.add(button);
                        button.setOnAction(event -> {
                            for (Button b : colorButtons)
                                b.setStyle(b.getStyle() + ";  -fx-effect: null");
                            button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  dropshadow(gaussian, white, 15, 0.5, 0, 0)");
                            view.setParameter(new Parameter());
                            view.getParameter().setChosenColor(color);
                            check = true;
                        });
                        flowPane.setHgap(10);
                        flowPane.getChildren().add(button);
                    }
                }

                //adding action pane to main pane
                vBox.getChildren().add(flowPane);
            } // color choose
            case 2, 3, 4, 5, 6, 8 -> check = true;
            case 7, 10 -> {
                //setting the right question
                if (view.getChosenCharacterCard().getID().equals(10))
                    question1.setText("Chose max 2 student to swap between entrance and hall");
                if (view.getChosenCharacterCard().getID().equals(7))
                    question1.setText("Chose max 3 student to swap between entrance and card map");
                vBox.getChildren().add(question1);
                //initialize parameter map1 and map2
                view.getParameter().setColorMap1(new HashMap<>());
                view.getParameter().setColorMap2(new HashMap<>());
                for (PawnColor color : PawnColor.values()) {
                    view.getParameter().getColorMap1().put(color, 0);
                    view.getParameter().getColorMap2().put(color, 0);
                }

                //creating of 2 map graphic structure
                //map1 is always the entrance, map2 could be the hall or the card map
                Map<PawnColor, Integer> map1 = view.getPlayer().getSchoolBoard().getStudentEntrance();
                Map<PawnColor, Integer> map2;
                if (view.getChosenCharacterCard().getID().equals(7)) {
                    CharacterCard7 card7 = (CharacterCard7) view.getChosenCharacterCard().getCardBehavior();
                    map2 = card7.getStudents();
                } else map2 = view.getPlayer().getSchoolBoard().getStudentHall();

                //map1 title + map display
                FlowPane flowPane1 = new FlowPane();
                flowPane1.setAlignment(Pos.CENTER);
                VBox vBox1 = new VBox();
                Text entrance = new Text("Entrance:");
                entrance.setFont(Font.font("System", 15));
                entrance.setFill(Color.WHITE);
                vBox1.getChildren().add(entrance);
                vBox1.setPrefWidth(300);
                for (PawnColor color : map1.keySet()) {
                    for (int i = 0; i < map1.get(color); i++) {
                        Button button = new Button();
                        button.setShape(new Circle(25));
                        button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect: null");
                        button.setCursor(Cursor.HAND);
                        button.setPrefWidth(40);
                        button.setPrefHeight(40);
                        button.setOnAction(event -> {
                            if (!button.getStyle().equals("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  dropshadow(gaussian, white, 15, 0.5, 0, 0)")) {
                                button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  dropshadow(gaussian, white, 15, 0.5, 0, 0)");
                                if (!view.getParameter().getColorMap2().containsKey(color))
                                    view.getParameter().getColorMap2().put(color, 1);
                                else
                                    view.getParameter().getColorMap2().replace(color, view.getParameter().getColorMap2().get(color) + 1);
                            } else {
                                button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  null");
                                view.getParameter().getColorMap2().put(color, view.getParameter().getColorMap2().get(color) - 1);
                            }
                            int counter = 0;
                            for (PawnColor color1 : view.getParameter().getColorMap2().keySet())
                                counter = counter + view.getParameter().getColorMap2().get(color1);
                            if (view.getChosenCharacterCard().getID().equals(7) && counter > 3)
                                check = false;
                            else if (view.getChosenCharacterCard().getID().equals(10) && counter > 2)
                                check = false;
                            else check = true;

                        });
                        flowPane1.setHgap(10);
                        flowPane1.setPrefWidth(300);
                        flowPane1.getChildren().add(button);
                    }
                }
                vBox1.getChildren().add(flowPane1);

                //map1 title + map display
                FlowPane flowPane2 = new FlowPane();
                flowPane2.setAlignment(Pos.CENTER);
                VBox vBox2 = new VBox();
                Text map2text = new Text("Hall:");
                if (view.getChosenCharacterCard().getID().equals(7))
                    map2text.setText("Card students:");
                map2text.setFont(Font.font("System", 15));
                map2text.setFill(Color.WHITE);
                vBox2.getChildren().add(map2text);
                vBox2.setPrefWidth(300);
                for (PawnColor color : map2.keySet()) {
                    for (int i = 0; i < map2.get(color); i++) {
                        Button button = new Button();
                        button.setShape(new Circle(25));
                        button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect: null");
                        button.setCursor(Cursor.HAND);
                        button.setPrefWidth(40);
                        button.setPrefHeight(40);
                        button.setOnAction(event -> {
                            if (!button.getStyle().equals("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  dropshadow(gaussian, white, 15, 0.5, 0, 0)")) {
                                button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  dropshadow(gaussian, white, 15, 0.5, 0, 0)");
                                if (!view.getParameter().getColorMap1().containsKey(color))
                                    view.getParameter().getColorMap1().put(color, 1);
                                else
                                    view.getParameter().getColorMap1().replace(color, view.getParameter().getColorMap1().get(color) + 1);
                            } else {
                                button.setStyle("-fx-border-color: white; -fx-background-color: " + color + ";  -fx-effect:  null");
                                view.getParameter().getColorMap1().put(color, view.getParameter().getColorMap1().get(color) - 1);
                            }
                            int counter = 0;
                            for (PawnColor color1 : view.getParameter().getColorMap1().keySet())
                                counter = counter + view.getParameter().getColorMap1().get(color1);
                            if (view.getChosenCharacterCard().getID().equals(7) && counter > 3)
                                check = false;
                            else if (view.getChosenCharacterCard().getID().equals(10) && counter > 2)
                                check = false;
                            else check = true;

                        });
                        flowPane2.setHgap(10);
                        flowPane2.setPrefWidth(300);
                        flowPane2.getChildren().add(button);
                    }
                }
                vBox2.getChildren().add(flowPane2);

                //adding to the action pane map1 structure and map2 structure
                flowPane.getChildren().add(vBox1);
                flowPane.getChildren().add(vBox2);

                //adding the action pane to main pane
                vBox.getChildren().add(flowPane);
            } // map swapping
        }

        //submit button creation
        Button conferma = new Button();
        conferma.setText("Conferma");
        conferma.setOnAction(event -> {
            //not enough money case
            if (view.getChosenCharacterCard().getPrice() > view.getPlayer().getWallet()) {
                GuiStarter.getCurrentApplication().showError("You don't have enough money");
                return;
            }

            //check update on map swapping case
            if (view.getChosenCharacterCard().getID().equals(10) || view.getChosenCharacterCard().getID().equals(7)) {
                int counter1 = 0, counter2 = 0;
                for (PawnColor color : PawnColor.values()) {
                    if (view.getParameter().getColorMap1().containsKey(color))
                        counter1 = counter1 + view.getParameter().getColorMap1().get(color);
                    if (view.getParameter().getColorMap2().containsKey(color))
                        counter2 = counter2 + view.getParameter().getColorMap2().get(color);
                }
                check = check && (counter2 == counter1);
            }

            //if al the checks passed
            if (check) {
                GuiStarter.getCurrentApplication().switchToMainBoard();
                GuiStarter.getCurrentApplication().closeCharacterStage();
                //if card id is 1, 3 or 5 we still need to chose an island, else send now the message
                if (view.getChosenCharacterCard().getID().equals(1) || view.getChosenCharacterCard().getID().equals(3) || view.getChosenCharacterCard().getID().equals(5))
                    GuiStarter.getCurrentApplication().choseIsland();
                else {
                    view.setMessageType(MessageType.CHOSEN_CHARACTER_CARD);
                    view.prepareMessage();
                }
            } else GuiStarter.getCurrentApplication().showError("Make your correct decision before continue");
        });

        Button annulla = new Button();
        annulla.setText("Annulla");
        annulla.setOnAction(event -> {
            view.setParameter(new Parameter());
            GuiStarter.getCurrentApplication().closeCharacterStage();
            view.setChosenCharacterCard(null);
            GuiStarter.getCurrentApplication().switchToMainBoard();
        });

        FlowPane tempFlowPane = new FlowPane();
        tempFlowPane.setAlignment(Pos.CENTER);
        tempFlowPane.setHgap(10);
        tempFlowPane.getChildren().add(conferma);
        tempFlowPane.getChildren().add(annulla);
        vBox.getChildren().add(tempFlowPane);

        //adding the elements in effect container
        effectContainer.getChildren().add(vBox);
    }


}


