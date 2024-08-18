FROM openjdk:21-jdk
WORKDIR /app
COPY target/*.jar recipe-manager.jar
COPY wait-for-vault.sh /wait-for-vault.sh
RUN chmod +x /wait-for-vault.sh
ENTRYPOINT ["/wait-for-vault.sh"]