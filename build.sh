#!/bin/bash

cd backend
mvn clean package
cd ..
cd frontend
npm install
npm run build
cd ..
docker-compose build
echo Done!
