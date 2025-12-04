# ===== 1단계: Build =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# pom.xml 먼저 복사
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

# 소스 전체 복사
COPY src ./src

# 패키징 (테스트 생략)
RUN mvn -e -B package -DskipTests


# ===== 2단계: Run =====
FROM eclipse-temurin:17-jdk
WORKDIR /app

# 빌드한 jar 복사
COPY --from=build /app/target/*.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
