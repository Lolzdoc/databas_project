package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class BlockPallets {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label NbrOfPallets;

    @FXML
    private Button palletCalcButton;


    @FXML
    void calcPalletAction(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert NbrOfPallets != null : "fx:id=\"NbrOfPallets\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert palletCalcButton != null : "fx:id=\"palletCalcButton\" was not injected: check your FXML file 'BlockPallets.fxml'.";


    }

}
