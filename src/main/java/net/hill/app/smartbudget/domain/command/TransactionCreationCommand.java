package net.hill.app.smartbudget.domain.command;

import java.time.LocalDate;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.model.TransactionType;

public record TransactionCreationCommand(String sequenceNumber, double amount,
                                         TransactionType transactionType,
                                         TransactionCategory transactionCategory, LocalDate date) {
}