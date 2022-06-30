package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ErrorType;
import it.polimi.ingsw.network.Phase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuiStarter extends Application {
    private static GuiStarter currentApplication;
    private Client client;
    private Stage primaryStage;
    private Stage characterStage;
    private Stage assistantStage;

    public static GuiStarter getCurrentApplication() {
        return currentApplication;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Allocates the client and sets a GUI to it.
     *
     * @param primaryStage -
     */
    @Override
    public void start(Stage primaryStage) {
        this.client = new Client();
        GUI gui = new GUI();
        client.setView(gui);
        gui.setClient(client);
        gui.setGuiStarter(this);
        currentApplication = this;

        primaryStage.getIcons().add(new Image("/assets/logo-cranio-creation.png"));
        primaryStage.setTitle("Eriantys");
        /*primaryStage.setOnCloseRequest(we -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        this.primaryStage = primaryStage;
        switchToLoginScene();
        primaryStage.show();
    }

    /**
     * Loads the login scene on the main stage of the GUI.
     */
    public void switchToLoginScene() {
        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/LoginScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            //primaryStage.setTitle("Login");
            primaryStage.sizeToScene();

        });
    }

    /**
     * Loads the create-game scene on the main stage of the GUI.
     */
    public void switchToCreateSettings() {

        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/CreateSettings.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            //primaryStage.setTitle("Login2");
            primaryStage.sizeToScene();

        });
    }

    /**
     * Loads the join-game scene on the main stage of the GUI.
     */
    public void switchToJoinSettings() {
        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/JoinSettings.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            //primaryStage.setTitle("Login2");
            primaryStage.sizeToScene();
        });
    }

    /**
     * Loads the wizard choice scene on the main stage of the GUI.
     */
    public void switchToWizardsScene() {
        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/WizardChoice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();
        });

    }

    /**
     * Loads the towers color choice scene on the main stage of the GUI.
     */
    public void switchToTowerScene() {

        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/TowerChoice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });

    }

    /**
     * Shows the scene in which assistant card are shown and chosen on a new modal stage of the GUI.
     */
    public void switchToDeckScene() {

        Platform.runLater(() -> {
            Stage stage = new Stage();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/AssistantDeck.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene sc = new Scene(root);
            stage.setScene(sc);
            assistantStage = stage;
            stage.sizeToScene();
            stage.setTitle("Assistant deck");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setOnCloseRequest(new EventHandler<>() {
                public void handle(WindowEvent we) {
                    System.out.println("Stage is closing");
                    if (getClient().getView().getChosenAssistantCard() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Chose an assistant card first", ButtonType.OK);
                        alert.showAndWait();
                        switchToDeckScene();
                        return;
                    }
                }
            });
            stage.initOwner(primaryStage);
            stage.show();

        });
    }

    /**
     * Loads the lobby scene on the main stage of the GUI.
     */
    public void switchToLobbyScene() {
        Platform.runLater(() -> {

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/LobbyWaiting.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();
        });
    }

    /**
     * Loads the main board scene on the main stage of the GUI.
     */
    public void switchToMainBoard() {

        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/MainBoard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });

    }

    /**
     * Shows an error alert on the GUI.
     */
    public void showError(String error) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "This error occured: " + error, ButtonType.OK);
            alert.showAndWait();
            if (error.equals("\"USERNAME_ALREADY_IN_LOBBY\"")) {
                switchToLoginScene();
            }
        });
    }


    /**
     * Shows an alert on the GUI to tell the user it's not his turn.
     */
    public void waitForYourTurn() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Now wait for your turn", ButtonType.OK);
            alert.showAndWait();
        });
    }


    /**
     * Shows an alert on the GUI to tell the user it's time to choose a cloud tile.
     */
    public void choseCT() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nChose a cloud and click on it", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/nuvola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    /**
     * Shows an alert on the GUI to tell the user it's time to choose mother nature's shift.
     */
    public void chooseMNmovement() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nClick on the island Mother Nature should go\nMax " + getClient().getView().getPlayer().getMaxShift() + " shift", ButtonType.OK);
//TODO vedere se ci va isola o island
            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    /**
     * Shows an alert on the GUI to tell the user it's time to choose an island.
     */
    public void choseIsland() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "NOW CHOSE AN ISLAND\nClick on the island you want to apply the effect", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    /**
     * Shows an alert on the GUI to tell the user it's time to choose a pawn move.
     */
    public void choosePawnMove() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "IT'S YOUR TURN\nChoose a student from your Entrance.\n Then, choose a destination clicking on your SchoolBoard Hall or on the Island you chose:", ButtonType.OK);

            ImageView icon = new ImageView("/assets/Reame/PNG/Isola.png");
            icon.setStyle("-fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, 0, 0, 0)");
            icon.setFitHeight(48);
            icon.setFitWidth(60);
            alert.getDialogPane().setGraphic(icon);
            alert.showAndWait();
        });
    }

    /**
     * Loads the end game scene on the main stage of the GUI.
     */
    public void showEndGameWindow() {
        Platform.runLater(() -> {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/WinnerScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Scene sc = new Scene(root);
            primaryStage.setScene(sc);
            primaryStage.sizeToScene();

        });
    }

    /**
     * Shows on a new modal stage of the GUI the character card on which the user clicked,
     * its attributes (if it has any), its description and its price.
     */
    public void useCharacterCard() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/CharacterCardScene.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Scene sc = new Scene(root);
            stage.setScene(sc);
            characterStage = stage;
            stage.sizeToScene();
            stage.setTitle("CharacterCard");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setOnCloseRequest(we -> {
                System.out.println("Stage is closing");
                getClient().getView().setParameter(new Parameter());
                getClient().getView().setChosenCharacterCard(null);
            });
            stage.show();

        });
    }

    /**
     * Closes the modal stage related to character cards.
     */
    public void closeCharacterStage() {

        Platform.runLater(() -> {
            characterStage.close();
        });
    }

    /**
     * Closes the modal stage related to assistant cards.
     */
    public void closeAssistantStage() {

        Platform.runLater(() -> {
            assistantStage.close();
        });
    }

    @Override
    public void stop(){
        System.exit(0);
    }

}
