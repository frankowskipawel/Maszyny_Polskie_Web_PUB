package pl.maszyny_polskie.controller;

import pl.maszyny_polskie.entity.Role;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.service.RoleServiceImpl;
import pl.maszyny_polskie.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RoleServiceImpl roleServiceImpl;

    @GetMapping("/login")
    public String loginGet(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "login";
    }

    @PostMapping("/login")
    public String submitForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResultUser, Model model) {

        User foundUser = userServiceImpl.findUserByUserName(user.getUserName());

        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            return "home";
        } else {
            model.addAttribute("error", "Błędy login lub hasło");
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(Model model, @ModelAttribute("user") User user) {
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(roleServiceImpl.findByRole("USER"));
        user.setRoles(roles);
        userServiceImpl.saveWithEncryptPassword(user);
        return "login";
    }
}
