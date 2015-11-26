package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    /* Set the fxml objects so we can use them in code */
    @FXML
    Button submitButton;
    @FXML
    TextField inputTextField;
    @FXML
    TextArea outputTextArea;


    /* This is the implemented method from the interface
     * initialize is run when ever the GUI loads up */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /* Handle the button clicks for the menu bar */
    public void onCloseMenuItem(ActionEvent actionEvent) {
        /* Close application */
        System.exit(0);
    }

    public void onClearMenuItem(ActionEvent actionEvent) {

    }

    public void onAboutMenuItem(ActionEvent actionEvent) {
        /* Using swing because too lazy to make a whole new view for this */
        JOptionPane.showMessageDialog(null, "This program was created by" +
                " Luis Padron.\n To use this simply select what you would want to do" +
                " and follow the prompts.");
    }

    /* Whenever use selects to look for related artists from the drop down menu bar */
    public void onRelatedArtistsMenuItem(ActionEvent actionEvent) {
        /* Check if submit button is hidden, if so, show it */
        if (!submitButton.isVisible()) {
           submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter artist name you would like to match");

    }
    /* Whenever use selects to look for new releases from the drop down menu bar */
    public void onNewReleasesMenuItem(ActionEvent actionEvent) {
        if (!submitButton.isVisible()) {
            submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter the country code, example: US");

    }

    /* Whenever use selects to look for featured play-lists from the drop down menu bar */
    public void onFeaturedPlaylistsMenuItem(ActionEvent actionEvent) {
        if (!submitButton.isVisible()) {
            submitButton.setVisible(true);
        }
        inputTextField.setPromptText("Enter the country code, example: US");

    }

    public void onSubmitButtonClick(ActionEvent actionEvent) {

    }
}
