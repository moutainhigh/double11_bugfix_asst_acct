/*
 * ! myPagination Jquery Pagination Plug-in Library v4.0
 * 
 * 
 * 参数配置列表： 
 * 
 * 参数名		 数据类型 	描述信息 
 * currPage  	int  		当前页  
 * pageCount  	int  		总页数  
 * pageSize  	int  		页码数  
 * cssStyle  	string  	样式，给与插件单独指定样式  
 * info  		Object  	页码栏配置信息  
 * ajax  		Object  	ajax 请求配置信息  
 * 
 * info(Object) 参数列表 
 * 参数名	 数据类型		描述信息 
 * first 	 string   	    首页  
 * last      string  		尾页  
 * prev      string  		上一页  
 * next      string  		下一页  
 * link      string  		鼠标放在链接上显示的值，支持("#","javascript:void(0)")等  
 * msg  	 string         信息栏，内容需包含在Html标签中 ，如<span>:&nbsp;我跳到{curr}/{sum}页</span>中间两个{}函数必须存在！  
 * first_on  true,false 	首页是否显示，默认显示  
 * last_on   true,false  	尾页是否显示，默认显示  
 * prev_on   true,false  	上一页是否显示，默认显示  
 * next_on   true,false  	下一页是否显示，默认显示  
 * msg_on    true,false   	信息栏是否显示，默认显示  
 * text  	 Object  		文本框样式配置，可指定样式，如：text:{width:'200px',color:'#ff0'}  等  
 * 
 * cookie_currPage: true,	// 开启 Coookie保存页数模式
 * cookie_currPageKey: "Pagination"	// 保存 cookie 值为 Pagination_currPage
 * 
 * 在 对 myPagination 进行 配置时，可能会出现 错误代码 提示，这是对 参数 进行判断 
 * 错误代码 -1 代表 当前页不能小于1 
 * 错误代码 -2 代表 当前页不能大于总页数 
 * 错误代码 -3 代表 页码不能 小于 2 
 *
 * MSG的几种样式（总页数 - {sumPage}，当前页数 - {currPage}，输入框 - {currText}）
 * 
 * Enter跳转：<span>当前第{currPage}页/共{sumPage}页到第{currText}页&nbsp;</span>
 * Click跳转: <span>共{sumPage}页到第{currText}页&nbsp;<input type='button' style='height:20px;font-size:10px;' value='确定'/></span>
 * Alink跳转: <span>共{sumPage}页到第{currText}页&nbsp;<a href='#' id='jump'>确定</a></span>
 * 
 * Date: 2011/7/18 19:47
 */
