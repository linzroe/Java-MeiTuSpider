package com.Lin.Util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.im4java.core.ImageCommand;



public class watermarkbk {
	private static Image watermarkImage = null;
    private static final boolean USE_GRAPHICS_MAGICK_PATH = true;
    private static final String GRAPHICS_MAGICK_PATH = "E:\\GraphicsMagick-1.3.23-Q8";
    private static final String IMAGE_MAGICK_PATH = "E:\\ImageMagick-6.2.7-Q8";

    /**
     * 水印图片路径
     */
    private static String watermarkImagePath = "C:\\Users\\Lin\\Desktop\\test\\2.png";
    static {
        try {
            //watermarkImage = ImageIO.read(new URL(watermarkImagePath));
            watermarkImage = ImageIO.read(new File(watermarkImagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 创建目录
     * 
     * @param path
     * @return path
     */
    private static String createDirectory(String path) {
        File file = new File(path);
        if (!file.exists())
            file.getParentFile().mkdirs();
        return path;
    }
    
    /**
     * 命令类型
     * 
     * @author hailin0@yeah.net
     * @createDate 2016年6月5日
     *
     */
    private enum CommandType {
        convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
        private String name;

        CommandType(String name) {
            this.name = name;
        }
    }
    
    /**
     * 获取 ImageCommand
     * 
     * @param command 命令类型
     * @return
     */
    private static ImageCommand getImageCommand(CommandType command) {
        ImageCommand cmd = null;
        switch (command) {
        case convert:
            cmd = new ConvertCmd(USE_GRAPHICS_MAGICK_PATH);
            break;
        case identify:
            cmd = new IdentifyCmd(USE_GRAPHICS_MAGICK_PATH);
            break;
        case compositecmd:
            cmd = new CompositeCmd(USE_GRAPHICS_MAGICK_PATH);
            break;
        }
        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            cmd.setSearchPath(USE_GRAPHICS_MAGICK_PATH ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }
	 /**
     * 图片水印
     * 
     * @param srcImagePath 源图片路径
     * @param destImagePath 目标图片路径
     * @param dissolve 透明度（0-100）
     * @throws Exception
     */
    public static void addImgWatermark(String srcImagePath, String destImagePath, Integer dissolve)
            throws Exception {
        // 原始图片信息
        BufferedImage buffimg = ImageIO.read(new File(srcImagePath));
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();

        IMOperation op = new IMOperation();
        // 水印图片位置
        op.geometry(watermarkImage.getWidth(null)+100, watermarkImage.getHeight(null)+100, w
                - watermarkImage.getWidth(null), h - watermarkImage.getHeight(null));
        // 水印透明度
        op.dissolve(dissolve);
        // 水印
        op.addImage(watermarkImagePath);
        // 原图
        op.addImage(srcImagePath);
        // 目标
        op.addImage(createDirectory(destImagePath));
        ImageCommand cmd = getImageCommand(CommandType.compositecmd);
        cmd.run(op);
    }
}
