#!/bin/bash

# Source: https://stackoverflow.com/questions/37694987/connecting-to-postgresql-in-a-docker-container-from-outside

# shellcheck disable=SC2120
function ps_default_connection() {
  input=$1
  echo input
  docker exec -it mysqldb bash
}

ps_default_connection
