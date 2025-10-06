package com.roger.curs.springboot.app.springboot_crud.repositories;

import com.roger.curs.springboot.app.springboot_crud.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
