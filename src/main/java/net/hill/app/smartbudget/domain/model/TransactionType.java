package net.hill.app.smartbudget.domain.model;

public enum TransactionType {
  IN, OUT;

  public static TransactionType fromString(String s) {
    return TransactionType.valueOf(s.toUpperCase());
  }

  public static TransactionType fromAmount(double amount) {
    return amount < 0 ? OUT : IN;
  }
}
