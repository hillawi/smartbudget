package net.hill.app.smartbudget.infrastructure.dataaccess.repo;

import java.util.List;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository
    extends JpaRepository<TransactionEntity, String>, JpaSpecificationExecutor<TransactionEntity> {
  List<TransactionEntity> findByOrderByDateDesc();
}