package de.foyangtech.ecommerce.catalogmanager.controller;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.LastProductsAdded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private LastProductsAdded lastProductsAdded;

    @GetMapping("index")
    public String index() {
        return  "index";
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("addedList",lastProductsAdded);
        return  "home";
    }

    @GetMapping("login")
    public String login() {return "login";}

    @GetMapping("logout_msg")
    public String logout() {return "logout_msg";}
}
