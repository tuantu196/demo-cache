# =========================
# 🏗️ Stage 1: Build the app
# =========================
FROM eclipse-temurin:17-jdk AS build

# Set working directory inside the container
WORKDIR /app

# Copy everything to /app
COPY . .

# Ensure gradlew has execute permission
RUN chmod +x ./gradlew

# Build Spring Boot jar without running tests
RUN ./gradlew clean bootJar --no-daemon

# =========================
# 🚀 Stage 2: Run the app
# =========================
FROM eclipse-temurin:17-jre

# Working directory for runtime
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Install sqlite3 (optional but useful if you want to open the DB inside container)
RUN apt-get update && apt-get install -y sqlite3 && rm -rf /var/lib/apt/lists/*

# Expose port
EXPOSE 8080

# Set the default command
ENTRYPOINT ["java", "-jar", "app.jar"]
