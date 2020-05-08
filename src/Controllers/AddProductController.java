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
import javafx.scene.control.TableColumn;
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
public class AddProductController implements Initializable {
    
    @FXML
    private TextField id_field;
    @FXML
    private TextField name_field;
    @FXML
    private TextField stock_field;
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
    private TableView<Part> part_table;
    @FXML
    private Button add_button;
    @FXML
    private Button delete_button;
    @FXML
    private TableView<Part> added_parts_table;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> stock;
    @FXML
    private TableColumn<?, ?> price;
    
    private Inventory inv;
    private int product_id;
    private ObservableList<Part> parts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id_field.setDisable(true);
    }    

    @FXML
    private void button_search(MouseEvent event)
    {
        search();
    }
    
    @FXML
    private void onEnter(ActionEvent event) {
        search();
    }
    
    @FXML
    private void quick_search(KeyEvent event)
    {
        search();
    }
    
    private void search()
    {
        String keyword = search_field.getText().trim();
        ObservableList<Part> parts = inv.lookupPart(keyword);
        part_table.setItems(parts);
        if (parts.isEmpty())
            search_field.requestFocus();
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        ChangeWindow("MainScreen", event);
    }

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
        try{Integer.parseInt(stock_field.getText());}
        catch (NumberFormatException e)
        {
            //  Error window: "Stock must be an integer"
            InvalidValueError("The value for \"Stock\" must be an integer.");
            stock_field.requestFocus();
            return;
        }
        int stock = Integer.parseInt(stock_field.getText());
        
        //  Check price is a double
        try{Double.parseDouble(price_field.getText());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Price/Cost\" must be a number.");
            price_field.requestFocus();
            return;
        }
        double price = Double.parseDouble(price_field.getText());
        
        //  Check if max is an int
        try{Integer.parseInt(max_field.getText());}
        catch (NumberFormatException e)
        {
            //  Error window: "Max must be an integer"
            InvalidValueError("The value for \"Max\" must be an integer.");
            max_field.requestFocus();
            return;
        }
        int max = Integer.parseInt(max_field.getText());
        
        //  Check if min is an int
        try{Integer.parseInt(min_field.getText());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Min\" must be an integer.");
            min_field.requestFocus();
            return;
        }
        int min = Integer.parseInt(min_field.getText());

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
        Product product = new Product(
                product_id,
                name,
                price,
                stock,
                min,
                max
        );
        
        //  Add parts to product
        for (Part p : parts)
            product.addAssociatedPart(p);
        inv.addProduct(product);
        
        //  Go back to MainScreen
        ChangeWindow("MainScreen", event);
    }
    
    private void InvalidValueError(String msg)
    {
        // Error window
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Value Entered");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    //  Add a part from the part_table to the added_parts_table
    @FXML
    private void add(MouseEvent event) {
        Part p = part_table.getSelectionModel().getSelectedItem();
        if (p == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        if (parts.contains(p))
        {
            invalidValueError("You've already added this item", "The item you selected is already in the list below.");
            return;
        }
        parts.add(p);
    }
    
    //  Remove part from list of added products, display in table. Refresh added_parts_table
    @FXML
    private void delete(MouseEvent event)
    {
        Part sel = added_parts_table.getSelectionModel().getSelectedItem();
        if (sel == null)
        {
            invalidValueError("No item selected", "Please select an item, then try again.");
            return;
        }
        parts.remove(sel);
        search();
    }
    
    //  Creates error popup
    private void invalidValueError(String header, String msg)
    {
        // Error window
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
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
    
    public void setup(Inventory inv, int product_id)
    {
        this.inv = inv;
        this.product_id = product_id;
        id_field.setText(Integer.toString(product_id));
        
        //  Display Parts
        part_table.setItems(inv.getAllParts());
        added_parts_table.setItems(parts);
    }
}
