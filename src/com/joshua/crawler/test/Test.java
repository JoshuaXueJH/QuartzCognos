package com.joshua.crawler.test;

import com.joshua.crawler.bean.FormData;
import com.joshua.crawler.bean.FormList;
import com.joshua.crawler.core.Crawler;

public class Test {

	/**
	 * test entrance
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FormData form = new FormData(FormList.LOGIN_URL, FormList.FORWARD_URL, FormList.BASE_URL, FormList.PORT,
				FormList.COOKIE_SITE, FormList.COOKIE_ROUTE, FormList.NAMESPACE, FormList.USERNAME, FormList.PASSWORD);
		Crawler.crawler(form);
	}

}
