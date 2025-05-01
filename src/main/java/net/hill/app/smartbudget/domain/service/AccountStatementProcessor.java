package net.hill.app.smartbudget.domain.service;

import java.util.List;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.command.TransactionCreationCommand;
import org.springframework.data.util.Pair;

/**
 * Interface representing a processor for handling account statements.
 * It provides a method to process the raw text extracted from a PDF account statement
 * and converts it into a structured list of transactions.
 */
public interface AccountStatementProcessor {
  /**
   * Processes the raw text extracted from a PDF account statement and converts it
   * into a structured list of transactions.
   *
   * @param pdfText the raw text content extracted from the PDF account statement
   * @return a list of extracted transactions, where each transaction includes details such as id,
   * amount, transactionType, and transactionCategory
   */
  List<Pair<TransactionCreationCommand, CategorizationCreationCommand>> extractTransactions(
      String pdfText);
}