# Base image
FROM openjdk:17-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file from the target directory into the container at /app
COPY target/Earthquake-App-0.0.1-SNAPSHOT.jar /app/earthquake-app.jar

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "/app/earthquake-app.jar"]
