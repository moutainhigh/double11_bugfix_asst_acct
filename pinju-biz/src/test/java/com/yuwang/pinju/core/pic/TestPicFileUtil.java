package com.yuwang.pinju.core.pic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author yejingfeng
 * 
 */
public class TestPicFileUtil {

	// public static String REST_URL =
	// "http://10.241.14.159:8080/storage-ws/rest/user/111/file/";
	public static String REST_URL = "http://127.0.0.1:8080/storage-ws/rest/user/111/file/";
	public static String IMAGE_REST_URL = "http://127.0.0.1:8080/storage-ws/rest/img";
	public static String NEED_THUM = "/thumb";

	// public static String REST_URL =
	// "http://10.241.14.159:8080/storage-ws/rest/fs/201106/20110615"";

	public static void main(String[] args) {
		try {
			// testREST();
			// testImageREST(false);
			testImageREST(true);
			// testHttpClient();
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testREST() throws RestClientException, IOException {
		RestTemplate rt = new RestTemplate();
		// create
		String fileName = rt.getForObject(REST_URL, String.class);
		System.out.println(fileName);
		String url = REST_URL + fileName + ".png";
		System.out.println(url);

		// post
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("file", new FileSystemResource("d:/temp/source/logo_cn.png"));
		// String postResult = rt.postForObject(url, parts, String.class);
		// System.out.println(postResult);
		// File file = new File("d:/temp/source/logo_cn.png");
		// byte[] b = new byte[] { 1, 2, 3, 5 };
		URI postForLocation = rt.postForLocation(url, parts);
		System.out.println("postForLocation:" + postForLocation);

		// get
		byte[] forObject = rt.getForObject(url, byte[].class);
		FileUtils.writeByteArrayToFile(new File("d:/temp/target/" + fileName
				+ ".png"), forObject);

		System.out.println(StringUtils.abbreviate(new String(forObject), 10));
		// delete
		// rt.delete(url);
	}

	public static void testImageREST(boolean needThum)
			throws RestClientException, IOException {
		RestTemplate rt = new RestTemplate();
		// post
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("file", new FileSystemResource(
				"d:/temp/source/0951d_infinitig37coupe2lg.jpg"));
		String url = IMAGE_REST_URL;
		if (needThum) {
			url = url + NEED_THUM;
		}
		String postResult = rt.postForObject(url, parts, String.class);
		System.out.println(postResult);
		System.out.println(IMAGE_REST_URL + postResult);
		// String[] split = StringUtils.split(postResult, "\r\n");
		// get
		byte[] forObject = rt.getForObject(IMAGE_REST_URL + postResult,
				byte[].class);
		FileUtils.writeByteArrayToFile(new File("d:/temp/target/"
				+ StringUtils.substringAfterLast(postResult, "/")), forObject);
		System.out.println(StringUtils.abbreviate(new String(forObject), 10));
		// delete
		rt.delete(IMAGE_REST_URL + postResult);
	}

	public static void createPreviewImage(String srcFile, String destFile) {
		final int IMAGE_SIZE = 140;
		try {
			File fi = new File(srcFile); // src
			File fo = new File(destFile); // dest
			BufferedImage bis = ImageIO.read(fi);

			int w = bis.getWidth();
			int h = bis.getHeight();
			double scale = (double) w / h;
			int nw = IMAGE_SIZE;
			int nh = (nw * h) / w;
			if (nh > IMAGE_SIZE) {
				nh = IMAGE_SIZE;
				nw = (nh * w) / h;
			}
			double sx = (double) nw / w;
			double sy = (double) nh / h;

			AffineTransform transform = new AffineTransform();
			transform.setToScale(sx, sy);
			AffineTransformOp ato = new AffineTransformOp(transform, null);
			BufferedImage bid = new BufferedImage(nw, nh,
					BufferedImage.TYPE_3BYTE_BGR);
			ato.filter(bis, bid);
			ImageIO.write(bid, " jpeg ", fo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					" Failed in create preview image. Error:  "
							+ e.getMessage());
		}
	}

}
