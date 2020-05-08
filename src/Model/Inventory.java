/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: Inventory

    This class holds and controls all parts and products for the project.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wessl
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     * Adds a part to the inventory
     * @param newPart 
     */
    public static void addPart(Part newPart)
    {
        //TODO prohibit duplicates (maybe only dupe ID's... and not sure this class assigns those)
        allParts.add(newPart);
    }
    
    /**
     * Adds a product to the inventory
     * @param newProduct 
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    /**
     * Gets a part from a part's ID
     * @param partId
     * @return 
     */
    public static Part lookupPart(int partId) throws Exception
    {
        for (Part p : allParts)
            if (p.getId() == partId)
                return p;
        throw new Exception("lookupPart was given part ID that does not exist");
    }
    
    /**
     * Gets a product from a product's ID
     * @param productId
     * @return 
     */
    public static Product lookupProduct(int productId) throws Exception
    {
        for (Product p : allProducts)
            if (p.getId() == productId)
                return p;
        throw new Exception("lookupPart was given part ID that does not exist");

    }
    
    /**
     * Gets all parts that contain a given string in their name
     * @param partName
     * @return 
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        //  Add parts with matching string sequences to "find" list
        List<Part> find = new ArrayList<Part>();
        partName = partName.trim().toLowerCase();
        for (Part p : allParts)
            if (p.getName().toLowerCase().contains(partName))
                find.add(p);
        
        //  Report what was found as an ObservableList
        ObservableList<Part> found = FXCollections.observableList(find);
        return found;
    }
    
    /**
     * Gets all products that contain a given string in their name
     * @param productName
     * @return 
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        //  Add products with matching string sequences to "find" list
        List<Product> find = new ArrayList<Product>();
        productName = productName.trim().toLowerCase();
        for (Product p : allProducts)
            if (p.getName().toLowerCase().contains(productName))
                find.add(p);
        
        //  Report what was found as an ObservableList
        ObservableList<Product> found = FXCollections.observableList(find);
        return found;
    }
    
    /**
     * Updates part variables
     * @param index
     * @param newPart 
     */
    public static void updatePart(int index, Part newPart) throws Exception
    {
        Part p = lookupPart(index);
        p.setId(newPart.getId());
        p.setMax(newPart.getMax());
        p.setMin(newPart.getMin());
        p.setName(newPart.getName());
        p.setPrice(newPart.getPrice());
        p.setStock(newPart.getStock());
        if (p instanceof InHouse)
        {
            ((InHouse) p).setMachineId(((InHouse) newPart).getMachineId());
        } else {
            ((Outsourced) p).setCompanyName(((Outsourced) newPart).getCompanyName());
        }
    }
    
    /**
     * Updates product variables
     * @param index
     * @param newProduct 
     */
    public static void updateProduct(int index, Product newProduct) throws Exception
    {
        Product p = lookupProduct(index);
        p.setId(newProduct.getId());
        p.setMax(newProduct.getMax());
        p.setMin(newProduct.getMin());
        p.setName(newProduct.getName());
        p.setPrice(newProduct.getPrice());
        p.setStock(newProduct.getStock());
    }
    
    /**
     * Removes a part from the inventory
     * @param selectedPart
     * @return 
     */
    public static boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        return false;   //TODO I'm assuming boolean is to report success?
    }
    
    /**
     * Removes a product from the inventory
     * @param selectedProduct
     * @return 
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }
    
    /**
     * Gets a list of all parts
     * @return 
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    /**
     * Gets a list of all products
     * @return 
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
