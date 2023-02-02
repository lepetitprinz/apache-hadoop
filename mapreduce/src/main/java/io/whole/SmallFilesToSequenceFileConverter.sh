hadoop jar mapreduce-1.0-SNAPSHOT.jar io.whole.SmallFilesToSequenceFileConverter \
-D mapreduce.job.reduces=2 \
/input/smallfiles /output/smallfiles