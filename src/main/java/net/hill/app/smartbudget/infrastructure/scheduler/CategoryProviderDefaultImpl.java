package net.hill.app.smartbudget.infrastructure.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.service.CategoryProvider;
import net.hill.app.smartbudget.infrastructure.config.CategorizationProperties;
import org.springframework.stereotype.Component;

@Component
public class CategoryProviderDefaultImpl implements CategoryProvider {
  private static final EnumMap<TransactionCategory, List<String>> CATEGORY_KEYWORDS =
      new EnumMap<>(TransactionCategory.class);

  static {
    Arrays.stream(TransactionCategory.values())
        .forEach(category -> CATEGORY_KEYWORDS.put(category, new ArrayList<>()));
  }

  public CategoryProviderDefaultImpl(CategorizationProperties categorizationProperties) {
    categorizationProperties.getExtraKeywords().forEach(
        (category, keywords) -> CATEGORY_KEYWORDS.get(category).addAll(keywords));
  }

  @Override
  public CategorizationCreationCommand categorizeTransaction(String inputText) {
    var sanitizedInputText = inputText.trim().toLowerCase().replace("\n", "");
    var transactionCategory = matchTransactionCategory(sanitizedInputText);
    return new CategorizationCreationCommand(sanitizedInputText, transactionCategory);
  }

  private static TransactionCategory matchTransactionCategory(String sanitizedInputText) {
    for (var entry : CATEGORY_KEYWORDS.entrySet()) {
      if (entry.getValue().stream().anyMatch(sanitizedInputText::contains)) {
        return entry.getKey();
      }
    }
    return TransactionCategory.UNKNOWN;
  }
}