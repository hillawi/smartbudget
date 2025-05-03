#!/usr/bin/bash

if [[ -d $SMART_BUDGET_PROJECT_DIR && -d $SMART_BUDGET_BIN_DIR ]]; then
  echo "================== Smart Budget - JAR Build =================="
  echo "Project dir: $SMART_BUDGET_PROJECT_DIR"
  echo "Bin dir: $SMART_BUDGET_BIN_DIR"
  printf "==================================================================\n"
else
  echo "Please define SMART_BUDGET_PROJECT_DIR and SMART_BUDGET_BIN_DIR environment variables"
  exit 1
fi

echo "Building..."

if mvn clean package -DskipTests -f "$SMART_BUDGET_PROJECT_DIR" > /tmp/smartbudget.log 2>&1; then
  mv "$SMART_BUDGET_PROJECT_DIR"/target/smart-budget-*.jar "$SMART_BUDGET_BIN_DIR"/smart-budget.jar
  echo "Build success"
else
  echo "Build failed"
fi
printf "=====================================================================\n\n"