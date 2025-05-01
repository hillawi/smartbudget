package net.hill.app.smartbudget.infrastructure.dataaccess.repo;

import java.util.List;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.CategorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorizationRepository extends JpaRepository<CategorizationEntity, Long> {
  List<CategorizationEntity> findAllByOrderByTransactionCategory();
}
