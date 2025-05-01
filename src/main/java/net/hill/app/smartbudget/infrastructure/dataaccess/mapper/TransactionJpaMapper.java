package net.hill.app.smartbudget.infrastructure.dataaccess.mapper;

import java.util.List;
import net.hill.app.smartbudget.domain.command.TransactionCreationCommand;
import net.hill.app.smartbudget.domain.model.Transaction;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.TransactionEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionJpaMapper {
  @Named("toDomain")
  @Mapping(target = "id", source = "transactionEntity.id")
  @Mapping(target = "sequenceNumber", source = "transactionEntity.sequenceNumber")
  @Mapping(target = "amount", source = "transactionEntity.amount")
  @Mapping(target = "transactionType", source = "transactionEntity.transactionType")
  @Mapping(target = "transactionCategory", source = "transactionEntity.transactionCategory")
  @Mapping(target = "date", source = "transactionEntity.date")
  @Mapping(target = "createdAt", source = "transactionEntity.createdAt")
  Transaction toDomain(TransactionEntity transactionEntity);

  @IterableMapping(qualifiedByName = "toDomain")
  List<Transaction> toDomains(List<TransactionEntity> transactionEntities);

  @Named("toEntity")
  @Mapping(target = "sequenceNumber", source = "transactionCreationCommand.sequenceNumber")
  @Mapping(target = "amount", source = "transactionCreationCommand.amount")
  @Mapping(target = "transactionType", source = "transactionCreationCommand.transactionType")
  @Mapping(target = "transactionCategory", source = "transactionCreationCommand.transactionCategory")
  @Mapping(target = "date", source = "transactionCreationCommand.date")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  TransactionEntity toEntity(TransactionCreationCommand transactionCreationCommand);

  @IterableMapping(qualifiedByName = "toEntity")
  List<TransactionEntity> toEntities(List<TransactionCreationCommand> transactionCreationCommands);
}