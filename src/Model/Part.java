/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    public void setStock(int stock)
    {
        this.stock = stock;
    }
    
    public void setMin(int min)
    {
        this.min = min;
    }
    
    public void setMax(int max)
    {
        this.max = max;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getPrice()
    {
        return price;
    }
    
    public int getStock()
    {
        return stock;
    }
    
    public int getMin()
    {
        return min;
    }
    
    public int getMax()
    {
        return max;
    }
}
