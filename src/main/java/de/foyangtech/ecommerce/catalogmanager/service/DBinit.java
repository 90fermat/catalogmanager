package de.foyangtech.ecommerce.catalogmanager.service;

import de.foyangtech.ecommerce.catalogmanager.persistance.dao.ProductDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.dao.UserDao;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.Product;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.ProductImage;
import de.foyangtech.ecommerce.catalogmanager.persistance.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DBinit implements CommandLineRunner {

    private UserDao userDao;

    private ProductDao productDao;

    private ProductService productService;

    private PasswordEncoder passwordEncoder;

    public DBinit(UserDao userDao, PasswordEncoder passwordEncoder,
                  ProductDao productDao, ProductService productService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.productDao = productDao;
        this.productService = productService;
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

        //Create some products

        Product chiffonB = new Product(
                "chiffon blanc",
                "458788acde",
                12,
                4,
                "Chiffon GmbH",
                "http://chiffon.com",
                "chiffon",
                new ProductImage("src/main/resources/images/seamless.jpg"),
                new Date());
        Product chiffonT = new Product(
                "chiffon tandon",
                "451288acde",
                14,
                6,
                "Chiffon GmbH",
                "http://chiffon.com",
                "chiffon",
                new ProductImage("src/main/resources/images/seamless.jpg"),
                new Date());
        Product chiffonV = new Product(
                "chiffon vert",
                "351288acde",
                25,
                9,
                "Chiffon GmbH",
                "http://chiffon.com",
                "chiffon",
                new ProductImage("src/main/resources/images/seamless.jpg"),
                new Date());
        Product chiffonR = new Product(
                "chiffon rouge",
                "351288a41e",
                15,
                9,
                "Chiffon GmbH",
                "http://chiffon.com",
                "chiffon",
                new ProductImage("src/main/resources/images/seamless.jpg"),
                new Date());
        Product chiffonU = new Product(
                "chiffon malte",
                "123288acde",
                32,
                12,
                "Chiffon GmbH",
                "http://chiffon.com",
                "chiffon",
                new ProductImage("src/main/resources/images/seamless.jpg"),
                new Date());

        List<Product> products = Arrays.asList(chiffonB, chiffonR, chiffonT, chiffonU, chiffonV);

        productService.addAllProducts(products);

    }
}
