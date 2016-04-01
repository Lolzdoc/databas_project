package application;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;


public class CreatePallet {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox blocked_enable;

    @FXML
    private TextField customer_id;

    @FXML
    private TextField deliv_date;

    @FXML
    private SplitMenuButton pallet_location;

    @FXML
    private TextField prod_date;

    @FXML
    private ListView<String> recipe_list;

    @FXML
    private Button submit;

    private String currentRecipe;
    private String currentCustomer;
    private Database db;
    private boolean blockedStatus = false;
    private String deliveryDate = null;
    private String productionDate = null;

    @FXML
    void submitButtonAction(ActionEvent event) {
        currentCustomer = customer_id.getText();
        if(currentCustomer == null){
//            Skicka felmeddelande (krävs customer ID)
        }

        deliveryDate = deliv_date.getText();
        if(!isValidDate(deliveryDate)){
//            Skicka felmeddelande (tomt eller felaktikt datum)
        }

        productionDate = prod_date.getText();
        if(!isValidDate(deliveryDate)){
//            Skicka felmeddelande (tomt eller felaktikt datum)
        }


    }

    public void setDatabase(Database db){
        this.db = db;
    }

    @FXML
    void initialize() {

        assert blocked_enable != null : "fx:id=\"blocked_enable\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert customer_id != null : "fx:id=\"customer_id\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert deliv_date != null : "fx:id=\"deliv_date\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert pallet_location != null : "fx:id=\"pallet_location\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert prod_date != null : "fx:id=\"prod_date\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert recipe_list != null : "fx:id=\"recipe_list\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        initTab();
        
        
        recipe_list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    currentRecipe = recipe_list.getSelectionModel().getSelectedItem();
                    
                });

        blocked_enable.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oldValue){
                    blockedStatus = false;
                } else {
                    blockedStatus = true;
                }
            }
        });
    }

    private void initTab() {
        fillRecipeList();
    }

    public void fillRecipeList() {
//        recipe_list = db.getRecipes();
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}

