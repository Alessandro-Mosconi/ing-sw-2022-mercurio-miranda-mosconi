package it.polimi.ingsw.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Parent popup;

    @FXML
    protected TextField username;
    @FXML
    private Text actiontarget;
    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) throws Exception {
        //possiamo fare che quando preme il tasto controlla l'input e poi va alla prossima fase (mandando anche il messaggio)

        actiontarget.setText("Sign in button pressed");
        System.out.println(username.getText());

        try {
            root = FXMLLoader.load(getClass().getResource("/GameSettings.fxml"));
            popup = FXMLLoader.load(getClass().getResource("/CreateDialog.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //root = FXMLLoader.load(getClass().getResource("GameSettings.fxml"));
        final Stage dialog = new Stage();

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stage);
        VBox dialogVbox = new VBox(20);

        Button createButton = new Button("CREATE");
        Button joinButton = new Button("JOIN");

        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        dialogVbox.getChildren().addAll(createButton, joinButton);

        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

        scene = new Scene(root,660, 370);
        stage.setScene(scene);
        stage.show();

    }

    public void submitGame(ActionEvent actionEvent) {
        //stessa cosa di sopra..
    }

    public TextField getUsername() {
        return username;
    }
    /**
     * Method automatically called when the scene is loaded.
     * Initializes the scene.
     */
    /*
    public void initialize()
    {
        label.setText("");
    }


    public void buttonClicked(ActionEvent actionEvent)
    {
        label.setText(textField.getText());
    }
    */
}
