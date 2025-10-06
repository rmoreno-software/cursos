package com.roger.curs.springboot.app.springboot_crud.repositories;

import com.roger.curs.springboot.app.springboot_crud.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    boolean existsBySku(String sku);

}
