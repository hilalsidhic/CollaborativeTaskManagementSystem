#!/bin/sh
# wait-for-mysql.sh

# Default values if not passed
MYSQL_HOST=${MYSQL_HOST:-mysql}
MYSQL_PORT=${MYSQL_PORT:-3306}
MYSQL_USER=${DATASOURCE_USERNAME:-root}
MYSQL_PASSWORD=${DATASOURCE_PASSWORD:-root}

echo "Waiting for MySQL at $MYSQL_HOST:$MYSQL_PORT..."

# Wait until MySQL is accepting connections
until mysqladmin ping -h"$MYSQL_HOST" -P"$MYSQL_PORT" -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" --silent; do
  echo "MySQL is unavailable - sleeping"
  sleep 2
done

echo "MySQL is up - starting Spring Boot"
exec java -jar /app/app.jar
