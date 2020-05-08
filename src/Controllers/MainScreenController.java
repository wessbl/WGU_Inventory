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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void delete_part(MouseEvent event) {
        Part selection = part_table.getSelectionModel().getSelectedItem();
        if (selection == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        if(confirm("Delete Part", "Are you sure you want to delete this part?", 
                "This cannot be undone."))
            inv.deletePart(selection);
        searchPart();
    }
    
    private boolean confirm(String title, String header, String text)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    @FXML
    private void modify_part(MouseEvent event) throws IOException {
        if (part_table.getSelectionModel().getSelectedItem() == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        ChangeWindow("ModifyPart", event);
    }

    @FXML
    private void add_part(MouseEvent event) throws IOException {
        ChangeWindow("AddPart", event);
    }

    @FXML
    private void search_part(MouseEvent event) {
        searchPart();
    }
    
    private void searchPart()
    {
        String keyword = part_search_field.getText().trim();
        ObservableList<Part> parts = inv.lookupPart(keyword);
        part_table.setItems(parts);
        if (parts.isEmpty())
            part_search_field.requestFocus();
    }

    @FXML
    private void delete_product(MouseEvent event) {
        Product selection = product_table.getSelectionModel().getSelectedItem();
        if (selection == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        if (confirm("Delete Part", 
                "Are you sure you want to delete this part?", 
                "This cannot be undone."))
            inv.deleteProduct(selection);
        searchProduct();
    }
    
    private void invalidValueError(String header, String msg)
    {
        // Error window
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void modify_product(MouseEvent event) throws IOException {
        if (product_table.getSelectionModel().getSelectedItem() == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        ChangeWindow("ModifyProduct", event);
    }

    @FXML
    private void add_product(MouseEvent event) throws IOException {
        ChangeWindow("AddProduct", event);
    }

    @FXML
    private void search_product(MouseEvent event) {
        searchProduct();
    }
    
    private void searchProduct()
    {
        String keyword = product_search_field.getText().trim();
        ObservableList<Product> prods = inv.lookupProduct(keyword);
        product_table.setItems(prods);
        if (prods.isEmpty())
            product_search_field.requestFocus();
    }

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        //  TODO For SQL: Unsaved Changes
        stage.close();
    }
    
    private void ChangeWindow(String window, MouseEvent event) throws IOException
    {
        //  Get Loader and Stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        Parent parent = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  Set controller for new window
        switch(window)
        {
            case "AddPart":
                AddPartController addPartCtrl = loader.getController();
                addPartCtrl.setup(inv, genPartID());
                break;
                
            case "ModifyPart":
                ModifyPartController modPartCtrl = loader.getController();
                modPartCtrl.setup(inv, part_table.getSelectionModel().getSelectedItem());
                break;
                
            case "AddProduct":
                AddProductController addProdCtrl = loader.getController();
                addProdCtrl.setup(inv, genProductID());
                break;
                
            case "ModifyProduct":
                ModifyProductController modProdCtrl = loader.getController();
                modProdCtrl.setup(inv, product_table.getSelectionModel().getSelectedItem());
                break;
                
            default:
                throw new IOException("Given invalid window descriptor: " + window);
        }
        //  Add confirmation for window close
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest((WindowEvent event1) -> {
            if(confirm("Exit",
                    "Are you sure you want to exit the application?",
                    "Your changes will not be saved."))
            {
                Platform.exit();
            } else {
                event1.consume();
            }
            
        });
        
        //  Show new stage
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    //  Generates next Part ID
    private int genPartID()
    {
        int max = 0;
        for (Part p : inv.getAllParts())
            if (p.getId() > max)
                max = p.getId();
        return max + 1;
    }
    
    //  Generates next Product ID
    private int genProductID()
    {
        int max = 0;
        for (Product p : inv.getAllProducts())
            if (p.getId() > max)
                max = p.getId();
        return max + 1;
    }
    
    //  Search Shortcut by pressing enter
    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == part_search_field)
            searchPart();
        else if (event.getSource() == product_search_field)
            searchProduct();
    }
    
    //  Set the current inventory from instantiating class
    public void setInventory(Inventory inv)
    {
        this.inv = inv;
        
        //  Display Parts & Products
        part_table.setItems(inv.getAllParts());
        product_table.setItems(inv.getAllProducts());
    }
    
    //  Initiate search with every change in search field
    @FXML
    public void quick_search_part(KeyEvent event)
    {
        searchPart();
    }

    
    //  Initiate search with every change in search field
    @FXML
    private void quick_search_product(KeyEvent event) {
        searchProduct();
    }
}
