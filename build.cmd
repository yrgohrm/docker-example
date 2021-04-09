@echo off
cd backend
cmd /C mvn clean package
cd ..
cd frontend
cmd /C npm install
cmd /C npm run build
cd ..
docker-compose build
echo Done!
