package com.roger.curs.springboot.jpa.springboot_jpa_relationship;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.*;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// manyToOne();
		// manyToOneFindById();
		// oneToMany();
		// oneToManyFindById();
		// removeAddress();
		// oneToManyInvoiceBidirectional();
		// oneToManyInvoiceBidirectionalFindById();
		// oneToManyRemoveInvoiceBidirectionalFindbyid();
		// oneToManyRemoveInvoiceBidirectional();
		// oneToOne();
		// oneToOneFindById();
		// oneToOneBidirectional();
		// oneToOneBidirectionalFindById();
		manyToMany();
	}

	@Transactional
	public void manyToOne() {
		System.out.println("******************************** MANY TO ONE ********************************");
		Client client = clientRepository.save(new Client("John", "Doe"));
		Invoice invoice = new Invoice("MANY TO ONE", "77");

		invoice.setClient(client);

		Invoice invoiceDb = invoiceRepository.save(invoice);

		System.out.println("InvoiceDB: " + invoiceDb);

		System.out.println("*****************************************************************************");

	}

	@Transactional
	public void manyToOneFindById() {
		System.out.println("******************************** MANY TO ONE FIND BY ID ********************************");
		Client client = clientRepository.findById(1L).orElseThrow();
		Invoice invoice = new Invoice("MANY TO ONE FIND BY ID", "780");

		invoice.setClient(client);

		Invoice invoiceDb = invoiceRepository.save(invoice);

		System.out.println("InvoiceDB: " + invoiceDb);

		System.out.println("****************************************************************************************");

	}

	@Transactional
	public void oneToMany() {
		System.out.println("******************************** ONE TO MANY ********************************");
		Client client = new Client("Roger", "Moreno");

		Address address1 = new Address("Palet i Barba", 36);
		Address address2 = new Address("Font de l'escot", 34);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println("Client: " + client);

		System.out.println("*****************************************************************************");

	}

	@Transactional
	public void oneToManyFindById() {
		System.out.println("******************************** ONE TO MANY FIND BY ID ********************************");
		Client client = clientRepository.findById(2L).orElseThrow();

		Address address1 = new Address("Palet i Barba", 36);
		Address address2 = new Address("Font de l'escot", 34);

		Set<Address> addresses = new HashSet<>();
		addresses.add(address1);
		addresses.add(address2);
		client.setAddresses(addresses);

		System.out.println("Client: " + clientRepository.save(client));

		System.out.println("****************************************************************************************");

	}

	@Transactional
	public void removeAddress() {
		System.out.println("******************************** REMOVE ADDRESS  ********************************");
		Client client = clientRepository.findById(2L).orElseThrow();

		Address address1 = new Address("Palet i Barba", 36);
		Address address2 = new Address("Font de l'escot", 34);

		Set<Address> addresses = new HashSet<>();
		addresses.add(address1);
		addresses.add(address2);
		client.setAddresses(addresses);

		Client savedClient = clientRepository.save(client);

		System.out.println("Client: " + savedClient);

		Optional<Client> optionalClient = clientRepository.findOneWithAddresses(2L);
		optionalClient.ifPresent(c -> {
			Address ad = new ArrayList<>(c.getAddresses()).get(0);
			c.getAddresses().remove(ad);
			Client clientAfterRemove = clientRepository.save(c);
			System.out.println("Client after remove: " + clientAfterRemove);
		});

		System.out.println("********************************************************************************");

	}

	@Transactional
	public void oneToManyInvoiceBidirectional() {
		System.out.println("******************************** ONE TO MANY INVOICE BIDIRECTIONAL  ********************************");
		Client client = new Client("Roger", "Moreno");

		Invoice invoice1 = new Invoice("compras de la casa", "5000");
		Invoice invoice2 = new Invoice("compras de oficina", "8000");

		client.addInvoice(invoice1).addInvoice(invoice2);

		System.out.println("Saved client: " + clientRepository.save(client));

		System.out.println("***************************************************************************************************");

	}

	@Transactional
	public void oneToManyInvoiceBidirectionalFindById() {
		System.out.println("******************************** ONE TO MANY INVOICE BIDIRECTIONAL FINDBYID  ********************************");
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", "5000");
			Invoice invoice2 = new Invoice("compras de oficina", "8000");

			client.addInvoice(invoice1).addInvoice(invoice2);

			System.out.println("Saved client: " + clientRepository.save(client));
		});

		System.out.println("*************************************************************************************************************");
	}

	@Transactional
	public void oneToManyRemoveInvoiceBidirectionalFindbyid () {
		System.out.println("******************************** ONE TO MANY REMOVE INVOICE BIDIRECTIONAL FINDBYID  ********************************");
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", "5000");
			Invoice invoice2 = new Invoice("compras de oficina", "8000");

			client.addInvoice(invoice1).addInvoice(invoice2);

			System.out.println("Saved client: " + clientRepository.save(client));
		});

		Optional<Client> optionalClient2 = clientRepository.findOneWithInvoices(1L);

		optionalClient2.ifPresent(client -> {
			Optional<Invoice> optionalInvoice = invoiceRepository.findById(2L);
			optionalInvoice.ifPresent(invoice -> {
				client.removeInvoice(invoice);
				System.out.println("Client after delete: " + clientRepository.save(client));
			});
		});

		System.out.println("********************************************************************************************************************");
	}

	@Transactional
	public void oneToManyRemoveInvoiceBidirectional () {
		System.out.println("******************************** ONE TO MANY REMOVE INVOICE BIDIRECTIONAL ********************************");

		Client client = new Client("Roger", "Moreno");

		Invoice invoice1 = new Invoice("compras de la casa", "5000");
		Invoice invoice2 = new Invoice("compras de oficina", "8000");

		client.addInvoice(invoice1).addInvoice(invoice2);

		Client savedClient = clientRepository.save(client);
		System.out.println("Saved client: " + savedClient);


		Optional<Client> optionalClient2 = clientRepository.findOneWithInvoices(savedClient.getId());

		optionalClient2.ifPresent(c -> {
			Optional<Invoice> optionalInvoice = invoiceRepository.findById(3L);
			optionalInvoice.ifPresent(invoice -> {
				c.removeInvoice(invoice);
				System.out.println("Client after delete: " + clientRepository.save(c));
			});
		});

		System.out.println("**********************************************************************************************************");
	}

	@Transactional
	public void oneToOne() {
		System.out.println("******************************** ONE TO ONE ********************************");

		ClientDetails clientDetails = clientDetailsRepository.save(new ClientDetails(false, 75));

		Client client = new Client("Erba", "Pura");

		client.setClientDetails(clientDetails);

		System.out.println("Client: " + clientRepository.save(client));

		System.out.println("****************************************************************************");

	}

	@Transactional
	public void oneToOneFindById() {
		System.out.println("******************************** ONE TO ONE FIND BY ID ********************************");

		ClientDetails clientDetails = clientDetailsRepository.save(new ClientDetails(true, 80));

		Client client = clientRepository.findOne(2L).get();

		client.setClientDetails(clientDetails);

		System.out.println("Client: " + clientRepository.save(client));

		System.out.println("***************************************************************************************");

	}

	@Transactional
	public void oneToOneBidirectional() {
		System.out.println("******************************** ONE TO ONE BIDIRECTIONAL ********************************");

		Client client = new Client("Erba", "Pura");

		ClientDetails clientDetails = new ClientDetails(false, 75);

		client.setClientDetails(clientDetails);

		System.out.println("Client: " + clientRepository.save(client));

		System.out.println("******************************************************************************************");

	}

	@Transactional
	public void oneToOneBidirectionalFindById() {
		System.out.println("******************************** ONE TO ONE BIDIRECTIONAL ********************************");

		clientRepository.findOne(2L).ifPresent(c -> {
			ClientDetails clientDetails = new ClientDetails(false, 75);

			c.setClientDetails(clientDetails);

			System.out.println("Client: " + clientRepository.save(c));
		});

		System.out.println("******************************************************************************************");

	}

	@Transactional
	public void manyToMany() {
		System.out.println("******************************** MANY TO MANY ********************************");

		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de Java Master", "Roger");
		Course course2 = new Course("Curso de JMIX", "Reyes");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println("Student1: " + student1);
		System.out.println("Student2: " + student2);

		System.out.println("******************************************************************************");

	}
}
