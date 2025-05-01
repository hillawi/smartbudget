package net.hill.app.smartbudget.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(UUID id, String sequenceNumber, double amount,
                          TransactionType transactionType, TransactionCategory transactionCategory,
                          LocalDate date, LocalDateTime createdAt) {
}