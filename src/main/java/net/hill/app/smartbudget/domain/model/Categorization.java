package net.hill.app.smartbudget.domain.model;

import java.time.LocalDateTime;

public record Categorization(Long id, String inputText, TransactionCategory transactionCategory,
                             LocalDateTime createdAt) {
}
