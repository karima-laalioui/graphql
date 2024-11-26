package ma.projet.graph.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Compte compte; // The account associated with the transaction

    private double montant; // Positive for deposits, negative for withdrawals
    private LocalDateTime date; // Date and time of the transaction
    private String description; // Description of the transaction
}