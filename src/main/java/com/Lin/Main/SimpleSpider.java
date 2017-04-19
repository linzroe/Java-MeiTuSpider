package com.Lin.Main;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.Lin.Runnable.MeiTuHtmlParser;
import com.Lin.Util.Utils;


 
public class SimpleSpider {
	private static final int page = 1;//始
	private static final int end = 1;//终点
	public static void main(String[] args) {
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
		String url="";
		for (int i = page; i <= end; i++) {
			HttpGet httpGet = new HttpGet(url+i);
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
	
//	public static String getTime() throws Exception{
//		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8")); // 时区设置    
//	      URL url=new URL("http://www.cctv.cn");//取得资源对象    
//	      URLConnection uc=url.openConnection();//生成连接对象    
//	      uc.connect(); //发出连接    
//	      long ld=uc.getDate(); //取得网站日期时间（时间戳）    
//	      Date date=new Date(ld); //转换为标准时间对象    
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMdd HH:mm:ss", Locale.CHINA);// 输出北京时间
//		return  sdf.format(date);
//	}
	
}
