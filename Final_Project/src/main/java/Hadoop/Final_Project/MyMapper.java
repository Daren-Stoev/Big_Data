package Hadoop.Final_Project;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MyMapper extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable> {

	
	static String start_date;
	static String end_date;
	static String country = "";
	static String town = "";
	static Boolean precise_Search = false;
	static String product;
	static String result;
	
	
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		
		String line = value.toString();
		
		String[] array = line.split(",");
		
		 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		 
		 String tempCountry = "";
		 String tempTown = "";
		 if(key.get() != 0)
		 {
		 try {
			 String curr = array[0];
			 Date startdate = sdf.parse(start_date);
			 Date finaldate = sdf.parse(end_date);
			 Date currentdate = sdf.parse(curr);
			 String sd = sdf.format(startdate);
			 String fd = sdf.format(finaldate);
			 String cd = sdf.format(currentdate);
			 System.out.println(startdate + "----" + finaldate + "-----" + currentdate);
			
			if(currentdate.after(startdate)) {
				System.out.println("Yes");
			}
			if(currentdate.before(startdate))
			{
				System.out.println("no");
			}
			
			if((currentdate.after(startdate)&&currentdate.before(finaldate)))
			{
				System.out.println("XD");
			try {					
				int price = Integer.parseInt(array[2]);
				if(country.isEmpty())
				{
					if(town.isEmpty()) {
						
						if(array[1].equalsIgnoreCase(product) || product.equalsIgnoreCase("all"))
						{
							Collect(output,array[7],array[5],array[3],price);
							
						}
						
					}
					
					if((array[5].contains(town) && precise_Search == false) || (array[5].equalsIgnoreCase(town) && precise_Search == true))
					{

						if(array[1].equalsIgnoreCase(product) || product.equalsIgnoreCase("all"))
						{
							tempTown = array[5];
							Collect(output,array[7],tempTown,array[3],price);
							
						}
					}
				} 
				else 
				{
					if((array[7].contains(country) && precise_Search == false) || (array[7].equalsIgnoreCase(country) && precise_Search == true))
					{
						tempCountry = array[7];
						if(town.isEmpty()) {
							
							if(array[1].equalsIgnoreCase(product) || product.equalsIgnoreCase("all"))
							{
								Collect(output,tempCountry,tempTown,array[3],price);
							}
							
							
						}
						if((array[5].contains(town) && precise_Search == false) || (array[5].equalsIgnoreCase(town) && precise_Search == true))
						{
							if(array[1].equalsIgnoreCase(product) || product.equalsIgnoreCase("all"))
							{
								tempTown = array[5];
								Collect(output,tempCountry,tempTown,array[3],price);
							}
							
						}
					}
				}
			}
				catch(NumberFormatException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		 }
		 
		
	}
	
	public void Collect(OutputCollector<Text, IntWritable> output, String country,String town, String payment_type, int price) throws IOException {
		
		String token = country;
		if(!town.isEmpty())
		{
			token = token + " " + this.town + " ";
		}
		
		if(result == "Средна сума")
		{
			
			output.collect(new Text(token), new IntWritable(price) );

		}
		if(result == "Тотал")
		{
			output.collect(new Text(token), new IntWritable(price) );
		}
		if(result == "Тип плащане тотал")
		{
			token = token + payment_type + " ";
			output.collect(new Text(token), new IntWritable(price) );
		}
		if(result == "Тип плащане средно")
		{
			token = token + payment_type + " ";
			output.collect(new Text(token), new IntWritable(price) );
		}
		
	}
	 

}


