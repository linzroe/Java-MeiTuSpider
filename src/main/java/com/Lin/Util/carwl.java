package com.Lin.Util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class carwl {
	public static String carwl(String url) throws Exception{
		try (CloseableHttpClient httpClient =HttpClientBuilder.create().build();
			CloseableHttpResponse httpResponse= httpClient.execute(new HttpGet(url))){
			String result=EntityUtils.toString(httpResponse.getEntity(),"utf-8");	
			return result;
		} catch (Exception e) {
			 throw new RuntimeException(e);
		}
		finally {
			
		}
		
	}
}
