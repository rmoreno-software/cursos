package com.roger.curs.springboot.app.springboot_crud.services;

import com.roger.curs.springboot.app.springboot_crud.entities.Product;
import com.roger.curs.springboot.app.springboot_crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product>  update(Product product) {
        Optional<Product> productDbOpt = productRepository.findById(product.getId());
        Product p;
        if (productDbOpt.isPresent()) {
            p = productDbOpt.get();
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice((product.getPrice()));
            p.setSku(product.getSku());
            return Optional.of(productRepository.save(p));
        }
        throw new RuntimeException("Error al modificar producto. Producto no encontrado.");
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> productDbOpt = productRepository.findById(id);
        productDbOpt.ifPresentOrElse(p -> {
            productRepository.delete(p);
        }, () -> {
            throw new RuntimeException("Error al eliminar producto. Producto no encontrado.");
        });
        return productDbOpt;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku);
    }
}
