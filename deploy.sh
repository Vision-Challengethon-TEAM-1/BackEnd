#!/bin/bash

set -e

IMAGE_NAME="adorableco/cheollian"
TAG="latest"

echo "🛠️  Gradle 프로젝트 빌드 중..."
./gradlew clean build -x test

echo "🐳️  Docker 이미지 빌드 중..."
docker build -t $IMAGE_NAME:$TAG .


echo "📤️  Docker Hub에 이미지 푸시 중..."
docker push $IMAGE_NAME:$TAG

echo "✅️  Docker 이미지 빌드 및 푸시 완료: $IMAGE_NAME:$TAG"
