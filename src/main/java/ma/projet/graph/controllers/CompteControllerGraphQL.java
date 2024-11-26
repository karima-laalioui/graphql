package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeCompte;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import ma.projet.graph.requests.TransactionRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CompteControllerGraphQL {

    private CompteRepository compteRepository;
    private TransactionRepository transactionRepository;

    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }

    @QueryMapping
    public Compte compteById(@Argument Long id) {
        Compte compte = compteRepository.findById(id).orElse(null);
        if (compte == null) throw new RuntimeException(String.format("Compte %s not found", id));
        return compte;
    }

    @QueryMapping
    public List<Compte> comptesByType(@Argument TypeCompte type) {
        return compteRepository.findByType(type);
    }

    @MutationMapping
    public Compte saveCompte(@Argument Compte compte) {
        return compteRepository.save(compte);
    }

    @MutationMapping
    public String deleteCompte(@Argument Long id) {
        if (!compteRepository.existsById(id)) {
            throw new RuntimeException(String.format("Compte %s not found", id));
        }
        compteRepository.deleteById(id);
        return String.format("Compte %s deleted successfully", id);
    }

    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count(); // Nombre total de comptes
        double sum = compteRepository.sumSoldes(); // Somme totale des soldes
        double average = count > 0 ? sum / count : 0; // Moyenne des soldes

        return Map.of(
                "count", count,
                "sum", sum,
                "average", average
        );
    }

    @QueryMapping
    public List<Transaction> transactionsByCompteId(@Argument Long compteId) {
        if (!compteRepository.existsById(compteId)) {
            throw new RuntimeException(String.format("Compte %s not found", compteId));
        }
        return transactionRepository.findByCompteId(compteId);
    }

    @MutationMapping
    public Transaction addTransaction(@Argument Long compteId, @Argument TransactionRequest request) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException(String.format("Compte %s not found", compteId)));

        Transaction transaction = new Transaction();
        transaction.setCompte(compte);
        transaction.setMontant(request.getMontant());
        transaction.setDate(LocalDateTime.now());
        transaction.setDescription(request.getDescription());

        return transactionRepository.save(transaction);
    }

}