package com.roger.curs.springoot.jpa.springboot_jpa;

import com.roger.curs.springoot.jpa.springboot_jpa.dto.PersonDTO;
import com.roger.curs.springoot.jpa.springboot_jpa.entities.Person;
import com.roger.curs.springoot.jpa.springboot_jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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

//		Iterable<Person> personsJava = repository.findByProgrammingLanguageAndName("Java", "Maria");
//
//		personsJava.forEach(p -> System.out.println(p));

//		List<String[]> personData = repository.findPersonData();
//
//		personData.forEach(p -> System.out.println(p[0] + " " + p[1]));

//		Iterable<Person> personData = repository.findByNameContaining("ria");
//
//		personData.forEach(p -> System.out.println(p));
//
//		findOne();

		// create();

		// update();

		// delete();
		// delete2();

		// personalizedQuery();
		//personalizedQuery2();

		//personalizedQueryDistinct();

		// personalizedQueryConcatUpperAndLower();

		personalizedQueryBetween();

	}

	@Transactional(readOnly = true)
	public void findOne() {
		Person person = null;
//		Optional<Person> optPerson = repository.findById(8L);
		Optional<Person> optPerson = repository.findOne(3L);
		if(optPerson.isPresent()) {
			person = optPerson.get();
			System.out.println(person.toString());
		} else {
			System.out.println("Person not found");
		}

	}

	@Transactional
	public void create() {

		System.out.println("CREANT NOU USUARI");

		Scanner scanner = new Scanner(System.in);
		System.out.print("Inserta el nom: ");
		String name = scanner.next();
		System.out.print("Inserta el cognom: ");
		String lastName = scanner.next();
		System.out.print("Inserta el llenguatge de programació: ");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastName, programmingLanguage);
		Person personNew = repository.save(person);
		System.out.printf("Nou usuari creat: %s%n", personNew);
	}

	@Transactional
	public void update() {
		System.out.println("MODIFICANT USUARI");
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introdueix el ID de la persona: ");
		Long id = scanner.nextLong();
		Optional<Person> optPerson = repository.findById(id);

		optPerson.ifPresentOrElse(person -> {
			System.out.println("Usuari trobat: " + person);
			System.out.print("Introdueix el nou llenguatge de programació: ");
			String pl = scanner.next();
			person.setProgrammingLanguage(pl);
			Person personUpdated = repository.save(person);
			System.out.println("Persona actualitzada: " + personUpdated);
		}, () -> System.out.println("Usuari no trobat"));

		scanner.close();
	}

	@Transactional
	public void delete() {
		System.out.println("ELIMINANT USUARI");
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introdueix el ID de la persona: ");
		Long id = scanner.nextLong();
		repository.deleteById(id);
		System.out.println("Usuari eliminat");
		scanner.close();
	}

	@Transactional
	public void delete2() {
		System.out.println("ELIMINANT USUARI");
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introdueix el ID de la persona: ");
		Long id = scanner.nextLong();
		Optional<Person> optPerson = repository.findById(id);

		optPerson.ifPresentOrElse(person -> {
			System.out.println("Usuari trobat: " + person);
			repository.delete(person);
			System.out.println("Persona eliminada");
		}, () -> System.out.println("Usuari no trobat"));

		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQuery() {
		System.out.println("PERSONALIZED QUERY");
		Scanner scanner = new Scanner(System.in);

		System.out.print("Introdueix el ID de la persona: ");
		Long id = scanner.nextLong();
		String name = repository.getNameById(id);
		Long idDb = repository.getIdById(id);
		String fullName = repository.getFullNameById(id);
		Object[] fullData = (Object[]) repository.findPersonDataFullById(id);
		System.out.println("ID" + idDb + " - Nom: " + name);
		System.out.println("Fullname: " + fullName);
        for (Object fullDatum : fullData) {
            System.out.print(fullDatum + " - ");
        }

		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQuery2() {
		System.out.println("PERSONALIZED QUERY 2");
		Scanner scanner = new Scanner(System.in);

		List<Object[]> dataMix = repository.findAllMixPersona();
		dataMix.forEach(reg -> {
			System.out.println("programmingLanguage=" + reg[1] + ", person=" + reg[0]);
		});

		System.out.println("LIST NAME+LASTNAME");
		List<Person> personsPersonalized = repository.findAllClassPersonPersonalized();
		personsPersonalized.forEach(p -> {
			System.out.println(p);
		});

		System.out.println("LIST PERSONDTO");
		List<PersonDTO> personsDTO = repository.findAllClassPersonDto();
		personsDTO.forEach(p -> {
			System.out.println(p);
		});

		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueryDistinct() {
		System.out.println("PERSONALIZED QUERY DISTINCT");

		System.out.println("Consultas con nombres de personas");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("Consultas con nombres de personas distinct");
		List<String> namesD = repository.findAllNamesDistinct();
		namesD.forEach(System.out::println);

		System.out.println("Consultas con programLanguages distinct");
		List<String> plD = repository.findProgrammingLanguagesDistinct();
		plD.forEach(System.out::println);

		System.out.println("Consultas con count programLanguages distinct");
		Long count = repository.findProgrammingLanguagesDistinctCount();
		System.out.println("Count: " + count);
	}

	@Transactional(readOnly = true)
	public void personalizedQueryConcatUpperAndLower() {
		System.out.println("PERSONALIZED QUERY CONCAT UPPER AND LOWER");

		System.out.println("Consultas nombres y apellidos de persona");
		List<String> names = repository.findAllFullNameConcat();
		names.forEach(System.out::println);

		System.out.println("Consultas nombres y apellidos de persona 2");
		List<String> names2 = repository.findAllFullNameConcat2();
		names2.forEach(System.out::println);

		System.out.println("Consultas nombres y apellidos de persona upper");
		List<String> namesUpper = repository.findAllFullNameConcatUpper();
		namesUpper.forEach(System.out::println);

		System.out.println("Consultas nombres y apellidos de persona lower");
		List<String> namesLower = repository.findAllFullNameConcatLower();
		namesLower.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueryBetween() {
		System.out.println("PERSONALIZED QUERY BETWEEN");

		System.out.println("Consultas persons between");
		List<Person> persons = repository.findPersonBetween();
		persons.forEach(System.out::println);

		System.out.println("Consultas persons between name");
		persons = repository.findPersonBetweenName();
		persons.forEach(System.out::println);

		System.out.println("Consultas between Query Method");
		persons = repository.findByIdBetween(5L, 10L);
		persons.forEach(System.out::println);

		System.out.println("Consultas persons between order by");
		persons = repository.findPersonBetweenOrderByName();
		persons.forEach(System.out::println);

		System.out.println("Consultas persons between name order by");
		persons = repository.findPersonBetweenNameOrderById();
		persons.forEach(System.out::println);

		System.out.println("Consultas between order by Query Method");
		persons = repository.findByIdBetweenOrderByNameDescLastnameAsc(5L, 10L);
		persons.forEach(System.out::println);

		System.out.println("Consultas getAll ordered");
		persons = repository.getAll();
		persons.forEach(System.out::println);

		System.out.println("Consultas getAll  query method");
		persons = repository.findAllByOrderByNameDesc();
		persons.forEach(System.out::println);

		System.out.println("Total persons: " + repository.totalPerson());
		System.out.println("Max Id: " + repository.maxId());
		System.out.println("Min id: " + repository.minId());

		System.out.println("LENGTH");
		List<Object[]> namesAndLength = repository.getNamesAndLength();
		namesAndLength.forEach(r -> System.out.println("Name: " + r[0] + " Length: " + r[1]));


		System.out.println("Min Length Name: " + repository.getMinLengthName());
		System.out.println("Max Length Name: " + repository.getMaxLengthName());

		System.out.println("CONSULTA RESUMEN FUNCIONES DE AGREGACION");
		Object[] resumeReg = (Object[]) repository.getResumeAggregationFunction();
		System.out.println("min=" + resumeReg[0] +
				", max=" + resumeReg[1] + ", sum=" + resumeReg[2] +
				", avg=" + resumeReg[3] + ", count=" + resumeReg[4]) ;

		System.out.println("SUBQUERIES");
		List<Object[]> per = repository.getShortterName();
		per.forEach(reg -> System.out.println("Name: " + reg[0] + " Length: " + reg[1]));

		System.out.println("Last registration: " + repository.getLastRegistration().orElseThrow());
	}
}
