package net.hill.app.smartbudget.infrastructure.dataaccess.repo;

import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.CategorizationEntity;
import net.hill.app.smartbudget.infrastructure.web.dto.CategorizationSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public final class CategorizationSpecifications {
  private static final String LIKE = "%";

  public static Specification<CategorizationEntity> categorizationsByCriteria(
      CategorizationSearchCriteria criteria) {
    return Specification.where(hasInputText(criteria.getInputText()))
        .and(hasTransactionCategory(criteria.getTransactionCategory()));
  }

  private CategorizationSpecifications() {
    throw new IllegalStateException("Utility class");
  }

  static Specification<CategorizationEntity> hasInputText(String inputText) {
    return (root, query, cb) -> inputText == null ? null :
        cb.like(cb.lower(root.get("inputText")), LIKE + inputText.toLowerCase() + LIKE);
  }

  static Specification<CategorizationEntity> hasTransactionCategory(
      TransactionCategory transactionCategory) {
    return (root, query, cb) -> transactionCategory == null ? null :
        cb.equal(root.get("transactionCategory"), transactionCategory);
  }
}