package net.hill.app.smartbudget.proccess.impl;

import static org.assertj.core.api.Assertions.assertThat;

import net.hill.app.smartbudget.domain.command.TransactionCreationCommand;
import net.hill.app.smartbudget.domain.service.AccountStatementProcessor;
import net.hill.app.smartbudget.infrastructure.config.CategorizationProperties;
import net.hill.app.smartbudget.infrastructure.scheduler.AccountStatementProcessorDefaultImpl;
import net.hill.app.smartbudget.infrastructure.scheduler.CategoryProviderDefaultImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

class AccountStatementProcessorDefaultImplTest {
  private AccountStatementProcessor processor;

  @BeforeEach
  void setUp() {
    processor = new AccountStatementProcessorDefaultImpl(
        new CategoryProviderDefaultImpl(new CategorizationProperties()));
  }

  @Test
  void extractTransactions() {
    var pdfText = """
        SOLDE AU  18-12-2024 EUR         +  734,59
        
        0001  19-12-2024  (VAL. 19-12-2024)         -  300,00\s
         VIREMENT INSTANTANE BELFIUS MOBILE VERS
         BE00 0000 0000 0000 John Doe
        
        0002  19-12-2024  (VAL. 19-12-2024)         -  29,98
         VOTRE DOMICILIATION EUROPEENNE 5W2J225733QZ8 POUR
         PayPal Europe S. a. r. l. et Cie S. C. A COMMUNICATION
         : xxxx/PAYPAL REFERENCE DU CREANCIER :  xxxx
        
        0003  19-12-2024  (VAL. 19-12-2024)         +3.861,93
         VERSEMENT DE BE11 1111 1111 1111 xyz /A/
         1BD0827-31-xxx REF. :
         1BD0827-31-3000565-yyy VERS
         BE00 0000 0000 0000 John Doe
        0004  20-12-2024  (VAL. 20-12-2024)                             -   31,75
          PAIEMENT MASTERCARD DEBIT 19/12 IWG BUSINESS C
          BRUSSELS BE 31,75 EUR CARTE N° 0000 0000 0000 1111 -
          John Doe
        
        0005  20-12-2024  (VAL. 20-12-2024)                             -  102,00
          VOTRE DOMICILIATION EUROPEENNE N00000 POUR
          xyz nv/sa COMMUNICATION : REF 55555555/
          55555555/ 66666666 REFERENCE DU CREANCIER :
          77777777
        """;

    var pairs = processor.extractTransactions(pdfText);
    var transactions = pairs.stream().map(Pair::getFirst).toList();

    assertThat(transactions).hasSize(5).extracting(TransactionCreationCommand::amount)
        .containsExactly(-300.0, -29.98, 3861.93, -31.75, -102.0);
  }
}