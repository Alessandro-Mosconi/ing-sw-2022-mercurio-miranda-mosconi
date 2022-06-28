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
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainBoardController
{
  @FXML
  private  GridPane islandContainer;
  @FXML
  private  VBox vboxContainer;
  @FXML
  private  FlowPane cloudContainer;
  @FXML
  private  FlowPane infoContainer;
  @FXML
  private  Text effectActive;
  @FXML
  private  Text playerNick;
  @FXML
  private  Text wallet;
  @FXML
  private  GridPane towerContainer;
  @FXML
  private  GridPane hall;
  @FXML
  private  GridPane entrance;
  @FXML
  private Button hallButton;
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

  public void initialize(){
    showSchoolBoard();

    showClouds();

    showIslands();

    showOtherSchoolboard();

    GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

    playerNick.setText(view.getUsername());
    if(view.getGamemode().equals(GameMode.expert)) {
      wallet.setText("Wallet: " + view.getPlayer().getWallet());
      showCharacterChard();
    }


}

public void showCharacterChard(){
  GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

  FlowPane flowPane = new FlowPane();
  flowPane.setHgap(10.0);
  flowPane.setVgap(10.0);

  for(CharacterCard card : view.getCharacterCards()){
    int cardID= card.getID();
    System.out.println("id: " + cardID);
    String path = "assets/Personaggi/CarteTOT_front"+(cardID)+".jpg";
    ImageView im2 = new ImageView(path);
    System.out.println("carte");
    im2.setFitHeight(120);
    im2.setFitWidth(90);
    im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 20, 0, 0, 0)");
    Button button = new Button();
    button.setStyle("-fx-background-color: transparent");
    button.setCursor(Cursor.HAND);
    button.setPrefHeight(130);
    button.setPrefWidth(90);

    button.setTooltip(
            new Tooltip("Card" + cardID + ": " + card.getCaption())
    );

    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        System.out.println(cardID + ") CharacterCard clicked");
        effectActive.setFont(Font.font("System", FontWeight.BOLD, 15));
        effectActive.setFill(Color.WHITE);
        effectActive.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0)");
        effectActive.setText("Last card used: id " + cardID);
        GuiStarter.getCurrentApplication().useCharacterCard();
        view.setChosenCharacterCard(card);
      }
    });

    AnchorPane anchorPane = new AnchorPane();
    anchorPane.getChildren().add(im2);


    ArrayList<Shape> shapes = new ArrayList<>();
    ArrayList<Text> text = new ArrayList<>();

    int a = 0;
    int b = 0;

    switch(cardID){
      case 1:
        CharacterCard1 card1 = (CharacterCard1) card.getCardBehavior();
        GridPane gridPane = new GridPane();

        shapes = new ArrayList<>();
        text = new ArrayList<>();

        a = 0;
        b = 0;

        for(PawnColor color : PawnColor.values())
        {
          shapes.add(new Circle(0.0, 0.0, 15));
          text.add(new Text("0"));

          int x = PawnColor.getColorIndex(color);
          shapes.get(x).setFill(PawnColor.getColor(x));
          text.get(x).setText(String.valueOf(card1.getStudents().get(color)));
          StackPane stack = new StackPane();
          stack.getChildren().addAll(shapes.get(x), text.get(x));
          gridPane.add(stack, a, b, 1, 1);
          b++;
          if (b == 3) {
            b = 0;
            a++;
          }

        }
        anchorPane.getChildren().add(gridPane);
        break;

      case 5:/*
        CharacterCard5 card5 = (CharacterCard5) card.getCardBehavior();
        GridPane gridPane1 = new GridPane();

        shapes = new ArrayList<>();

        a = 0;
        b = 0;

        for(int i=0; i<4; i++)
        {
          shapes.add(new Rectangle(0.0, 0.0, 40, 40));

          shapes.get(i).setFill(Color.WHITE);
          shapes.get(i).setStroke(Color.RED);

          StackPane stack = new StackPane();
          stack.getChildren().addAll(shapes.get(x), text.get(x));
          gridPane2.add(stack, a, b, 1, 1);
          b++;
          if (b == 3) {
            b = 0;
            a++;
          }

        }
        anchorPane.getChildren().add(gridPane2);
        */

        break;
      case 7:
        CharacterCard7 card7 = (CharacterCard7) card.getCardBehavior();
        GridPane gridPane2 = new GridPane();

        shapes = new ArrayList<>();
        text = new ArrayList<>();

        a = 0;
        b = 0;

        for(PawnColor color : PawnColor.values())
        {
          shapes.add(new Circle(0.0, 0.0, 15));
          text.add(new Text("0"));

          int x = PawnColor.getColorIndex(color);
          shapes.get(x).setFill(PawnColor.getColor(x));
          text.get(x).setText(String.valueOf(card7.getStudents().get(color)));
          StackPane stack = new StackPane();
          stack.getChildren().addAll(shapes.get(x), text.get(x));
          gridPane2.add(stack, a, b, 1, 1);
          b++;
          if (b == 3) {
            b = 0;
            a++;
          }

        }
        anchorPane.getChildren().add(gridPane2);
        break;
      case 11:
        CharacterCard11 card11 = (CharacterCard11) card.getCardBehavior();
        GridPane gridPane3 = new GridPane();

        shapes = new ArrayList<>();
        text = new ArrayList<>();

        a = 0;
        b = 0;

        for(PawnColor color : PawnColor.values())
        {
          shapes.add(new Circle(0.0, 0.0, 15));
          text.add(new Text("0"));

          int x = PawnColor.getColorIndex(color);
          shapes.get(x).setFill(PawnColor.getColor(x));
          text.get(x).setText(String.valueOf(card11.getStudents().get(color)));
          StackPane stack = new StackPane();
          stack.getChildren().addAll(shapes.get(x), text.get(x));
          gridPane3.add(stack, a, b, 1, 1);
          b++;
          if (b == 3) {
            b = 0;
            a++;
          }

        }
        anchorPane.getChildren().add(gridPane3);
        break;
    }

    anchorPane.getChildren().add(button);

    flowPane.setAlignment(Pos.CENTER);
    flowPane.getChildren().add(anchorPane);

  }

  vboxContainer.getChildren().add(flowPane);
}

