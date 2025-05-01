package net.hill.app.smartbudget.infrastructure.dataaccess.mapper;

import java.util.List;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.model.Categorization;
import net.hill.app.smartbudget.infrastructure.dataaccess.entity.CategorizationEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategorizationJpaMapper {
  @Named("toDomain")
  Categorization toDomain(CategorizationEntity categorizationEntity);

  @IterableMapping(qualifiedByName = "toDomain")
  List<Categorization> toDomains(List<CategorizationEntity> unknownCategoryEntities);

  @Named("toEntity")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  CategorizationEntity toEntity(CategorizationCreationCommand categorizationCreationCommand);

  @IterableMapping(qualifiedByName = "toEntity")
  List<CategorizationEntity> toEntities(
      List<CategorizationCreationCommand> categorizationCreationCommands);
}