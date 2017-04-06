/**
 * 
 */
package com.yuwang.pinju.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

/**  
 * @Project: pinju-model
 * @Description: 文件(包括图片)安全测试类
 * @author 石兴 shixing@zba.com
 * @date 2011-7-29 上午11:04:34
 * @update 2011-7-29 上午11:04:34
 * @version V1.0  
 */
public class FileSecurityUtilsTest {

	/**
	 * Test method for {@link com.yuwang.pinju.common.FileSecurityUtils#testConvertImageToJPG
	 */
	@Test
	public final void testConvertImageToJPG() {
		String source = "D:/img/a.bmp";
		File file = new File(source);
		/*boolean flag = FileSecurityUtils.convertImageToJPG(file);
		assertTrue(flag);*/
	}
	
	/**
	 * Test method for {@link com.yuwang.pinju.common.FileSecurityUtils#filterImage(java.io.File,int)}.
	 */
	@Test
	public final void testResizeImageByScale(){
		String source = "D:/img/Sunset.jpg";
		File file = new File(source);
		FileSecurityUtils.filterImage(file,"jpg");
	}
	
	/**
	 * Test method for {@link com.yuwang.pinju.common.FileSecurityUtils#isImageValid(java.io.File)}.
	 */
	@Test
	public final void testIsImageValid() {
		File file1 = new File("d:/img/111.jpg");
		boolean flag1 = FileSecurityUtils.isImageValid(file1);
		assertTrue(flag1);
		
		File file2 = new File("d:/img/222.gif");
		boolean flag2 = FileSecurityUtils.isImageValid(file2);
		assertFalse(flag2);
		
		File file3 = new File("d:/img/aaa.xls");
		boolean flag3 = FileSecurityUtils.isImageValid(file3);
		assertFalse(flag3);
	}

	/**
	 * Test method for {@link com.yuwang.pinju.common.FileSecurityUtils#isFileValid(java.io.File)}.
	 */
	@Test
	public final void testIsFileValid() {
		File file1 = new File("d:/img/aaa.xls");
		boolean flag1 = FileSecurityUtils.isFileValid(file1);
		assertTrue(flag1);
		
		File file2 = new File("d:/img/bbb.bat");
		boolean flag2 = FileSecurityUtils.isFileValid(file2);
		assertFalse(flag2);
		
		File file3 = new File("d:/img/ccc.docx");
		boolean flag3 = FileSecurityUtils.isFileValid(file3);
		assertTrue(flag3);
		
		File file4 = new File("d:/img/ddd.zip");
		boolean flag4 = FileSecurityUtils.isFileValid(file4);
		assertTrue(flag4);
		
		File file5 = new File("d:/img/eee.xlsx");
		boolean flag5 = FileSecurityUtils.isFileValid(file5);
		assertTrue(flag5);
	}

}
