package edu.eci.cvds.servlet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.servlet.repositories.AdminRepository;
import edu.eci.cvds.servlet.model.Administrator;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public Administrator addAdmin(Administrator administrator){
        return this.adminRepository.save(administrator);
    }

    public Administrator getAdmin(String adminName){
        return this.adminRepository.findById(adminName).get();
    }

    public List<Administrator> getAdmins(){
        return this.adminRepository.findAll();
    }

    public Administrator updateAdmin(Administrator administrator){
        if(this.adminRepository.existsById(administrator.getName())){
            return adminRepository.save(administrator);
        }
        
        return null;
    }

    public void deleteAdmin(String adminName){
        this.adminRepository.deleteById(adminName);
    }
}
