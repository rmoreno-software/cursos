package com.roger.curso.springboot.webapp.springboot_web.controllers;

import com.roger.curso.springboot.webapp.springboot_web.models.User;
import com.roger.curso.springboot.webapp.springboot_web.models.dtos.ParamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/var")
public class PathVariableController {

    @Value("${config.code}")
    private int code;

    @Value("${config.username}")
    private String username;

    @Value("${config.message}")
    private String message;

    @Value("${config.listOfValues}")
    private List<String> listOfValues;

    @Value("#{${config.valuesMap}}")
    private Map<String, Object> map;

    @Value("#{${config.valuesMap}.product}")
    private String product;

    @Autowired
    private Environment environment;

    @GetMapping("/baz/{message}")
    public ParamDto baz(@PathVariable String message) {
        ParamDto paramDto = new ParamDto();
        paramDto.setMessage(message);
        return paramDto;
    }

    @GetMapping("/mix/{product}/{id}")
    public Map<String, Object> mix(@PathVariable String product, @PathVariable Long id) {
        Map<String, Object> json = new HashMap<>();
        json.put("product", product);
        json.put("id", id);
        return json;
    }

    @PostMapping("/create")
    public User create(@RequestBody User user) {
        user.setName(user.getName().toUpperCase());
        return user;
    }

    @GetMapping("/values")
    public Map<String, Object> getValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("code", code);
        values.put("username", username);
        values.put("message", message);
        values.put("listOfValues", listOfValues);
        values.put("map", map);
        values.put("product", product);
        values.put("message2", environment.getProperty("config.message"));
        values.put("code2", environment.getProperty("config.code", Integer.class));

        return values;
    }
}
