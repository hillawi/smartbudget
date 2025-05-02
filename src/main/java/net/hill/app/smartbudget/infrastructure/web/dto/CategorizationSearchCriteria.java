package net.hill.app.smartbudget.infrastructure.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.hill.app.smartbudget.domain.model.TransactionCategory;

@Getter
@Setter
@Builder
public class CategorizationSearchCriteria {
  private String inputText;
  private TransactionCategory transactionCategory;

  public static CategorizationSearchCriteria fromSearchForm(
      CategorizationSearchForm categorizationSearchForm) {
    return CategorizationSearchCriteria.builder()
        .inputText(categorizationSearchForm.getInputText())
        .transactionCategory(categorizationSearchForm.getSelectedCategory()).build();
  }
}