(function($) {
	$.fn.myPagination = function(param) {
		init(param, $(this));
		return $(this)
	};
	function init(param, obj) {
		if (param && param instanceof Object) {
			var options;
			var currPage;
			var pageCount;
			var pageSize;
			var tempPage;
			var defaults = new Object({
						currPage : 1,
						pageCount : 10,
						pageSize : 5,
						cssStyle : 'badoo',
						ajax : {
							on : false,
							pageCountId : 'pageCount',
							param : {
								on : false,
								page : 1
							},
							ajaxStart : function() {
								return false;
							}
						},
						info : {
							first : '首页',
							last : '尾页',
							next : '下一页',
							prev : '上一页',
							first_on : true,
							last_on : true,
							next_on : true,
							prev_on : true,
							msg_on : true,
							link : '#',
							msg : '<span>&nbsp;&nbsp;跳{currText}/{sumPage}页</span>',
							text : {
								width : '22px'
							}
						},
						callback:function(){return false;}
					});
			function getCurrPage() {
				if (options.info && options.info.cookie_currPageKey
						&& options.info.cookie_currPage) {
					var cookie_currPage = $
							.cookie(options.info.cookie_currPageKey
									+ "_currPage");
					if (cookie_currPage != "" && cookie_currPage != null) {
						return cookie_currPage;
					}
				}
				if (options.currPage) {
					return options.currPage;
				} else {
					return defaults.currPage;
				}
			}
			function getPageCount() {
				if (options.pageCount) {
					return options.pageCount;
				} else {
					return defaults.pageCount;
				}
			}
			function getPageSize() {
				if (options.pageSize) {
					return options.pageSize;
				} else {
					return defaults.pageSize;
				}
			}
			function getCssStyle() {
				if (options.cssStyle) {
					return options.cssStyle;
				} else {
					return defaults.cssStyle;
				}
			}
			function getAjax() {
				if (options.ajax && options.ajax.on) {
					return options.ajax;
				} else {
					return defaults.ajax;
				}
			}
			function getParam() {
				if (options.ajax.param && options.ajax.param.on) {
					options.ajax.param.page = currPage;
					return options.ajax.param;
				} else {
					defaults.ajax.param.page = currPage;
					return defaults.ajax.param;
				}
			}
			function getFirst() {
				if (options.info && options.info.first_on == false) {
					return "";
				}
				if (options.info && options.info.first_on && options.info.first) {
					var str = "<a href='" + getLink() + "' title='1'>"
							+ options.info.first + "</a>";
					return str;
				} else {
					var str = "<a href='" + getLink() + "' title='1'>"
							+ defaults.info.first + "</a>";
					return str;
				}
			}
			function getLast(pageCount) {
				if (options.info && options.info.last_on == false) {
					return "";
				}
				if (options.info && options.info.last_on && options.info.last) {
					var str = "<a href='" + getLink() + "' title='" + pageCount
							+ "'>" + options.info.last + "</a>";
					return str;
				} else {
					var str = "<a href='" + getLink() + "' title='" + pageCount
							+ "'>" + defaults.info.last + "</a>";
					return str;
				}
			}
			function getPrev() {
				if (options.info && options.info.prev_on == false) {
					return "";
				}
				if (options.info && options.info.prev) {
					return options.info.prev;
				} else {
					return defaults.info.prev;
				}
			}
			function getNext() {
				if (options.info && options.info.next_on == false) {
					return "";
				}
				if (options.info && options.info.next) {
					return options.info.next;
				} else {
					return defaults.info.next;
				}
			}
			function getLink() {
				if (options.info && options.info.link) {
					return options.info.link;
				} else {
					return defaults.info.link;
				}
			}
			function getMsg() {
				var input = "<input type='text' value='" + currPage + "' >";
				if (options.info && options.info.msg_on == false) {
					return false;
				}
				if (options.info && options.info.msg) {
					var str = options.info.msg;
					str = str.replace("{currText}", input);
					str = str.replace("{currPage}", currPage);
					str = str.replace("{sumPage}", pageCount);
					return str;
				} else {
					var str = defaults.info.msg;
					str = str.replace("{currText}", input);
					str = str.replace("{currPage}", currPage);
					str = str.replace("{sumPage}", pageCount);
					return str;
				}
			}
			function getText() {
				var msg = getMsg();
				if (msg) {
					msg = $(msg);
				} else {
					return "";
				}
				var input = msg.children(":text");
				if (options.info && options.info.text) {
					var css = options.info.text;
					for (temp in css) {
						var val = eval("css." + temp);
						input.css(temp, val);
					}
					return msg.html();
				} else {
					var css = defaults.info.text;
					for (temp in css) {
						var val = eval("css." + temp);
						input.css(temp, val)
					}
					return msg.html();
				}
			}
			function getPageCountId() {
				if (options.ajax && options.ajax.pageCountId) {
					return options.ajax.pageCountId;
				} else {
					return defaults.ajax.pageCountId;
				}
			}
			function getAjaxStart() {
				if (options.ajax && options.ajax.ajaxStart) {
					options.ajax.ajaxStart();
				} else {
					defaults.ajax.ajaxStart;
				}
			}
			function saveCurrPage(page) {
				if (options.info && options.info.cookie_currPageKey
						&& options.info.cookie_currPage) {
					var key = options.info.cookie_currPageKey + "_currPage";
					$.cookie(key, page);
				}
			}
			function getInt(val) {
				return parseInt(val);
			}
			function isCode(val) {
				if (val < 1) {
					alert("输入值不能小于1");
					return false;
				}
				var patrn = /^[0-9]{1,8}$/;
				if (!patrn.exec(val)) {
					alert("请输入正确的数字");
					return false;
				}
				if (val > pageCount) {
					alert("输入值不能大于总页数");
					return false;
				}
				return true;
			}
			function updateView() {
				currPage = getInt(currPage);
				pageCount = getInt(pageCount);
				var link = getLink();
				var firstPage = lastPage = 1;
				if (currPage - tempPage > 0) {
					firstPage = currPage - tempPage;
				} else {
					firstPage = 1;
				}
				if (firstPage + pageSize > pageCount) {
					lastPage = pageCount + 1;
					firstPage = lastPage - pageSize;
				} else {
					lastPage = firstPage + pageSize;
				}
				var content = "";
				content += getFirst();
				if (currPage == 1) {
					//content += "<span class=\"disabled\" title=\"" + getPrev()
							//+ "\">" + getPrev() + " </span>";
				} else {
					content += "<a href='" + link + "' title='"
							+ (currPage - 1) + "'>" + getPrev() + " </a>";
				}
				if (firstPage <= 0) {
					firstPage = 1;
				}
				for (firstPage; firstPage < lastPage; firstPage++) {
					if (firstPage == currPage) {
						content += "<span class=\"current\" title=\""
								+ firstPage + "\">" + firstPage + "</span>";
					} else {
						content += "<a href='" + link + "' title='" + firstPage
								+ "'>" + firstPage + "</a>";
					}
				}
				if (currPage == pageCount) {
					//content += "<span class=\"disabled\" title=\"" + getNext()
							//+ "\">" + getNext() + " </span>";
				} else {
					content += "<a href='" + link + "' title='"
							+ (currPage + 1) + "'>" + getNext() + " </a>";
				}
				content += getLast(pageCount);
				content += getText();
				obj.html(content);
				obj.children(":text").keypress(function(event) {
					var keycode = event.which;
					if (keycode == 13) {
						var page = $(this).val();
						if (isCode(page)) {
							obj.children("a").unbind("click");
							obj.children("a").each(function() {
								$(this).click(function() {
									return false
								});
							});
							if(options.callback){
								options.callback(page);
								//createView(page);
							}else{
								createView(page);
							}
						}else{
							$(this).val('');
							$(this).focus();
						}
						return false;
					}
				});
				obj.children(":button").click(function(i) {
					var page = $(obj.children(":text")[0]).val();
					if (isCode(page)) {
						obj.children("a").unbind("click");
						obj.children("a").each(function() {
							$(this).click(function() {
								return false
							});
						});
						if(options.callback){
							options.callback(page);
							//createView(page);
						}else{
							createView(page);
						}
					}else{
						$(obj.children(":text")[0]).val('');
						$(obj.children(":text")[0]).focus();
					}
					return false;
				});
				obj.children("a").each(function(i) {
						if(this.id && this.id == 'jump'){
							$(this).click(function() {
								var page = $(obj.children(":text")[0]).val();
								if (isCode(page)) {
									obj.children("a").unbind("click");
									obj.children("a").each(function() {
										$(this).click(function() {
											return false;
										});
									});
									if(options.callback){
										options.callback(page);
										//createView(page);
									}else{
										createView(page);
									}
									$(this).focus();
								}else{
									$(obj.children(":text")[0]).val('');
									$(obj.children(":text")[0]).focus();
								}
								return false;
							});
						}else{
							var page = this.title;
							$(this).click(function() {
									obj.children("a").unbind("click");
									obj.children("a").each(function() {
										$(this).click(function() {
											return false;
										});
									});
									if(options.callback){
										options.callback(page);
										//createView(page);
									}else{
										createView(page);
									}
									$(this).focus();
									return false;
							});
						}
				});
			};
			function createView(page) {
				currPage = page;
				saveCurrPage(page);
				var ajax = getAjax();
				if (ajax.on) {
					getAjaxStart();
					var varUrl = ajax.url;
					var param = getParam();
					$.ajax({
						url : varUrl,
						type : 'GET',
						data : param,
						contentType : "application/x-www-form-urlencoded;utf-8",
						async : true,
						cache : false,
						timeout : 60000,
						error : function() {
							alert("访问服务器超时，请重试，谢谢！");
						},
						success : function(data) {
							loadPageCount({
										dataType : ajax.dataType,
										callback : ajax.callback,
										data : data
									});
							updateView();
						}
					})
				} else {
					updateView();
				}
			}
			function checkParam() {
				if (currPage < 1) {
					//alert("配置参数错误\n错误代码:-1");
					currPage = 1;
					return false;
				}
				if (parseInt(currPage, 10) > parseInt(pageCount, 10)) {
					currPage = pageCount;
					//alert("配置参数错误\n错误代码:-2");
					return false;
				}
				if (pageSize < 2) {
					pageSize = 2;
					//alert("配置参数错误\n错误代码:-3");
					return false;
				}
				return true;
			}
			function loadPageCount(options) {
				if (options.dataType) {
					var data = options.data;
					var resultPageCount = false;
					var isB = true;
					var pageCountId = getPageCountId();
					switch (options.dataType) {
						case "json" :
							data = eval("(" + data + ")");
							resultPageCount = eval("data." + pageCountId);
							break;
						case "xml" :
							resultPageCount = $(data).find(pageCountId).text();
							break;
						default :
							isB = false;
							var callback = options.callback + "(data)";
							eval(callback);
							resultPageCount = $("#" + pageCountId).val();
							break;
					}
					if (resultPageCount) {
						pageCount = resultPageCount;
					}
					if (isB) {
						var callback = options.callback + "(data)";
						eval(callback);
					}
				}
			}
			options = param;
			currPage = getCurrPage();
			pageCount = getPageCount();
			pageSize = getPageSize();
			tempPage = getInt(pageSize / 2);
			var cssStyle = getCssStyle();
			obj.addClass(cssStyle);
			if (checkParam()) {
				updateView();
				createView(currPage);
			}
		}
	}
})(jQuery);