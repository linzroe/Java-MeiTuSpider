package com.Lin.Io;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;



public class MeiTuImageCreator implements Runnable {
	private static int count = 0;
	private String imageUrl;
	private String title;
	private int page;
	 //存储路径
	public MeiTuImageCreator(String imageUrl,int page,String title) {
		this.imageUrl = imageUrl;
		this.title = title;
		this.page = page;
	}
	private static  String basePath = "E:/Test/"; 

	@Override
	public void run() {
		File dir = new File(basePath+title);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
		try {
			File file = new File( basePath+title+"/"+page+"-"+imageName);
			OutputStream os = new FileOutputStream(file);
			//创建一个url对象
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			byte[] buff = new byte[1024];
			while(true) {
				int readed = is.read(buff);
				if(readed == -1) {
					break;
				}
				byte[] temp = new byte[readed];
				System.arraycopy(buff, 0, temp, 0, readed);
				//写入文件
				os.write(temp);
			}
			is.close(); 
            os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
