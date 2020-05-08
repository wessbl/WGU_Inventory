/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: Product

    This class represents a single Product. Products also contain a list of
    associated Part objects.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wessl
 */
public class Product 
{
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * Constructor for the product
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max 
     */
    public Product(int id, String name, double price, int stock, 
                    int min, int max)
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    
    /**
     * Sets the product ID
     * @param id 
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * Sets the product name
     * @param name 
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Sets the product price
     * @param price 
     */
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    /**
     * Sets the product desired stock level
     * @param stock 
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }
    
    /**
     * Sets the product minimum stock level
     * @param min 
     */
    public void setMin(int min)
    {
        this.min = min;
    }
    
    /**
     * Sets the product maximum stock level
     * @param max 
     */
    public void setMax(int max)
    {
        this.max = max;
    }
    
    /**
     * Sets the product price (integer version)
     * @param price 
     */
    public void setPrice(int price)
    {
        this.price = price;
    }
    
    /**
     * Gets the product ID
     * @return 
     */
    public int getId()
    {
        return id;
    }
    
    /**
     * Gets the product name
     * @return 
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the product price
     * @return 
     */
    public double getPrice()
    {
        return price;
    }
    
    /**
     * Gets the product stock
     * @return 
     */
    public int getStock()
    {
        return stock;
    }
    
    /**
     * Gets the product min
     * @return 
     */
    public int getMin()
    {
        return min;
    }
    
    /**
     * Gets the product max
     * @return 
     */
    public int getMax()
    {
        return max;
    }
    
    /**
     * Adds a part to the product
     * @param part 
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }
    
    /**
     * Removes a part from the product
     * @param selectedPart
     * @return 
     */
    public boolean deleteAssociatedPart(Part selectedPart)
    {
        if(associatedParts.contains(selectedPart))
        {
            associatedParts.remove(selectedPart);
            return true;
        }
        return false;
    }
    
    /**
     * Gets the list of all parts in the product
     * @return 
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }
}
