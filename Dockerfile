# Usa uma imagem leve com Java 17
FROM eclipse-temurin:21-jdk-alpine

# Define a pasta de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto para dentro do container
COPY . .

# Compila o projeto (pula os testes para agilizar)
RUN ./mvnw clean package -DskipTests

# Roda o jar gerado
CMD ["java", "-jar", "target/financas-0.0.1-SNAPSHOT.jar"]
