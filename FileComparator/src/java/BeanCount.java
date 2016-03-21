package java;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class BeanCount {

	
	public static class Map extends Mapper {
	    private final static IntWritable one = new IntWritable(1);
	    private MyBean myBean = new MyBean();
	        
	    public void map(MyBean key, 
	    			Text value, Context context) throws IOException, InterruptedException {
	        String line = value.toString();
	        String[] input = line.split(",");
	        myBean.setName(input[0]);
	        myBean.setToday(new Date(input[1]));
	        myBean.setNumber(new Long(input[2]));
	        myBean.setDec(new BigDecimal(input[3]));
	        context.write(myBean, myBean);
	    }
	 } 
	
	 public static class Reduce extends Reducer {

		    public void reduce(MyBean key, Iterable<MyBean> values, Context context) 
		      throws IOException, InterruptedException {
		        int sum = 0;
		        for (MyBean myBean : values) {
		            sum += sum+1;
		        }
		        if(sum<2 || sum > 2){
		        	context.write(key,key);
		        }
		    }
		 }
	
	 public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		        
		        Job job = new Job(conf, "wordcount");
		    
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(IntWritable.class);
		        
		    job.setMapperClass(Map.class);
		    job.setReducerClass(Reduce.class);
		        
		    job.setInputFormatClass(TextInputFormat.class);
		    job.setOutputFormatClass(TextOutputFormat.class);
		        
		    FileInputFormat.addInputPath(job, new Path(args[0]));
		    FileOutputFormat.setOutputPath(job, new Path(args[1]));
		        
		    job.waitForCompletion(true);
		 }
		        
}
