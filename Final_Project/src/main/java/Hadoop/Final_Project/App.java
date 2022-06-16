package Hadoop.Final_Project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

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
	JTextField country_text;
	
	public void init() {
		setSize(1000,750);
		setTitle("Hadoop Helper");
		
		setLayout(null);
		
		country_text = new JTextField();
		final JTextField town_text = new JTextField();
		JButton startButton = new JButton("Run Hadoop");
		final JCheckBox preciseSearchButton = new JCheckBox("Chin");
		//preciseSearchButton.setMnemonic(KeyEvent.VK_C); 
		preciseSearchButton.setSelected(false);
		
		String[] products = { "All", "Product1", "Product2", "Product3"};
		final JComboBox productsList = new JComboBox(products);
		productsList.setSelectedIndex(0);
		
		String[] results = { "Средна сума", "Тотал", "Тип плащане тотал","Тип плащане средно"};
		final JComboBox resultsList = new JComboBox(results);
		productsList.setSelectedIndex(0);
		
		
	    UtilDateModel model_start = new UtilDateModel();
	    JDatePanelImpl datePanel_start = new JDatePanelImpl (model_start);
	    final JDatePickerImpl datePicker_start = new JDatePickerImpl(datePanel_start);

	    
	    UtilDateModel model_final = new UtilDateModel();
	    JDatePanelImpl datePanel_final = new JDatePanelImpl (model_final);
	    final JDatePickerImpl datePicker_final = new JDatePickerImpl(datePanel_final);
	    
		final JLabel label_date_start_failure = new JLabel();
		final JLabel label_date_final_failure = new JLabel();
		final JLabel startDateLabel = new JLabel("Start Date");
		final JLabel finalDateLabel = new JLabel("Final Date");
		label_date_start_failure.setForeground(Color.RED);
		label_date_final_failure.setForeground(Color.RED);
		final JLabel countryLabel = new JLabel("Country");
		final JLabel townLabel = new JLabel("Town/City");
		final JLabel preciseSearchLabel = new JLabel("PreciseSearch");
		final JLabel productsListLabel = new JLabel("Product");
		final JLabel resultsListLabel = new JLabel("Result");
		
		add(datePicker_start);   
	    add(datePicker_final);     
		add(country_text);
		add(town_text);
		add(startButton);
		add(preciseSearchButton);
		add(productsList);
		add(resultsList);
		
		
	    add(startDateLabel);
	    add(finalDateLabel);
	    add(label_date_start_failure);
	    add(label_date_final_failure);
	    add(countryLabel);
	    add(townLabel);
	    add(preciseSearchLabel);
	    add(productsListLabel);
	    add(resultsListLabel);
	    
		startButton.setBounds(50,0,200,30);
		datePicker_start.setBounds(50, 50, 200, 30);
		datePicker_final.setBounds(50, 100, 200, 30);
		country_text.setBounds(50,150,200,30);
		town_text.setBounds(50,200,200,30);
		preciseSearchButton.setBounds(150, 235, 50, 30);
		productsList.setBounds(50, 300, 200, 30);
		resultsList.setBounds(50, 350, 200, 30);
		
		startDateLabel.setBounds(50,25,200,30);
		finalDateLabel.setBounds(50,75,200,30);
		label_date_start_failure.setBounds(250, 50, 200, 30);
		label_date_final_failure.setBounds(250, 100, 200, 30);
		countryLabel.setBounds(50, 125, 200, 30);
		townLabel.setBounds(50, 175, 200, 30);
		preciseSearchLabel.setBounds(50, 235, 200, 30);
		productsListLabel.setBounds(50, 275, 200, 30);
		resultsListLabel.setBounds(50, 325, 200, 30);
		


		
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String country = country_text.getText();
				String start_date = datePicker_start.getJFormattedTextField().getText();
				String final_date = datePicker_final.getJFormattedTextField().getText();
				String town = town_text.getText();
				boolean precise_Search = preciseSearchButton.isSelected();
				String product = productsList.getSelectedItem().toString();
				String result = resultsList.getSelectedItem().toString();
				System.out.println(result);
				if(start_date.isEmpty())
				{
				label_date_start_failure.setText("Това поле е задължително!");
				}
				if(final_date.isEmpty())
				{
				label_date_final_failure.setText("Това поле е задължително!");
				}
				if(!start_date.isEmpty())
				{
					label_date_start_failure.setText("");
				}
				if(!final_date.isEmpty())
				{
					label_date_final_failure.setText("");
				}
				
				//	if(fd.compareTo(sd)<0) // fd - sd

				
				
				
				if(!start_date.isEmpty() && !final_date.isEmpty())
				{
					try {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					start_date = start_date.replace(".", "/").toString();
					Date sd = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);

					final_date = final_date.replace(".", "/").toString();
					Date fd = new SimpleDateFormat("dd/MM/yyyy").parse(final_date);
					String s = sdf.format(sd);
					String f = sdf.format(fd);
					
					
					MyMapper.start_date = s;
					MyMapper.end_date = f;
					MyMapper.country = country;
					MyMapper.town = town;
					MyMapper.product = product;
					MyMapper.result = result;
					MyMapper.precise_Search = precise_Search;
					MyReducer.result = result;
					System.out.println(product);
					System.out.println(s);
					System.out.println(f);
					runHadoop();
					}
					 catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}		
				
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
    	
      	Path inputPath = new Path("hdfs://127.0.0.1:9000/input/CSV files/Final/SalesJan2009.csv");
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
