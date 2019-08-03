package de.foyangtech.ecommerce.catalogmanager.service;

import de.foyangtech.ecommerce.catalogmanager.persistance.dao.UserDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DBinit implements CommandLineRunner {

    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    public DBinit(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create User
        User vanessa = new User(
                "Ngueneko",
                "Vanessa",
                "vngueneko",
                passwordEncoder.encode("abcdef"),
                "ROLE_ADMIN" );

        User steve = new User(
                "Ngueneko",
                "Steve",
                "sngueneko",
                passwordEncoder.encode("abcdef"),
                "ROLE_ADMIN" );

        User cyrille = new User(
                "Foyang",
                "Cyrille",
                "cfoyang",
                passwordEncoder.encode("abcdef"),
                "ROLE_ADMIN" );

        List<User> users = Arrays.asList(vanessa, steve, cyrille);

        //Save to database
        userDao.saveAll(users);

    }
}
