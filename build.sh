#!/bin/bash

if [ "${TRAVIS_JDK_VERSION}" = "openjdk11" ]; then
  mvn -T 1C -B -e -Dorg.slf4j.simpleLogger.showDateTime=true -Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss:SSS -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn clean install sonar:sonar -Dsonar.organization=goldmansachs -Dsonar.projectKey=goldmansachs_jdmn -Psonar-coverage
elif [ "${TRAVIS_JDK_VERSION}" = "openjdk8" ]; then
  mvn -T 1C -B -e -Dorg.slf4j.simpleLogger.showDateTime=true -Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss:SSS -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn clean install
fi
