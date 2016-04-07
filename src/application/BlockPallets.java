package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class BlockPallets {

    private Database db;
    private List<String> allRecipes = null;//new ArrayList<String>();

    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {
        fillList();
    }
    private void fillList(){

        ArrayList<String> pallets = db.getPallets_filtered(null, null, "1901-01-01", "9999-12-31", null, false);
        allRecipes = db.getRecipes();


        recipe_list.setItems(FXCollections.observableList(allRecipes));
        Filter_result_pane.setItems(FXCollections.observableList(pallets));
        // remove any selection
        recipe_list.getSelectionModel().clearSelection();
    }



        @FXML
        private TextField end_date;

        @FXML
        private ListView<String> Filter_result_pane;

        @FXML
        private Label NbrOfPallets;

        @FXML
        private ListView<String> recipe_list;

        @FXML
        private TextField start_date;

        @FXML
        private Button palletCalcButton;

        @FXML
        private Button block_button;

        @FXML
        void refresh_button_action(ActionEvent event) {

        }





    @FXML
    void blockButtonAction(ActionEvent event) {
       ArrayList<String> pallets = db.getPallets_filtered(null, -1, start_date.getText(), end_date.getText(), null, false);
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
