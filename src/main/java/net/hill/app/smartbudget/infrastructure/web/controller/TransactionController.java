package net.hill.app.smartbudget.infrastructure.web.controller;

import static java.util.stream.Collectors.groupingBy;
import static net.hill.app.smartbudget.domain.model.TransactionType.IN;
import static net.hill.app.smartbudget.domain.model.TransactionType.OUT;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.hill.app.smartbudget.domain.model.Transaction;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.infrastructure.dataaccess.adapter.TransactionRepositoryAdapter;
import net.hill.app.smartbudget.infrastructure.web.dto.TransactionSearchCriteria;
import net.hill.app.smartbudget.infrastructure.web.dto.TransactionSearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class TransactionController {
  private final TransactionRepositoryAdapter transactionRepositoryAdapter;

  public TransactionController(TransactionRepositoryAdapter transactionRepositoryAdapter) {
    this.transactionRepositoryAdapter = transactionRepositoryAdapter;
  }

  @GetMapping("/")
  public String transactions(Model model) {
    var transactions = transactionRepositoryAdapter.findOrdered();
    addIncomesAttributes(model, transactions);
    addExpensesAttributes(model, transactions);
    addFormAttributes(model, new TransactionSearchForm());
    return "index";
  }

  @PostMapping("/")
  public String processSelection(@ModelAttribute TransactionSearchForm transactionSearchForm,
                                 Model model) {
    if (transactionSearchForm != null && transactionSearchForm.hasSelection()) {
      var searchCriteria = TransactionSearchCriteria.fromSearchForm(transactionSearchForm);
      var transactions = transactionRepositoryAdapter.search(searchCriteria);
      addIncomesAttributes(model, transactions);
      addExpensesAttributes(model, transactions);
      addFormAttributes(model, transactionSearchForm);
      return "index";
    }
    return transactions(model);
  }

  private static void addIncomesAttributes(Model model, List<Transaction> transactions) {
    Predicate<Transaction> incomePredicate = tx -> tx.transactionType().equals(IN);
    var incomes = transactions.stream().filter(incomePredicate).toList();
    var incomesPerMonth = amountsPerMonth(transactions, incomePredicate);

    model.addAttribute("incomes", incomes);
    model.addAttribute("incomesTotal", incomes.size());
    model.addAttribute("incomesPerMonth", incomesPerMonth);
  }

  private static void addExpensesAttributes(Model model, List<Transaction> transactions) {
    Predicate<Transaction> expensePredicate = tx -> tx.transactionType().equals(OUT);
    var expenses = transactions.stream().filter(expensePredicate).toList();
    var expensesPerMonth = amountsPerMonth(transactions, expensePredicate);
    var amountsPerCategory = amountsPerCategory(transactions, expensePredicate);

    model.addAttribute("expenses", expenses);
    model.addAttribute("expensesTotal", expenses.size());
    model.addAttribute("expensesPerMonth", expensesPerMonth);
    model.addAttribute("amountsPerCategory", amountsPerCategory);
  }

  private static Map<YearMonth, Double> amountsPerMonth(List<Transaction> transactions,
                                                        Predicate<Transaction> predicate) {
    return transactions.stream()
        .filter(predicate)
        .collect(Collectors.groupingBy(
            transaction -> YearMonth.from(transaction.date()),
            Collectors.summingDouble(tx -> Math.abs(tx.amount()))
        ));
  }

  private static Map<TransactionCategory, Double> amountsPerCategory(
      List<Transaction> transactions, Predicate<Transaction> expensePredicate) {
    return transactions.stream()
        .filter(expensePredicate)
        .collect(groupingBy(Transaction::transactionCategory,
            Collectors.summingDouble(tx -> Math.abs(tx.amount()))));
  }

  private void addFormAttributes(Model model, TransactionSearchForm transactionSearchForm) {
    var selectedYearMonth = transactionSearchForm.getSelectedYearMonth();
    model.addAttribute("months", transactionRepositoryAdapter.findMonths());
    model.addAttribute("categories",
        transactionRepositoryAdapter.findCategories(selectedYearMonth));
    model.addAttribute("transactionSearchForm", transactionSearchForm);
  }
}