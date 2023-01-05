mvn clean package -DskipTests=true

rm -f rgenerator-api-spring-boot.jar
cp ../client/target/rgenerator-api-spring-boot.jar ./rgenerator-api-spring-boot.jar

rm -f application.properties
cp ../client/src/main/resources/application.properties.prod ./application.properties

docker build -t rgenerator-backend ./
rm ./rgenerator-api-spring-boot.jar
rm ./application.properties