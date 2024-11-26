package ma.projet.graph;

import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeCompte;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class GraphApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CompteRepository compteRepository, TransactionRepository transactionRepository) {
		return args -> {
			// Initialize accounts with associated transactions
			Compte compte1 = new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, new ArrayList<>());
			Compte compte2 = new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, new ArrayList<>());
			Compte compte3 = new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, new ArrayList<>());

			// Save accounts to initialize IDs
			compte1 = compteRepository.save(compte1);
			compte2 = compteRepository.save(compte2);
			compte3 = compteRepository.save(compte3);

			// Create and associate transactions
			compte1.getTransactions().add(new Transaction(null, compte1, 500.0, LocalDateTime.now(), "Initial deposit"));
			compte1.getTransactions().add(new Transaction(null, compte1, -100.0, LocalDateTime.now(), "ATM withdrawal"));

			compte2.getTransactions().add(new Transaction(null, compte2, 2000.0, LocalDateTime.now(), "Salary deposit"));
			compte2.getTransactions().add(new Transaction(null, compte2, -300.0, LocalDateTime.now(), "Utility payment"));

			compte3.getTransactions().add(new Transaction(null, compte3, 1000.0, LocalDateTime.now(), "Initial deposit"));
			compte3.getTransactions().add(new Transaction(null, compte3, -150.0, LocalDateTime.now(), "Groceries"));

			// Save accounts with transactions
			compteRepository.save(compte1);
			compteRepository.save(compte2);
			compteRepository.save(compte3);
		};
	}
}