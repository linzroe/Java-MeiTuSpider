package com.Lin.Main;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.Lin.Runnable.MeiTuHtmlParser;
import com.Lin.Util.Utils;
import com.Lin.Util.carwl;
import com.Lin.enetity.JueDui;



 
public class SimpleSpider {
	private static final int page = 1;//始
	private static final int end = 1;//终点
	public static void main(String[] args) {
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		for (int i = 2; i < 3; i++) {
			String url="http://www.jdlingyu.moe/%e7%89%b9%e7%82%b9/page/"+i+"/";
			List<JueDui> result=getsImg(url);
			for (JueDui jueDui : result) {
				System.out.println(jueDui.getUrl());
				url=jueDui.getUrl();
				HttpGet httpGet = new HttpGet(url);
				httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36");
				try {
					Thread.sleep(2000);
					CloseableHttpResponse response = httpClient.execute(httpGet);
					InputStream in = response.getEntity().getContent();
					String html = Utils.convertStreamToString(in);
					new Thread(new MeiTuHtmlParser(html, i)).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
   
 


	public static String mulu(String url){
		try (CloseableHttpClient httpClient =HttpClientBuilder.create().build();
				CloseableHttpResponse httpResponse= httpClient.execute(new HttpGet(url))){
				String result=EntityUtils.toString(httpResponse.getEntity(),"utf-8");	
				return result;
			} catch (Exception e) {
				 throw new RuntimeException(e);
			}
	}


	
	public static List<JueDui> getsImg(String url) {
		try {
			String result=mulu(url);
			Document doc=Jsoup.parse(result);
			doc.setBaseUri(url);
			Elements  as=doc.select(".pin-coat a");
			List<JueDui> list=new ArrayList<>();
			for (int i = 0; i < as.size(); i++) {
				if(i%3==0){
					JueDui j=new JueDui();
					j.setTitle(as.get(i).text());
					j.setUrl(as.get(i).absUrl("href"));
					list.add(j);
				}
			}
			return list; 
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}



 
	
}
