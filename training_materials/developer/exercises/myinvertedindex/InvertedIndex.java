import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.io.
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;


public class InvertedIndex {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Usage: InvertedIndex <input dir> <output dir>");
			System.exit(-1);
		}

		JobConf conf = new JobConf(InvertedIndex.class);
		// First we give our job a meaningful name
		conf.setJobName("InvertedIndex");

		// Next, we specify the input for the mapper and the output from the reducer
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		// The data passed to the mapper is specified by an InputFormat
		conf.setInputFormat(KeyValueTextInputFormat.class);
		
		// Set mapper/reducer
		conf.setMapperClass(InvertedIndexMapper.class);
		conf.setReducerClass(InvertedIndexReducer.class);
		
		// Set mapper outputs
		conf.setMapOutputKeyClass(Text.class);
		conf.setMapOutputValueClass(Text.class);

		// Set reducer outputs
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		// Run the job with this configuration
		// Difference between runJob and submitJob - runJob waits until job
		// has finished before exiting.
		// runJob allows MR tasks to be chained (cascaded).
		JobClient.runJob(conf);
		System.exit(0);	
	}
	
}
