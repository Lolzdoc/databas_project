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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Felaktig inmatning");
        String s = "";
        try{
            customerID = Integer.parseInt(customer_id.getText());
        } catch (NumberFormatException e){
            s += "CustomerID är felaktig" + "\n";
            error = true;
        }
        if(db.customerNotRegistered(customerID)){
            s += "Kunden finns ej i databasen" + "\n";
            error = true;
        }

        String deliveryDate = deliv_date.getText();
        if (!deliveryDate.isEmpty()) {
            if(!isValidDate(deliveryDate)){
                s += "Felaktigt deliv date" + "\n";
                error = true;
            }
        }

        String productionDate = prod_date.getText();
        if(!isValidDate(productionDate)){
            s += "Felaktigt prod date" + "\n";
            error = true;
        }
        Boolean blockedStatus = blocked_enable.isSelected();
        if(blockedStatus){
            deliveryDate = "";
        }


        if(currentRecipe == null){
            s += "Ingen recept valt" + "\n";
            error = true;
        }

        if(error){
            alert.setContentText(s);
            alert.showAndWait();
        } else {
            if(!db.createPallet(customerID,deliveryDate,productionDate,blockedStatus,currentLocation,currentRecipe)){
                System.out.println("ERROR failed to create pallet, please verify that there is enough raw materials");
            }

        }
    }



    private void fillList(){

        List<String> allRecipes = null;//new ArrayList<String>();
        allRecipes = db.getRecipes();

        recipe_list.setItems(FXCollections.observableList(allRecipes));

        // remove any selection
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
   /*     assert blocked_enable != null : "fx:id=\"blocked_enable\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert customer_id != null : "fx:id=\"customer_id\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert deliv_date != null : "fx:id=\"deliv_date\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert pallet_location != null : "fx:id=\"pallet_location\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert prod_date != null : "fx:id=\"prod_date\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert recipe_list != null : "fx:id=\"recipe_list\" was not injected: check your FXML file 'CreatePallet.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'CreatePallet.fxml'.";
    */
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
        pallet_location.setText("Deep-Freeze Storage");
        currentLocation = pallet_location.getText();
    }

}
