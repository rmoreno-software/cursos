package com.roger.curs.springboot.error.springboot_error.services;


import com.roger.curs.springboot.error.springboot_error.models.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long id);

}
