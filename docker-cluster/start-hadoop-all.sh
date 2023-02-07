#!/bin/bash

# the default node number is 3
N=${1:-3}

# start hadoop master container
docker rm -f hadoop-master
echo "start hadoop-master container..."
sudo docker run -itd \
                --net=hadoop \
                -p 50070:50070 \
                -p 8088:8088 \
                --name hadoop-master \
                --hostname hadoop-master \
                hadoop-cluster:latest

# start hadoop slave container
i=1
while [ $i -lt $N ]
do
	docker rm -f hadoop-slave$i
	echo "start hadoop-slave$i container..."
	docker run -itd \
				--net=hadoop \
	            --name hadoop-slave$i \
	            --hostname hadoop-slave$i \
	            hadoop-cluster:latest
	i=$(( $i + 1 ))
done 