package com.roger.curso.springboot.webapp.springboot_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/home"})
    public String home() {
        return "redirect:/list";
    }

    @GetMapping("/forward")
    public String forward() {
        return "forward:/details";
    }
}
