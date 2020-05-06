/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Inventory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wessl
 */
public class AddProductController implements Initializable {

    @FXML
    private TextField name_field;
    @FXML
    private TextField inv_field;
    @FXML
    private TextField price_field;
    @FXML
    private TextField max_field;
    @FXML
    private TextField min_field;
    @FXML
    private TextField search_field;
    @FXML
    private Button search_button;
    @FXML
    private Button cancel_button;
    @FXML
    private Button save_button;
    @FXML
    private TableView<?> part_table;
    @FXML
    private Button add_button;
    @FXML
    private Button delete_button;
    @FXML
    private TableView<?> added_parts_table;
    private Inventory inv;
    
    public AddProductController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO populate part_table with data
        // TODO set on close request?
    }    

    @FXML
    private void search(MouseEvent event) {
        //  TODO
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        ChangeWindow("MainScreen", event);
    }

    @FXML
    private void save(MouseEvent event) throws IOException {
        // TODO Do your saving...
        
        //  Go back to MainScreen
        ChangeWindow("MainScreen", event);
    }

    @FXML
    private void add(MouseEvent event) {
        //TODO add Part to a list of added products, display in table. Refresh added_parts_table
    }

    @FXML
    private void delete(MouseEvent event) {
        //TODO remove part from list of added products, display in table. Refresh added_parts_table
    }
    
    private void ChangeWindow(String window, MouseEvent event) throws IOException
    {
        //  Get Loader and Stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  Set controller for new window - always goes back to MainScreen
	Controllers.MainScreenController c = new Controllers.MainScreenController(inv);
	loader.setController(c);
        
        //  Show new stage
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void onEnter(ActionEvent event) {
        // TODO
    }
    
    public void setInventory(Inventory inv)
    {
        this.inv = inv;
    }
}
