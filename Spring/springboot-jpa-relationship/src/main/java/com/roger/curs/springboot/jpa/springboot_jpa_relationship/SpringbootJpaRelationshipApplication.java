package com.roger.curs.springboot.jpa.springboot_jpa_relationship;

import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.roger.curs.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		manyToOne();
		manyToOneFindById();
	}

	public void manyToOne() {
		System.out.println("******************************** MANY TO ONE ********************************");
		Client client = clientRepository.save(new Client("John", "Doe"));
		Invoice invoice = new Invoice("MANY TO ONE", "77");

		invoice.setClient(client);

		Invoice invoiceDb = invoiceRepository.save(invoice);

		System.out.println("InvoiceDB: " + invoiceDb);

		System.out.println("*****************************************************************************");

	}

	public void manyToOneFindById() {
		System.out.println("******************************** MANY TO ONE FIND BY ID ********************************");
		Client client = clientRepository.findById(1L).orElseThrow();
		Invoice invoice = new Invoice("MANY TO ONE FIND BY ID", "780");

		invoice.setClient(client);

		Invoice invoiceDb = invoiceRepository.save(invoice);

		System.out.println("InvoiceDB: " + invoiceDb);

		System.out.println("****************************************************************************************");

	}
}
