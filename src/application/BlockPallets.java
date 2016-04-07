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
    private ArrayList<String > pallets_for_blocking = null;
    private String currentRecipe = "";

    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {
        fillList();
    }
    private void fillList(){


        allRecipes = db.getRecipes();
        recipe_list.setItems(FXCollections.observableList(allRecipes));
        recipe_list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    currentRecipe = newV;
                });
        // remove any selection
        recipe_list.getSelectionModel().select(0);


        pallets_for_blocking = db.getPallets_filtered(null, null, "1901-01-01", "9999-12-31", currentRecipe, false);
        Filter_result_pane.setItems(FXCollections.observableList(pallets_for_blocking));
        Filter_result_pane.getSelectionModel().clearSelection();



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

            String prod_date_start_filter = start_date.getText();
            String prod_date_end_filter = end_date.getText();
            prod_date_start_filter = prod_date_start_filter.trim();
            prod_date_end_filter = prod_date_end_filter.trim();


            if (!prod_date_start_filter.matches("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$")) {
                prod_date_start_filter = "1901-01-01";
            }

            if (!prod_date_end_filter.matches("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$")) {
                prod_date_end_filter = "9999-12-31";
            }


            pallets_for_blocking = db.getPallets_filtered(null, null, prod_date_start_filter, prod_date_end_filter, currentRecipe, false);
            Filter_result_pane.setItems(FXCollections.observableList(pallets_for_blocking));
            // remove any selection
            Filter_result_pane.getSelectionModel().clearSelection();


        }





    @FXML
    void blockButtonAction(ActionEvent event) {
    }

    @FXML
    void calcPalletAction(ActionEvent event) {
        System.out.println("pallets_for_blocking.size() = " + pallets_for_blocking.size());
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
