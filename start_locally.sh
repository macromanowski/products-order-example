#!/bin/bash
#run compose-file
pushd docker
  docker-compose up -d --no-recreate --no-build
popd