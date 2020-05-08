/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: Outsourced

    This class represents a single Outsourced Part project
 */
package Model;

/**
 *
 * @author wessl
 */
public class Outsourced extends Part
{
    private String companyName;
    
    /**
     * Outsourced part constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName 
     */
    public Outsourced(int id, String name, double price, int stock, 
                      int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }
    
    /**
     * Sets company name
     * @param companyName 
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    /**
     * Gets company name
     * @return 
     */
    public String getCompanyName()
    {
        return companyName;
    }
}