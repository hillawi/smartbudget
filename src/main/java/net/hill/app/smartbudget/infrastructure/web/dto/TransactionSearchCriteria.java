package net.hill.app.smartbudget.infrastructure.web.dto;

import java.time.YearMonth;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.model.TransactionType;

@Getter
@Setter
@Builder
public class TransactionSearchCriteria {
  private String sequenceNumber;
  private TransactionType transactionType;
  private TransactionCategory transactionCategory;
  private YearMonth yearMonth;
  private Double minAmount;
  private Double maxAmount;

  public static TransactionSearchCriteria fromSearchForm(
      TransactionSearchForm transactionSearchForm) {
    return TransactionSearchCriteria.builder()
        .yearMonth(transactionSearchForm.getSelectedYearMonth())
        .transactionCategory(transactionSearchForm.getSelectedCategory())
        .build();
  }
}