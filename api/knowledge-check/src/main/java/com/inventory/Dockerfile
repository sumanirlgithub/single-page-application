# Use latest OpenJDK (currently JDK 21) with slim OS for smaller image size
FROM openjdk:21-jdk-slim

# Create a non-root user for security best practices
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from target directory
COPY target/app.jar .

# Change ownership to non-root user
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Expose port (not really needed for command-line app, but good practice)
EXPOSE 8080

# Command to run the application with optimized JVM settings
CMD ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]n with optimized JVM settings