package com.roger.curs.springboot.error.springboot_error;

import com.roger.curs.springboot.error.springboot_error.models.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    List<User> users() {
        List<User> users = new ArrayList<>();
        users.add(new User("Roger", "Moreno", 1L));
        users.add(new User("Pepe", "González", 2L));
        users.add(new User("Andrés", "Mena", 3L));
        users.add(new User("Alicia", "López", 4L));
        users.add(new User("Josefa", "Ramírez", 5L));
        return users;
    }

}
