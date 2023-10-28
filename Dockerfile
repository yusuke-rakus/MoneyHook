FROM openjdk:11
WORKDIR /app
COPY . .
ENV DATABASE_URL="moneyhook"
ENV DATABASE_USER_NAME="moneyhook"
ENV DATABASE_PASSWORD="password"
CMD /bin/bash -c "sh gradlew build -x test && \
      java -jar build/libs/MoneyHooksAPI-1.0.jar"