package com.roger.curs.springboot.jpa.springboot_jpa_relationship;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// manyToOne();
		// manyToOneFindById();
		// oneToMany();
		// oneToManyFindById();
		removeAddress();
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

		client.setAddresses(Arrays.asList(address1, address2));

		System.out.println("Client: " + clientRepository.save(client));

		System.out.println("****************************************************************************************");

	}

	@Transactional
	public void removeAddress() {
		System.out.println("******************************** REMOVE ADDRESS  ********************************");
		Client client = clientRepository.findById(2L).orElseThrow();

		Address address1 = new Address("Palet i Barba", 36);
		Address address2 = new Address("Font de l'escot", 34);

		client.setAddresses(Arrays.asList(address1, address2));

		Client savedClient = clientRepository.save(client);

		System.out.println("Client: " + savedClient);

		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(c -> {
			Address ad = c.getAddresses().get(0);
			c.getAddresses().remove(ad);
			Client clientAfterRemove = clientRepository.save(c);
			System.out.println("Client after remove: " + clientAfterRemove);
		});

		System.out.println("********************************************************************************");

	}
}
