rem mvn clean package -DskipTests=true

rem mvn clean package -DskipTests=true

cd eureka
docker build -t eureka .
cd ..

cd config-server
docker build -t config-server .
cd ..

cd gateway
docker build -t gateway .
cd ..

cd user-service
docker build -t user-service .
cd ..

cd patient-service
docker build -t patient-service .
cd ..