FROM openjdk:8-jre-alpine
ENV APP_FILE trendyol-service.jar
ENV APP_HOME /app
EXPOSE 9103
COPY target/$APP_FILE $APP_HOME/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]
