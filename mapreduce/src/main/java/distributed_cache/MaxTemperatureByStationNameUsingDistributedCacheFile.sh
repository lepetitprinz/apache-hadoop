hadoop jar target/mapreduce-1.0-SNAPSHOT.jar \
distributed_cache.MaxTemperatureByStationNameUsingDistributedCacheFile \
-files input/ncdc/metadata/stations-fixed-width.txt input/ncdc/all \
output/ncdc/cache