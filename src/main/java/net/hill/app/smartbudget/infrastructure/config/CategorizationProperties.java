package net.hill.app.smartbudget.infrastructure.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("smartbudget.categorization")
public class CategorizationProperties {
  private Map<TransactionCategory, List<String>> extraKeywords = new HashMap<>();
}