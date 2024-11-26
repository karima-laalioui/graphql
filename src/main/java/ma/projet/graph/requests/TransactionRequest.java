package ma.projet.graph.requests;

import lombok.Data;

@Data
public class TransactionRequest {
    private double montant;
    private String description;
}