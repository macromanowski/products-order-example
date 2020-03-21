#!/bin/bash

#create .jar file
./gradlew clean build
BUILD_RESULT=$?
if [ $BUILD_RESULT -ne 0 ]; then
  echo "Build has failed!"
fi

#copy file
cp build/libs/products-order-example-0.1-SNAPSHOT.jar docker/

#run compose-file
pushd docker
  docker-compose up --force-recreate --build -d
popd