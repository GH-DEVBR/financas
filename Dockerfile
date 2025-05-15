FROM eclipse-temurin:21-jdk-alpine


WORKDIR /app

# copia tudo (incluindo mvnw e a pasta .mvn)
COPY . .

# dá permissão de execução ao script mvnw
RUN chmod +x mvnw

# agora roda o build
RUN ./mvnw clean package -DskipTests

# por fim o comando de startup
CMD ["java", "-jar", "target/financas-0.0.1-SNAPSHOT.jar"]

