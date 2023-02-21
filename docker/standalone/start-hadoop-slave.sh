#!/bin/bash

# the default node number is 3
N=${1:-3}

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