# Use a base image for Java runtime
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /Users/rahatali/Desktop

# Copy the compiled Spring Boot jar file into the container
COPY target/AuthService-0.0.1-SNAPSHOT.jar AuthService.jar

# Expose the port on which the application runs
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "AuthService.jar"]