# Trendyol Service (Docker) #

### 1) Docker create network ##
```bash
docker network create trendyol-network
```

---
## 2) Build image of eureka & run ##
```bash
mvn clean install
```
```bash
docker build -t poc/eureka:1.0 .
```
```bash
docker run --rm --name eureka -p 9101:9101 --net trendyol-network poc/trendyol:1.0
```
## 3) Build image of apigw(GateWay) & run ##
```bash
mvn clean install
```
```bash
docker build -t poc/apigw:1.0 .
```
```bash
docker run --rm --name apigw -p 9100:9100 --net trendyol-network poc/apigw:1.0
```
## 3) Build image of trendyol-service & run ##
```bash
mvn clean install
```
```bash
docker build -t poc/trendyol-service:1.0 .
```
```bash
docker run --rm --name trendyol-service -p 9103:9103 --net trendyol-network poc/trendyol-service:1.0
```
# Trendyol Service (Without Docker)  Maven and Java required only #
 ```
 Run install.sh inside project directory
 ```
 
# Project UI's #
 Eureka UI *[__http://localhost:9101/__](http://localhost:9101/)* <br>
 Swagger UI *[__http://localhost:9100/trendyol-service/swagger-ui.html__](http://localhost:9100/trendyol-service/swagger-ui.html)* <br>
 Database Console UI *[__http://localhost:9100/trendyol-service/db-console/__](http://localhost:9100/trendyol-service/db-console/)* <br>
