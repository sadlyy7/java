FROM eclipse-temurin:17-jdk

WORKDIR /app

# 전체 복사
COPY . .

# mvnw 있을 경우 mvnw 사용 → 없으면 mvn 사용
RUN chmod +x mvnw || true
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# jar 실행
CMD ["java", "-jar", "target/*.jar"]
