package net.hill.app.smartbudget.domain.service;

import net.hill.app.smartbudget.domain.command.CategorizationCreationCommand;

public interface CategoryProvider {
  CategorizationCreationCommand categorizeTransaction(String inputText);
}