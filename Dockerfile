FROM openjdk:11
WORKDIR /app
COPY . .
CMD /bin/bash -c "sh gradlew build -x test && \
      java -jar build/libs/MoneyHooksAPI-1.0.jar"