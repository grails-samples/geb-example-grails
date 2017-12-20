#!/bin/bash

echo "Running tests..."

EXIT_STATUS=0

./gradlew check -Dgeb.env=chromeHeadless -Dwebdriver.chrome.driver=/usr/local/share/chromedriver || EXIT_STATUS=$?

if [[ $EXIT_STATUS -ne 0 ]]; then
  echo "Check ChromeHeadless failed"
  exit $EXIT_STATUS
fi

exit $EXIT_STATUS