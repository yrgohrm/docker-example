@echo off
set JDBC_SERVER=localhost:1433
set JDBC_DB=TheGame
set JDBC_USER=sa
set JDBC_PASSWORD=yrgoP4ssword

java -jar runtime\winstone-0.9.10.jar --warfile target\docker-example-backend.war
