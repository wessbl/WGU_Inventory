/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Part> part_table;
    @FXML
    private Button delete_part_button;
    @FXML
    private Button modify_part_button;
    @FXML
    private Button add_part_button;
    @FXML
    private Button part_search_button;
    @FXML
    private TextField part_search_field;
    @FXML
    private TableView<Product> product_table;
    @FXML
    private Button delete_product_button;
    @FXML
    private Button modify_product_button;
    @FXML
    private Button add_product_button;
    @FXML
    private Button product_search_button;
    @FXML
    private TextField product_search_field;
    @FXML
    private Button exit_button;
    
    private Inventory inv;
    
    public MainScreenController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO set on close request?
        
        //  Display Parts & Products
        part_table.setItems(inv.getAllParts());
        product_table.setItems(inv.getAllProducts());
        //part_table.refresh(); //TODO
        
    }    

    @FXML
    private void delete_part(MouseEvent event) {
        // TODO
    }

    @FXML
    private void modify_part(MouseEvent event) throws IOException {
        ChangeWindow("ModifyPart", event);
    }

    @FXML
    private void add_part(MouseEvent event) throws IOException {
        ChangeWindow("AddPart", event);
    }

    @FXML
    private void search_part(MouseEvent event) {
        //TODO
    }

    @FXML
    private void delete_product(MouseEvent event) {
        //TODO
    }

    @FXML
    private void modify_product(MouseEvent event) throws IOException {
        ChangeWindow("ModifyProduct", event);
    }

    @FXML
    private void add_product(MouseEvent event) throws IOException {
        ChangeWindow("AddProduct", event);
    }

    @FXML
    private void search_product(MouseEvent event) {
        //  TODO
    }

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        //TODO Unsaved changes?
        stage.close();
    }
    
    private void ChangeWindow(String window, MouseEvent event) throws IOException
    {
        //  Get Loader and Stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  Set controller for new window
        switch(window)
        {
            case "ModifyPart":
                loader.setController(new Controllers.ModifyPartController(inv));
                break;
            case "AddPart":
                loader.setController(new Controllers.AddPartController(inv));
                break;
            case "ModifyProduct":
                loader.setController(new Controllers.ModifyProductController(inv));
                break;
            case "AddProduct":
                loader.setController(new Controllers.AddProductController(inv));
                break;
            default:
                throw new IOException("Given invalid window descriptor: " + window);
        }
        
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
