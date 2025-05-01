package net.hill.app.smartbudget.domain.command;

import net.hill.app.smartbudget.domain.model.TransactionCategory;

public record CategorizationCreationCommand(String inputText,
                                            TransactionCategory transactionCategory) {
}