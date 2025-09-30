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
	}

	public void manyToOne() {
		System.out.println("******************************** MANY TO ONE ********************************");
		Client client = clientRepository.save(new Client("John", "Doe"));
		Invoice invoice = new Invoice("Invoice 1", "77");

		invoice.setClient(client);

		Invoice invoiceDb = invoiceRepository.save(invoice);

		System.out.println("InvoiceDB: " + invoiceDb);

		System.out.println("*****************************************************************************");

	}
}
