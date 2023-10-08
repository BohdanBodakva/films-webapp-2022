#!/usr/bin/env bash
# shellcheck disable=SC2164
cd /home/ec2-user/server
sudo java -jar -Dserver.port=80 \
    films_webapp-0.0.1-SNAPSHOT.jar > /dev/null 2> /dev/null < /dev/null &