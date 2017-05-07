package com.hadoop.exm;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/7.
 */
public class WordCount {

    /**
     * Object 输入数据的具体内容
     * Text 每行文本数据
     * Text 每个单词分解的统计结果
     * IntWritable 输出记录的结果
     */
    private static class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {
        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String lineContent = value.toString();
            String[] result = lineContent.split(" ");
            for(int t=0;t<result.length;t++){
                context.write(new Text(result[t]),new IntWritable(1));
            }
        }
    }

    /**
     * Text Map输出的文本内容
     * IntWritable Map处理的个数
     * Text Reduce输出文本
     * IntWritable Reduce的输出个数
     */
    private static class WordCountReducer extends Reducer<Text,IntWritable,Text,IntWritable>{
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable count:values){
                sum +=count.get();
            }
            context.write(key,new IntWritable(sum));

        }
    }

    public static void main(String[] args) throws Exception{
        if(args.length !=2){
            System.out.println("本程序需要两个参赛");
            System.exit(1);
        }
        Configuration conf = new Configuration();
        String[] argArray = new GenericOptionsParser(conf,args).getRemainingArgs();
        Job job = Job.getInstance(conf,"hadoop");
        job.setJarByClass(WordCount.class);//设置执行的jar文件程序类
        job.setMapperClass(WordCountMapper.class);//指定Mapper的处理类
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(WordCountReducer.class);//设置Reduce处理类
        //设置Map-Reduce最终执行结果
        job.setOutputKeyClass(Text.class);//信息设置为文本
        job.setOutputValueClass(IntWritable.class);//最终内容设置为一个数值

        //设置输入以及输出路径
        FileInputFormat.addInputPath(job,new Path(argArray[0]));
        FileOutputFormat.setOutputPath(job,new Path(argArray[1]));
        //等待执行完毕
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
