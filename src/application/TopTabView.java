package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class TopTabView {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane ablockPalletsTab;
    @FXML
    private BlockPallets ablockPalletsTabController;


    @FXML
    private AnchorPane aSearchTab;
    @FXML
    private FindPallet aSearchTabController;


    @FXML
    private AnchorPane apalletTab;
    @FXML
    private CreatePallet apalletTabController;


    public void initialize() {
		System.out.println("TopTabView initializing");

		// send the booking controller ref to the login controller
		// in order to pass data around
		//aLoginTabController.setBookingTab(aBookingTabController);
	}
	
	public void setDatabase(Database db) {

        ablockPalletsTabController.setDatabase(db);
        aSearchTabController.setDatabase(db);
        apalletTabController.setDatabase(db);
	}

   /* public BookingTab getBookingTab() {
        return aBookingTabController;
    }
    */
}



