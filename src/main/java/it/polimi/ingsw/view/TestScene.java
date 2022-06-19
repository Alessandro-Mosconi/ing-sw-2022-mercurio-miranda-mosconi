package it.polimi.ingsw.view;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TestScene {
    public Button button;
    public Label label;
    public TextField textField;


    /**
     * Method automatically called when the scene is loaded.
     * Initializes the scene.
     */
    public void initialize()
    {
        label.setText("");
    }


    public void buttonClicked(ActionEvent actionEvent)
    {
        label.setText(textField.getText());
    }
}
