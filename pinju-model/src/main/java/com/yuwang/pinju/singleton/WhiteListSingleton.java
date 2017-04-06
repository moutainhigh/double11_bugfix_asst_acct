/**
 * 
 */
package com.yuwang.pinju.singleton;

import org.jsoup.safety.Whitelist;

/**
 * @Project: pinju-model
 * @Title: WhiteListSingeton.java
 * @Package com.yuwang.pinju.singeton
 * @Description: html白名单(可配置多个)
 * @author liuboen liuboen@zba.com
 * @date 2011-9-21 下午03:56:02
 * @version V1.0
 */

public class WhiteListSingleton {
	/**
	 * 商品详情-描述 白名单
	 */
	private Whitelist detailWhitelist;
	private static WhiteListSingleton instance;

	public static WhiteListSingleton getInstance() {
		if (instance == null) {
			instance = new WhiteListSingleton();
		}
		return instance;
	}

	private WhiteListSingleton() {
		detailWhitelist = setDetailWhitelist();
	}

	private Whitelist setDetailWhitelist() {
		Whitelist whitelist = Whitelist.basicWithImages();
		whitelist
				.addTags("a", "b", "blockquote", "br", "caption", "cite",
						"code", "col", "colgroup", "dd", "div", "dl", "dt",
						"em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img",
						"li", "ol", "p", "pre", "q", "small", "strike",
						"strong", "sub", "sup", "table", "tbody", "td",
						"tfoot", "th", "thead", "tr", "u", "ul", "&nbsp;",
						"embed", "span", "hr", "map", "area", "del", "s")
				.addAttributes("table", "summary", "width")
				.addAttributes("td", "abbr", "axis", "colspan", "rowspan",
						"width")
				.addAttributes("th", "abbr", "axis", "colspan", "rowspan",
						"scope", "width")
				.addAttributes("font", "color", "size", "face",
						".background-color", "style")
				.addAttributes("span", ".color", ".background-color",
						".font-size", ".font-family", ".background",
						".font-weight", ".font-style", ".text-decoration",
						".vertical-align", "style")
				.addAttributes("div", "align", ".border", ".margin",
						".padding", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".font-weight", ".background", ".font-style",
						".text-decoration", ".vertical-align", ".margin-left",
						"style")
				.addAttributes("table", "border", "cellspacing", "cellpadding",
						"width", "height", "align", "bordercolor", ".padding",
						".margin", ".border", "bgcolor", ".text-align",
						".color", ".background-color", ".font-size",
						".font-family", ".font-weight", ".font-style",
						".text-decoration", ".background", ".width", ".height",
						"style")
				.addAttributes("td", "align", "valign", "width", "height",
						"colspan", "rowspan", "bgcolor", ".text-align",
						".color", ".background-color", ".font-size",
						".font-family", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".background",
						"style")
				.addAttributes("th", "align", "valign", "width", "height",
						"colspan", "rowspan", "bgcolor", ".text-align",
						".color", ".background-color", ".font-size",
						".font-family", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".background",
						"style")
				.addAttributes("a", "href", "target", "name", "bid","style")
				.addAttributes("img", "src", "width", "height", "border",
						"alt", "title", "align", "usemap", ".width", ".height", "/", "style")
				.addAttributes("hr", "/")
				.addAttributes("br", "/")
				.addAttributes("p", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("ol", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("ul", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("li", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("p", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("blockquote", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h1", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h2", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h3", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h4", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h5", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("h6", "align", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".background", ".font-weight", ".font-style",
						".text-decoration", ".vertical-align", ".text-indent",
						".margin-left", "style")
				.addAttributes("embed", "src", "width", "height", "type",
						"loop", "autostart", "quality", ".width", ".height",
						"align", "allowscriptaccess")
				.addAttributes("map", "name")
				.addAttributes("area", "shape", "coords", "href", "target");
		return whitelist;
	}

	/**
	 * @return the detailWhitelist
	 */
	public Whitelist getDetailWhitelist() {
		return detailWhitelist;
	}

}
