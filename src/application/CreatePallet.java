package application;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class CreatePallet {



    private Database db;


    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {
        fillList();
    }

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

    private String currentLocation = null;
    private String currentRecipe = null;

    @FXML
    void submitButtonAction(ActionEvent event) {
        Integer customerID = null;
        boolean error = false;
        String s = "";
        try{
            customerID = Integer.parseInt(customer_id.getText());
        } catch (NumberFormatException e){
            s += "Invalid customer ID" + "\n";
            error = true;
        }
        if(db.customerNotRegistered(customerID)){
            s += "Customer not found in database" + "\n";
            error = true;
        }

        String deliveryDate = deliv_date.getText();
        if (!deliveryDate.isEmpty()) {
            if(!isValidDate(deliveryDate)){
                s += "Invalid delivery date" + "\n";
                error = true;
            }
        }

        String productionDate = prod_date.getText();
        if(!isValidDate(productionDate)){
            s += "Invalid production date" + "\n";
            error = true;
        }
        Boolean blockedStatus = blocked_enable.isSelected();
        if(blockedStatus){
            deliveryDate = "";
        }


        if(currentRecipe == null){
            s += "No recipe has been chosen" + "\n";
            error = true;
        }

        if(error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Wrong Input");
            alert.setContentText(s);
            alert.showAndWait();
        } else {
            db.createPallet(customerID,deliveryDate,productionDate,blockedStatus,currentLocation,currentRecipe);


        }
    }



    private void fillList(){

        List<String> allRecipes = null;
        allRecipes = db.getRecipes();


        pallet_location.setText("Deep-Freeze Storage");
        currentLocation = pallet_location.getText();



        recipe_list.setItems(FXCollections.observableList(allRecipes));
        recipe_list.getSelectionModel().select(0);


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

    public void initialize() {
        for (final MenuItem item : pallet_location.getItems()) {
            item.setOnAction((event) -> {
                currentLocation = item.getText();
                pallet_location.setText(item.getText());
            });
        }


        recipe_list.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldV, newV) -> {
                    currentRecipe = newV;
                });


    }

}
