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
public class InHouse extends Part
{
    private int machineId;
    
    public InHouse(int id, String name, double price, int stock, 
                   int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }
    
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }
    
    public int getMachineId()
    {
        return machineId;
    }
}