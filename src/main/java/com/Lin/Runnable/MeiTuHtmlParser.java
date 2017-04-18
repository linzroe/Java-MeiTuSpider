package com.Lin.Runnable;
import java.util.ArrayList;
import java.util.List;

import com.Lin.Io.MeiTuImageCreator;



public class MeiTuHtmlParser implements Runnable {
	private String html;
	private int page;
	public MeiTuHtmlParser(String html,int page) {
		this.html = html;
		this.page = page;
	}
	@Override
	public void run() {
		List<String> list = new ArrayList<String>();
		String title=html.substring(html.indexOf("<title>")+7, html.lastIndexOf("</title>"));
		html = html.substring(html.indexOf("<section class=\"content\" >/n")+29,html.lastIndexOf("</section>"));
		String[] images = html.split("<img ");
		for (String url : images) {
			if(url.indexOf("src=")!=-1){
				list.add("http://www.ydo.tv"+url.substring(url.indexOf("src=\"/")+5, url.lastIndexOf("\" />")));
			}
		}
 
		for(String imageUrl : list){
				new Thread(new MeiTuImageCreator(imageUrl,page,title+"/")).start();
		}
	}
}
