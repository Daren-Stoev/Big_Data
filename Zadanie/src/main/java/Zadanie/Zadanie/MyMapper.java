package Zadanie.Zadanie;

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
		

		if(key.get() != 0)		
		{
			int tokencount = tokens.countTokens();
		while(tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			 
			// Взимаме информацията от колоната с начини на заплащане
			if(tokens.countTokens()== 8 && tokencount == 12) 
			output.collect(new Text(token), new IntWritable(1));
			// допълнитени клаузи в случай че в редът има празна колона, или запетая където не трябва
			else if ((tokencount < 12 && tokens.countTokens() == 7) || (tokencount > 12 && tokens.countTokens() == 8)) 
				output.collect(new Text(token), new IntWritable(1));
			//Взимаме колоните с вид продукт
			//Поради някаква причина ми излиза отделен Product3 само веднъж, отделно от другитe Product3
			else if( tokencount - tokens.countTokens() == 2)
				output.collect(new Text(token), new IntWritable(1));	
			//Взимаме датите на поръчка
			else if(tokens.countTokens() == tokencount - 1)
			{
				//Проверяваме къде има празно място, за да може след това да го използваме като граница за изрязване на низа
				int token_space_char = token.indexOf( ' ');
				if(token_space_char >= 0)
				{
					//изрязваме низа за да използваме само датата, тъй като не ни интересува часът, в който е направена поръчката
				token = token.substring(0, token_space_char).toString();
				}
				output.collect(new Text(token), new IntWritable(1));
			}
			
		}
		
	}
	}
}
