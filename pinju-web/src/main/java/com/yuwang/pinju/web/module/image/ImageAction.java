package com.yuwang.pinju.web.module.image;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.constant.images.ImagesCategoryConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.image.manager.ImagesCategoryManager;
import com.yuwang.pinju.core.image.manager.StorageFileInfoManager;
import com.yuwang.pinju.core.storage.manager.FileInfoManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.images.ImagesCategoryDO;
import com.yuwang.pinju.domain.item.ItemInput;
import com.yuwang.pinju.domain.storage.FileInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;

public class ImageAction extends ActionSupport {

	String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png" };

	private File[] imgFile;

	private String[] imgFileContentType;

	private String[] imgFileFileName;

	private String d;

	private FileStorageManager fileStorageManager;
	private FileInfoManager fileInfoManager;
	private ImagesCategoryManager imagesCategoryManager;
	private StorageFileInfoManager storageFileInfoManager;
	
	/**
	 * 图片上传
	 * 
	 * @return
	 */
	public String uploadImage() {

		JSONObject obj = new JSONObject();

		PrintWriter out = null;

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			out = response.getWriter();
			String size = request.getParameter("size");
			String type = request.getParameter("type");
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			
			//判断图片空间大小
			ImagesCategoryDO icDo = imagesCategoryManager.getImagesCategoryObject(login.getMasterMemberId());
			long imageSize = ImagesCategoryConstant.MAXIMAGE_SIZE;
			if(icDo!=null){
				imageSize = ImagesCategoryConstant.MAXIMAGE_SIZE - icDo.getUserSize();
			}
			
			int fileSize = ItemInput.MAX_IMAGE_SIZE;
			if(size!=null&&!size.equals("")&&NumberUtil.isDouble(size)){
				fileSize = Integer.parseInt(size);
			}
			if (imgFile != null && imgFile.length > 0) {
				for (File f : imgFile) {
					// 大小检验
					if (f.length() / ItemInput.FILE_SIZE_K > fileSize) {
						out.print(getError(Message.getMessage(MessageName.FILE_SIZE_TO_LARGE)));
						return null;
					}
					if(type!=null&&!type.equals("")){
						imageSize = imageSize - f.length();
						if(imageSize<0){
							out.print(getError("图片空间不足！"));
							return null;
						}
					}
					// 类型检验
					if (!FileSecurityUtils.isImageValid(f)) {
						out.print(getError(Message.getMessage(MessageName.FILE_TYPE_INVALID)));
						return null;
					}
				}
			}
			String[] fileName;
			if(type!=null&&!type.equals("")){
				fileName = storageFileInfoManager.insertStorageFileInfo(imgFile, imgFileFileName, login.getMasterMemberId(), login.getMasterMemberName(), Integer.parseInt(type));
			}else{
				fileName = fileStorageManager.saveImage(imgFile, imgFileFileName, login.getMasterMemberId(), "", true);
				fileInfoManager.addFileInfos(login.getMasterMemberId(), fileName, imgFile);
			}

			if (fileName != null && fileName.length > 0) {
				obj.put("error", 0);
				obj.put("url", PinjuConstant.VIEW_IMAGE_SERVER + fileName[0]);
				out.print(obj.toString());
				return null;
			}

		} catch (Exception e) {
			if (out != null) {
				out.print(getError("上传文件失败。"));
				return null;
			}
			return null;
		}

		return null;
	}

	public String getImageList() {

		JSONObject obj = new JSONObject();

		PrintWriter out = null;

		try {

			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			out = response.getWriter();

			String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");

			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();

			List<FileInfoDO> ls = fileInfoManager.getFileInfoByMemberId(login.getMasterMemberId());
			List<Hashtable> fileList = new ArrayList<Hashtable>();
			for (FileInfoDO f : ls) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = f.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				hash.put("is_dir", false);
				hash.put("has_file", false);

				hash.put("filesize", f.getSize() == null ? "0" : f.getSize());
				hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
				hash.put("filetype", fileExt);
				hash.put("filename", f.getPath() + fileName);
				hash.put("filenameshow", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(f.getGmtModified()));
				fileList.add(hash);
			}

			if ("size".equals(order)) {
				Collections.sort(fileList, new SizeComparator());
			} else if ("type".equals(order)) {
				Collections.sort(fileList, new TypeComparator());
			} else {
				Collections.sort(fileList, new NameComparator());
			}

			obj.put("current_url", PinjuConstant.VIEW_IMAGE_SERVER);
			obj.put("total_count", fileList.size());
			obj.put("file_list", fileList);

			out.print(obj.toString());

		} catch (Exception e) {
			if (out != null) {
				out.print(getError(""));
				return null;
			}
			return null;
		}

		return null;
	}

	public static class NameComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filename")).compareTo((String) hashB.get("filename"));
			}
		}
	}

	public static class SizeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				if (((Long) hashA.get("filesize")) > ((Long) hashB.get("filesize"))) {
					return 1;
				} else if (((Long) hashA.get("filesize")) < ((Long) hashB.get("filesize"))) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	public static class TypeComparator implements Comparator {
		public int compare(Object a, Object b) {
			Hashtable hashA = (Hashtable) a;
			Hashtable hashB = (Hashtable) b;
			if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
				return -1;
			} else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
				return 1;
			} else {
				return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
			}
		}
	}

	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toString();
	}

	public File[] getImgFile() {
		return imgFile;
	}

	public void setImgFile(File[] imgFile) {
		this.imgFile = imgFile;
	}

	public String[] getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String[] imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String[] getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String[] imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}
	
	public void setFileInfoManager(FileInfoManager fileInfoManager) {
		this.fileInfoManager = fileInfoManager;
	}
	
	public void setStorageFileInfoManager(StorageFileInfoManager storageFileInfoManager) {
		this.storageFileInfoManager = storageFileInfoManager;
	}
	
	public void setImagesCategoryManager(ImagesCategoryManager imagesCategoryManager) {
		this.imagesCategoryManager = imagesCategoryManager;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

}
