hadoop jar mapreduce-1.0-SNAPSHOT.jar workflow.v4.MaxTemperatureDriver \
-conf conf/hadoop-cluster.xml \
-D mapreduce.task.profile=true \
/input/ncdc/all /output/ncdc/profile