# Use an official OpenJDK base image
FROM openjdk:21

# Set working directory in container
WORKDIR /app

# Copy everything to the container
COPY . .

# Compile Java files
RUN javac arbitraryarithmetic/*.java MyInfArith.java

# Default command to run your Java program
ENTRYPOINT ["java", "MyInfArith"]
