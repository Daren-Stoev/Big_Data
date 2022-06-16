package Hadoop.SalesDataProject;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MyMapper extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable> {

	
	static String filter = "all";
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		
		String line = value.toString();
		
		String[] array = line.split(",");
		
		try {
			
			int price = Integer.parseInt(array[2]);
			if(filter.equalsIgnoreCase("all"))
			{
			output.collect(new Text(array[7]), new IntWritable(price));
			} else 
			{
				if(array[7].contains(filter))
				{
					output.collect(new Text(array[7]), new IntWritable(price));
				}
			}
			
		} catch(NumberFormatException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
