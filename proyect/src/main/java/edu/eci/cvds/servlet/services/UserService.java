package edu.eci.cvds.servlet.services;

import edu.eci.cvds.servlet.model.User;
import edu.eci.cvds.servlet.repositories.UserRepository;
import edu.eci.cvds.exception.Exception;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private static UserRepository userRepository;

    public static User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new Exception("No encontramos el usuario con el id: " + id);
        }
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new Exception("No encontramos el usuario con el correo: " + email);
        }
    }

    

}
