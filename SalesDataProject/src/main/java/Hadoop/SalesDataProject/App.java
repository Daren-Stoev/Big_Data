package Hadoop.SalesDataProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;




/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	JTextField text;
	
	public void init() {
		setSize(300,200);
		setTitle("Hadoop Helper");
		
		setLayout(null);
		
		 text = new JTextField();
		JButton button = new JButton("Run Hadoop");
		
		add(text);
		add(button);
		
		text.setBounds(50,50,200,30);
		button.setBounds(50,100,200,30);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String country = text.getText();
				
				MyMapper.filter = country;
				
				runHadoop();
				
			}
		});
		
		
	}
	
	
    public static void main( String[] args ) 
    {
    	App app = new App();
    	app.init();
    	app.setVisible(true);
    }
    
    public static void runHadoop() {
    	
      	Path inputPath = new Path("hdfs://127.0.0.1:9000/input/CSV files/SalesJan2009.csv");
        Path outputPath = new Path("hdfs://127.0.0.1:9000/output/result/Sales");
        
        JobClient job = new JobClient();
        
        JobConf conf = new JobConf(App.class);
        
        conf.setMapOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setMapperClass(MyMapper.class);
        conf.setReducerClass(MyReducer.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        
        FileInputFormat.setInputPaths(conf, inputPath);
        FileOutputFormat.setOutputPath(conf, outputPath);
        
        try {
			FileSystem hdfs = FileSystem.get(URI.create("hdfs://127.0.0.1:9000"), conf);
			
			if(hdfs.exists(outputPath))
			{
				hdfs.delete(outputPath, true);
			}
			
			job.setConf(conf);
			
			job.runJob(conf);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    }
    
    
    
}
