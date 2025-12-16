package com.roger.curs.springoot.jpa.springboot_jpa.repositories;

import com.roger.curs.springoot.jpa.springboot_jpa.dto.PersonDTO;
import com.roger.curs.springoot.jpa.springboot_jpa.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage = ?1")
    List<Person> buscaPerProgrammingLanguage(String programmingLanguage);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.name, p.lastname from Person p")
    List<String[]> findPersonData();

    @Query("select p from Person p where p.id = ?1")
    Optional<Person> findOne(Long id);

    Iterable<Person> findByNameContaining(String name);

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select p.id from Person p where p.id=?1")
    Long getIdById(Long id);

    @Query("select concat(p.name, ' ', p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id =?1")
    Object findPersonDataFullById(Long id);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPersona();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllClassPersonPersonalized();

    @Query("select new com.roger.curs.springoot.jpa.springboot_jpa.dto.PersonDTO(p.name, p.lastname) from Person p")
    List<PersonDTO> findAllClassPersonDto();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findProgrammingLanguagesDistinct();

    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findProgrammingLanguagesDistinctCount();

    @Query("select concat(p.name, ' ', p.lastname) from Person p")
    List<String> findAllFullNameConcat();

    @Query("select p.name || ' ' || p.lastname from Person p")
    List<String> findAllFullNameConcat2();

    @Query("select upper(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findAllFullNameConcatUpper();

    @Query("select lower(p.name || ' ' || p.lastname) from Person p")
    List<String> findAllFullNameConcatLower();

    @Query("select p from Person p where p.id between 2 and 5")
    List<Person> findPersonBetween();

    @Query("select p from Person p where p.name between 'J' and 'Q'")
    List<Person> findPersonBetweenName();

    List<Person> findByIdBetween(Long id1, Long id2);

    @Query("select p from Person p where p.name between 'J' and 'Q' order by p.id desc")
    List<Person> findPersonBetweenNameOrderById();

    @Query("select p from Person p where p.id between 2 and 5 order by p.name asc, p.lastname desc")
    List<Person> findPersonBetweenOrderByName();

    List<Person> findByIdBetweenOrderByNameDescLastnameAsc(Long id1, Long id2);

    @Query("select p from Person p order by p.name desc")
    List<Person> getAll();

    List<Person> findAllByOrderByNameDesc();

    @Query("select count(p) from Person p")
    Long totalPerson();

    @Query("select min(p.id) from Person p")
    Long minId();

    @Query("select max(p.id) from Person p")
    Long maxId();

    @Query("select p.name, length(p.name) from Person p")
    List<Object[]> getNamesAndLength();

    @Query("select min(length(p.name)) from Person p")
    Integer getMinLengthName();

    @Query("select max(length(p.name)) from Person p")
    Integer getMaxLengthName();

    @Query("select min(p.id), max(p.id), sum(p.id), avg(length(p.name)), count(p.id) from Person p")
    Object getResumeAggregationFunction();

    @Query("select " +
            "p.name, length(p.name) " +
            "from Person p where length(p.name) = " +
                "(select min(length(p2.name)) from Person p2)")
    List<Object[]> getShortterName();

    @Query("select p from Person p where p.id = (select max(p2.id) from Person p2)")
    Optional<Person> getLastRegistration();

    @Query("select p from Person p where p.id in ?1")
    List<Person> getPersonsByIds(List<Long> ids);

    @Query("select p from Person p where p.id not in ?1")
    List<Person> getPersonsNotIn(List<Long> ids);
}
