package net.hill.app.smartbudget.infrastructure.web.controller;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.hill.app.smartbudget.domain.model.Categorization;
import net.hill.app.smartbudget.infrastructure.dataaccess.adapter.CategorizationRepositoryAdapter;
import net.hill.app.smartbudget.infrastructure.web.dto.CategorizationSearchCriteria;
import net.hill.app.smartbudget.infrastructure.web.dto.CategorizationSearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/categorizations")
public class CategorizationController {
  private static final String CATEGORIZATIONS = "categorizations";
  private final CategorizationRepositoryAdapter categorizationRepositoryAdapter;

  public CategorizationController(CategorizationRepositoryAdapter categorizationRepositoryAdapter) {
    this.categorizationRepositoryAdapter = categorizationRepositoryAdapter;
  }

  @GetMapping
  public String categorizations(Model model) {
    var categorizations = categorizationRepositoryAdapter.findOrdered();
    addCategorizationAttributes(model, categorizations);
    addFormAttributes(model, new CategorizationSearchForm());
    return CATEGORIZATIONS;
  }

  @PostMapping
  public String processSelection(@ModelAttribute CategorizationSearchForm categorizationSearchForm,
                                 Model model) {
    if (categorizationSearchForm != null && categorizationSearchForm.hasSelection()) {
      var searchCriteria = CategorizationSearchCriteria.fromSearchForm(categorizationSearchForm);
      var categorizations = categorizationRepositoryAdapter.search(searchCriteria);
      addCategorizationAttributes(model, categorizations);
      addFormAttributes(model, categorizationSearchForm);
      return CATEGORIZATIONS;
    }
    return categorizations(model);
  }

  private static void addCategorizationAttributes(Model model,
                                                  List<Categorization> categorizations) {
    var categoriesCountMap = categorizations.stream().collect(groupingBy(
        Categorization::transactionCategory, Collectors.counting()));

    model.addAttribute(CATEGORIZATIONS, categorizations);
    model.addAttribute("categorizationsTotal", categorizations.size());
    model.addAttribute("categoriesCount", categoriesCountMap);
  }

  private void addFormAttributes(Model model, CategorizationSearchForm categorizationSearchForm) {
    var categories = categorizationRepositoryAdapter.findCategories();
    model.addAttribute("categories", categories);
    model.addAttribute("categorizationSearchForm", categorizationSearchForm);
  }
}