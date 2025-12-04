# Java 21 JDK 이미지 사용 (Spring Boot 3.5.x 호환)
FROM eclipse-temurin:21-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 파일 복사
COPY . .

# Maven 설치
RUN apt-get update && apt-get install -y maven

# jar 파일 빌드
RUN mvn clean package -DskipTests

# Spring Boot 실행
CMD ["java", "-jar", "target/JavaWeb-1-0.0.1-SNAPSHOT.jar"]
