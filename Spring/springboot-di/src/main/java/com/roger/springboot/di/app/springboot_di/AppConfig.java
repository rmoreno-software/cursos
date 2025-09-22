package com.roger.springboot.di.app.springboot_di;

import com.roger.springboot.di.app.springboot_di.repositories.ProductRepository;
import com.roger.springboot.di.app.springboot_di.repositories.ProductRepositoryJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

@Configuration
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("classpath:json/product.json")
    private Resource resource;

    @Bean
    ProductRepository productRepositoryJson() {
        return new ProductRepositoryJson(resource);
    }
}
