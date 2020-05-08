/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: Part

    This class represents a single Part. Parts must be either In-House or
    Outsourced, so this is a parent class.
 */
package Model;

/**
 *
 * @author wessl
 */
public class Part
{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /**
     * Part constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max 
     */
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    
    /**
     * Sets the part id
     * @param id 
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * Sets the part name
     * @param name 
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Sets the part price
     * @param price 
     */
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    /**
     * Sets the part stock
     * @param stock 
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }
    
    /**
     * Sets the part min
     * @param min 
     */
    public void setMin(int min)
    {
        this.min = min;
    }
    
    /**
     * Sets the part max
     * @param max 
     */
    public void setMax(int max)
    {
        this.max = max;
    }
    
    /**
     * Gets the part id
     * @return 
     */
    public int getId()
    {
        return id;
    }
    
    /**
     * Gets the part name
     * @return 
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the part price
     * @return 
     */
    public double getPrice()
    {
        return price;
    }
    
    /**
     * Gets the part stock
     * @return 
     */
    public int getStock()
    {
        return stock;
    }
    
    /**
     * Gets the part min
     * @return 
     */
    public int getMin()
    {
        return min;
    }
    
    /**
     * Gets the part max
     * @return 
     */
    public int getMax()
    {
        return max;
    }
}
