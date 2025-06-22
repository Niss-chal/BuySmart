/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import buysmart.dao.computersDAO;
import buysmart.model.computersModel;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author batas
 */
public class ComputerDAOTest {
    @Test
    public void testGetComputers() throws SQLException{
        List<computersModel> computers=computersDAO.getComputers();
        Assert.assertNotNull("Computer list should not be null",computers);
        Assert.assertFalse("Computers list should not be empty",computers.isEmpty());
        computersModel computer=computers.get(0);
        Assert.assertNotNull("Image path should not be null",computer.getImagePath());
        Assert.assertNotNull("Description shouldnot be null",computer.getDescription());
        Assert.assertTrue("Price should be greater than 0",computer.getPrice()>0);
        
    }
}
