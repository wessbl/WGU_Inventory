/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: ModifyPartController

    This class controls the ModifyPart view. It validates and sends entered data
    to the model for storage. It must be given variables as specified by the
    setup() method in order to modify and preserve data among all windows in the
    project.
 */
package Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wessl
 */
public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup Part_Type;
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
    private Button cancel_button;
    @FXML
    private Button save_button;
    @FXML
    private Label com_mach_label;
    @FXML
    private TextField com_mach_field;
    @FXML
    private RadioButton inhouse_button;
    @FXML
    private RadioButton outsourced_button;
    
    private Inventory inv;
    private Part part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Default to In-House option
        id_field.setDisable(true);
        com_mach_label.setText("Machine ID");
        com_mach_field.setPromptText("Mach ID");
    }

    /**
     * Goes back to MainScreen immediately, no confirmation
     * @param event (cancel_button click)
     * @throws IOException 
     */
    @FXML
    private void cancel(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChangeWindow("MainScreen", stage);
    }

    /**
     * A generic method that checks the entered data, and saves if valid. If
     * not valid, presents an error window for user and cancels the save. 
     * @return success or fail boolean.
     */    
    private boolean save() throws Exception
    {
        String name = name_field.getText().trim();
        String com_mach = com_mach_field.getText().trim();
        
        //  Verify text boxes are filled out (we'll check numbers later)
        if (name.isEmpty())
        {
            InvalidValueError("The \"Name\" field cannot be empty");
            name_field.requestFocus();
            return false;
        }
        
        if (com_mach.isEmpty())
        {
            InvalidValueError("The \"" + com_mach_field.getPromptText() + "\" field cannot be empty");
            com_mach_field.requestFocus();
            return false;
        }
        
        //  Check stock is an int
        try{Integer.parseInt(stock_field.getText().trim());}
        catch (NumberFormatException e)
        {
            //  Error window: "Stock must be an integer"
            InvalidValueError("The value for \"Stock\" must be an integer.");
            stock_field.requestFocus();
            return false;
        }
        int stock = Integer.parseInt(stock_field.getText().trim());
        
        //  Check price is a double
        try{Double.parseDouble(price_field.getText().trim());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Price/Cost\" must be a number.");
            price_field.requestFocus();
            return false;
        }
        double price = Double.parseDouble(price_field.getText().trim());
        
        //  Check if max is an int
        try{Integer.parseInt(max_field.getText().trim());}
        catch (NumberFormatException e)
        {
            //  Error window: "Max must be an integer"
            InvalidValueError("The value for \"Max\" must be an integer.");
            max_field.requestFocus();
            return false;
        }
        int max = Integer.parseInt(max_field.getText().trim());
        
        //  Check if min is an int
        try{Integer.parseInt(min_field.getText().trim());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Min\" must be an integer.");
            min_field.requestFocus();
            return false;
        }
        int min   = Integer.parseInt(min_field.getText().trim());
        
        //  Check if machine id is an int (for In-House)
        boolean inHouse = inhouse_button.isSelected();
        if (inHouse)
        {
            try
            {
                Integer.parseInt(com_mach_field.getText().trim());
            } catch (NumberFormatException e)
            {
                InvalidValueError("The \"" + com_mach_field.getPromptText() + "\" field must be an int");
                com_mach_field.requestFocus();
                return false;
            }
        }

        // Check for valid integer values: Min, Max, Stock
        if (min < 0)
        {
            InvalidValueError("Min must be 0 or greater");
            min_field.requestFocus();
            return false;
        }
        if (max < 1)
        {
            InvalidValueError("Max must be 1 or greater");
            max_field.requestFocus();
            return false;
        }
        if (min > max)
        {
            InvalidValueError("The value for \"Min\" cannot be greater than \"Max\".");
            min_field.requestFocus();
            return false;
        }
        if (stock > max || stock < min)
        {
            InvalidValueError("The value for \"Stock\" must be between Min and Max.");
            stock_field.requestFocus();
            return false;
        }
        
        /*
        Save the data:
            We need 4 different ways of saving:
                - Original part was In-house, and this value did not change
                - Original part was In-house, but this value changed
                - Original part was Outsourced, and this value did not change
                - Original part was Outsourced, but value changed
        
            If the value did not change, simply update it.
            If it changed, add the new, delete the old
        */
        boolean inHouse_part = part instanceof InHouse;
        
        if (inHouse_part)
        {
            if (inHouse)    //  From inHouse to inHouse
            {
                Inventory.updatePart(part.getId(), new InHouse(
                    part.getId(),
                    name, 
                    price,
                    stock,
                    min,
                    max,
                    Integer.parseInt(com_mach)
                ));
            }
            
            else            //  From inHouse to Outsourced
            {
                Inventory.addPart(new Outsourced(
                    part.getId(),
                    name, 
                    price,
                    stock,
                    min,
                    max,
                    com_mach
                ));
                Inventory.deletePart(part);
            }
        }
        
        else //Oursourced originally
        {
            if (!inHouse)   //  From Outsourced to Outsourced
            {               
                Inventory.updatePart(part.getId(), new Outsourced(
                    part.getId(),
                    name, 
                    price,
                    stock,
                    min,
                    max,
                    com_mach
                ));
            }
            else            //  From Outsourced to InHouse
            {
                Inventory.addPart(new InHouse(
                    part.getId(),
                    name, 
                    price,
                    stock,
                    min,
                    max,
                    Integer.parseInt(com_mach)
                ));
                Inventory.deletePart(part);
            }
        }
        
        return true;
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
     * Changes the view/controller back to MainScreen
     * @param window A text that represents the window to go to
     * @param stage The current stage
     * @throws IOException 
     */
    private void ChangeWindow(String window, Stage stage) throws IOException
    {
        //  Get Loader & load it
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        Parent root = loader.load();
        
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
     * Changes form for an In-House part
     * @param event (inHouse_button click)
     */
    @FXML
    private void in_house(MouseEvent event) {
        com_mach_field.setText("");
        com_mach_label.setText("Machine ID");
        com_mach_field.setPromptText("Mach ID");
    }

    /**
     * Changes form for an Outsourced part
     * @param event (outsourced_button click)
     */
    @FXML
    private void outsourced(MouseEvent event) {
        com_mach_field.setText("");
        com_mach_label.setText("Company Name");
        com_mach_field.setPromptText("Company Name");
    }

    /**
     * User can push enter in a field to save
     * @param event (enter is pressed while editing any field on the form)
     * @throws IOException 
     */
    @FXML
    private void onEnter(ActionEvent event) throws IOException, Exception {
        if (save())
        {
            //  Go back to MainScreen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeWindow("MainScreen", stage);
        }
    }

    /**
     * Save button
     * @param event (save_button click)
     * @throws IOException 
     */
    @FXML
    private void save_click(MouseEvent event) throws IOException, Exception {
        if(save())
        {
            //  Go back to MainScreen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeWindow("MainScreen", stage);
        }
    }
    
    /**
     * Gives this class necessary variables from instantiating class. Must be
     * called before switching to this controller's window.
     * @param inv the inventory
     * @param part_id the part to modify
     */
    public void setup(Inventory inv, Part p)
    {
        this.inv = inv;
        this.part = p;
        
        //  Display type specific data
        if (p instanceof InHouse)
        {
            inhouse_button.setSelected(true);
            com_mach_field.setText(Integer.toString(((InHouse) p).getMachineId()));
        }
        else
        {
            outsourced_button.setSelected(true);
            com_mach_field.setText(((Outsourced) p).getCompanyName());
        }
        id_field.setText(Integer.toString(p.getId()));
        name_field.setText(p.getName());
        stock_field.setText(Integer.toString(p.getStock()));
        price_field.setText(Double.toString(p.getPrice()));
        max_field.setText(Integer.toString(p.getMax()));
        min_field.setText(Integer.toString(p.getMin()));
    }
}
