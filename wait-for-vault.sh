#!/bin/sh

# Wait for 20 seconds
echo "Waiting for Vault to be ready..."
sleep 20

# Start the Spring Boot application
echo "Starting the Spring Boot application..."
java -Dspring.profiles.active=prod -jar /app/recipe-manager.jar
