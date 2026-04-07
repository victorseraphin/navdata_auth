# Alteramos para uma imagem que suporte ARM64 na Oracle Cloud
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app

# Como agora o packaging é JAR, o Maven vai gerar um .jar
COPY target/auth-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-Xmx4g", "-Xms2g", "-jar", "app.jar"]