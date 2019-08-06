package de.foyangtech.ecommerce.catalogmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("index")
    public String index() {return  "index";}

    @GetMapping("login")
    public String login() {return "login";}

    @GetMapping("logout_msg")
    public String logout() {return "logout_msg";}
}
