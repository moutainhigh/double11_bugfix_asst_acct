/**
 * 
 */
package com.yuwang.pinju.web.module.shop.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.yuwang.pinju.common.FileSecurityUtils;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.storage.manager.FileStorageManager;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignModuleAction extends BaseWithUserAction implements
		ServletRequestAware {
	private File imageFile;
	private String imageFileFileName;
	private Long id;
	private String result;
	private Integer moduleId;
	private Long userPageId;
	HttpServletRequest request;
	private ShopBaseDesignerManager shopDesignBestSellerManager;
	private String myFileFileName;
	
	private String imgFileHidden;
	
	private String myFileSeq;
	
	private FileStorageManager fileStorageManager;
	
	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMyFileSeq() {
		return myFileSeq;
	}
	private ShopUserModuleAO shopUserModuleAO;
	
	private File[] myFile;
	
	public void setMyFileSeq(String myFileSeq) {
		this.myFileSeq = myFileSeq;
	}

	public String getImgFileHidden() {
		return imgFileHidden;
	}

	public void setImgFileHidden(String imgFileHidden) {
		this.imgFileHidden = imgFileHidden;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sbf = new StringBuffer();
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		for (Object keyObj : request.getParameterMap().keySet()) {
			String key = (String) keyObj;
			if (key != null && key.trim().length() > 0
					&& request.getParameter(key) != null
					&& !key.equals("imageFile")) {
				if ("userPageId".equals(key) || "moduleId".equals(key)
						|| "shopId".equals(key) || "id".equals(key) || "imgFileHidden".equals(key)
						|| "url".equals(key))
					continue;
				
				if("html".equals(key)){
					String html = request.getParameter("html").trim();
					if(html.length() == 0){
						html = " ";
					}
					shopUserModuleParamDO.setSave_html(html);
					continue;
				}
				
				if("imagePath".equals(key)){
					String imagePaths = request.getParameterValues("imagePath")[0];
					sbf.append(imagePaths.replaceAll(",", "\n")+"\n");
					continue;
				}
				sbf.append(key).append("=").append(
						StringUtil
								.array2String(request.getParameterValues(key)))
						.append("\n");
			}
		}

		
			if(myFile != null && myFile.length > 0){
				String picNames[] = saveFile(myFile);
				String numString = request.getParameter("num") == null ? "" : request.getParameter("num");
				int length = 0;
				String numArray[] = new String[]{};
				if(!numString.equals("")){
					numArray = numString.split(",");
				}
				length = picNames.length + numArray.length;
				int m = 0;
				for(int i=0;i<length;i++){
					if(numString.indexOf(""+i) != -1){
						continue;
					}else{
						sbf.append("myfile"+(i+1)).append("=").append(picNames[m]).append("\n");
						m = m + 1;
					}
				}
				
			}
			

		if(url!=null&&!url.trim().equals("")){
//			File newFile = new File("E:/work/apache-tomcat-6.0.32/webapps/pinju-web/uploadFile/"+newImageName+getExtention(imageFileFileName));
//			copy(imageFile,newFile);
//			String[] newImageName = fileStorageManager.saveImage(new File[]{imageFile}, new String[]{imageFileFileName}, getUserId(), getNickName());
//			sbf.append("imageName").append("=").append(imageFileFileName).append("\n");
//			if(url.indexOf(PinjuConstant.IMAGE_SERVER)>=0) 
//				url = url.substring(url.indexOf(PinjuConstant.IMAGE_SERVER)+PinjuConstant.IMAGE_SERVER.length());
			sbf.append("imageUrl").append("=").append(url).append("\n");
		}

		if (id != null)
			shopUserModuleParamDO.setId(id);
		shopUserModuleParamDO.setUserId(getUserId());
		shopUserModuleParamDO.setUserPageId(userPageId);
		shopUserModuleParamDO.setSave_config(sbf.toString());
		shopUserModuleParamDO.setModuleId(moduleId.longValue());

		Object retObj = shopUserModuleAO
				.saveModuleParam(shopUserModuleParamDO);
		if (retObj != null)
			result = "保存成功。";
		else
			result = "保存失败。";
		JSONObject json = new JSONObject();
		json.put("root", result);
		return SUCCESS;
	}
	
//	private static void copy(File src, File dst) {
//		try {
//			InputStream in = null;
//			OutputStream out = null;
//			try {
//				in = new BufferedInputStream(new FileInputStream(src),
//						BUFFER_SIZE);
//				out = new BufferedOutputStream(new FileOutputStream(dst),
//						BUFFER_SIZE);
//				byte[] buffer = new byte[BUFFER_SIZE];
//				while (in.read(buffer) > 0) {
//					out.write(buffer);
//				}
//			} finally {
//				if (null != in) {
//					in.close();
//				}
//				if (null != out) {
//					out.close();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}



	private String[] saveFile(File[] files) {
		// TODO Auto-generated method stub
		String fileNames[] = myFileFileName.split(",");
		String picNames[] = null;
		
		List<String> fileNameList = new ArrayList<String>();
		for(int i=0;i<fileNames.length;i++){
			String fileName = fileNames[i].toLowerCase();
			
			if (FileSecurityUtils.isImageValid(files[i])) {
				fileNameList.add(fileName);
			}
			
//			if(fileName.indexOf(".gif")!=-1 || fileName.indexOf(".jpg")!=-1 || fileName.indexOf(".jpeg")!=-1 || fileName.indexOf(".png")!=-1){
//				fileNameList.add(fileName);
//			}
		}
		fileNames = new String[fileNameList.size()];
		for(int i=0;i<fileNameList.size();i++){
			fileNames[i] = fileNameList.get(i);
		}
		
//		for (int i = 0; i < files.length; i++) {
			
		try {
			picNames = fileStorageManager.saveImage(files, fileNames, getUserId(), getNickName(),true);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.info("",e);
		}
			
			
//		}
		return picNames;

	
	}

	/*private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}*/

	public void setUserPageId(Long userPageId) {
		this.userPageId = userPageId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	

	

	public ShopBaseDesignerManager getShopDesignBestSellerManager() {
		return shopDesignBestSellerManager;
	}

	public void setShopDesignBestSellerManager(
			ShopBaseDesignerManager shopDesignBestSellerManager) {
		this.shopDesignBestSellerManager = shopDesignBestSellerManager;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public FileStorageManager getFileStorageManager() {
		return fileStorageManager;
	}

	public void setFileStorageManager(FileStorageManager fileStorageManager) {
		this.fileStorageManager = fileStorageManager;
	}

	public ShopUserModuleAO getShopUserModuleAO() {
		return shopUserModuleAO;
	}

	public void setShopUserModuleAO(ShopUserModuleAO shopUserModuleAO) {
		this.shopUserModuleAO = shopUserModuleAO;
	}

	public Long getUserPageId() {
		return userPageId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
}
