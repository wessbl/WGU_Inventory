/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: MainScreenController

    This class controls the MainScreen view. It serves as a central hub for the
    entire application, wherein it has options to open 4 other windows, which
    all direct back to MainScreen only. It can delete parts or products on its
    own. It must be given an Inventory by using the setInventory() method, or
    it will start with an empty inventory.
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

    /**
     * Deletes the selected part in the list, or gives error and cancels
     * @param event 
     */
    @FXML
    private void delete_part(MouseEvent event) {
        Part selection = part_table.getSelectionModel().getSelectedItem();
        if (selection == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        
        //  Look for associated parts
        ObservableList<Product> assoc_prods = FXCollections.observableArrayList();
        for (Product prod : Inventory.getAllProducts())
            if (prod.getAllAssociatedParts().contains(selection))
                assoc_prods.add(prod);
        
        //  Build a message
        String txt;
        if(assoc_prods.isEmpty())
            txt = "This cannot be undone.";
        else
        {
            txt = "This product has associated parts: \n";
            for (int i = 0; i < assoc_prods.size(); i++)
            {
                txt += assoc_prods.get(i).getName();
                if (i != assoc_prods.size() - 1)
                    txt += ", ";
                else {
                    txt += ".";
                }
            }
        }
        if(confirm("Delete Part", "Are you sure you want to delete this part?", 
                txt))
        {
            Inventory.deletePart(selection);
            for (Product prod : assoc_prods)
                prod.deleteAssociatedPart(selection);
        }
        searchPart();
    }
    
    /**
     * A confirmation popup, fields indicate parts of the popup
     * @param title
     * @param header
     * @param text
     * @return 
     */
    private boolean confirm(String title, String header, String text)
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * Gives selected part to ModifyPart controller & switches view, or gives 
     * error and cancels
     * @param event
     * @throws IOException 
     */
    @FXML
    private void modify_part(MouseEvent event) throws IOException {
        if (part_table.getSelectionModel().getSelectedItem() == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        ChangeWindow("ModifyPart", event);
    }

    /**
     * Switches to AddPart controller & switches view
     * @param event
     * @throws IOException 
     */
    @FXML
    private void add_part(MouseEvent event) throws IOException {
        ChangeWindow("AddPart", event);
    }

    /**
     * Searches parts by clicking button
     * @param event 
     */
    @FXML
    private void search_part(MouseEvent event) {
        searchPart();
    }
    
    /**
     * Narrows down the items in part_table to only those whose name includes
     * the keyword in search_field
     */
    private void searchPart()
    {
        String keyword = part_search_field.getText().trim();
        ObservableList<Part> parts = Inventory.lookupPart(keyword);
        part_table.setItems(parts);
        if (parts.isEmpty())
            part_search_field.requestFocus();
    }

    /**
     * Deletes the selected product in the list, or gives error and cancels
     * @param event 
     */
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
            Inventory.deleteProduct(selection);
        searchProduct();
    }
    
    /**
     * Popup window method, when a value is incorrect for a situation. Params
     * represent different parts of the popup.
     * @param header
     * @param msg 
     */
    private void invalidValueError(String header, String msg)
    {
        // Error window
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Gives selected part to ModifyProduct controller & switches view, or gives 
     * error and cancels
     * @param event
     * @throws IOException 
     */
    @FXML
    private void modify_product(MouseEvent event) throws IOException {
        if (product_table.getSelectionModel().getSelectedItem() == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        ChangeWindow("ModifyProduct", event);
    }

    /**
     * Switches to AddProduct controller & switches view
     * @param event
     * @throws IOException 
     */
    @FXML
    private void add_product(MouseEvent event) throws IOException {
        ChangeWindow("AddProduct", event);
    }

    /**
     * Search products by clicking button
     * @param event 
     */
    @FXML
    private void search_product(MouseEvent event) {
        searchProduct();
    }
    
    /**
     * Narrows down the items in part_table to only those whose name includes
     * the keyword in search_field
     */
    private void searchProduct()
    {
        String keyword = product_search_field.getText().trim();
        ObservableList<Product> prods = Inventory.lookupProduct(keyword);
        product_table.setItems(prods);
        if (prods.isEmpty())
            product_search_field.requestFocus();
    }

    /**
     * Another way to exit the program, exit_button
     * @param event (exit_button clicked)
     */
    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        //  TODO For SQL: Unsaved Changes
        stage.close();
        Platform.exit();
    }
    
    /**
     * Changes the view/controller to another window, and gives variables and 
     * closing rules to the windows.
     * @param windowA text that represents the window to go to
     * @param event contains which button was clicked
     * @throws IOException 
     */
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
    
    /**
     * Generates next Part ID
     * @return part id
     */
    private int genPartID()
    {
        int max = 0;
        for (Part p : Inventory.getAllParts())
            if (p.getId() > max)
                max = p.getId();
        return max + 1;
    }
    
    /**
     * Generates next Product ID
     * @return product id
     */
    private int genProductID()
    {
        int max = 0;
        for (Product p : Inventory.getAllProducts())
            if (p.getId() > max)
                max = p.getId();
        return max + 1;
    }
    
    /**
     * Search Shortcut by pressing enter
     * @param event (enter clicked in either search field)
     */
    @FXML
    private void onEnter(ActionEvent event) {
        if (event.getSource() == part_search_field)
            searchPart();
        else if (event.getSource() == product_search_field)
            searchProduct();
    }
    
    /**
     * Set the current inventory from instantiating class
     * @param inv Inventory object
     */
    public void setInventory(Inventory inv)
    {
        this.inv = inv;
        
        //  Display Parts & Products
        part_table.setItems(Inventory.getAllParts());
        product_table.setItems(Inventory.getAllProducts());
    }
    
    /**
     * Initiate search with every change in search field
     * @param event 
     */
    @FXML
    public void quick_search_part(KeyEvent event)
    {
        searchPart();
    }

    
    /**
     * Initiate search with every change in search field
     * @param event 
     */
    @FXML
    private void quick_search_product(KeyEvent event) {
        searchProduct();
    }
}
