package Hadoop.DataAnalyzer;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.FileInputFormat;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	Configuration conf = new Configuration();
    	
    	JobConf jobConf = new JobConf(conf,App.class);
    	
    	jobConf.setMapperClass(MyMapper.class);
    	jobConf.setReducerClass(MyReducer.class);
    	jobConf.setOutputKeyClass(Text.class);
    	jobConf.setMapOutputValueClass(IntWritable.class);
    	
    	Path inputPath = new Path("hdfs://127.0.0.1:9000/input/data_numbers.txt");
    	Path outputPath = new Path("hdfs://127.0.0.1:9000/result");
    	
    	FileInputFormat.setInputPaths(jobConf, inputPath);
    	FileOutputFormat.setOutputPath(jobConf, outputPath);
    	
    	FileSystem hdfs = FileSystem.get(URI.create("hdfs://127.0.0.1:9000"), conf);
    	
    	if(hdfs.exists(outputPath))
    		hdfs.delete(outputPath,true);
    	
    	RunningJob result = JobClient.runJob(jobConf);        
        System.out.println("Success=" + result.isComplete());
    	
    }
}