public void showOtherSchoolboard(){

  GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

  //ArrayList<Button> scButton = new ArrayList<>();

    ArrayList<Player> players = (ArrayList<Player>) view.getPlayers().clone();

  players.removeIf(player -> player.getNickName().equals(view.getPlayer().getNickName()));

  int i=0;


  for(Player player : players)
  {
    Button scButton = new Button();
    scButton.setFont(Font.font(25));
    scButton.setOnAction(new EventHandler<ActionEvent>() {
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
        stage.setTitle("My modal window");
        //stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        stage.show();
      }
    });

    scButton.setText(player.getNickName());
    infoContainer.setVgap(10);
    infoContainer.setHgap(10);
    infoContainer.setAlignment(Pos.CENTER);
    infoContainer.getChildren().add(scButton);
    System.out.println("aggiunto!");
    i++;
  }
}

public void showSchoolBoard(){
  GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

  ArrayList<Shape> towers = new ArrayList<>();

  int col=0, row=0;
  for (int i =0; i<view.getPlayer().getSchoolBoard().getTowersNumber(); i++)
  {
    towers.add(new Rectangle(0.0, 0.0, 40, 40));
    towers.get(i).setFill(TowerColor.getColor(view.getPlayer().getSchoolBoard().getTowersColor()));
    towers.get(i).setStroke(Color.BLACK);
    towerContainer.add(towers.get(i), col, row, 1, 1);
    towerContainer.setAlignment(Pos.CENTER);
    col++;
    if(col==4){
      col=0;
      row++;
    }
  }

  hallButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent event) {
      System.out.println("Hall clicked");
      view.setDestination(-1);
      view.setMessageType(MessageType.PAWN_MOVE);
      view.prepareMessage();
    }
  });

  if(!view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE)||!view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE)||!view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE))
    hallButton.setDisable(true);
  else hallButton.setDisable(false);

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

  col=0;
  row=0;
  int num=0;

  ArrayList<Button> entranceTable = new ArrayList<>();
  map = view.getPlayer().getSchoolBoard().getStudentEntrance();

  for(PawnColor color: PawnColor.values())
  {
    num=map.get(color);
    while(num!=0) {
      entranceTable.add(new Button());
      entranceTable.get((row * 5) + col).setStyle("-fx-border-color: white; -fx-background-color: " + color.toString());
      entranceTable.get((row * 5) + col).setCursor(Cursor.HAND);
      entranceTable.get((row * 5) + col).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          System.out.println(color.toString() + ") entrance pawn clicked");
          if(!view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE)&&!view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE)&&!view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE))
            view.setColorToMove(color);
        }
      });
      entranceTable.get((row * 5) + col).setShape(new Circle(25));
      entranceTable.get((row * 5) + col).setPrefWidth(40);
      entranceTable.get((row * 5) + col).setPrefHeight(40);
      if(!view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE)&&!view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE)&&!view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE)) {
        entranceTable.get((row * 5) + col).setDisable(true);
        entranceTable.get((row * 5) + col).setStyle("-fx-border-color: white; -fx-opacity: 1; -fx-background-color: " + color );
        entranceTable.get((row * 5) + col).setCursor(Cursor.NONE);
      }
      else entranceTable.get((row * 5) + col).setDisable(false);

      entrance.add(entranceTable.get((row * 5) + col), col, row, 1, 1);
      col++;
      if (col == 5) {
        col = 0;
        row++;
      }
      num--;
    }
  }


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

