/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package test;

import buysmart.dao.ComputersDAO;
import buysmart.model.ComputersModel;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class ComputerDAOTestIT {
     @Test
    public void testGetComputers() throws SQLException{
        List<ComputersModel> computers=ComputersDAO.getComputers();
        //basic assertions
        Assert.assertNotNull("Computer list should not be null",computers);
        Assert.assertFalse("Computers list should not be empty",computers.isEmpty());
        //test first item
        ComputersModel computer=computers.get(0);
        Assert.assertNotNull("Image path should not be null",computer.getImagePath());
        Assert.assertNotNull("Description shouldnot be null",computer.getDescription());
        Assert.assertTrue("Price should be greater than 0",computer.getPrice()>0);
        
    }
 
    
}
