#!/usr/bin/bash

if [[ -d $SMART_BUDGET_BIN_DIR ]]; then
  echo "===================================== Smart Budget - Shutdown ======================================"
  echo "Bin dir: $SMART_BUDGET_BIN_DIR"
  printf "=======================================================================================================\n"
else
  echo "Please define SMART_BUDGET_BIN_DIR environment variable"
  exit 1
fi

if ! test -f "$SMART_BUDGET_BIN_DIR"/smart-budget.pid; then
  echo "No PID file found. Nothing to do."
  exit 2
fi

PID=$(cat "$SMART_BUDGET_BIN_DIR"/smart-budget.pid)

if ps -p ${PID} > /dev/null 2>&1; then
  echo "Killing PID ${PID}..."
  kill -9 ${PID}
else
  echo "PID not found."
fi

rm "$SMART_BUDGET_BIN_DIR"/smart-budget.pid

echo "PID file removed."

if test -f "$SMART_BUDGET_BIN_DIR"/smart-budget.log; then
  echo "Log file removed."
fi