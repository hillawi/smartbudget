package net.hill.app.smartbudget.infrastructure.web.dto;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
public class TransactionSearchForm {
  private String selectedMonth;
  private TransactionCategory selectedCategory;

  public boolean hasSelection() {
    return StringUtils.hasText(selectedMonth) || selectedCategory != null;
  }

  public YearMonth getSelectedYearMonth() {
    if (selectedMonth == null) {
      return null;
    }

    try {
      return YearMonth.parse(selectedMonth);
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}