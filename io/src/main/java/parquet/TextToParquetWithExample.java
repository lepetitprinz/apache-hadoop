package parquet;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.GroupFactory;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.example.ExampleOutputFormat;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.IOException;

public class TextToParquetWithExample extends Configured implements Tool {

    /*
     * Define the parquet schema
     */
    private static final MessageType SCHEMA = MessageTypeParser.parseMessageType(
        "message Line {" +
            "   required int 64 offset;\n" +
            "   required binary line (UTF8);\n" +
            "}");

    public static class TextToParquetMapper
        extends Mapper<LongWritable, Text, Void, Group> {

        private GroupFactory groupFactory = new SimpleGroupFactory(SCHEMA);

        @Override
        protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
            Group group = groupFactory.newGroup()
                .append("offset", key.get())
                .append("line", value.toString());
            context.write(null, group);
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n",
                getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }

        Job job = new Job(getConf(), "Text to Parquet");
        job.setJarByClass(getClass());

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(TextToParquetMapper.class);
        job.setNumReduceTasks(0);

        job.setOutputFormatClass(ExampleOutputFormat.class);
        ExampleOutputFormat.setSchema(job, SCHEMA);

        job.setOutputKeyClass(Void.class);
        job.setOutputValueClass(Group.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new TextToParquetWithExample(), args);
        System.exit(exitCode);
    }
}
