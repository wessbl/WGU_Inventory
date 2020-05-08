/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: ModifyProductController

    This class controls the ModifyProduct view. It validates and sends entered
    data to the model for storage. It must be given variables as specified by
    the setup() method in order to modify and preserve data among all windows in
    the project.
 */
package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wessl
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField id_field;
    @FXML
    private TextField name_field;
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
    private Button add_button;
    @FXML
    private Button delete_button;
    @FXML
    private TableView<Part> added_parts_table;
    private Inventory inv;
    @FXML
    private TextField stock_field;
    @FXML
    private TableView<Part> part_table;
    
    private ObservableList<Part> new_parts;
    private ObservableList<Part> old_parts;
    private Product product;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_field.setDisable(true);
    }

    /**
     * Searching by clicking button
     * @param event (search_button click)
     */
    @FXML
    private void button_search(MouseEvent event)
    {
        search();
    }
    
    /**
     * Searching by clicking enter in search_field
     * @param event 
     */
    @FXML
    private void onEnter(ActionEvent event) {
        search();
    }
    
    /**
     * Searching by typing any key in search_field
     * @param event (key pressed in search_field)
     */
    @FXML
    private void quick_search(KeyEvent event)
    {
        search();
    }
    
    /**
     * Narrows down the items in part_table to only those whose name includes
     * the keyword in search_field
     */
    private void search()
    {
        String keyword = search_field.getText().trim();
        ObservableList<Part> parts = inv.lookupPart(keyword);
        part_table.setItems(parts);
        if (parts.isEmpty())
            search_field.requestFocus();
    }

    /**
     * Goes back to MainScreen immediately, no confirmation
     * @param event (cancel_button click)
     * @throws IOException 
     */
    @FXML
    private void cancel(MouseEvent event) throws IOException {
        ChangeWindow("MainScreen", event);
    }

    /**
     * Checks the entered data, and saves if valid. If not valid, presents an 
     * error window for user and cancels the save.
     */
    @FXML
    private void save(MouseEvent event) throws IOException {
        
        /******* Validate Data *******/
        
        String name = name_field.getText().trim();
        
        //  Verify text boxes are filled out (we'll check numbers later)
        if (name.isEmpty())
        {
            InvalidValueError("The \"Name\" field cannot be empty");
            name_field.requestFocus();
            return;
        }
        
        //  Check stock is an int
        try{Integer.parseInt(stock_field.getText().trim());}
        catch (NumberFormatException e)
        {
            //  Error window: "Stock must be an integer"
            InvalidValueError("The value for \"Stock\" must be an integer.");
            stock_field.requestFocus();
            return;
        }
        int stock = Integer.parseInt(stock_field.getText().trim());
        
        //  Check price is a double
        try{Double.parseDouble(price_field.getText().trim());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Price/Cost\" must be a number.");
            price_field.requestFocus();
            return;
        }
        double price = Double.parseDouble(price_field.getText().trim());
        
        //  Check if max is an int
        try{Integer.parseInt(max_field.getText().trim());}
        catch (NumberFormatException e)
        {
            //  Error window: "Max must be an integer"
            InvalidValueError("The value for \"Max\" must be an integer.");
            max_field.requestFocus();
            return;
        }
        int max = Integer.parseInt(max_field.getText().trim());
        
        //  Check if min is an int
        try{Integer.parseInt(min_field.getText().trim());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Min\" must be an integer.");
            min_field.requestFocus();
            return;
        }
        int min = Integer.parseInt(min_field.getText().trim());

        // Check for valid integer values: Min, Max, Stock
        if (min < 0)
        {
            InvalidValueError("Min must be 0 or greater");
            min_field.requestFocus();
            return;
        }
        if (max < 1)
        {
            InvalidValueError("Max must be 1 or greater");
            max_field.requestFocus();
            return;
        }
        if (min > max)
        {
            InvalidValueError("The value for \"Min\" cannot be greater than \"Max\".");
            min_field.requestFocus();
            return;
        }
        if (stock > max || stock < min)
        {
            InvalidValueError("The value for \"Stock\" must be between Min and Max.");
            stock_field.requestFocus();
            return;
        }
        
        /******* Save Data *******/
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setMin(min);
        product.setMax(max);
        
        //  Delete old parts from product
        for (Part p : old_parts)
            product.deleteAssociatedPart(p);
        
        //  Add new parts to product
        for (Part p : new_parts)
            product.addAssociatedPart(p);
        
//        inv.addProduct(product);
        
        //  Go back to MainScreen
        ChangeWindow("MainScreen", event);
    }
    
    /**
     * Popup window method, when a value is incorrect for a situation
     * @param msg error message
     */
    private void InvalidValueError(String msg)
    {
        // Error window
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Value Entered");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Add a part from the part_table to the added_parts_table
     * @param event (add_button click)
     */
    @FXML
    private void add(MouseEvent event) {
        Part p = part_table.getSelectionModel().getSelectedItem();
        if (p == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        if (new_parts.contains(p))
        {
            invalidValueError("You've already added this item", "The item you selected is already in the list below.");
            return;
        }
        new_parts.add(p);
    }

    /**
     * Remove part from list of added products, display in table. Refresh 
     * added_parts_table
     * @param event 
     */
    @FXML
    private void delete(MouseEvent event) {
        Part sel = added_parts_table.getSelectionModel().getSelectedItem();
        if (sel == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        new_parts.remove(sel);
        search();
    }
    
    /**
     * Creates error popup
     * @param header
     * @param msg 
     */
    private void invalidValueError(String header, String msg)
    {
        // Error window
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    /**
     * Changes the view/controller back to MainScreen
     * @param windowA text that represents the window to go to
     * @param event
     * @throws IOException 
     */
    private void ChangeWindow(String window, MouseEvent event) throws IOException
    {
        //  Get Loader and Stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  Set controller for new window - always goes back to MainScreen
	MainScreenController c = loader.getController();
        c.setInventory(inv);
	loader.setController(c);
        
        //  Show new stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Gives this class necessary variables from instantiating class. Must be
     * called before switching to this controller's window.
     * @param inv the inventory
     * @param product the product to modify
     */
    public void setup(Inventory inv, Product product)
    {
        this.inv = inv;
        this.product = product;
        new_parts = FXCollections.observableArrayList();
        old_parts = FXCollections.observableArrayList();
        for (Part p : product.getAllAssociatedParts())
        {
            new_parts.add(p);
            old_parts.add(p);
        }
        
        //  Set tables
        part_table.setItems(inv.getAllParts());
        added_parts_table.setItems(new_parts);
        
        //  Display product data
        id_field.setText(Integer.toString(product.getId()));
        name_field.setText(product.getName());
        stock_field.setText(Integer.toString(product.getStock()));
        price_field.setText(Double.toString(product.getPrice()));
        max_field.setText(Integer.toString(product.getMax()));
        min_field.setText(Integer.toString(product.getMin()));
    }

}
