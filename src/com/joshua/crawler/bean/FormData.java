package com.joshua.crawler.bean;

/**
 * encapsulation of parameters needed
 * 
 * @author XueJianhua
 *
 */
public class FormData {
	private String login_URL;
	private String forward_URL;
	private int port;
	private String cookieSite;
	private String namespace;
	private String username;
	private String password;
	private String cookieRoute;
	private String base_URL;

	public FormData(String login_URL, String forward_URL, String base_URL, int port, String cookieSite,
			String cookieRoute, String namespace, String username, String password) {
		super();
		this.login_URL = login_URL;
		this.forward_URL = forward_URL;
		this.base_URL = base_URL;
		this.port = port;
		this.cookieSite = cookieSite;
		this.cookieRoute = cookieRoute;
		this.namespace = namespace;
		this.username = username;
		this.password = password;
	}

	public FormData() {
		super();
	}

	public String getBase_URL() {
		return base_URL;
	}

	public void setBase_URL(String base_URL) {
		this.base_URL = base_URL;
	}

	public String getLogin_URL() {
		return login_URL;
	}

	public void setLogin_URL(String login_URL) {
		this.login_URL = login_URL;
	}

	public String getForward_URL() {
		return forward_URL;
	}

	public void setForward_URL(String forward_URL) {
		this.forward_URL = forward_URL;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getCookieSite() {
		return cookieSite;
	}

	public void setCookieSite(String cookieSite) {
		this.cookieSite = cookieSite;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCookieRoute() {
		return cookieRoute;
	}

	public void setCookieRoute(String cookieRoute) {
		this.cookieRoute = cookieRoute;
	}

}
