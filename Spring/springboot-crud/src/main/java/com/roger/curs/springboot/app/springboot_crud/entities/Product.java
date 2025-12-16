package com.roger.curs.springboot.app.springboot_crud.entities;

import com.roger.curs.springboot.app.springboot_crud.validations.IsExistsDb;
import com.roger.curs.springboot.app.springboot_crud.validations.IsRequired;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @IsRequired(message = "{IsRequired.product.name}")
    @Size(min = 3, max = 30)
    private String name;

    @NotNull(message = "{NotNull.product.price}")
    @Min(20)
    private Integer price;

    @IsRequired
    private String description;

    @IsRequired
    @IsExistsDb
    private String sku;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
