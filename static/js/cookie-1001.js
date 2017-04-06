/**
 * 品聚 Cookie 操作工具
 *
 * @auth gaobaowen
 * @since 2011.06.07
 * @returns
 */

if (!window.console) {
	window.console = {};
}
if (!console.log) {
	console.log = function(){};
}
if (!console.info) {
	console.info = function(){};
}
 
var PinjuCookie = function(base, logoutLink, debug) {
  this.base = base;
  this.logoutLink = logoutLink ? '?returnUrl=' + encodeURIComponent(logoutLink) : '';
  this.debug = !!debug;
  this.cookieMap = this._getCookies();
}

/**
 * 调试模式
 */
PinjuCookie.DEBUG = true;

/**
 * 会话 TOKEN 的 Cookie 名字
 */
PinjuCookie.TOKEN = 'TOKEN';

/**
 *
 */
PinjuCookie.LOGS = ['N'];

/**
 * SDO 登录检查的名字
 */
PinjuCookie.SDO_CHECKER = "SDO";

/**
 * 购物车数量
 */
PinjuCookie.SD = "SD";

/**
 * 购物车内容
 */
PinjuCookie.SC = "SC";

/**
 * SDO 登录检查时效
 */
PinjuCookie.SDO_CHECKER_INTERVAL = 5 * 60 * 1000;

/**
 * 推广商信息
 */
PinjuCookie.P = "P";

/**
 * 推广商信息写入时间
PinjuCookie.PT = "PT"; */

/**
 * 推广商信息检查时效
PinjuCookie.P_CHECKER_INTERVAL = 15 * 24 * 60 * 60 * 1000;*/

/**
 * 未登录时顶部导航 HTML 模版
 */
PinjuCookie.UNLOGIN = '<span><a href="{0}/member/login.htm{1}">请登录</a></span> <span><a href="{0}/member/register.htm{1}">免费注册</a></span>';

PinjuCookie.LOGIN_NO_NICKNAME = '<span>您好，欢迎来到品聚，请点击<a href="{0}/my/info.htm" title="填写姓名、昵称，以及联系方式等">这里</a>完善个人信息</span> <span><a href="{0}/member/logout.htm">退出</a></span>';

PinjuCookie.LOGIN = '<span>您好，<a href="{0}/my/info.htm">{1}</a> 欢迎来到品聚</span> <span><a href="{0}/member/logout.htm{2}">退出</a></span>';

PinjuCookie.SDO = '<iframe src="{0}/sdo.html" width="0" height="0"></iframe>';

/**
 * Cookie 写入模版
 */
PinjuCookie.COOKIE_TEMPLATE = '{0}={1}; domain={2}; path={3}; expires={4}';

/**
 * Cookie 写入
 * @param name cookie 名称
 * @param value cookie 值
 * @param domain cookie 写入域（默认为 PJ.COOKIE_DOMAIN ）
 * @param path cookie 写入路径（默认为 /）
 * @param expires cookie 时效（分钟）
 */
PinjuCookie.writeCookie = function(name, value, domain, path, expires) {
  var date = new Date();
  date.setTime(date.getTime() + expires * 60000);
  document.cookie = pinju.Person.format( PinjuCookie.COOKIE_TEMPLATE, name, encodeURIComponent(value), (domain || PJ.COOKIE_DOMAIN), (path || '/'), date.toGMTString() );
};

/**
 * 写入 sdo 检查 cookie，值为当前 UNIX 纪元毫秒
 *
 * @param expires 时效（分钟）
 */
PinjuCookie.writeSdoCookie = function( expires ) {
  PinjuCookie.writeCookie( PinjuCookie.SDO_CHECKER, new Date().getTime(), PJ.COOKIE_DOMAIN, '/', expires );
};

/**
 * 清除一个 Cookie
 */
PinjuCookie.clearCookie = function(name, domain, path) {
  PinjuCookie.writeCookie(name, '', domain, path, 0);
};

/**
 * 登录、注册后需要忽略的返回页面
 */
