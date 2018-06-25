#!/bin/bash

echo "Running tests..."

EXIT_STATUS=0

./gradlew -Dgeb.env=chromeHeadless check || EXIT_STATUS=$?

./gradlew -Dgeb.env=firefoxHeadless iT || EXIT_STATUS=$?

exit $EXIT_STATUS
