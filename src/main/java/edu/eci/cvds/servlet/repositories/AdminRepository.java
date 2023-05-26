package edu.eci.cvds.servlet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.servlet.model.Administrator;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, String> {
    
}
