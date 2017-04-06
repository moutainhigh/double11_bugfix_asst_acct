package com.yuwang.pinju.common;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import magick.ImageInfo;
import magick.MagickImage;

/**
 * @Description: 文件(包括图片)安全类,类型校验不依赖于后缀名,一切以后缀名发起的攻击都是伪攻击
 * @author 石兴 shixing@zba.com
 * @date 2011-7-29 上午09:23:42
 * @update 2011-11-01 下午15:31:42
 * @version V1.0
 */
public class FileSecurityUtils {
	
	
	static {
		if (System.getProperty("jmagick.systemclassloader") == null) {
			System.setProperty("jmagick.systemclassloader", "no");
		}
		try {
			MagickImage.class.getClass();
		} catch (Error e) {
			System.err.println("Make sure JMagick libraries are available in java.library.path. Current value: ");
			System.err.println("java.library.path=" + System.getProperty("java.library.path"));
			throw e;
		}
	}
	
	/**
	 * 目前支持的文件类型列表,图片格式不要配在此处,请调用getImageType方法获取
	 */
	private static final String FILE_SUFFIX[][] = {{"doc", "D0CF11E0"}, 
												  {"rtf", "7B5C727466"}, 
												  {"xls", "D0CF11E0"},
												  {"docx","504B0304140006"},
												  {"xlsx","504B0304140006"},
						                          {"pdf", "255044462D312E"}, 
						                          {"zip", "504B030414"},
						                          {"rar", "52617221"}}; 
	
	/**
	 * 目前支持的图片格式列表,图片类型必须调用getImageType()方法获取
	 */
	private final static String[] SUFFIX_IMAGE = { "jpg", "jpeg", "gif", "png" }; 
	
	/**
	 * 目前支持的文件格式列表,图片文件类型请勿在此配置
	 */
	private final static String[] SUFFIX_FILE = { "doc", "docx", "rtf", "xls", "pdf", "zip", "rar"}; 
	
	/**
	 * GIF类型
	 */
	private final static String IMAGE_GIF = "gif";
	
