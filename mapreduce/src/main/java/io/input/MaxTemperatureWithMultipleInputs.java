package io.input;

import workflow.v1.MaxTemperatureReducer;
import workflow.v3.MaxTemperatureMapper;

import common.JobBuilder;
import common.MetOfficeRecordParser;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

public class MaxTemperatureWithMultipleInputs extends Configured
    implements Tool {

    static class MetOfficeMaxTemperatureMapper
        extends Mapper<LongWritable, Text, Text, IntWritable> {

        private MetOfficeRecordParser parser = new MetOfficeRecordParser();

        @Override
        protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
            parser.parse(value);
            if (parser.isValidTemperature()) {
                context.write(
                    new Text(parser.getYear()),
                    new IntWritable(parser.getAirTemperature())
                );
            }
        }
    }
    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 3) {
            JobBuilder.printUsage(this, "<ncdc input> <metoffice input> <output>");
            return -1;
        }

        Job job = new Job(getConf(), "Max Temperature with multiple input formats");
        job.setJarByClass(getClass());

        Path ncdcInputPath = new Path(args[0]);
        Path metOfficeInputPath = new Path(args[1]);
        Path outputPath = new Path(args[2]);

        MultipleInputs.addInputPath(job, ncdcInputPath,
            TextInputFormat.class, MaxTemperatureMapper.class);
        MultipleInputs.addInputPath(job, metOfficeInputPath,
            TextInputFormat.class, MetOfficeMaxTemperatureMapper.class);
        FileOutputFormat.setOutputPath(job, outputPath);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setCombinerClass(MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new MaxTemperatureWithMultipleInputs(), args);
        System.exit(exitCode);
    }
}