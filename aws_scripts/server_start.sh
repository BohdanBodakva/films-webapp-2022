#!/usr/bin/env bash
# shellcheck disable=SC2164
cd /home/ec2-user/server
sudo java -jar -Dserver.port=80 \
    *.jar > /var/log/myapp.log 2> /dev/null < /dev/null &