hadoop jar target/mapreduce-1.0-SNAPSHOT.jar sort.MaxTemperatureUsingSecondarySort \
-conf conf/hadoop-local.xml input/ncdc/all output/ncdc/secondarysort