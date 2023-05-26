package edu.eci.cvds.services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


import edu.eci.cvds.servlet.model.Administrator;
import edu.eci.cvds.servlet.repositories.AdminRepository;
import edu.eci.cvds.servlet.services.AdminService; 


@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    
    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private Administrator administrator;

    @BeforeEach
    public void setUp(){
        this.administrator = new Administrator("Admin Prueba", "567");

    }

    @Test
    public void shouldAddAdminToBD(){
       when(adminRepository.save(any(Administrator.class))).thenReturn(administrator);
       Administrator savedAdmin = adminService.addAdmin(administrator);
       assertEquals(administrator, savedAdmin);
    }

    @Test
    public void shouldModifyAdmin(){
        when(adminRepository.existsById(anyString())).thenReturn(true);
        when(adminRepository.save(any(Administrator.class))).thenReturn(administrator);
        Administrator updatedAdmin = adminService.updateAdmin(administrator);
        assertEquals(updatedAdmin, administrator);
    }

    @Test
    public void shouldGetAdmin(){
        when(adminRepository.findById(anyString())).thenReturn(Optional.of(administrator));
        Administrator auxAdmin = adminService.getAdmin("Admin Prueba");
        assertEquals(administrator, auxAdmin);
    }

    @Test
    public void shouldGetAdmins(){
        List<Administrator> administrators = new ArrayList<Administrator>();
        administrators.add(administrator);
        when(adminRepository.findAll()).thenReturn(administrators);
        List<Administrator> administrators2 = adminService.getAdmins();
        assertEquals(administrators, administrators2);
    }

    @Test
    public void shouldDeleteAdmin(){
        doNothing().when(adminRepository).deleteById(anyString());
        adminService.deleteAdmin("Admin Prueba");
        verify(adminRepository, times(1)).deleteById("Admin Prueba");
    }
}
