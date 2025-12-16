package com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.ClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long> {
}
