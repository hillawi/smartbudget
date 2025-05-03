package net.hill.app.smartbudget;

import net.hill.app.smartbudget.infrastructure.config.CategorizationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EnableConfigurationProperties({CategorizationProperties.class})
@SpringBootApplication(scanBasePackages = {"net.hill.app.smartbudget"})
public class SmartBudgetApp {
  public static void main(String[] args) {
    SpringApplication.run(SmartBudgetApp.class, args);
  }
}