package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class BlockPallets {

    private Database db;
    public void setDatabase(Database db) {
        this.db = db;
    }





    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label NbrOfPallets;

    @FXML
    private Button block_button;

    @FXML
    private TextField end_date;

    @FXML
    private Button palletCalcButton;

    @FXML
    private ListView<?> recipe_list;

    @FXML
    private TextField start_date;


    @FXML
    void blockButtonAction(ActionEvent event) {
    }

    @FXML
    void calcPalletAction(ActionEvent event) {
    }


    public void initialize() {
      /*  assert NbrOfPallets != null : "fx:id=\"NbrOfPallets\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert block_button != null : "fx:id=\"block_button\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert end_date != null : "fx:id=\"end_date\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert palletCalcButton != null : "fx:id=\"palletCalcButton\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert recipe_list != null : "fx:id=\"recipe_list\" was not injected: check your FXML file 'BlockPallets.fxml'.";
        assert start_date != null : "fx:id=\"start_date\" was not injected: check your FXML file 'BlockPallets.fxml'.";
    */

    }

}
