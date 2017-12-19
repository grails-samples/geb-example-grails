#!/bin/bash

echo "Running tests..."

EXIT_STATUS=0

./gradlew check -Dgeb.env=chromeHeadless || EXIT_STATUS=$?

exit $EXIT_STATUS