hadoop jar mapreduce-1.0-SNAPSHOT.jar sort.SortByTemperatureUsingTotalOrderPartitioner \
-D mapreduce.job.reduces=30 /input/ncdc/all-seq /output/ncdc/totalsort