package com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}
