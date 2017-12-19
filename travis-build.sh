#!/bin/bash

echo "Running tests..."

EXIT_STATUS=0

./gradlew check -Dgeb.env=chromeHeadless -Dwebdriver.chrome.driver=/usr/local/share/chromedriver || EXIT_STATUS=$?

exit $EXIT_STATUS