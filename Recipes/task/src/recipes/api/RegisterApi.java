package recipes.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.data.User;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class RegisterApi {
    private final UserService userService;

    @Autowired
    public RegisterApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody User user) {
        userService.save(user);
    }
}
