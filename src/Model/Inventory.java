/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public void addPart(Part newPart)
    {
        //TODO prohibit duplicates (maybe only dupe ID's... and not sure this class assigns those)
        allParts.add(newPart);
    }
    
    public void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partId)
    {
        for (Part p : allParts)
            if (p.getId() == partId)
                return p;
        //TODO this is wrong!
        return new Part(0, "", 0, 0, 0, 0);
    }
    
    public Product lookupProduct(int productId)
    {
        for (Product p : allProducts)
            if (p.getId() == productId)
                return p;
        //TODO this is wrong!
        return new Product(0, "", 0, 0, 0, 0);
    }
    
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
    
    public void updatePart(int index, Part newPart)
    {
        Part p = lookupPart(index);
        p = newPart;   //TODO test
    }
    
    public void updateProduct(int index, Product newProduct)
    {
        Product p = lookupProduct(index);
        p = newProduct;
    }
    
    public boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        return false;   //TODO I'm assuming boolean is to report success?
    }
    
    public boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }
    
    public ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }
}
