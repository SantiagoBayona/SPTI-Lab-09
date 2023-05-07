package edu.eci.cvds.servlet.repositories;

import edu.eci.cvds.servlet.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long userId);
    User findByEmail(String userEmail);

}


