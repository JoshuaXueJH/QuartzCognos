package com.joshua.quartz.test;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.joshua.quartz.job.CognosCrawlerJob;

public class Test {
	public void go() throws Exception {
		SchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();

		// job1 每两分钟跑一次
		JobDetail job = newJob(CognosCrawlerJob.class).withIdentity("job1", "goup1").build();
		CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
				.withSchedule(cronSchedule("0 11 14 * * ?")).build();
		Date date = scheduler.scheduleJob(job, trigger);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		System.out
				.println(job.getKey() + "已被安排于:" + format.format(date) + "并以如下规则重复执行" + trigger.getExpressionSummary());

		scheduler.start();
	}

	public static void main(String[] args) throws Exception {
		Test test = new Test();
		test.go();
	}
}
