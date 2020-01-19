mvn clean install
cd eureka/target
java -jar eureka.jar &
cd ../../
cd apigw/target
java -jar apigw.jar &
cd ../../
cd trendyol-service/target
java -jar trendyol-service.jar &