PinjuCookie.ignoreLinks = [
  encodeURIComponent('/security/forgot-password.htm'),
  encodeURIComponent('/security/forgot-password-do.htm'),
  encodeURIComponent('/security/link.htm'),
  encodeURIComponent('/security/retrieve-password-do.htm')
];

PinjuCookie.prototype = {

  /**
   * 判断用户是否登录
   */
  isLogin : function() {
    if(!this._isTokenCorrect()) {
      return false;
    }
    return (this._findLogin().length == PinjuCookie.LOGS.length);
  },

  isSdoTimeout : function() {
    var sdo = this.getValue( PinjuCookie.SDO_CHECKER );
    if ( !sdo ) {
      PinjuCookie.DEBUG && window.console && console.info('cannot find sdo checker cookie, need check sdo');
      return true;
    }
    var current = new Date().getTime();
    PinjuCookie.DEBUG && window.console && console.info('current time: %d, sdo cookie time: %d, diff: %f', sdo, current, (current - sdo) / 60000);
    return current - sdo > PinjuCookie.SDO_CHECKER_INTERVAL;
  },

  writeSdoChecker : function() {
    this._write( pinju.Person.format( PinjuCookie.SDO, this.base ) );
  },

  getShoppingCartCount : function() {
    var count = this.getValue(PinjuCookie.SD);
    PinjuCookie.DEBUG && window.console && console.info('cart count: %s', count);
    if(isNaN(count)) return 0;
    return count;
  },

  getShoppingCartContent : function() {
	  return this.getValue(PinjuCookie.SC);
  },

  getAdvertiseInfo : function() {
	  var pCookieValue = decodeURIComponent(this.getValue(PinjuCookie.P));
	  if(pCookieValue){
	  	var values = pCookieValue.split("-");
	  	if(parseInt(values[1]) == 0){
	  		return pCookieValue.substr(0, pCookieValue.lastIndexOf("-"));
	  	}else{
			var start = parseInt(values[2]);
			var end = new Date().getTime();
			var days = (end - start)/ (24*60*60*1000);
			if(days > 15){
				var newValue = values[0] + "-0-" + values[2];
				PinjuCookie.writeCookie(PinjuCookie.P, newValue, PJ.COOKIE_DOMAIN, '/', 31536000);
				return values[0] + "-0";
			}else{
				return pCookieValue.substr(0, pCookieValue.lastIndexOf("-"));
			}
	  	}
	  }else{
	  	return "";
	  }
  },
  /*
  getAdvertiseTime : function() {
	  return this.getValue(PinjuCookie.PT);
  },
  */
  getAdvertise : function() {
	  var name, value;
	  var str = window.location.href; //取得整个地址栏
	  var num = str.indexOf("?")
	  str = str.substr(num + 1); //取得所有参数
	  var arr = str.split("&"); //各个参数放到数组里
	  for ( var i = 0; i < arr.length; i++) {
		num = arr[i].indexOf("=");
		if (num > 0) {
			name = arr[i].substring(0, num);
			if(name == "p"){
				value = arr[i].substr(num + 1);
			}
		}
	  }
	  if (value == undefined || value == "") {
	  	value = this.getAdvertiseInfo();
	  }
	  if(value){
	  	return value;
	  }else{
	  	return "";
	  }
  },

  /**
   * 根据 Cookie 的名字获取 Cookie 值
   */
  getValue : function(name) {
    return this.cookieMap[name];
  },

  /**
   * 显示登录导航信息
   */
  showLoginNav : function() {

    var find = this._findLogin();

    // Token 没找到也不会登录。清除所有已找到的登录 Cookie 信息
    if ( !this._isTokenCorrect() ) {
      this._write( pinju.Person.format( PinjuCookie.UNLOGIN, this.base, this._generateReturnUrl() ) );
      this._clearLogin( find );
      return false;
    }

    // 如果找到的登录 Cookie 信息数量与预定数量不符时也不是登录，清除已找到的登录 Cookie 信息
    if( find.length != PinjuCookie.LOGS.length ) {
      this._clearLogin( find );
      this._write( pinju.Person.format( PinjuCookie.UNLOGIN, this.base, this._generateReturnUrl() ) );
      return false;
    }

    var nickname = decodeURI(this.getValue( 'N' ));
    // 昵称为“[UNKNOW]”时需要用户填写昵称
    if(nickname == '[UNKNOWN]') {
      this._write( pinju.Person.format( PinjuCookie.LOGIN_NO_NICKNAME, this.base ));
      return true;
    }
    this._write( pinju.Person.format( PinjuCookie.LOGIN, this.base, nickname, this.logoutLink ));
  },

  /**
   * 生成 returnUrl 参数
   * 若 returnUrl 属于忽略列表中的 URL 不需要处理 returnUrl
   */
  _generateReturnUrl : function() {
    var returnUrl = encodeURIComponent( this._getReturnUrl() );
    PinjuCookie.DEBUG && window.console && console.info('[_generateReturnUrl] returnUrl [%s]', returnUrl);
    if (!returnUrl || returnUrl.length == 0) {
      return '';
    }
    // 检查忽略列表
    for (var i = 0, k = PinjuCookie.ignoreLinks.length; i < k; i++) {
      if ( returnUrl.indexOf(PinjuCookie.ignoreLinks[i]) > 0 ) {
        PinjuCookie.DEBUG && window.console && console.info('[_generateReturnUrl] returnUrl [%s] contains ignore symbol [%s]', returnUrl, PinjuCookie.ignoreLinks[i]);
        return '';
      }
    }
    // 是否是注册相关 URL
    if ( this._isRegister(returnUrl) ) {
      return this._getLinkReturnUrl( returnUrl );
    }
    return '?returnUrl=' + returnUrl;
  },

  /**
   * 是否属于注册 URL
   */
  _isRegister : function(returnUrl) {
    if (returnUrl.indexOf('register.htm') > 0) {
      return true;
    }
    if (returnUrl.indexOf('register-do.htm') > 0) {
      return true;
    }
    return false;
  },

  _isTokenCorrect : function() {
    return true;
  },

  _clearLogin : function(find) {
    var count = 0;
    for(var i = 0, k = find.length; i < k; i++) {
      PinjuCookie.clearCookie( find[i] );
      count++;
    }
    return count;
  },

  _findLogin : function() {
    var find = [];
    for(var i = 0, k = PinjuCookie.LOGS.length; i < k; i++) {
      if( this.cookieMap[PinjuCookie.LOGS[i]] ) {
        find.push( PinjuCookie.LOGS[i] );
      }
    }
    return find;
  },

  _getCookies : function() {
    var cookies = document.cookie.split(/;\s+/);
    var cookieMap = [];
    for (var i = 0, k = cookies.length; i < k; i++) {
      var keyValue = cookies[i].split('=');
      cookieMap[keyValue[0]] = this._removeVersion1Quote( keyValue[1] );
    }
    return cookieMap;
  },

  _getReturnUrl : function() {
    return location.href.replace(/([?&])(?:[?&]?r=[0-9]+)+(.)?/g, function(a, b, c){
      if(!c) return '';
      if(b == '?') return '?';
      return '&';
    });
  },

  /**
   * 截取链接中的 returnUrl 参数
   */
  _getLinkReturnUrl : function(returnUrl) {
    if (!returnUrl) return '';
    returnUrl = decodeURIComponent(returnUrl);
    RegExp.lastIndex = 0;
    var match = returnUrl.match(/[?&]returnUrl=([^&]+)/);
    if (!match || match.length < 2) return '';
    return '?returnUrl=' + match[1];
  },

  _write : function(content) {
    document.write( content );
  },

  /**
   * 移除 RFC2109/RFC2965 Cookie 版本 1 中的双引号
   */
  _removeVersion1Quote : function(value) {
    if(!value || value.indexOf('"') < 0) return value;
    return value.replace(/^"/, '').replace(/"$/, '');
  }
}