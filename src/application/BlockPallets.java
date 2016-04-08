package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;


public class BlockPallets {

    private Database db;
    private List<String> allRecipes = new ArrayList<>();
    private ArrayList<String> pallets_for_blocking = null;
    private String currentRecipe = "";
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

    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {
        fillList();
    }

    private void fillList() {

        allRecipes.add("");
        allRecipes.addAll(db.getRecipes());
        recipe_list.setItems(FXCollections.observableList(allRecipes));



        recipe_list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    currentRecipe = newV;
                    refresh();
                }
        );
        // remove any selection
        recipe_list.getSelectionModel().select(0);


        pallets_for_blocking = db.getPallets_filtered(null, -1, "1901-01-01", "9999-12-31", currentRecipe, false);
        Filter_result_pane.setItems(FXCollections.observableList(pallets_for_blocking));
        Filter_result_pane.getSelectionModel().clearSelection();

    }

    @FXML
    void refresh_button_action(ActionEvent event) {
        refresh();
    }

    private void refresh() {
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


        pallets_for_blocking = db.getPallets_filtered(null, -1, prod_date_start_filter, prod_date_end_filter, currentRecipe, false);
        Filter_result_pane.setItems(FXCollections.observableList(pallets_for_blocking));
        // remove any selection
        Filter_result_pane.getSelectionModel().clearSelection();

    }



    @FXML
    void blockButtonAction(ActionEvent event) {

        if (db.blockPallets(pallets_for_blocking)){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database error");
            alert.setContentText("Failed to block pallets");
            alert.showAndWait();
        }


    }

    @FXML
    void calcPalletAction(ActionEvent event) {
        NbrOfPallets.setText(((Integer) pallets_for_blocking.size()).toString());
    }


    public void initialize() {


    }

}
