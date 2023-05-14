cd .\gateway\target
start java -jar gateway-1.0-SNAPSHOT.jar
cd ..\..\organization-service\target
start java -jar organization-service-0.0.1-SNAPSHOT.jar
cd ..\..\user-service\target
start java -jar user-service-0.0.1-SNAPSHOT.jar
cd ..\..\front-end
npm start