	/**
	 * Created on 2011-7-29
	 * <p>Discription:[获取文件16进制信息]</p>
	 * @param b
	 * @return fileTypeHex
	 * @author:[石兴 2011.07.29]
	 */
	private final static String getFileHexString(byte[] b) {
		StringBuilder stringBuilder = new StringBuilder();
		if (b == null || b.length <= 0) {
			return null;
		}
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Created on 2011-7-29
	 * <p>Discription:[获取文件类型,目前只支持已配置的类型,若不在此范围类,则返回空串]</p>
	 * <p>优点：执行效率比较高</p>
	 * <p>缺点：该方法不是太安全，byte信息可能被伪造，所以只能用在相对安全的功能点</p>
	 * <p>注意：图片类型的获取不要调用此方法，请调用getImageType(File file) </p>
	 * @param file
	 * @return fileType
	 * @author:[石兴]
	 */
	public final static String getFileTypeByFile(File file) {
		String filetype = EMPTY;
		try {
			byte[] b = new byte[100];
			InputStream is = new FileInputStream(file);
			is.read(b);
			is.close();
			String filetypeHex = String.valueOf(getFileHexString(b));
			for (String type[] : FILE_SUFFIX) {
				if (filetypeHex.toUpperCase().startsWith(type[1])) {
					filetype = type[0];
					break;
				}
			}
		} catch (Exception e) {
			filetype = EMPTY;
		}
		return filetype;
	}
	
	/**
	 * Created on 2011-11-1 
	 * @desc <p>Discription:[过滤图片恶意信息]</p>
	 * @param 
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-11-1] [更改人姓名]
	 */
	public static boolean filterImage(File file,String suffix){
		boolean result = false;
		MagickImage magic = null;
		try {
			/****** imageToBlob来回折腾得到imageByte,目的是为解决中文文件名问题 ******/
			/*ImageInfo imageInfo = new ImageInfo(file.getAbsolutePath());
			MagickImage magickImage = new MagickImage(imageInfo);
			byte[] imageByte = magickImage.imageToBlob(imageInfo);
			magic = new MagickImage(new ImageInfo(),imageByte);*/
		    ImageInfo imgInfo = new ImageInfo(file.getAbsolutePath());
		    magic = new MagickImage(imgInfo);
            int width = (int) magic.getDimension().getWidth();
            int height = (int) magic.getDimension().getHeight();
            if (IMAGE_GIF.equalsIgnoreCase(suffix)) {//对于GIF逐帧缩放
            	MagickImage[] images=magic.breakFrames();  
                for(int i=0;i<images.length;i++){  
                   images[i]= images[i].scaleImage(width,height);  
                }  
                magic=new MagickImage(images);  
                magic.setImageAttribute("Dispose","1");  
                magic.setImageAttribute("Delay","1");  
                magic.profileImage("*", null); 
                magic.setImageAttribute("comment", null); 
                //magic.setFileName(trimExtension(file.getAbsolutePath()).concat(".").concat(suffix.toLowerCase()));  
                magic.setFileName(file.getAbsolutePath());
                magic.writeImage(new ImageInfo());  
			}else {
				magic = magic.scaleImage(width, height);
				magic.profileImage("*", null); 
				//newImage.setImageAttribute("JPEG-Sampling-factors", null); 
				magic.setImageAttribute("comment", null); 
				//magic.setFileName(trimExtension(file.getAbsolutePath()).concat(".").concat(suffix.toLowerCase()));
				magic.setFileName(file.getAbsolutePath());
				magic.writeImage(new ImageInfo());
			}
            magic.destroyImages();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * Created on 2011-11-1 
	 * @desc <p>Discription:[按指定宽度和高度缩放图片]</p>
	 * @param file 缩放源 | newWidth 缩放后的宽度 | newHeight 缩放后的高度 | newFileName 缩放后的文件全路径
	 * @return boolean
	 * @author:[石兴]
	 * @update:[2011-11-1] [更改人姓名]
	 */
	public static boolean scaleImage(File file,int newWidth,int newHeight,String newFileName,boolean isProportional) throws FileNotFoundException{
		if(file==null || !file.isFile()){
			throw new FileNotFoundException();
		}
		boolean result = false;
		MagickImage magic = null;
		try {
			ImageInfo imageInfo = new ImageInfo(file.getAbsolutePath());
			MagickImage magickImage = new MagickImage(imageInfo);
			byte[] imageByte = magickImage.imageToBlob(imageInfo);
			magic = new MagickImage(new ImageInfo(),imageByte);
            int width = (int) magic.getDimension().getWidth();
            int height = (int) magic.getDimension().getHeight();
    		if (width > newWidth || height > newWidth) {  
    			if(isProportional){
	                if(width * newHeight > height * newWidth){  
	                	newHeight = height * newWidth / width;  
	                }else{  
	                	newWidth = width * newHeight / height;  
	                } 
    			}
            } else {  
            	newWidth = width;  
            	newHeight = height;  
            }  
            MagickImage newImage = magic.scaleImage(newWidth, newHeight);
            newImage.profileImage("*", null); 
            //newImage.setImageAttribute("JPEG-Sampling-factors", null); 
            newImage.setImageAttribute("comment", null); 
            newImage.setFileName(newFileName);
            newImage.writeImage(new ImageInfo());
            magic.destroyImages();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
    /**
     * Created on 2011-11-1 
     * @desc <p>Discription:[裁剪图片]</p>
     * @param file 裁剪源 | startX 起始X坐标|startY 起始Y坐标 | newWidth 需要裁剪的宽度 | newHeight 需要裁剪的高度  | newFileName 裁剪后的全路径
     * @return boolean
     * @author:[石兴]
     * @update:[2011-11-1] [更改人姓名]
     */
	public static boolean cutImage(File file,int startX,int startY,int newWidth,int newHeight,String newFileName)throws FileNotFoundException{
		if(file==null || !file.isFile()){
			throw new FileNotFoundException();
		}
		boolean result = false;
		MagickImage magic = null;
		try {
			ImageInfo imageInfo = new ImageInfo(file.getAbsolutePath());
			MagickImage magickImage = new MagickImage(imageInfo);
			byte[] imageByte = magickImage.imageToBlob(imageInfo);
			magic = new MagickImage(new ImageInfo(),imageByte);
            int width = (int) magic.getDimension().getWidth();
            int height = (int) magic.getDimension().getHeight();
            if(newWidth > width || newHeight > height){
            	newWidth = width;
            	newHeight = height;
            }
            Rectangle rect = new Rectangle(startX, startY, newWidth, newHeight);
            MagickImage newImage = magic.cropImage(rect);
            newImage.profileImage("*", null); 
            newImage.setImageAttribute("JPEG-Sampling-factors", null); 
            newImage.setImageAttribute("comment", null); 
            newImage.setFileName(newFileName);
            newImage.writeImage(new ImageInfo());
            magic.destroyImages();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * Created on 2011-7-29
	 * <p>Discription:[获取图片实际类型,若返回空串,则说明不是图片]</p>
	 * @param file
	 * @return ImageType
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static final String getImageType(File file) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return EMPTY;
			}
			ImageReader reader = iter.next();
			iis.close();
			return reader.getFormatName();
		} catch (Exception e) {
			return EMPTY;
		}
	}

	/**
	 * Created on 2011-11-01
	 * <p>
	 * Discription:[获取图片实际文件名{扩展名根据获取图片实际类型生成},若返回NULL,则说明不是图片]
	 * </p>
	 * 
	 * @param file
	 * @param fileName
	 * @return 真实的文件名
	 * @author:[李友国]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static final String getImageFileName(File file, String fileName) {
		try {
			String imageType = getImageType(file).toLowerCase();
			if (EMPTY.equals(imageType))
				return null;
			return fileName.substring(0, fileName.lastIndexOf(".")) + "."
					+ imageType;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Created on 2011-7-29 
	 * <p>Discription:[判断图片类型是否是已定义类型]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
    private static boolean isImageSuffixValid(String suffix){
        if(isEmpty(suffix)){
            return false;
        }
        for (int i = 0; i < SUFFIX_IMAGE.length; i++){
            if(!SUFFIX_IMAGE[i].equalsIgnoreCase(suffix)){
                continue;
            }else {
                return true;
            }
        }
        return false;
    }
    
	/**
	 * Created on 2011-7-29 
	 * <p>Discription:[判断文件类型是否是已定义类型]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static boolean isFileSuffixValid(String suffix) {
        if(isEmpty(suffix)){
            return false;
        }
        for (int i = 0; i < SUFFIX_FILE.length; i++){
            if(!SUFFIX_FILE[i].equalsIgnoreCase(suffix)){
                continue;
            }else {
                return true;
            }
        }
        return false;
	}
    
    /**
     * Created on 2011-7-29 
     * <p>Discription:[判断图片类型是否有效,有效则过滤,无效返回false]</p>
     * @param file
     * @return true | false 有效  | 无效
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isImageValid(File file){
		String suffix = FileSecurityUtils.getImageType(file);
		if (!isImageSuffixValid(suffix)) {
			return false;
		}
		return filterImage(file,suffix);
    }
    
    /**
     * Created on 2011-7-29 
     * <p>Discription:[判断文件类型是否有效,有效则过滤,无效返回false]</p>
     * @param file
     * @return true | false 有效  | 无效
     * @author:[石兴]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static boolean isFileValid(File file){
    	String suffix = FileSecurityUtils.getFileTypeByFile(file);
    	if (!isFileSuffixValid(suffix)) {
			return false;
		}
    	return true;
    }
    
    /**
     * Created on 2011-11-7 
     * @desc <p>Discription:[去掉扩展名]</p>
     * @param 
     * @return String
     * @author:[石兴]
     * @update:[2011-11-7] [更改人姓名]
     */
    public static String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i >-1) && (i < (filename.length()))) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }
    
    public static void main(String[] args) {
		String src = "d:/img/20111108045643214.jpg";
		boolean flag = isImageValid(new File(src));
		System.out.println(flag);
	}
    
}
