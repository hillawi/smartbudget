package net.hill.app.smartbudget.infrastructure.web.controller;

import static java.util.stream.Collectors.groupingBy;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.hill.app.smartbudget.domain.model.Categorization;
import net.hill.app.smartbudget.infrastructure.dataaccess.adapter.CategorizationRepositoryAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/categorizations")
public class CategorizationController {
  private final CategorizationRepositoryAdapter categorizationRepositoryAdapter;

  public CategorizationController(CategorizationRepositoryAdapter categorizationRepositoryAdapter) {
    this.categorizationRepositoryAdapter = categorizationRepositoryAdapter;
  }

  @GetMapping
  public String categorizations(Model model) {
    var categorizations = categorizationRepositoryAdapter.findOrdered();
    var categoriesCountMap = categorizations.stream().collect(groupingBy(
        Categorization::transactionCategory, Collectors.counting()));

    model.addAttribute("categorizations", categorizations);
    model.addAttribute("categorizationsTotal", categorizations.size());
    model.addAttribute("categoriesCount", categoriesCountMap);
    return "categorizations";
  }
}