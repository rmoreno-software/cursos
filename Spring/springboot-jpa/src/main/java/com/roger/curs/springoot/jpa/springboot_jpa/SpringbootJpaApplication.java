package com.roger.curs.springoot.jpa.springboot_jpa;

import com.roger.curs.springoot.jpa.springboot_jpa.entities.Person;
import com.roger.curs.springoot.jpa.springboot_jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Iterable<Person> persons = repository.findAll();
//
//		persons.forEach(p -> System.out.println(p));
//
//		Iterable<Person> personsJava = repository.findByProgrammingLanguage("Java");
//
//		personsJava.forEach(p -> System.out.println(p));

//		Iterable<Person> personsJava = repository.buscaPerProgrammingLanguage("Java");
//
//		personsJava.forEach(p -> System.out.println(p));

		Iterable<Person> personsJava = repository.findByProgrammingLanguageAndName("Java", "Maria");

		personsJava.forEach(p -> System.out.println(p));
	}
}
