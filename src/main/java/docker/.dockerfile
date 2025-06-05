# Estágio de construção (build)
FROM eclipse-temurin:23-jdk-jammy as builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

# Estágio final (runtime)
FROM eclipse-temurin:23-jre-jammy

WORKDIR /app

# Copia o JAR do estágio de construção
COPY --from=builder /app/target/*.jar app.jar

# Cria diretório para o SQLite e define permissões
RUN mkdir -p /app/database \
    && chmod -R 777 /app/database

# Variáveis para configuração do Spring Boot (opcional)
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_PATH=/app/database/fono.db

# Porta exposta (ajuste conforme sua aplicação)
EXPOSE 8080

# Comando de execução (com segurança para o SQLite)
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --spring.datasource.url=jdbc:sqlite:${DB_PATH}"]