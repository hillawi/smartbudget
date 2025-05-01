package net.hill.app.smartbudget.infrastructure.scheduler;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import net.hill.app.smartbudget.domain.service.AccountStatementProcessor;
import net.hill.app.smartbudget.infrastructure.dataaccess.adapter.CategorizationRepositoryAdapter;
import net.hill.app.smartbudget.infrastructure.dataaccess.adapter.TransactionRepositoryAdapter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DocumentImporter {
  private final Path path;
  private final TransactionRepositoryAdapter transactionRepositoryAdapter;
  private final CategorizationRepositoryAdapter categorizationRepositoryAdapter;
  private final AccountStatementProcessor accountStatementProcessor;

  public DocumentImporter(@Value("${smartbudget.documents-input-path}") String documentsInPath,
                          AccountStatementProcessor accountStatementProcessor,
                          TransactionRepositoryAdapter transactionRepositoryAdapter,
                          CategorizationRepositoryAdapter categorizationRepositoryAdapter) {
    path = Paths.get(documentsInPath);
    if (!Files.isDirectory(path)) {
      throw new IllegalArgumentException("Documents input path must be a directory");
    }
    this.transactionRepositoryAdapter = transactionRepositoryAdapter;
    this.categorizationRepositoryAdapter = categorizationRepositoryAdapter;
    this.accountStatementProcessor = accountStatementProcessor;
  }

  @Scheduled(fixedDelay = 10_000)
  public void fixedRateTask() {
    DirectoryStream.Filter<Path> filter =
        p -> Files.isRegularFile(p) && p.toString().toLowerCase().endsWith(".pdf");
    try (DirectoryStream<Path> pdfPaths = Files.newDirectoryStream(path, filter)) {
      for (var pdfPath : pdfPaths) {
        try (var pdfDocument = PDDocument.load(pdfPath.toFile())) {
          var textStripper = new PDFTextStripper();
          var text = textStripper.getText(pdfDocument);
          var pairs = accountStatementProcessor.extractTransactions(text);

          var transactionCreationCommands = pairs.stream().map(Pair::getFirst).toList();
          transactionRepositoryAdapter.saveAll(transactionCreationCommands);

          var categorizationCreationCommands = pairs.stream().map(Pair::getSecond).toList();
          categorizationRepositoryAdapter.saveAll(categorizationCreationCommands);

          Files.move(pdfPath, pdfPath.resolveSibling(pdfPath.getFileName() + ".imported"));
        }
      }
    } catch (IOException e) {
      log.error("Error reading documents", e);
    }
  }
}
