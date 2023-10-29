FROM openjdk:11
WORKDIR /app
COPY . .
RUN sh gradlew build -x test
ENTRYPOINT java -jar build/libs/MoneyHooksAPI-1.0.jar