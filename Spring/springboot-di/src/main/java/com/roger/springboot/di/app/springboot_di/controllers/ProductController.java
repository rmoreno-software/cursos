package com.roger.springboot.di.app.springboot_di.controllers;

import com.roger.springboot.di.app.springboot_di.models.Product;
import com.roger.springboot.di.app.springboot_di.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    @Qualifier("primaryProductService")
    private ProductService productService;

    @GetMapping("")
    public List<Product> list() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product show(@PathVariable Long id) {
        return productService.findById(id);
    }
}
