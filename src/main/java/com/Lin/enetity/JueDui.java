package com.Lin.enetity;

public class JueDui {
	public String title;
	public String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "JueDui [title=" + title + ", url=" + url + "]";
	}
	public JueDui() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JueDui(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}
	
}
