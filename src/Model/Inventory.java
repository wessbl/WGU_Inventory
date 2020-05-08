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
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    /**
     * Adds a part to the inventory
     * @param newPart 
     */
    public void addPart(Part newPart)
    {
        //TODO prohibit duplicates (maybe only dupe ID's... and not sure this class assigns those)
        allParts.add(newPart);
    }
    
    /**
     * Adds a product to the inventory
     * @param newProduct 
     */
    public void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    /**
     * Gets a part from a part's ID
     * @param partId
     * @return 
     */
    public Part lookupPart(int partId)
    {
        for (Part p : allParts)
            if (p.getId() == partId)
                return p;
        //TODO this is wrong!
        return new Part(0, "", 0, 0, 0, 0);
    }
    
    /**
     * Gets a product from a product's ID
     * @param productId
     * @return 
     */
    public Product lookupProduct(int productId)
    {
        for (Product p : allProducts)
            if (p.getId() == productId)
                return p;
        //TODO this is wrong!
        return new Product(0, "", 0, 0, 0, 0);
    }
    
    /**
     * Gets all parts that contain a given string in their name
     * @param partName
     * @return 
     */
    public ObservableList<Part> lookupPart(String partName)
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
    public ObservableList<Product> lookupProduct(String productName)
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
    public void updatePart(int index, Part newPart)
    {
        Part p = lookupPart(index);
        p = newPart;   //TODO test
    }
    
    /**
     * Updates product variables
     * @param index
     * @param newProduct 
     */
    public void updateProduct(int index, Product newProduct)
    {
        Product p = lookupProduct(index);
        p = newProduct;
    }
    
    /**
     * Removes a part from the inventory
     * @param selectedPart
     * @return 
     */
    public boolean deletePart(Part selectedPart)
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
    public boolean deleteProduct(Product selectedProduct)
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
    public ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    /**
     * Gets a list of all products
     * @return 
     */
    public ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
