package serialization;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.VIntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class MapWritableEx {
    public static void main(String[] args) {
        MapWritable src = new MapWritable();
        src.put(new IntWritable(1), new Text("cat"));
        src.put(new VIntWritable(2), new LongWritable(163));

        System.out.println(src.get(new IntWritable(1)));
        System.out.println(src.get(new VIntWritable(2)));
    }
}
