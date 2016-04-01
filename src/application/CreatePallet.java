package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;


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
    private ListView<?> recipe_list;

    @FXML
    private Button submit;


    @FXML
    void submitButtonAction(ActionEvent event) {
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


    }

}
