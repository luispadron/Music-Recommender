package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 * Created by luisp on 11/25/2015.
 */
public class MainViewController {

    @FXML
    MenuItem menuItemClear;

    public void onMenuItemClearClick(ActionEvent actionEvent) {
        System.out.println("Cleared");
        menuItemClear.setText("CLEARED");
}

}
