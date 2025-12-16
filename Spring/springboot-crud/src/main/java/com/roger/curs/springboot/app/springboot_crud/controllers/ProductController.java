package com.roger.curs.springboot.app.springboot_crud.controllers;

import com.roger.curs.springboot.app.springboot_crud.entities.Product;
import com.roger.curs.springboot.app.springboot_crud.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    private ProductValidation productValidation;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Product product = productService.findById(id).orElse(null);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", "error", "message", "product not found")
            );
        }

        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult bindingResult) {
        // productValidation.validate(product, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return validation(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult bindingResult) {
        try {
            // productValidation.validate(product, bindingResult);
            if (bindingResult.hasFieldErrors()) {
                return validation(bindingResult);
            }
            return ResponseEntity.ok(productService.update(product).orElseThrow());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error updating Product. Product Not Found."
                    ));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id).orElseThrow());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error deleting Product. Product Not Found."
                    ));
        }
    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        errors.put("status", "error");
        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
