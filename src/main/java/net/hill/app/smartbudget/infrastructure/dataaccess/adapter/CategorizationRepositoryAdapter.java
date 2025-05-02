package net.hill.app.smartbudget.infrastructure.dataaccess.adapter;

import jakarta.transaction.Transactional;
import java.util.List;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.model.Categorization;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.infrastructure.dataaccess.mapper.CategorizationJpaMapper;
import net.hill.app.smartbudget.infrastructure.dataaccess.repo.CategorizationRepository;
import net.hill.app.smartbudget.infrastructure.dataaccess.repo.CategorizationSpecifications;
import net.hill.app.smartbudget.infrastructure.web.dto.CategorizationSearchCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CategorizationRepositoryAdapter {
  private final CategorizationRepository categorizationRepository;
  private final CategorizationJpaMapper categorizationJpaMapper;

  public CategorizationRepositoryAdapter(CategorizationRepository categorizationRepository,
                                         CategorizationJpaMapper categorizationJpaMapper) {
    this.categorizationRepository = categorizationRepository;
    this.categorizationJpaMapper = categorizationJpaMapper;
  }

  public List<Categorization> findOrdered() {
    return categorizationJpaMapper.toDomains(
        categorizationRepository.findAllByOrderByTransactionCategory());
  }

  public List<TransactionCategory> findCategories() {
    return findOrdered().stream().map(Categorization::transactionCategory).distinct().toList();
  }

  public List<Categorization> search(CategorizationSearchCriteria categorizationSearchCriteria) {
    var spec = CategorizationSpecifications.categorizationsByCriteria(categorizationSearchCriteria);
    var categorizations =
        categorizationRepository.findAll(spec, Sort.by(Sort.Order.asc("transactionCategory")));
    return categorizationJpaMapper.toDomains(categorizations);
  }

  public void saveAll(List<CategorizationCreationCommand> categorizationCreationCommands) {
    categorizationRepository.saveAll(
        categorizationJpaMapper.toEntities(categorizationCreationCommands));
  }
}
