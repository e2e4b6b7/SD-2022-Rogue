#!/usr/bin/env bash

./gradlew fatJar
java -Xms312m -Xmx712m -XX:MaxGCPauseMillis=20 -jar view/build/libs/rogue-0.0.1.jar
