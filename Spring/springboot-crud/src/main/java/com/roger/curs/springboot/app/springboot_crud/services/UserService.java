package com.roger.curs.springboot.app.springboot_crud.services;

import com.roger.curs.springboot.app.springboot_crud.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    User save(User user);
    Optional<User> findByUsername(String username);

}
