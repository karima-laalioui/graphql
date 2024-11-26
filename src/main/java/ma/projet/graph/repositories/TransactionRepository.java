package ma.projet.graph.repositories;

import ma.projet.graph.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompteId(Long compteId); // Retrieve transactions by account ID
}