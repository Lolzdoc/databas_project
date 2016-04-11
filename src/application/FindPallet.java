package application;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class FindPallet {


    private Database db;
    @FXML
    private Text deliver_button;
    @FXML
    private Text Backed_date_out;
    @FXML
    private TextField prod_date_start;
    @FXML
    private Button block_Button;
    @FXML
    private CheckBox is_Blocked;
    @FXML
    private ComboBox<String> recipe_list;
    @FXML
    private Label recipe_out;
    @FXML
    private Text location_out;
    @FXML
    private Text blocked_out;
    @FXML
    private Text delivery_date_out;
    @FXML
    private TextField deliv_date;
    @FXML
    private ListView<String> filter_result_list;
    @FXML
    private Text customer_id_out;
    @FXML
    private TextField customer_id;
    @FXML
    private TextField deliv_date_in;
    @FXML
    private TextField prod_date_end;
    private String currentRecipe = "";
    private String currentPalletID = "";

    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {
        List<String> allRecipes = new ArrayList<String>();
        allRecipes.add("");
        allRecipes.addAll(db.getRecipes());

        recipe_list.setItems(FXCollections.observableList(allRecipes));
        recipe_list.getSelectionModel().clearSelection();
        filter();
    }

    @FXML
    void filterPalletButtonAction(ActionEvent event) {
        filter();
    }

    @FXML
    void deliver_button_action(ActionEvent event) {
        if (db.deliverPallet(deliv_date_in.getText().trim(), Integer.parseInt(currentPalletID))) {
            delivery_date_out.setText(deliv_date_in.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Input");
            alert.setContentText("Incorrect delivery date");
            alert.showAndWait();
        }
    }


    private void filter() {
        boolean error = false;
        String s = "";
        int customer_id_filter = -1;

        if (customer_id.getText().trim().matches("^[0-9]+$")) {
            customer_id_filter = Integer.parseInt(customer_id.getText().trim());
        }


        String prod_date_start_filter = prod_date_start.getText();
        String prod_date_end_filter = prod_date_end.getText();
        prod_date_start_filter = prod_date_start_filter.trim();
        prod_date_end_filter = prod_date_end_filter.trim();



        if (!prod_date_start_filter.matches("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$")) {
            if(prod_date_start_filter.isEmpty()){
                prod_date_start_filter = "1901-01-01";
            } else {
                s += "Invalid start date" + "\n";
                error = true;
            }

        }

        if (!prod_date_end_filter.matches("^[0-9]{4}[-][0-9]{2}[-][0-9]{2}$")) {
            if(prod_date_end_filter.isEmpty()){
                prod_date_end_filter = "9999-12-31";
            } else {
                s += "Invalid end date" + "\n";
                error = true;
            }
        }

        if(error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Input");
            alert.setContentText(s);
            alert.showAndWait();
        } else{
            List<String> pallets_filtered;

            pallets_filtered = db.getPallets_filtered(deliv_date.getText(), customer_id_filter, prod_date_start_filter, prod_date_end_filter, currentRecipe, is_Blocked.isSelected());

            filter_result_list.setItems(FXCollections.observableList(pallets_filtered));

            // remove any selection
            filter_result_list.getSelectionModel().clearSelection();
        }


    }


    public void initialize() {
        filter_result_list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    db.update_review_panel(newV, recipe_out, customer_id_out, location_out, blocked_out, Backed_date_out, delivery_date_out);
                    currentPalletID = newV;
                });

        recipe_list.getSelectionModel().selectedItemProperty().addListener((ChangeListener) (ov, old_val, new_val) -> {
            currentRecipe = (String) new_val;
        });


    }

}
