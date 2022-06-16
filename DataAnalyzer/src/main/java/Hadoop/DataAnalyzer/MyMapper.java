package Hadoop.DataAnalyzer;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MyMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		
		StringTokenizer tokens = new StringTokenizer(value.toString(), ",");
		
		while(tokens.hasMoreTokens())
		{
			
			long currentToken = Long.parseLong(tokens.nextToken());
			
			if(currentToken%2 == 0)
			{
			output.collect(new Text("Even"), new IntWritable(1));
			}
			else
			{
				output.collect(new Text("Odd"), new IntWritable(1));
			}
		}
		
	/*	if(tokens.hasMoreTokens())
		{
			Text currentToken = new Text(tokens.nextToken());
			IntWritable currentTokenValue = new IntWritable(tokens.countTokens());
			output.collect(currentToken, currentTokenValue);
		}
		*/
		
	}

}
