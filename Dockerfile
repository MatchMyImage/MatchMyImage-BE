FROM bellsoft/liberica-openjdk-alpine:17

CMD ["./gradlew", "clean", "build", "-x", "test"]

ARG JAR_FILE=./build/libs/LetMeDoWith-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} LetMeDoWith.jar

EXPOSE 8080


#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "LetMeDoWith.jar", "--DB_URL=letmedowith-develop.cx6ssukgecrt.ap-northeast-2.rds.amazonaws.com", "--DB_PORT=3306", "--DB_SCHEMA=letmedowith_app", "--DB_USERNAME=admin"]
ENTRYPOINT ["java", "-jar", "LetMeDoWith.jar"]