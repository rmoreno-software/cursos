package com.roger.curs.springoot.jpa.springboot_jpa.repositories;

import com.roger.curs.springoot.jpa.springboot_jpa.entities.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
