package com.joshua.quartz.job;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.joshua.crawler.bean.FormData;
import com.joshua.crawler.bean.FormList;

import com.joshua.crawler.core.Crawler;

public class CognosCrawlerJob implements Job {

	@Override
	@Test
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		FormData form = new FormData(FormList.LOGIN_URL, FormList.FORWARD_URL, FormList.BASE_URL, FormList.PORT,
				FormList.COOKIE_SITE, FormList.COOKIE_ROUTE, FormList.NAMESPACE, FormList.USERNAME, FormList.PASSWORD);
		Crawler.crawler(form);
	}

}
