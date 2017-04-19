package com.Lin.Io;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;

import com.Lin.Util.watermarkbk;




public class MeiTuImageCreator implements Runnable {
	public MeiTuImageCreator(){
		
	}
	private static int count = 0;
	private String imageUrl;
	private String title;
	private int page;
	private int size;
	 //存储路径
	public MeiTuImageCreator(String imageUrl,int page,String title,int size) {
		this.imageUrl = imageUrl;
		this.title = title;
		this.page = page;
		this.size = size;
	}
	private static  String basePath = "E:/Test/"; 

	@Override
	public void run() {
		File dir = new File(basePath+page);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		
		String imageName =size+imageUrl.substring(imageUrl.lastIndexOf("."),imageUrl.length());
		try {
			File file = new File( basePath+page+"/"+page+"-"+imageName);
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
		}finally {
			try {
				watermarkbk.addImgWatermark(basePath+page+"/"+page+"-"+imageName, basePath+page+"/"+page+"-"+imageName, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

    /**
     * 水印图片
     */
     
}
