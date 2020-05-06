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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField inv_field;
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
    private Inventory inv;
    
    public ModifyPartController(Inventory inv)
    {
        this.inv = inv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO set on close request?

        // Default to In-House option
        id_field.setDisable(true);
        com_mach_label.setText("Machine ID");
        com_mach_field.setPromptText("Mach ID");
        
        //  TODO auto-populate values
    }

    @FXML
    private void cancel(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChangeWindow("MainScreen", stage);
    }

    //  A generic method that checks the entered data, and saves if valid. Returns success or fail
    private boolean save()
    {
        //  Verify text boxes are filled out (we'll check numbers later)
        if (name_field.getText().trim().isEmpty())
        {
            InvalidValueError("The \"Name\" field cannot be empty");
            name_field.requestFocus();
            return false;
        }
        // TODO is company/machine id necessary?
        if (com_mach_field.getText().trim().isEmpty())
        {
            InvalidValueError("The \"" + com_mach_field.getPromptText() + "\" field cannot be empty");
            com_mach_field.requestFocus();
            return false;
        }
        
        //  Check inv is an int
        try{int inv = Integer.parseInt(inv_field.getText());}
        catch (NumberFormatException e)
        {
            //  Error window: "Inv must be an integer"
            InvalidValueError("The value for \"Inv\" must be an integer.");
            inv_field.requestFocus();
            return false;
        }
        
        //  Check price is a double
        try{double price = Double.parseDouble(price_field.getText());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Price/Cost\" must be a number.");
            price_field.requestFocus();
            return false;
        }
        
        //  Check if max is an int
        try{int max = Integer.parseInt(max_field.getText());}
        catch (NumberFormatException e)
        {
            //  TODO Error window: "Max must be an integer"
            InvalidValueError("The value for \"Max\" must be an integer.");
            max_field.requestFocus();
            return false;
        }
        
        //  Check if min is an int
        try{int min = Integer.parseInt(min_field.getText());}
        catch (NumberFormatException e)
        {
            InvalidValueError("The value for \"Min\" must be an integer.");
            min_field.requestFocus();
            return false;
        }
        
        // Check for valid integer values: Min, Max, Inv
        int inv = Integer.parseInt(inv_field.getText());
        int min = Integer.parseInt(min_field.getText());
        int max = Integer.parseInt(max_field.getText());
        
        if (min < 0)
        {
            InvalidValueError("Min must be 0 or greater");
            min_field.setText("");
            min_field.requestFocus();
            return false;
        }
        if (max < 1)
        {
            InvalidValueError("Max must be 1 or greater");
            max_field.setText("");
            max_field.requestFocus();
            return false;
        }
        if (min > max)
        {
            InvalidValueError("The value for \"Min\" cannot be greater than \"Max\".");
            min_field.requestFocus();
            return false;
        }
        if (inv > max || inv < min)
        {
            InvalidValueError("The value for \"Inv\" must be between Min and Max.");
            inv_field.requestFocus();
            return false;
        }
        
        // TODO Do your saving...
        
        return true;
    }
    
    private int stringInt (String num)
    {
        return Integer.parseInt(num);
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
    
    private void ChangeWindow(String window, Stage stage) throws IOException
    {
        //  Get Loader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/" + window + ".fxml"));
        
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
    private void in_house(MouseEvent event) {
        com_mach_field.setText("");
        com_mach_label.setText("Machine ID");
        com_mach_field.setPromptText("Mach ID");
    }

    @FXML
    private void outsourced(MouseEvent event) {
        com_mach_field.setText("");
        com_mach_label.setText("Company Name");
        com_mach_field.setPromptText("Company Name");
    }

    @FXML
    private void onEnter(ActionEvent event) throws IOException {
        if (save())
        {
            //  Go back to MainScreen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeWindow("MainScreen", stage);
        }
    }

    @FXML
    private void save_click(MouseEvent event) throws IOException {
        if(save())
        {
            //  Go back to MainScreen
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ChangeWindow("MainScreen", stage);
        }
    }
    
    public void setInventory(Inventory inv)
    {
        this.inv = inv;
    }
}
