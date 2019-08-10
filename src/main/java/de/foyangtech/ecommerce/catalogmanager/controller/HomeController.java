package de.foyangtech.ecommerce.catalogmanager.controller;

import de.foyangtech.ecommerce.catalogmanager.persistance.model.LastProductsAdded;
import de.foyangtech.ecommerce.catalogmanager.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String home(Model model, @AuthenticationPrincipal UserPrincipal user) {
        model.addAttribute("addedList",lastProductsAdded);
        model.addAttribute("username",user.getUsername());
        return  "home";
    }

    @GetMapping("login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        if ("true".equals(error)){
            model.addAttribute("error", true);
        }
        return "login";
    }

    @GetMapping("logout_msg")
    public String logout() {return "logout_msg";}
}
