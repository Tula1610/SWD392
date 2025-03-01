# Sử dụng OpenJDK 17 trên Alpine Linux làm base image
FROM openjdk:17-jdk-alpine

# Đặt thư mục làm thư mục làm việc
WORKDIR /app

# Sao chép file JAR từ thư mục target vào container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8080

# Đặt biến môi trường an toàn (Sử dụng UPPER_CASE và dấu _ thay vì .)
ENV SPRING_DATA_MONGODB_URI=mongodb+srv://khoihmse171467:khoi1610@swd.tftrh.mongodb.net/Swd?retryWrites=true&w=majority
# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
