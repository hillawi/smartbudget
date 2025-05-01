package net.hill.app.smartbudget.infrastructure.dataaccess.adapter;

import jakarta.transaction.Transactional;
import java.util.List;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.model.Categorization;
import net.hill.app.smartbudget.infrastructure.dataaccess.mapper.CategorizationJpaMapper;
import net.hill.app.smartbudget.infrastructure.dataaccess.repo.CategorizationRepository;
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

  public void saveAll(List<CategorizationCreationCommand> categorizationCreationCommands) {
    categorizationRepository.saveAll(
        categorizationJpaMapper.toEntities(categorizationCreationCommands));
  }
}
