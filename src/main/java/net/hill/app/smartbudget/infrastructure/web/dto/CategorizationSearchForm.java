package net.hill.app.smartbudget.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
public class CategorizationSearchForm {
  private String inputText;
  private TransactionCategory selectedCategory;

  public boolean hasSelection() {
    return StringUtils.hasText(inputText) || selectedCategory != null;
  }
}