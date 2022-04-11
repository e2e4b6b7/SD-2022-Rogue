#!/usr/bin/env bash

./gradlew fatJar
java -Xms384m -Xmx768m -XX:MaxGCPauseMillis=20 -jar view/build/libs/rogue-0.0.1.jar
