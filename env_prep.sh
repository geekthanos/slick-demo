#!/usr/bin/env bash

DC_ELASTICSEARCH=elasticsearch
REPO_ELASTICSEARCH=docker.elastic.co/elasticsearch/elasticsearch:5.5.1

RES=$( docker ps -a  | awk '{print $NF}' | grep -w ${DC_ELASTICSEARCH} )
if [ "$RES" = "$DC_ELASTICSEARCH" ]; then
    docker start $DC_ELASTICSEARCH
else
    docker pull $REPO_ELASTICSEARCH
    docker run -d -p 9200:9200 --name $DC_ELASTICSEARCH -e "http.host=0.0.0.0" -e "transport.host=127.0.0.1" $REPO_ELASTICSEARCH
fi