package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.data.MyUserDetail;
import recipes.data.User;
import recipes.repositories.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException(username + " not found user"));
        return user.map(MyUserDetail::new).get();
    }
}
