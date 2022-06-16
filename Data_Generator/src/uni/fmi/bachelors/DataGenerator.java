package uni.fmi.bachelors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataGenerator {
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("data.txt");
		FileWriter writer = new FileWriter(file, false);
		
		Scanner in = new Scanner(System.in);
		System.out.println("Въведете интервал");
		
		long x = in.nextInt();
		long y = in.nextInt();
		
		for(long i = x; i< y; i++)
		{
			x = i;
			writer.write("\n" + x);
			if(x % 100000 == 0)
				System.out.println("Generating " + i );
			while(x != 1)
			{
				if(x % 2 == 0)
				{
					x = x/2;
				}
				else
				{
					x = x * 3 + 1; 
				}
				writer.write(", " + x);
			}
		}
		System.out.println("Job's done!");
		
		
		
	}
}
