package com.joshua.crawler.core;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.joshua.crawler.bean.FormData;

public class Crawler {
	public static Map<String, String> cookieMap = new HashMap<String, String>();
	public static Map<String, String> nameAndTimeMap = new HashMap<String, String>();

	public static void crawler(FormData formData) {
		formValidate(formData);
		Cookie[] cookies = getCookie(formData);
		processCookie(cookies);
		getItemNameAndTime(formData.getForward_URL(), formData.getBase_URL());
		saveItemNameAndTime();
	}

	/**
	 * save report file's name & time
	 */
	private static void saveItemNameAndTime() {
		OutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			//out = new FileOutputStream(URLEncoder.encode("Entry_Name&Time" + new Date().toString() + ".csv", "utf-8"));
			StringBuilder sb = new StringBuilder();
			for (String str : new Date().toString().split(" ")) {
				sb.append("_").append(str);
			}
			out = new FileOutputStream(
					URLEncoder.encode("EntryNameDate" + sb.toString().replaceAll(":", "_") + ".txt", "utf-8"));
			osw = new OutputStreamWriter(out);
			bw = new BufferedWriter(osw);
			if (nameAndTimeMap != null && !nameAndTimeMap.isEmpty()) {
				bw.append("NAME,LAST_UPDATETIME\r\n");
				for (Entry<String, String> entry : nameAndTimeMap.entrySet()) {
					//					bw.append(entry.getKey() + ","
					//							+ new String(entry.getValue().split(",")[0] + " " + entry.getValue().split(",")[1])
					//							+ "\r\n");
					bw.append(entry.getKey() + "----------------" + entry.getValue() + "\r\n");
				}
				bw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * using recursive call to iterate all the folders and sub-folders, to save report file's information in a map
	 * @param url 
	 * @param base_url
	 */
	public static void getItemNameAndTime(String url, String base_url) {
		try {
			Document document = Jsoup.connect(url).cookies(cookieMap).get();
			Elements elements = document.getElementsByTag("tr");
			Elements spans = document.getElementsByAttributeValue("id", "breadcrumbContainer").first()
					.getElementsByTag("span");
			StringBuilder route = new StringBuilder();
			for (Element span : spans) {
				route.append(span.text());
			}
			String finalRoute = route.toString().replaceFirst("Current Path", "").replaceAll(">", "/");

			for (Element element : elements) {
				if (element.attr("style").equals("height:1px")) {
					if (element.getElementsByTag("tbody").first().getElementsByTag("td").size() >= 3) {// report file situation
						String name = element.getElementsByTag("a").first().text();
						if (!name.isEmpty()) {
							String time = element.getElementsByTag("nobr").first().text();
							nameAndTimeMap.put(finalRoute + "/" + name, time);
							System.out.println(finalRoute + "/" + name + " ---------- " + time);
						}
					} else if (element.getElementsByTag("tbody").first().getElementsByTag("td").size() == 2) {// folder situation
						String name = element.getElementsByTag("a").first().text();
						if (!name.isEmpty()) {
							//String time = element.getElementsByTag("nobr").first().text();
							String str = element.getElementsByTag("a").first().attr("href");
							if (!str.equals("#") && (str.length() == 143 || str.length() == 135)) {
								String id = str.substring(str.lastIndexOf("=") + 1);
								// System.out.println(BASE_URL + id);
								// System.out.println(name);
								getItemNameAndTime(base_url + id, base_url);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * processing of cookies
	 * 
	 * @param cookies
	 * @return
	 */
	public static void processCookie(Cookie[] cookies) {
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + ":" + cookie.getValue());
			cookieMap.put(cookie.getName(), cookie.getValue());
		}
	}

	/**
	 * submit the login form to LoginURL and to get the cookies returned from
	 * server
	 * 
	 * @param formData
	 * @return
	 */
	private static Cookie[] getCookie(FormData formData) {
		try {
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(formData.getLogin_URL());
			DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",
					CookiePolicy.BROWSER_COMPATIBILITY);
			NameValuePair space = new NameValuePair("CAMNamespace", formData.getNamespace());
			NameValuePair name = new NameValuePair("CAMUsername", formData.getUsername());
			NameValuePair psw = new NameValuePair("CAMPassword", formData.getPassword());
			post.setRequestBody(new NameValuePair[] { space, name, psw });
			client.executeMethod(post);
			System.out.println(client.getState());
			post.releaseConnection();

			CookieSpec cookieSpec = CookiePolicy.getDefaultSpec();
			return cookieSpec.match(formData.getCookieSite(), formData.getPort(), formData.getCookieRoute(), false,
					client.getState().getCookies());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * validation of parameters to make sure they are not empty
	 * 
	 * @param formData
	 */
	public static void formValidate(FormData formData) {
		if (formData.getLogin_URL().isEmpty() || formData.getForward_URL().isEmpty()
				|| formData.getCookieRoute().isEmpty() || formData.getCookieSite().isEmpty() || formData.getPort() == 0
				|| formData.getNamespace().isEmpty() || formData.getUsername().isEmpty()
				|| formData.getPassword().isEmpty()) {
			throw new RuntimeException("Input paramaters could not be empty!");
		}
	}
}
