FROM eclipse-temurin:23-jre-alpine

WORKDIR /app

# Метаданные (опционально)
LABEL maintainer="kseni" \
      service="logging-demo" \
      version="0.0.1-SNAPSHOT"

# Копируем собранный jar
COPY build/libs/logging-demo-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт (для документации, реально порт открывает приложение)
EXPOSE 8080

# Запускаем приложение
# -XX:MaxRAMPercentage — JVM использует 75% доступной памяти контейнера
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]