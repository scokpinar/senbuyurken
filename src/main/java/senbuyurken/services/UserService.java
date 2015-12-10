/**
 *
 */
package senbuyurken.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senbuyurken.entities.User;
import senbuyurken.repositories.UserRepository;

import java.util.List;

/**
 * @author Siva
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Boolean checkUser(String email) {
        return userRepository.findByEmailAddress(email) == null;
    }
}