public void showClouds(){
  GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        for (CloudTile cloud : view.getClouds()) {

    String path = "assets/Reame/PNG/nuvola.png";
    ImageView im2 = new ImageView(path);
    im2.setFitHeight(150);
    im2.setFitWidth(200);
    im2.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0)");
    Button button2 = new Button();
    button2.setStyle("-fx-background-color: transparent");
    button2.setPrefHeight(200);
    button2.setPrefWidth(200);

    button2.setCursor(Cursor.HAND);
    if(!view.getPhase().equals(Phase.CHOOSING_CT))
      button2.setDisable(true);
    else button2.setDisable(false);

            button2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(cloud.getCloudID() + ") cloud clicked");
                    view.setChosenCloudPos(cloud.getCloudID());
                    view.setMessageType(MessageType.CHOSEN_CT);
                    view.prepareMessage();
                }
            });

            AnchorPane anchorPane2 = new AnchorPane();
            anchorPane2.getChildren().add(im2);

            ArrayList<Shape> shapes = new ArrayList<>();
            ArrayList<Text> text = new ArrayList<>();

            int a = 0;
            int b = 0;

            GridPane gridPane = new GridPane();

            for (PawnColor color : PawnColor.values()) {
                shapes.add(new Circle(0.0, 0.0, 15));
                text.add(new Text("0"));

                int x = PawnColor.getColorIndex(color);
                shapes.get(x).setFill(PawnColor.getColor(x));
                text.get(x).setText(String.valueOf(cloud.getStudents().get(color)));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(shapes.get(x), text.get(x));
                gridPane.add(stack, a, b, 1, 1);
                b++;
                if (b == 3) {
                    b = 0;
                    a++;
                }

            }

            FlowPane flowPane = new FlowPane();
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setPrefSize(200, 170);
            flowPane.getChildren().add(gridPane);
            anchorPane2.getChildren().add(flowPane);

            anchorPane2.getChildren().add(button2);

            cloudContainer.getChildren().add(anchorPane2);
        }
    }

    public void showIslands() {
        GUI view = (GUI) GuiStarter.getCurrentApplication().getClient().getView();

        Map<PawnColor, Integer> map = new HashMap<>();

        int MN = 10;
        for (PawnColor color : PawnColor.values())
            map.put(color, 2);

        map.replace(PawnColor.green, 4);
        map.replace(PawnColor.yellow, 1);
        map.replace(PawnColor.blue, 0);

        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            array.add(i);

        int j = 0;

        int row1 = 0;
        int col1 = 0;
        for (Island island : view.getIslandManager().getIslandList()) {

            AnchorPane anchorPane = new AnchorPane();
            GridPane gridPane = new GridPane();

            ArrayList<Shape> shapes = new ArrayList<>();
            shapes.add(new Rectangle(0.0, 0.0, 25, 25));

            ArrayList<Text> text = new ArrayList<>();
            text.add(new Text());

            int a = 0;
            int b = 1;
            if (island.getTowerColor() != null)
                shapes.get(0).setFill(TowerColor.getColor(island.getTowerColor()));
            else shapes.get(0).setFill(Color.TRANSPARENT);

            StackPane stack2 = new StackPane();
            stack2.getChildren().addAll(shapes.get(0), text.get(0));
            gridPane.add(stack2, 0, 0, 1, 1);

            for (PawnColor color : PawnColor.values()) {
                shapes.add(new Circle(0.0, 0.0, 15));
                text.add(new Text("0"));

                int x = PawnColor.getColorIndex(color);
                shapes.get(x + 1).setFill(PawnColor.getColor(x));
                text.get(x + 1).setText(String.valueOf(island.getIslandStudents().get(color)));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(shapes.get(x + 1), text.get(x + 1));
                gridPane.add(stack, a, b, 1, 1);
                b++;
                if (b == 3) {
                    b = 0;
                    a++;
                }

            }

            Button button = new Button();
            button.setPrefHeight(125);
            button.setPrefWidth(125);
            button.setStyle("-fx-background-color: transparent");
            button.setCursor(Cursor.HAND);
            if (view.getPhase().equals(Phase.CHOOSING_CT))//todo penso anche nelle phases waiting + planning vada bloccato
                button.setDisable(true);
            else button.setDisable(false);

            ImageView im = new ImageView("assets/Reame/PNG/Isola.png");
            im.setFitHeight(110);
            im.setFitWidth(110);
            im.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 15, 0, 0, 0)");

            if (island.isMotherNature())
                im.setStyle("-fx-effect:  dropshadow(gaussian, rgba(255, 255, 255 , 255), 30, 0.7, 0, 0)");


            button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              System.out.println(island.getIslandID() + ") island clicked");
              /* solo se chosenCharacterCard viene settato a null quando l'effetto finisce
              if(view.getChosenCharacterCard()!=null)
                if(view.getChosenCharacterCard().getID().equals(1)||view.getChosenCharacterCard().getID().equals(3)||view.getChosenCharacterCard().getID().equals(5))
                  view.getParameter().setIsland(island);
                else
               */
              if(view.getPhase().equals(Phase.CHOOSING_MN_SHIFT)) {
                int shift = (view.getIslandManager().getIslandList().size() + island.getIslandID() - view.getIslandManager().getCurrMNPosition())%12;
                if (shift > view.getPlayer().getMaxShift()) {
                  GuiStarter.getCurrentApplication().showError(ErrorType.INVALID_MN_SHIFT.toString());
                  return;
                }else {
                  view.setMessageType(MessageType.MN_SHIFT);
                  view.setMN_shift(shift);
                }
              } else if(view.getPhase().equals(Phase.CHOOSING_FIRST_MOVE)||view.getPhase().equals(Phase.CHOOSING_SECOND_MOVE)||view.getPhase().equals(Phase.CHOOSING_THIRD_MOVE)) {
                view.setDestination(island.getIslandID());
                view.setMessageType(MessageType.PAWN_MOVE);
              }


              view.prepareMessage();
                }
            });


            FlowPane flowPane = new FlowPane();
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setPrefSize(110, 110);
            flowPane.getChildren().add(gridPane);


            anchorPane.getChildren().add(im);
            Shape noEntryTile = new Circle(0.0, 0.0, 50);
            noEntryTile.setFill(Color.RED);
            noEntryTile.setStyle("-fx-opacity: 0.5");
            noEntryTile.setStroke(Color.RED);
            noEntryTile.setStrokeWidth(3.0);
            if (island.isNoEntryTile()) {
                FlowPane flowPane1 = new FlowPane();
                flowPane1.setPrefSize(110, 110);
                flowPane1.getChildren().add(noEntryTile);
                anchorPane.getChildren().add(flowPane1);

            }


            anchorPane.getChildren().add(flowPane);
            anchorPane.getChildren().add(button);
            islandContainer.add(anchorPane, col1, row1);
            System.out.println("col: " + col1 + "row: " + row1);
            if ((row1 == 0 || row1 == 1 || row1 == 2) && col1 == 3)
                row1++;
            else if (row1 == 3 && (col1 == 3 || col1 == 2 || col1 == 1))
                col1--;
            else if ((row1 == 3 || row1 == 2 || row1 == 1) && col1 == 0)
                row1--;
            else col1++;

        }
    }
}
