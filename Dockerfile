FROM gcr.io/cloud-builders/java
WORKDIR /app
COPY . .
#COPY --from=gcr.io/cloudsql-docker/gce-proxy /cloud_sql_proxy /cloud_sql_proxy
RUN sh gradlew build -x test
ENTRYPOINT java -jar build/libs/MoneyHooksAPI-1.0.jar
#ENTRYPOINT ["/cloud_sql_proxy", "--dir=/cloudsql", "-instances=<INSTANCE_CONNECTION_NAME>=tcp:0.0.0.0:3306", "-credential_file=/secrets/cloudsql/credentials.json"]

# Cloud SQL Auth Proxy をダウンロード
RUN curl -LO https://dl.google.com/cloudsql/cloudsql-proxy-installer.sh

# Cloud SQL Auth Proxy をインストール
RUN chmod +x cloudsql-proxy-installer.sh
RUN ./cloudsql-proxy-installer.sh

# Cloud SQL Auth Proxy を起動
CMD ./cloudsql-proxy -instances=<INSTANCE_CONNECTION_NAME> -credential_file=/app/credentials.json
