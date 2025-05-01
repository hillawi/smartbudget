package net.hill.app.smartbudget.infrastructure.scheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.command.TransactionCreationCommand;
import net.hill.app.smartbudget.domain.model.TransactionType;
import net.hill.app.smartbudget.domain.service.AccountStatementProcessor;
import net.hill.app.smartbudget.domain.service.CategoryProvider;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class AccountStatementProcessorDefaultImpl implements AccountStatementProcessor {
  private static final String REGEX =
      "(?:^|\\n)(\\d{4})\\s+(\\d{2}-\\d{2}-\\d{4})\\s+\\(VAL\\. \\d{2}-\\d{2}-\\d{4}\\)\\s+" +
      "([+-]?)\\s*([\\d.]+),(\\d{2})\\s*\\n([\\s\\S]*?(?=(?:^|\\n)\\d{4}|\\z))";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  private final CategoryProvider categoryProvider;

  public AccountStatementProcessorDefaultImpl(CategoryProvider categoryProvider) {
    this.categoryProvider = categoryProvider;
  }

  @Override
  public List<Pair<TransactionCreationCommand, CategorizationCreationCommand>> extractTransactions(
      String text) {
    List<Pair<TransactionCreationCommand, CategorizationCreationCommand>> pairs = new ArrayList<>();

    var sanitizedText = sanitizeText(text);

    var pattern = Pattern.compile(REGEX, Pattern.MULTILINE);
    var matcher = pattern.matcher(sanitizedText);
    while (matcher.find()) {
      pairs.add(buildTransactionCreationCommand(matcher));
    }

    return pairs;
  }

  private Pair<TransactionCreationCommand, CategorizationCreationCommand> buildTransactionCreationCommand(
      Matcher matcher) {
    var sequenceNumber = matcher.group(1);
    var date = matcher.group(2);
    var sign = matcher.group(3);
    var integerPart = matcher.group(4);
    var decimalPart = matcher.group(5);
    double amount = Double.parseDouble(
        (sign != null ? sign : "+") + integerPart.replace(".", "") + "." + decimalPart);
    var extraLines = matcher.group(6);
    var categorizationCreationCommand = categoryProvider.categorizeTransaction(extraLines);
    return Pair.of(new TransactionCreationCommand(sequenceNumber, amount,
        TransactionType.fromAmount(amount), categorizationCreationCommand.transactionCategory(),
        LocalDate.parse(date, FORMATTER)), categorizationCreationCommand);
  }

  private static String sanitizeText(String text) {
    return text.replaceAll("\\s" + System.lineSeparator(), System.lineSeparator()).trim();
  }
}