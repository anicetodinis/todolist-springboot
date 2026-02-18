# Estágio de build
FROM ubuntu:latest AS build

# Instalação do JDK e Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Compila a aplicação
RUN mvn clean install -DskipTests

# Estágio final
FROM eclipse-temurin:17-jdk-jammy

# Expõe a porta da aplicação
EXPOSE 8080

# Copia o JAR do estágio de build
COPY --from=build /app/target/todolist-0.0.1-SNAPSHOT.jar /app.jar

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "/app.jar"]