package sort;

import common.JobBuilder;
import common.NcdcRecordParser;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/*
 * A Mapreduce program for transforming the weather data into Sequence File format.
 */
public class SortDataPreprocessor extends Configured implements Tool {

    static class CleanerMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

        private NcdcRecordParser parser = new NcdcRecordParser();

        @Override
        protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
            parser.parse(value);
            if (parser.isValidTemperature()) {
                context.write(
                    new IntWritable(parser.getAirTemperature()),
                    value);
            }
        }
    }

    @ Override
    public int run(String[] args) throws Exception {
        Job job = JobBuilder.parseInputAndOutput(this, getConf(), args);
        if (job == null) { return -1; };

        job.setMapperClass(CleanerMapper.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(0);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        // GzipCodec does not supported on UNIX OS (Mac / Windows)
//        SequenceFileOutputFormat.setCompressOutput(job, true);
//        SequenceFileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
//        SequenceFileOutputFormat.setOutputCompressionType(job, CompressionType.BLOCK);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new SortDataPreprocessor(), args);
        System.exit(exitCode);
    }
}
