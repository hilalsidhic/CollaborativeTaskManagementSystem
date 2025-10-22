FROM openjdk:21-jdk-slim

WORKDIR /app

# Install MySQL client (needed for wait-for-mysql.sh)
RUN apt-get update && \
    apt-get install -y default-mysql-client && \
    rm -rf /var/lib/apt/lists/*

COPY build/libs/TaskMaster-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-mysql.sh wait-for-mysql.sh
RUN chmod +x wait-for-mysql.sh
EXPOSE 8080

# Run the wait script instead of the jar directly
ENTRYPOINT ["sh", "wait-for-mysql.sh"]
