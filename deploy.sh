#!/bin/bash
set -e  # stop if something fails

# Go to project directory
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

# Stop running app if it's already running
echo ">>> Stopping old app..."
pkill -f 'fraxen-0.0.1-SNAPSHOT.jar' || true

# Start new app
echo ">>> Starting new app..."
nohup java -jar target/fraxen-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
