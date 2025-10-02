#!/bin/bash
set -e

# Export DB secrets (must be set here)
export DB_IP="${DB_IP:?DB_IP not set}"
export DB_NAME="${DB_NAME:?DB_NAME not set}"
export DB_USERNAME="${DB_USERNAME:?DB_USERNAME not set}"
export DB_PASSWORD="${DB_PASSWORD:?DB_PASSWORD not set}"

cd /home/ubuntu/fraxen || exit

echo ">>> Cleaning repo..."
git reset --hard HEAD
git clean -fd

echo ">>> Pulling latest changes..."
git pull origin main

echo ">>> Building project..."
mvn clean package -DskipTests

echo ">>> Stopping old app..."
pkill -f 'fraxen-0.0.1-SNAPSHOT.jar' || true

echo ">>> Starting new app..."
nohup \
  DB_IP="$DB_IP" \
  DB_NAME="$DB_NAME" \
  DB_USERNAME="$DB_USERNAME" \
  DB_PASSWORD="$DB_PASSWORD" \
  java -jar target/fraxen-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

