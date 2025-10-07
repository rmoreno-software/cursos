package com.roger.curs.springboot.app.springboot_crud.repositories;

import com.roger.curs.springboot.app.springboot_crud.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
