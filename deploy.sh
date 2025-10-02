#!/bin/bash
set -e

# Ensure DB env vars are set (inherited from SSH session)
: "${DB_IP:?DB_IP not set}"
: "${DB_NAME:?DB_NAME not set}"
: "${DB_USERNAME:?DB_USERNAME not set}"
: "${DB_PASSWORD:?DB_PASSWORD not set}"

cd /home/ubuntu/fraxen || exit

# Update repo
echo ">>> Cleaning repo..."
git reset --hard HEAD
git clean -fd

echo ">>> Pulling latest changes..."
git pull origin main

# Build with Maven
echo ">>> Building project..."
mvn clean package -DskipTests

# Stop old app
echo ">>> Stopping old app..."
pkill -f 'fraxen-0.0.1-SNAPSHOT.jar' || true

# Start new app with environment variables
echo ">>> Starting new app..."
nohup java -jar target/fraxen-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
