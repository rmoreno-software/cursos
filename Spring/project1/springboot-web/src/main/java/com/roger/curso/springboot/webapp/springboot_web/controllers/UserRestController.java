package com.roger.curso.springboot.webapp.springboot_web.controllers;

import com.roger.curso.springboot.webapp.springboot_web.models.User;
import com.roger.curso.springboot.webapp.springboot_web.models.dtos.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/details")
    public Map<String, Object> details() {
        User user = new User("Roger", "Moreno");
        Map<String, Object> body = new HashMap<>();

        body.put("title", "Hola mundo Spring Boot");
        body.put("user", user);

        return body;
    }

    @GetMapping("/details-dto")
    public UserDto detailsMap() {
        UserDto userDto = new UserDto();
        User user = new User("Roger", "Moreno");

        userDto.setUser(user);
        userDto.setTitle("Hola mundo Spring Boot");

        return userDto;
    }

    @GetMapping("/list")
    public List<User> list() {
        User user1 = new User("Roger", "Moreno");
        User user2 = new User("Alicia", "Lopez");
        User user3 = new User("Reyes", "Amador");
        User user4 = new User("Xavi", "Lorente");

        List<User> users = Arrays.asList(user1, user2, user3, user4);

        return users;
    }
}
