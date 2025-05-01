package net.hill.app.smartbudget.infrastructure.dataaccess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.model.TransactionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Setter(AccessLevel.NONE)
  private UUID id;
  @Column(name = "sequence_number", nullable = false)
  private String sequenceNumber;
  @Column(nullable = false)
  private double amount;
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType transactionType;
  @Column(name = "category", nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionCategory transactionCategory;
  @Column(nullable = false)
  private LocalDate date;
  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
}