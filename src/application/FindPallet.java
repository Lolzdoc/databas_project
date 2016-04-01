package application;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.ListView;
        import javafx.scene.control.RadioButton;
        import javafx.scene.control.SplitMenuButton;
        import javafx.scene.control.TextField;
        import javafx.scene.text.Text;


public class FindPallet {



    private Database db;


    public void setDatabase(Database db) {
        this.db = db;
    }

    public void fillTables() {

    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text Backed_date_out;

    @FXML
    private Button block_Button;

    @FXML
    private Text blocked_out;

    @FXML
    private TextField customer_id;

    @FXML
    private Text customer_id_out;

    @FXML
    private Text deleivery_date_out;

    @FXML
    private ListView<?> filter_result_list;

    @FXML
    private RadioButton is_Blocked;

    @FXML
    private Text location_out;

    @FXML
    private TextField prod_date_end;

    @FXML
    private TextField prod_date_start;

    @FXML
    private SplitMenuButton recipe_list;


    @FXML
    void filterPalletButtonAction(ActionEvent event) {
    }


    public void initialize() {
        /*
        assert Backed_date_out != null : "fx:id=\"Backed_date_out\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert block_Button != null : "fx:id=\"block_Button\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert blocked_out != null : "fx:id=\"blocked_out\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert customer_id != null : "fx:id=\"customer_id\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert customer_id_out != null : "fx:id=\"customer_id_out\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert deleivery_date_out != null : "fx:id=\"deleivery_date_out\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert filter_result_list != null : "fx:id=\"filter_result_list\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert is_Blocked != null : "fx:id=\"is_Blocked\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert location_out != null : "fx:id=\"location_out\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert prod_date_end != null : "fx:id=\"prod_date_end\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert prod_date_start != null : "fx:id=\"prod_date_start\" was not injected: check your FXML file 'FindPallet.fxml'.";
        assert recipe_list != null : "fx:id=\"recipe_list\" was not injected: check your FXML file 'FindPallet.fxml'.";
    */

    }

}
