FROM openjdk:11
WORKDIR /app
COPY . .
COPY --from=gcr.io/cloudsql-docker/gce-proxy /cloud_sql_proxy /cloud_sql_proxy
RUN sh gradlew build -x test
ENTRYPOINT java -jar build/libs/MoneyHooksAPI-1.0.jar
ENTRYPOINT ["/cloud_sql_proxy", "--dir=/cloudsql", "-instances=<INSTANCE_CONNECTION_NAME>=tcp:0.0.0.0:3306", "-credential_file=/secrets/cloudsql/credentials.json"]