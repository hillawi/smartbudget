package net.hill.app.smartbudget.infrastructure.dataaccess.repo;

import java.time.YearMonth;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.model.TransactionType;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.TransactionEntity;
import net.hill.app.smartbudget.infrastructure.web.dto.TransactionSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public final class TransactionSpecifications {
  public static Specification<TransactionEntity> transactionsByCriteria(
      TransactionSearchCriteria criteria) {
    return Specification.where(hasSequenceNumber(criteria.getSequenceNumber()))
        .and(hasTransactionType(criteria.getTransactionType()))
        .and(hasTransactionCategory(criteria.getTransactionCategory()))
        .and(hasYearMonth(criteria.getYearMonth()))
        .and(hasMinAmount(criteria.getMinAmount()))
        .and(hasMaxAmount(criteria.getMaxAmount()));
  }

  private TransactionSpecifications() {
    throw new IllegalStateException("Utility class");
  }

  static Specification<TransactionEntity> hasSequenceNumber(String sequenceNumber) {
    return (root, query, cb) -> sequenceNumber == null ? null :
        cb.equal(root.get("sequenceNumber"), sequenceNumber);
  }

  static Specification<TransactionEntity> hasTransactionType(
      TransactionType transactionType) {
    return (root, query, cb) -> transactionType == null ? null :
        cb.equal(root.get("transactionType"), transactionType);
  }

  static Specification<TransactionEntity> hasTransactionCategory(
      TransactionCategory transactionCategory) {
    return (root, query, cb) -> transactionCategory == null ? null :
        cb.equal(root.get("transactionCategory"), transactionCategory);
  }

  static Specification<TransactionEntity> hasYearMonth(YearMonth yearMonth) {
    return (root, query, cb) -> {
      if (yearMonth == null) {
        return null;
      }
      var startOfMonth = yearMonth.atDay(1);
      var endOfMonth = yearMonth.atEndOfMonth();
      return cb.between(root.get("date"), startOfMonth, endOfMonth);
    };
  }

  static Specification<TransactionEntity> hasMinAmount(Double minAmount) {
    return (root, query, cb) -> minAmount == null ? null :
        cb.greaterThanOrEqualTo(root.get("amount"), minAmount);
  }

  static Specification<TransactionEntity> hasMaxAmount(Double maxAmount) {
    return (root, query, cb) -> maxAmount == null ? null :
        cb.lessThanOrEqualTo(root.get("amount"), maxAmount);
  }
}