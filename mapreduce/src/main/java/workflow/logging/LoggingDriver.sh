hadoop jar mapreduce-1.0-SNAPSHOT.jar workflow.logging.LoggingDriver \
-conf conf/hadoop-cluster.xml -D mapreduce.map.log.level=DEBUG \
/input/ncdc/sample.txt /output/ncdc/logging