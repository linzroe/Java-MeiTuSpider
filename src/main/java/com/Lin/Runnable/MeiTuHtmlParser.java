package com.Lin.Runnable;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
		Document doc=Jsoup.parse(html);
		String title=doc.select("title").first().text().replaceAll(" - 绝对领域", "");
		Elements as=doc.select(".main-body p a");
		for (int i = 0; i < as.size(); i++) {
			list.add(as.get(i).absUrl("href"));
		}
		
		
 
		for (int i = 0; i < list.size(); i++) {
			new Thread(new MeiTuImageCreator(list.get(i),page,"/"+title+"/",i)).start();
		}
	}
	
 
 
}
