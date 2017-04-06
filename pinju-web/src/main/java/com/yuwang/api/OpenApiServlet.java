/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.util.FileCopyUtils;
import org.unitils.thirdparty.org.apache.commons.io.IOUtils;

import com.yuwang.api.common.Constants;
import com.yuwang.api.common.ParserAlias;
import com.yuwang.api.common.ResponseConfig;
import com.yuwang.api.core.ao.OpenApiApplicationAO;
import com.yuwang.api.core.ao.OpenApiMethodAO;
import com.yuwang.api.core.ao.OpenApiSessionAO;
import com.yuwang.api.domain.OpenApiApplicationDO;
import com.yuwang.api.domain.OpenApiMethodDO;
import com.yuwang.api.domain.OpenApiSessionDO;

import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.core.util.StringUtil;


/**
 * 开放API的请求入口
 * 
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public class OpenApiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2300436147002199619L;
	
	private final Log logger = LogFactory.getLog("open-api");
	private final Log log = LogFactory.getLog(this.getClass().getName());

	/**
	 * 分布式缓存
	 */
	private static MemcachedClient shopMemcachedClient;

	/**
	 * 开放API方法接口
	 */
	private static OpenApiMethodAO openApiMethodAO;

	/**
	 * 用户sessionKey接口
	 */
	private static OpenApiSessionAO openApiSessionAO;

	/**
	 * 第三方应用接口
	 */
	private static OpenApiApplicationAO openApiApplicationAO;

	/**
	 * 当前开放的接口类
	 */
	private static Map<String, Object> interfaceMap;

	/**
	 * 图片上传辅助类
	 */
	private static DiskFileItemFactory fileItemFactory = null;

	/**
	 * 签名生成接口
	 */
	private static MemberSecurityManager memberSecurityManager;

	private static Map<String, ResponseConfig> aliasMap;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		shopMemcachedClient = (MemcachedClient) SpringBeanFactory
				.getBean("shopMemcachedClient");
		openApiMethodAO = (OpenApiMethodAO) SpringBeanFactory
				.getBean("openApiMethodAO");
		openApiSessionAO = (OpenApiSessionAO) SpringBeanFactory
				.getBean("openApiSessionAO");
		openApiApplicationAO = (OpenApiApplicationAO) SpringBeanFactory
				.getBean("openApiApplicationAO");
		memberSecurityManager = (MemberSecurityManager) SpringBeanFactory
				.getBean("memberSecurityManager");
		interfaceMap = new HashMap<String, Object>();

		/**
		 * 用commonUpload处理上传图片，支持同时上传多张图片
		 */
		File tempPath = new File(config.getServletContext().getContextPath()
				+ Constants.OPEN_API_UPLOAD_FILE_TEMP_PATH);// 上传图片的临时路径
		if (!tempPath.exists())
			tempPath.mkdir();
		fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setRepository(tempPath);

		/**
		 * 创建API方法别名配置
		 */
		createAliasMap(config.getInitParameter("apiAliasConfig"));
	}

	/**
	 * 创建API方法别名配置
	 * 
	 * @param configFilePath
	 *            --配置XML
	 */
	@SuppressWarnings("unchecked")
	private void createAliasMap(String configFilePath) {
		aliasMap = new HashMap<String, ResponseConfig>();
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(OpenApiServlet.class.getClassLoader()
					.getResourceAsStream(configFilePath));
			List<Element> allMethod = doc.getRootElement().getChildren();
			for (Iterator iterator = allMethod.iterator(); iterator.hasNext();) {
				Element method = (Element) iterator.next();
				String image = method.getAttributeValue("image");
				ResponseConfig config = new ResponseConfig();
				config.setHasImageServer((!StringUtil.isEmpty(image) && "true"
						.equals(image.trim().toLowerCase())));
				List<ParserAlias> paList = new ArrayList<ParserAlias>();
				// 解析class
				List<Element> classes = method.getChildren();
				for (Element classDO : classes) {
					ParserAlias pa = new ParserAlias(classDO
							.getAttributeValue("alias"), Class.forName(classDO
							.getAttributeValue("name")));
					// 解析aliasFields
					Element aliasFields = classDO.getChild("aliasFields");
					if (null != aliasFields) {
						for (Iterator iteratorAF = aliasFields.getChildren(
								"field").iterator(); iteratorAF.hasNext();) {
							Element aliasField = (Element) iteratorAF.next();
							pa.setAliasField(aliasField
									.getAttributeValue("alias"), aliasField
									.getAttributeValue("name"));
						}
					}
					// 解析omitFields
					Element omitFields = classDO.getChild("omitFields");
					if (null != omitFields) {
						pa.setOmitFields(omitFields.getTextTrim());
					}
					// 解析collectionFields
					Element collectionFields = classDO.getChild("collectionFields");
					if (null != collectionFields) {
						pa.setCollectionFields(collectionFields.getTextTrim());
					}
					paList.add(pa);
				}
				config.setListAlias(paList);
				aliasMap.put(method.getAttributeValue("name"), config);
			}
		} catch (JDOMException e) {
			log.warn(e);
			// ignore
		} catch (IOException e) {
			log.warn(e);
			// ignore
		} catch (ClassNotFoundException e) {
			log.warn(e);
			// ignore
		}
	}
	
	/**
	 * 开放API用户请求入口
	 * 
	 * @param req
	 *            --请求
	 * @param resp
	 *            --响应
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding(Constants.OPEN_API_REQUEST_ENCODING);

		ApiRequest request = new ApiRequest();
		ApiResponse response = new ApiResponse();
		try {
			request.setClientIp(getClientIp(req));//获取客户端IP地址
			if (!checkSetParameter(req, request, response))// 校验并设定系统参数
				throw new Exception(response.getMsg());
			try {
				Object o = getInterfaceObject(request.getCurrentMethod());// 获取接口类
				Object[] params = { request, response };

				Class[] paramTypes = { ApiRequest.class, ApiResponse.class };
				long startTime = System.currentTimeMillis();
				int returnCode = (Integer) MethodUtils.invokeMethod(o, request
						.getCurrentMethod().getInterMethod(), params,
						paramTypes);// 调用接口
				logger.debug("##############invoke method '" + request.getApiMethodName() + "' time:" + (System.currentTimeMillis() - startTime) + "ms.");
				/**
				 * 返回失败
				 */
				response.setErrorCode(returnCode);
				if (returnCode != Constants.SUBMIT_APIMETHOD_INNER_SUCCESS
						.intValue()) {
					log.warn(response.getMsg());
					logger.warn("User Request:"
							+ request.toString()
							+ ";\nInvoke Result:"
							+ response.toClone("1".equals(request.getCurrentMethod()
											.getConfig("ISQUERY"))).toString());
				}else{
					/**
					 * 设置别名解析
					 */
					if (aliasMap != null
							&& aliasMap.containsKey(request.getApiMethodName())) {
						ResponseConfig config = aliasMap.get(request
								.getApiMethodName());
						if (config.isHasImageServer())
							response.setImageServer(PinjuConstant.IMAGE_SERVER);
						response.setAliasClazz(config.getListAlias());
					}
					response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_SUCCESS_MSG);
				}
				logger.info("User Request:" + request.toString()
						+ ";\nInvoke Result:" + response.toClone(
								"1".equals(request.getCurrentMethod()
										.getConfig("ISQUERY"))).toString());
			} catch (Exception e) {
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
				log.error(e);
				logger.error("User Request:"
						+ request.toString()
						+ ";\nInvoke Result:"
						+ response.toClone("1".equals(request.getCurrentMethod()
										.getConfig("ISQUERY"))).toString());
			}
		} catch (Exception e) {
			// ignore
			log.warn(e.getMessage());
			logger.warn("User Request:" + request.toString()
					+ ";\nInvoke Result:" + response.toClone(false).toString());
		} finally {
			request.clearTempFile();
		}
		resp.setContentType("application/"
				+ (Constants.OPEN_API_RESPONSE_PARSER_JSON.equals(request
						.getFormat()) ? "json" : "xml") + "; charset="
				+ Constants.OPEN_API_REQUEST_ENCODING);
		resp.getWriter().print(response.toString());
	}

	/**
	 * 获取系统开放的API方法，只从数据库中取出一次，放入到分布式缓存(保证缓存的调用失败不影响业务流程)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, OpenApiMethodDO> getMethodMap() {
		Map<String, OpenApiMethodDO> map = null;
		try {
			map = (Map<String, OpenApiMethodDO>) shopMemcachedClient
					.get(Constants.YUWANG_OPENAPI_MEMCACHE_METHOD_KEY);
		} catch (Exception e) {
			// ignore
			log.warn(e);
		}
		if (map == null || map.size() == 0) {
			map = openApiMethodAO.getAllList();
			try {
				shopMemcachedClient.set(
						Constants.YUWANG_OPENAPI_MEMCACHE_METHOD_KEY, 60 * 30,
						map);// 过期时间30分钟
			} catch (Exception e) {
				// ignore
				log.warn(e);
			}
		}
		return map;
	}

	/**
	 * 获取所以第三方应用开发信息，只从数据库中取出一次，放入到分布式缓存(保证缓存的调用失败不影响业务流程)
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<Long, OpenApiApplicationDO> getAppMap() {
		Map<Long, OpenApiApplicationDO> map = null;
		try {
			map = (Map<Long, OpenApiApplicationDO>) shopMemcachedClient
					.get(Constants.YUWANG_OPENAPI_MEMCACHE_APP_KEY);
		} catch (Exception e) {
			// ignore
			log.warn(e);
		}
		if (map == null) {
			map = openApiApplicationAO.getAllOpenApiApplication();
			try {
				shopMemcachedClient
						.set(Constants.YUWANG_OPENAPI_MEMCACHE_APP_KEY,
								60 * 10, map);// 过期时间10分钟
			} catch (Exception e) {
				// ignore
				log.warn(e);
			}
		}
		return map;
	}

	/**
	 * 获取调用接口类（此类也是单例对象，生成后将此对象置入到分布式缓存中存贮）
	 * 
	 * @param apiMethod
	 *            --当前请求调用的方法
	 * @return 返回此接口实现类实例
	 * @throws Exception
	 */
	private Object getInterfaceObject(OpenApiMethodDO apiMethod)
			throws Exception {
		if (interfaceMap.containsKey(apiMethod.getInterfaceClass()))
			return interfaceMap.get(apiMethod.getInterfaceClass());
		Object o = Class.forName(apiMethod.getInterfaceClass()).newInstance();
		interfaceMap.put(apiMethod.getInterfaceClass(), o);
		return o;
	}

	/**
	 * 获取当前请求enctype
	 * 
	 * @param req
	 *            --前台请求
	 * @return 是否通过multipart/form-data形式发送请求
	 */
	private boolean hasUpload(HttpServletRequest req) {
		String contentType = req.getContentType();
		if (contentType == null)
			return false;
		if (contentType.toLowerCase().startsWith("multipart/"))
			return true;
		return false;
	}

	/**
	 * 校验系统参数，并设置请求对象
	 * 
	 * @param req
	 *            --请求
	 * @param request
	 *            --请求对象
	 * @param response
	 *            --响应
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean checkSetParameter(HttpServletRequest req,
			ApiRequest request, ApiResponse response) {

		if (!"POST".equalsIgnoreCase(req.getMethod())) {// 只接收POST方法
			response.setErrorCode(Constants.SUMBIT_METHOD_FIDDEN);
			response.setMsg(Constants.SUMBIT_METHOD_FIDDEN_MSG);
			return false;
		}

		boolean hasUpload = hasUpload(req);
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			List<FileItem> list = new ArrayList<FileItem>();
			if (hasUpload) {
				/**
				 * 用commonUpload处理上传图片，支持同时上传多张图片
				 */
				ServletFileUpload upload = new ServletFileUpload(
						fileItemFactory);
				upload.setSizeMax(Constants.OPEN_API_UPLOAD_FILE_COUNT_MAX_SIZE);// 图片上传时请求最大大小5M
				list = upload.parseRequest(req);
				for (FileItem item : list) {
					if (item.isFormField()) {
						if ("format".equals(item.getFieldName())) {
							request.setFormat(item.getString(Constants.OPEN_API_REQUEST_ENCODING));
							continue;
						}
						if ("sign".equals(item.getFieldName())) {
							request.setSign(item.getString(Constants.OPEN_API_REQUEST_ENCODING));
							continue;
						}
						if ("appId".equals(item.getFieldName())) {
							request.setApiId(item.getString(Constants.OPEN_API_REQUEST_ENCODING));
							continue;
						}
						if ("method".equals(item.getFieldName())) {
							request.setApiMethodName(item.getString(Constants.OPEN_API_REQUEST_ENCODING));
							continue;
						}
						if ("sessionKey".equals(item.getFieldName())) {//
							request.setSessionKey(item.getString(Constants.OPEN_API_REQUEST_ENCODING));
							continue;
						} else
							// 应用级参数
							paramMap.put(item.getFieldName(), item.getString(Constants.OPEN_API_REQUEST_ENCODING));
					}
				}
			} else {
				Map map = req.getParameterMap();
				for (Object o : map.keySet()) {
					String key = (String) o;
					if ("appId".equals(key)) {
						request.setApiId(req.getParameter(key));
						continue;
					}
					if ("sign".equals(key)) {
						request.setSign(req.getParameter(key));
						continue;
					}
					if ("method".equals(key)) {
						request.setApiMethodName(req.getParameter(key));
						continue;
					}
					if ("sessionKey".equals(key)) {
						request.setSessionKey(req.getParameter(key));
						continue;
					}
					if ("format".equals(key)) {
						request.setFormat(req.getParameter(key));
						continue;
					}
					paramMap.put(key, req.getParameter(key));// 系统级参数
				}
			}
			request.setTextParams(paramMap);// 设置应用级参数
			response.setParser(request.getFormat());// 设置返回结果的格式

			// 校验开放的API方法
			long startTime = System.currentTimeMillis();
			if (EmptyUtil.isBlank(request.getApiMethodName())) {
				response.setErrorCode(Constants.SUMBIT_APIMETHOD_INVALID);
				response.setMsg(Constants.SUMBIT_APIMETHOD_INVALID_MSG);
				return false;
			}
			Map<String, OpenApiMethodDO> methodMap = getMethodMap();// 获取系统开放的所有API方法
			logger.debug("##############get api method time:" + (System.currentTimeMillis() - startTime) + "ms.");
			if (methodMap == null
					|| !methodMap.containsKey(request.getApiMethodName())) {// 未实现的开放API
				response.setErrorCode(Constants.SUMBIT_APIMETHOD_INVALID);
				response.setMsg(Constants.SUMBIT_APIMETHOD_INVALID_MSG);
				return false;
			}
			OpenApiMethodDO currentMethod = methodMap.get(request
					.getApiMethodName());
			request.setCurrentMethod(currentMethod);// 设置当前的方法

			// APPID 是否为空 是否存在
			startTime = System.currentTimeMillis();
			OpenApiApplicationDO currentApp = null;
			try {
				currentApp = getAppMap()
						.get(Long.parseLong(request.getApiId()));
			} catch (NumberFormatException e) {
			} catch (NullPointerException e) {
			}
			if (null == currentApp) {
				response.setErrorCode(Constants.SUMBIT_APIAPPID_ISNULL_INVALID);
				response.setMsg(Constants.SUMBIT_APIAPPID_ISNULL_INVALID_MSG);
				return false;
			}
			logger.debug("##############get api application time:" + (System.currentTimeMillis() - startTime) + "ms.");
			request.setAppSecret(currentApp.getSecret());
			int expireTime = Integer.parseInt(currentApp.getConfig("expireTime") == null ? "30" : currentApp
					.getConfig("expireTime"));
			// 校验sessionKey是否合法
			startTime = System.currentTimeMillis();
			if (0 == currentMethod.getNeedSession()) {
				if (!checkSessionAuthorized(request, response, expireTime)) {
					response.setErrorCode(Constants.SUMBIT_APISESSIONKEY_INVALID);
					response.setMsg(Constants.SUMBIT_APISESSIONKEY_INVALID_MSG);
					return false;
				}
			}
			if(2 == currentMethod.getNeedSession())
				checkSessionAuthorized(request, response, expireTime);
			logger.debug("##############check session time:" + (System.currentTimeMillis() - startTime) + "ms.");

			// 校验数字签名
			startTime = System.currentTimeMillis();
			String sign = request.getSignData();
			String hex = memberSecurityManager.digestMessageHex(sign, null);
			if (EmptyUtil.isBlank(hex)) {
				log.warn("[OPEN-API] isOpenApi, calc sign is empty, sign data: "
								+ sign);
				response.setErrorCode(Constants.SUBMIT_APIMETHOD_INNER_ERROR);
				response.setMsg(Constants.SUBMIT_APIMETHOD_INNER_ERROR_MSG);
				return false;
			}
			if (!hex.equals(request.getSign())) {
				log.warn("[OPEN-API] isOpenApi, calc sign is: " + hex
						+ ", user app sign: " + request.getSign());
				response.setErrorCode(Constants.SUBMIT_SYSTEMDATA_SIGN_INVALID);
				response.setMsg(Constants.SUBMIT_SYSTEMDATA_SIGN_INVALID_MSG);
				return false;
			}
			logger.debug("##############check sign time:" + (System.currentTimeMillis() - startTime) + "ms.");
			if (hasUpload)
				return setUploadMap(list, request, response);// 设置上传的文件
			return true;
		} catch (SizeLimitExceededException e) {
			response.setErrorCode(Constants.SUBMIT_UPLOAD_COUNT_SIZE_LIMIT_EXCEEDED);
			response.setMsg(Constants.SUBMIT_UPLOAD_COUNT_SIZE_LIMIT_EXCEEDED_MSG);
			log.error(e);
			return false;
		} catch (FileUploadException e) {
			response.setErrorCode(Constants.SUBMIT_UPLOAD_PARAM_INVALID);
			response.setMsg(Constants.SUBMIT_UPLOAD_PARAM_INVALID_MSG);
			log.error(e);
			return false;
		} catch (IOException e) {
			response.setErrorCode(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR);
			response.setMsg(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR_MSG);
			log.error(e);
			return false;
		}
	}

	/**
	 * 校验sessionKey是否合法， 缓存5分钟，即每5分钟更新一次数据库
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean checkSessionAuthorized(ApiRequest request, ApiResponse response, int expireTime) {
		OpenApiSessionDO session = null;
		if (StringUtil.isEmpty(request.getSessionKey())) {
			response.setErrorCode(Constants.SUMBIT_APISESSIONKEY_INVALID);
			response.setMsg(Constants.SUMBIT_APISESSIONKEY_INVALID_MSG);
			return false;
		}
		try {
			session = (OpenApiSessionDO) shopMemcachedClient
					.get(Constants.PJ_OPEN_API_SESSION + request.getApiId()
							+ request.getSessionKey());
		} catch (Exception e) {
			log.warn(e);
		}
		if (session == null) {
			session = openApiSessionAO.checkSessionAuthorized(request.getApiId(),
					request.getSessionKey(),expireTime);
			if (null == session)
				return false;
			try {
				shopMemcachedClient.set(Constants.PJ_OPEN_API_SESSION
						+ request.getApiId() + request.getSessionKey(), 5 * 60,
						session);// 缓存5分钟，即每5分钟更新一次数据库
			} catch (Exception e) {
				log.warn(e);
			}
		}
		request.setMemberId(session.getSellerId());
		request.setNickName(session.getNickName());
		return true;
	}

	/**
	 * 处理上传的图片，放入到请求对象MAP中
	 * 
	 * @param list
	 *            --请求参数列表
	 * @param request
	 *            --应用请求对象
	 * @param response
	 *            --响应
	 * @return 设置是否成功
	 * @throws SizeLimitExceededException
	 * @throws FileUploadException
	 * @throws IOException
	 */
	private boolean setUploadMap(List<FileItem> list, ApiRequest request,
			ApiResponse response) {
		long startTime = System.currentTimeMillis();
		try {
			Map<String, File> fileMap = new LinkedHashMap<String, File>();
			request.setUploadParams(fileMap);// 设置上传的文件
			int count = 0;
			for (FileItem item : list) {
				if (!item.isFormField() && !StringUtil.isEmpty(item.getName())) {// 生成文件参数
					count++;
					String value = item.getName();
					int start = value.lastIndexOf("\\");
					String fileName = value.substring(start + 1);
					try {
						long size = item.getSize();
						if (size > Constants.OPEN_API_UPLOAD_FILE_MAX_SIZE) {
							response
									.setErrorCode(Constants.SUBMIT_UPLOAD_SIZE_LIMIT_EXCEEDED);
							response.setMsg("上传图片必须<="
									+ Constants.OPEN_API_UPLOAD_FILE_MAX_SIZE
									/ 1024 + "KB.");
							return false;
						}
						File dest = new File(
								Constants.OPEN_API_UPLOAD_FILE_TEMP_PATH + "/"
										+ createUploadFileName(count, fileName));
						InputStream is = null;
						OutputStream os = null;
						try {
							is = item.getInputStream();
							os = new FileOutputStream(dest);
							FileCopyUtils.copy(is, os);
						} catch (Exception e) {
							log.error(e);
							// ignore;
						} finally {
							IOUtils.closeQuietly(os);
							IOUtils.closeQuietly(is);
						}
						fileMap.put(fileName, dest);
					} catch (Exception e) {
						response
								.setErrorCode(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR);
						response
								.setMsg(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR_MSG);
						log.error(e);
						return false;
					}
				}
			}
			if (0 == count) {
				response
						.setErrorCode(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR);
				response
						.setMsg(Constants.SUBMIT_UPLOAD_FILE_WRITEDATA_ERROR_MSG);
				return false;
			}
			return true;
		} finally {
			logger.debug("##############get upload file time:"
					+ (System.currentTimeMillis() - startTime) + "ms.");
		}
	}

	private String createUploadFileName(int num, String fileName) {
		StringBuilder sbd = new StringBuilder();
		sbd.append(num).append(System.currentTimeMillis()).append("_").append(
				fileName);
		return sbd.toString();
	}
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	private String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
