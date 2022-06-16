package WordCounter.WordCounter;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        Path inputPath = new Path("hdfs://127.0.0.1:9000/input/CSV files/SalesJan2009.csv");
        Path outputPath = new Path("hdfs://127.0.0.1:9000/output/result_Zadanie");
        
        Configuration config = new Configuration();
        
        JobConf myJob = new JobConf(config, App.class);
        
        FileInputFormat.setInputPaths(myJob, inputPath);
        
        FileOutputFormat.setOutputPath(myJob, outputPath);
        
        myJob.setMapOutputKeyClass(Text.class);
        myJob.setMapOutputValueClass(IntWritable.class);
        myJob.setOutputFormat(TextOutputFormat.class);
        myJob.setMapperClass(MyMapper.class);
        myJob.setReducerClass(MyReducer.class);
        
        FileSystem fs = FileSystem.get(URI.create("hdfs://127.0.0.1:9000"), config);
        
        if(fs.exists(outputPath))
        {
        	fs.delete(outputPath, true);
        }
        
        RunningJob runJob = JobClient.runJob(myJob);
        
        System.out.println("Is Successful = " + runJob.isSuccessful());
        
    }
}
