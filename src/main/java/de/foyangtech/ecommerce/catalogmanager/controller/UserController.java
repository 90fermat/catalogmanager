package de.foyangtech.ecommerce.catalogmanager.controller;

import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.UserDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.User;
import de.foyangtech.ecommerce.catalogmanager.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> addDefaultUser() {
        User user = new User();
        user.setFirstname("Vanessa");
        user.setLastname("Ngueneko");
        user.setUsername("vngueneko");
        user.setPassword(passwordEncoder.encode("abcdef"));
        user.setRole("ROLE_ADMIN");

        User savedUser = userDao.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @GetMapping( produces = "application/json")
    public ResponseEntity<User> getDefaultUser(@RequestParam("username") String username) {

        User userByUsername = userDao.findUserByUsername(username);
        return new ResponseEntity<>(userByUsername, HttpStatus.OK);
    }

    @GetMapping("/create")
    public String showCreateForm(@AuthenticationPrincipal UserPrincipal user, Model model) {

        model.addAttribute("user", user);
        return "user/user_create";
    }

    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal UserPrincipal user, Model model) {

        model.addAttribute("user", user);
        return "user/user_profile";
    }

    @GetMapping("/edit")
    public String showUserEdit(@AuthenticationPrincipal UserPrincipal user, Model model) {

        model.addAttribute("user", user);
        return "user/user_edit";
    }

}
