package edu.eci.cvds.services;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.eci.cvds.servlet.model.Administrator;
import edu.eci.cvds.servlet.services.AdminService; 

public class AdminServiceTest {
    
    @Autowired
    AdminService adminService;

    @Test
    public void shouldAddAdminToBD(){
        adminService.addAdmin(new Administrator("Admin Prueba", "567"));
        Administrator aux = adminService.getAdmin("Admin Prueba");
        Assert.assertEquals(adminService.getAdmin("Admin Prueba").toString(), aux.toString());
    }
}
