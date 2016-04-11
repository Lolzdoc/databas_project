package application;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

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

    public void initializeFix() {
        ablockPalletsTabController.fillTables();
        aSearchTabController.fillTables();
        apalletTabController.fillTables();
    }

    public void initialize() {
        System.out.println("TopTabView initializing");

    }

    public void setDatabase(Database db) {

        ablockPalletsTabController.setDatabase(db);
        aSearchTabController.setDatabase(db);
        apalletTabController.setDatabase(db);
    }

}



