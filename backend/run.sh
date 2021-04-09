#!/bin/bash

export JDBC_SERVER=localhost:1433
export JDBC_DB=TheGame
export JDBC_USER=sa
export JDBC_PASSWORD=yrgoP4ssword

java -jar runtime/winstone-0.9.10.jar --warfile target/docker-example-backend.war
