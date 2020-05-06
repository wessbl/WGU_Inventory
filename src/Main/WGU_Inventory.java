/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.Inventory;
import Model.InHouse;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author wessl
 */
public class WGU_Inventory extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //  Create Inventory
        Inventory inv = new Inventory();
        CreateData(inv);
        
        //  Get loader & root
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
        Parent root = (Parent) loader.load();
        
        //  Give inventory to controller
	Controllers.MainScreenController c = loader.getController();
        c.setInventory(inv);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void CreateData(Inventory inv) {
        //  Create Parts
        Part nut = new InHouse(418, "Nut",     1.0, 100, 20, 300, 12);      //TODO this one labeled as Part? Should I do that to all?
        Part blt = new InHouse(123, "Bolt",    1.0, 100, 20, 300, 12);
        Part mtr = new InHouse(81,  "Motor",   25,  20,  10, 40,  3 );
        Part sdr = new InHouse(214, "Solder",  1.0, 100, 20, 150, 2 );
        Part wir = new InHouse(299, "Wire",    1.0, 200, 95, 450, 4 );
        Part bat = new InHouse(56,  "Battery", 5.1, 50,  20, 100, 95);
        Part cir = new InHouse(57,  "Circuit", 11,  50,  20, 100, 63);
        Part arm = new InHouse(59,  "Arm",     32,  10,  5,  20,  40);
        Part sns = new InHouse(3,   "Sensor",  15,  100, 20, 300, 42);
        
        Part gad = new Outsourced(323, "Gadget",  30, 6, 2, 20, "Gadget Co"   );
        Part ion = new Outsourced(323, "Ionizer", 60, 2, 2, 10, "Ion Inc"     );
        Part pla = new Outsourced(323, "Plasma",  99, 9, 7, 20, "Star City"   );
        Part srv = new Outsourced(323, "Servo",   10, 5, 2, 10, "RoboCo"      );
        Part wt  = new Outsourced(323, "Weight",  1,  6, 5, 20, "Lead Lmtd"   );
        Part gr  = new Outsourced(323, "Gear",    2,  6, 2, 20, "Gary's Gears");
        
        //  Create products
        Product cmp = new Product(1, "Computer",      400, 50,  25, 100);
        Product rbt = new Product(1, "Robot",         999, 25,  10, 50 );
        Product car = new Product(1, "RC Car",        25,  100, 50, 200);
        Product cam = new Product(1, "Camera",        50,  100, 50, 200);
        Product ls  = new Product(1, "LightSword",    799, 25,  10, 50 );
        Product brn = new Product(1, "Brain Implant", 999, 75,  50, 150);
        
        //  Add Assoc Parts to all Products
        cmp.addAssociatedPart(nut);
        cmp.addAssociatedPart(blt);
        cmp.addAssociatedPart(sdr);
        cmp.addAssociatedPart(wir);
        cmp.addAssociatedPart(cir);
        
        rbt.addAssociatedPart(nut);
        rbt.addAssociatedPart(blt);
        rbt.addAssociatedPart(srv);
        rbt.addAssociatedPart(arm);

        car.addAssociatedPart(mtr);
        car.addAssociatedPart(bat);
        car.addAssociatedPart(gad);
        car.addAssociatedPart(gr);

        cam.addAssociatedPart(sns);
        cam.addAssociatedPart(wir);

        ls.addAssociatedPart(sdr);
        ls.addAssociatedPart(wir);
        ls.addAssociatedPart(ion);
        ls.addAssociatedPart(pla);

        brn.addAssociatedPart(wir);
        brn.addAssociatedPart(sns);
        brn.addAssociatedPart(bat);
        brn.addAssociatedPart(cir);
        brn.addAssociatedPart(ion);
        
        //  Add items to inv
        inv.addPart(wt);
        inv.addPart(blt);
        inv.addPart(mtr);
        inv.addPart(sdr);
        inv.addPart(wir);
        inv.addPart(bat);
        inv.addPart(cir);
        inv.addPart(arm);
        inv.addPart(sns);
        inv.addPart(gad);
        inv.addPart(ion);
        inv.addPart(pla);
        inv.addPart(srv);
        inv.addPart(wt);
        inv.addPart(gr);
        
        inv.addProduct(cmp);
        inv.addProduct(rbt);
        inv.addProduct(car);
        inv.addProduct(cam);
        inv.addProduct(ls);
        inv.addProduct(brn);
    }
    
}
