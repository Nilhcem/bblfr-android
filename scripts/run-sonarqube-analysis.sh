#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew :app:assembleRealDebug :app:testRealDebugUnitTest :app:sonarqube

echo "SonarQube reports: http://localhost:9000"
