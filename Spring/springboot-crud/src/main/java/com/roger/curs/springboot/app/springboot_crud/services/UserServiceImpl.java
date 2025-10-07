package com.roger.curs.springboot.app.springboot_crud.services;

import com.roger.curs.springboot.app.springboot_crud.entities.Role;
import com.roger.curs.springboot.app.springboot_crud.entities.User;
import com.roger.curs.springboot.app.springboot_crud.repositories.RoleRepository;
import com.roger.curs.springboot.app.springboot_crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        Optional<Role> optRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optRole.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> optRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
