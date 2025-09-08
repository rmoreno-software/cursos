package com.roger.curso.springboot.webapp.springboot_web.controllers;

import com.roger.curso.springboot.webapp.springboot_web.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/details")
    public String details(Model model) {

        User user = new User("Roger", "Moreno");
        user.setEmail("roger.moreno@terrassa.cat");

        model.addAttribute("title", "Hola Mundo Spring Boot");
        model.addAttribute("user", user);

        return "details";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("title", "Listado de usuarios");
        return "list";
    }

    @ModelAttribute("users")
    public List<User> userModel() {
        return Arrays.asList(
                new User("Pepa", "Gonzalez"),
                new User("Lalo", "Pérez", "lalo@correo.com"),
                new User("Juanita", "Roe", "juana@correo.com"),
                new User("Andres", "Doe"));
    }
}
