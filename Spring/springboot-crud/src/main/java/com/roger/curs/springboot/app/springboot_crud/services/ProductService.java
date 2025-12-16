package com.roger.curs.springboot.app.springboot_crud.services;

import com.roger.curs.springboot.app.springboot_crud.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    Optional<Product> update(Product product);
    Optional<Product> delete(Long id);
    boolean existsBySku(String sku);

}
