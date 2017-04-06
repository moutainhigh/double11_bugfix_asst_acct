/**
 * 
 */
package com.yuwang.pinju.web.module.fileupload.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.storage.ao.FileUploadAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * @author yejingfeng
 * 
 */
public class FilesUploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6457015722103431992L;
	private static final int BUFFER_SIZE = 16 * 1024;
	private File[] imgFile;
	private String[] imgFileContentType;
	private String[] imgFileFileName;
	private FileUploadAO fileUploadAO;

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

	public FileUploadAO getFileUploadAO() {
		return fileUploadAO;
	}

	public void setFileUploadAO(FileUploadAO fileUploadAO) {
		this.fileUploadAO = fileUploadAO;
	}

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	@Override
	public String execute() {

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			return SUCCESS;
		}
		Long memberId = login.getMemberId();
		String nickName = login.getNickname();
		ResultSupport result = fileUploadAO.saveItemPics(imgFile,
				imgFileFileName, memberId, nickName);
		if (result.isSuccess()) {
			String[] retStr = (String[]) result.getModels().get("fileNames");
			ActionContext.getContext().put("retStr", retStr);
		} else {
			String errStr = result.getResultCode();
			ActionContext.getContext().put("errStr", errStr);
		}
		return SUCCESS;
	}

}
