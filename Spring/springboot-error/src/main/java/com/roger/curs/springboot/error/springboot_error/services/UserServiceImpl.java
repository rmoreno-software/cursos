package com.roger.curs.springboot.error.springboot_error.services;

import com.roger.curs.springboot.error.springboot_error.models.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private List<User> users;

    public UserServiceImpl() {
        this.users = new ArrayList<>();
        this.users.add(new User("Roger", "Moreno", 1L));
        this.users.add(new User("Pepe", "González", 2L));
        this.users.add(new User("Andrés", "Mena", 3L));
        this.users.add(new User("Alicia", "López", 4L));
        this.users.add(new User("Josefa", "Ramírez", 5L));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        for (User u : users) {
            if (u.getId().equals(id)) {
                user = u;
                break;
            }
        }
        return Optional.ofNullable(user);
    }
}
