package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.data.User;
import recipes.repositories.UserRepository;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void save(User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
        }
    }
}
