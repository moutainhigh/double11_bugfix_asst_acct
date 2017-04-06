/**
 * 
 */
package com.yuwang.pinju.common;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.yuwang.pinju.Constant.WhiteUrlConstants;
import com.yuwang.pinju.singleton.WhiteListSingleton;

/**  
 * @Project: pinju-model
 * @Title: JsoupUtil.java
 * @Package com.yuwang.pinju.common
 * @Description: {@link Jsoup}提供一些常用的JSOUP 进行Html及JS过滤方法
 * @author liuboen liuboen@zba.com
 * @date 2011-9-20 上午10:51:01
 * @version V1.0  
	 <p/>
	 Start with one of the defaults:
	 <ul>
	 <li>{@link #cleanCustom}
	 <li>{@link #cleanFewHtml}
	 <li>{@link #cleanAllowImg}
	 <li>{@link #basicWithImages}
	 <li>{@link #cleanMostHtml}
	 <li>{@link #getDetailDescription}
	 </ul>
	 <p/>
 */

public class JsoupUtil {

	/**
	 * 通过个性化{@link Whitelist}进行过滤
	 * 
	 */
	public static String cleanCustom(String text,Whitelist whitelist){
		if (text==null) {
			return null;
		}
		text= Jsoup.clean(text, whitelist);
		return text;
	}
	/**
	 * 通过{@link Whitelist#relaxed()}过滤,保留大多数Html标签(会过滤掉链接)
	 * This whitelist allows a full range of text and structural body HTML: a, b, blockquote, br, 
	 * caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, 
	 * pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul
	 * 
	 */
	public static String cleanFewHtml(String text){
		if (text==null) {
			return null;
		}
		text= Jsoup.clean(text, Whitelist.relaxed());
		return text;
	}
	
	/**
	 * 	通过{@link Whitelist#basicWithImages()}过滤,只保留IMG标签(链接可替换)
	 *  This whitelist allows the same text tags as basic(), and also allows img tags,
	 *  with appropriate attributes, with src pointing to http or https
	 */
	public static String cleanAllowImg(String text){
		if (text==null) {
			return null;
		}

		text=Jsoup.clean(text, Whitelist.basicWithImages());
		return text;
	}
	
	
	
	/**
	 * 通过{@link Whitelist#basic()}过滤大多数HTML及JS
	 *  This whitelist allows a fuller range of text nodes: a, b, blockquote, br, cite, code, dd, dl, dt,
	 *   em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul, and appropriate attributes
	 */
	public static String cleanMostHtml(String text){
		if (text==null) {
			return null;
		}
		return Jsoup.clean(text, Whitelist.basic());
	}

	/**
	 * 去掉非pinju.com下链接
	 * @param text
	 * @return
	 */
	public  static String removeNotDetailDesWhite(String text){
		if (text==null) {
			return null;
		}
		 Document doc = Jsoup.parse(text);
		  Elements links = doc.select("a[href]");
		  for (Element link : links){
			  String url=link.attr("href");
			  if (url!=null) {
				  if (!isWhiteUrl(url,WhiteUrlConstants.DETAIL_DESCRIPTION_WHITE_URL)) {
					  link.removeAttr("href");
				  }else {
					  link.attr("target","_blank");
				  }
			  }
	       }
		  return doc.body().html();
	}
	
	/**
	 * 是否定义好的白名单
	 * @param url
	 * @param whiteUrl
	 * @return
	 */
	public static boolean isWhiteUrl(String url,String whiteUrl[]){
		URL thisHref;
		try {
			thisHref = new URL(url);
			String thisUrl=thisHref.getHost();
			for (String urlModel : whiteUrl) {
				/*int indexOf=thisUrl.indexOf(urlModel);
				if (indexOf!=-1&&indexOf<13) {
					return Boolean.TRUE;
				}*/
				if (thisUrl.endsWith(urlModel)) {
					return Boolean.TRUE;
				}
			}
		} catch (MalformedURLException e) {
			return Boolean.FALSE;
		}
		
		return Boolean.FALSE;
	}
	
	/**
	 * 商品详情过滤
	 * @param descrioption
	 * @return
	 */
	public static String getDetailDescription(String descrioption){
		if (descrioption==null) {
			return null;
		}
		Document doc = Jsoup.parse(descrioption);
		
	 	Whitelist whitelist=WhiteListSingleton.getInstance().getDetailWhitelist();
        Cleaner cleaner = new Cleaner(whitelist);
        Document clean = cleaner.clean(doc);
        Elements links = clean.select("a[href]");
		  for (Element link : links){
			  String url=link.attr("href");
			  if (url!=null) {
				  if (!isWhiteUrl(url,WhiteUrlConstants.DETAIL_DESCRIPTION_WHITE_URL)) {
					  link.removeAttr("href");
				  }else {
					  link.attr("target","_blank");
				  }
			  }
	       }
	    return clean.body().html().replaceAll("\n","");
	}
}
