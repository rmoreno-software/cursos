package com.roger.curs.springboot.error.springboot_error.controllers;

import com.roger.curs.springboot.error.springboot_error.exceptions.UserNotFoundException;
import com.roger.curs.springboot.error.springboot_error.models.domain.User;
import com.roger.curs.springboot.error.springboot_error.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        // int value = 100 / 0;
        int value = Integer.parseInt("10x");
        System.out.println(value);
        return "ok 200";
    }

    @GetMapping("/show/{id}")
    public User show(@PathVariable(name = "id") Long id) {
        return userService.findById(id).orElseThrow(() ->
                new UserNotFoundException("Usuario con id " + id + " no encontrado"));
    }
}
