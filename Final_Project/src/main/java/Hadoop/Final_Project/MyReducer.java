package Hadoop.Final_Project;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MyReducer extends MapReduceBase implements Reducer<Text, IntWritable,Text,LongWritable> {

	static String result = "sum";
	
	@Override
	public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, LongWritable> output,
			Reporter reporter) throws IOException {
		// TODO Auto-generated method stub
		
		int sum = 0;
		int count = 0;
		
		while(values.hasNext()) {
			sum += values.next().get();
			count ++;
		}
		if(!result.equalsIgnoreCase("sum"))
		{
			sum = sum/count;
		}
		output.collect(key, new LongWritable(sum));
		
	}

}
