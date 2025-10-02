package com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("select s from Student s left join fetch s.courses where s.id = ?1")
    Optional<Student> findOneWithCourses(Long id);
}
