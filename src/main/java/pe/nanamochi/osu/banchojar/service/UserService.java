package pe.nanamochi.osu.banchojar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPasswordMd5(username, password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
