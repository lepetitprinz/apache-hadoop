package ioformat.input;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;

public class MaxTemperatureWithMultipleInputs extends Configured
    implements Tool {

    static class MetOfficeMaxTemperatureMapper
        extends Mapper<LongWritable, Text, Text, IntWritable> {

        private
    }
}