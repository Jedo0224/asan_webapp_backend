# Use the official gradle image to create a build artifact.
FROM gradle:8.6.0-jdk17 AS build

# Set the working directory.
WORKDIR /home/gradle/src

# Copy the current directory contents into the container at /home/gradle/src
COPY . /home/gradle/src

# Build a release artifact, skipping tests
RUN gradle build -x test --no-daemon

# Use the official OpenJDK image for a lean production stage of our multi-stage build.
FROM openjdk:17-oracle

# Set the working directory.
WORKDIR /app

# Copy the jar to the production image from the builder stage.
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-app.jar

# Set environment variable for Spring profile
ENV SPRING_PROFILES_ACTIVE=prod

# Run the web service on container startup.
CMD ["java", "-jar", "/app/spring-app.jar"]
