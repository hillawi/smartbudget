package net.hill.app.smartbudget.infrastructure.scheduler;

import java.util.HashMap;
import java.util.Map;
import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;
import net.hill.app.smartbudget.domain.model.TransactionCategory;
import net.hill.app.smartbudget.domain.service.CategoryProvider;
import org.springframework.stereotype.Component;

@Component
public class CategoryProviderDefaultImpl implements CategoryProvider {
  private static final Map<String, TransactionCategory> CATEGORY_KEYWORDS = new HashMap<>();

  static {
    CATEGORY_KEYWORDS.put("frost b3.1", TransactionCategory.HOUSING_RENT);
    CATEGORY_KEYWORDS.put("electrabel", TransactionCategory.HOUSING);
    CATEGORY_KEYWORDS.put("vivaqua", TransactionCategory.HOUSING);
    CATEGORY_KEYWORDS.put("brutele", TransactionCategory.HOUSING);
    CATEGORY_KEYWORDS.put("002/7421/77367", TransactionCategory.HOUSING);
    CATEGORY_KEYWORDS.put("carrefour", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("colruyt", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("lidl", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("delhaize", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("ad reyers", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("harun", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("new delhi traiteur", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("trademed", TransactionCategory.FOOD);
    CATEGORY_KEYWORDS.put("marie popelin", TransactionCategory.SCHOOL);
    CATEGORY_KEYWORDS.put("mc assure", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("mutualite", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("multipharma", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("medi-market", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("pharmacie", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("medighosn", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("cliniques univ", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("pearle opticiens", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("synlab", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("dentiste duquesne", TransactionCategory.HEALTHCARE);
    CATEGORY_KEYWORDS.put("zeeman", TransactionCategory.CLOTHING);
    CATEGORY_KEYWORDS.put("berca", TransactionCategory.CLOTHING);
    CATEGORY_KEYWORDS.put("orchestra", TransactionCategory.CLOTHING);
    CATEGORY_KEYWORDS.put("action", TransactionCategory.UTILITIES);
    CATEGORY_KEYWORDS.put("brico", TransactionCategory.UTILITIES);
    CATEGORY_KEYWORDS.put("trafic", TransactionCategory.UTILITIES);
    CATEGORY_KEYWORDS.put("ikea", TransactionCategory.UTILITIES);
    CATEGORY_KEYWORDS.put("spf finances", TransactionCategory.TAXES);
    CATEGORY_KEYWORDS.put("oneplus", TransactionCategory.TELECOM);
    CATEGORY_KEYWORDS.put("lycatalk", TransactionCategory.TELECOM);
    CATEGORY_KEYWORDS.put("lycamobile", TransactionCategory.TELECOM);
    CATEGORY_KEYWORDS.put("stib", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("delijn", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("lukoil", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("parking fp", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("yellowbrick", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("interparking zaventem", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("parking des 2 portes", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("4411 parking and mobility", TransactionCategory.TRANSPORT);
    CATEGORY_KEYWORDS.put("dubai", TransactionCategory.TRAVEL);
    CATEGORY_KEYWORDS.put("alipay", TransactionCategory.TRAVEL);
    CATEGORY_KEYWORDS.put("trip.com", TransactionCategory.TRAVEL);
    CATEGORY_KEYWORDS.put("trip. com", TransactionCategory.TRAVEL);
    CATEGORY_KEYWORDS.put("wu", TransactionCategory.FAMILY_SUPPORT);
    CATEGORY_KEYWORDS.put("b22001480", TransactionCategory.SPORT);
    CATEGORY_KEYWORDS.put("triton", TransactionCategory.SPORT);
    CATEGORY_KEYWORDS.put("decathlon", TransactionCategory.SPORT);
    CATEGORY_KEYWORDS.put("rayna tourism", TransactionCategory.ENTERTAINMENT);
    CATEGORY_KEYWORDS.put("devoteam", TransactionCategory.SALARY);
    CATEGORY_KEYWORDS.put("be37 6511 6079 9128", TransactionCategory.FAMILY_ALLOWANCES);
    CATEGORY_KEYWORDS.put("amzn", TransactionCategory.AMAZON);
    CATEGORY_KEYWORDS.put("amazon", TransactionCategory.AMAZON);
    CATEGORY_KEYWORDS.put("paypal", TransactionCategory.PAYPAL);
    CATEGORY_KEYWORDS.put("commune d'evere", TransactionCategory.ADMINISTRATION);
    CATEGORY_KEYWORDS.put("commune d evere", TransactionCategory.ADMINISTRATION);
    CATEGORY_KEYWORDS.put("gestion de votre compte", TransactionCategory.BANKING);
    CATEGORY_KEYWORDS.put("be45 9502 0281 3389", TransactionCategory.SAVINGS);
    CATEGORY_KEYWORDS.put("retrait d'especes", TransactionCategory.SAVINGS);
    CATEGORY_KEYWORDS.put("media markt", TransactionCategory.MISCELLANEOUS);
    CATEGORY_KEYWORDS.put("bol.com", TransactionCategory.MISCELLANEOUS);
    CATEGORY_KEYWORDS.put("bol. com", TransactionCategory.MISCELLANEOUS);
    CATEGORY_KEYWORDS.put("world of beauty", TransactionCategory.MISCELLANEOUS);
  }

  @Override
  public CategorizationCreationCommand categorizeTransaction(String inputText) {
    var sanitizedInputText = inputText.trim().toLowerCase().replace("\n", "");
    var transactionCategory = matchTransactionCategory(sanitizedInputText);
    return new CategorizationCreationCommand(sanitizedInputText, transactionCategory);
  }

  private static TransactionCategory matchTransactionCategory(String sanitizedInputText) {
    for (var entry : CATEGORY_KEYWORDS.entrySet()) {
      if (sanitizedInputText.contains(entry.getKey())) {
        return entry.getValue();
      }
    }
    return TransactionCategory.UNKNOWN;
  }
}