package net.hill.app.smartbudget.infrastructure.dataaccess.adapter;

import jakarta.transaction.Transactional;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import net.hill.app.smartbudget.domain.command.TransactionCreationCommand;
import net.hill.app.smartbudget.domain.model.Transaction;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.TransactionEntity;
import net.hill.app.smartbudget.infrastructure.dataaccess.mapper.TransactionJpaMapper;
import net.hill.app.smartbudget.infrastructure.dataaccess.repo.TransactionRepository;
import net.hill.app.smartbudget.infrastructure.dataaccess.repo.TransactionSpecifications;
import net.hill.app.smartbudget.infrastructure.web.dto.TransactionSearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TransactionRepositoryAdapter {
  private final TransactionRepository transactionRepository;
  private final TransactionJpaMapper transactionJpaMapper;

  public TransactionRepositoryAdapter(TransactionRepository transactionRepository,
                                      TransactionJpaMapper transactionJpaMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionJpaMapper = transactionJpaMapper;
  }

  public List<Transaction> findOrdered() {
    return transactionJpaMapper.toDomains(transactionRepository.findByOrderByDateDesc());
  }

  public List<Transaction> search(TransactionSearchCriteria transactionSearchCriteria) {
    var spec = TransactionSpecifications.transactionsByCriteria(transactionSearchCriteria);
    return transactionJpaMapper.toDomains(
        transactionRepository.findAll(spec, Sort.by(Sort.Order.desc("date"))));
  }

  public List<YearMonth> findMonths() {
    return transactionRepository.findByOrderByDateDesc().stream()
        .map(tx -> YearMonth.from(tx.getDate())).distinct().toList();
  }

  public List<TransactionCategory> findCategories(YearMonth yearMonth) {
    var categoryStream = transactionRepository.findByOrderByDateDesc().stream()
        .sorted(Comparator.comparing(TransactionEntity::getTransactionCategory));
    if (yearMonth == null) {
      return categoryStream.map(TransactionEntity::getTransactionCategory).distinct().toList();
    } else {
      return categoryStream.filter(tx -> YearMonth.from(tx.getDate()).equals(yearMonth))
          .map(TransactionEntity::getTransactionCategory).distinct().toList();
    }
  }

  public void saveAll(List<TransactionCreationCommand> transactionCreationCommands) {
    transactionRepository.saveAll(transactionJpaMapper.toEntities(transactionCreationCommands));
  }
}
