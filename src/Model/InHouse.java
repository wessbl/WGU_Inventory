/* * * * * * * * * * * * * * * * *
 *  Author:     Wess Lancaster   *
 *  Date:       May 2020         *
 *  Project:    WGU_Inventory    *
 * * * * * * * * * * * * * * * * *

    Class: InHouse

    This class represents a single InHouse Part object.
 */
package Model;

/**
 *
 * @author wessl
 */
public class InHouse extends Part
{
    private int machineId;
    
    /**
     * Constructor for an In-House Part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId 
     */
    public InHouse(int id, String name, double price, int stock, 
                   int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }
    
    /**
     * Sets machine id to new value
     * @param machineId 
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }
    
    /**
     * Gets machine id
     * @return 
     */
    public int getMachineId()
    {
        return machineId;
    }
}