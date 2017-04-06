/**
 * @namespace  pj 基础的命名空间(pinju.com)
 * @version    0.1
 */
var pj = {
	version: '0.1'
};
/**
 * @namespace  pj.base 公用基础功能
 */
pj.base = {};
/**
 * @namespace  pj.ajax 对jQuery的AJAX功能进行再次封装
 */
pj.ajax = {};
/**
 * @namespace  pj.ui 界面功能
 */
pj.ui = {
	inputHandler: []
};
/**
 * 存放全局配置
 */
pj.config = {
	//是否需要统一处理href="##"的链接，true || false
	need_fix_anchor: false,
	debug: false
};
/**
 * 用于存放兼容性测试的值
 */
pj.__supportability__ = {
	placeholder: ('placeholder' in document.createElement('input')),
	autofocus: ('autofocus' in document.createElement('input'))
};
/**
 * 兼容性处理
 * @author renguodong ~ 2011-07-13
 */
pj.base.__fixSupportability = function() {
	if (pj.config.need_fix_anchor) _fixAnchor();
	if (!pj.__supportability__.placeholder) _fixPlaceholder();
	if (!pj.__supportability__.autofocus) _fixAutofocus();
	/**
	 * 统一处理href="##"的链接，取消默认事件
	 * @author renguodong ~ 2011-07-13
	 */
	function _fixAnchor() {
		$(document).bind('click', function(event) {
			var _t = event.target;
			return !(_t.tagName.toLowerCase() === 'a' && _t.hash === '##');
		});
	}
	/**
	 * 兼容placeholder属性，提供输入控件内提示文本效果
	 * @author renguodong ~ 2011-07-13
	 */
	function _fixPlaceholder() {
		$('input, textarea').each(function() {
			var _self = $(this);
			var _ph_text = _self.prop('placeholder');
			if (!!_ph_text) {
				_self.val(_ph_text).addClass('hph');
				_self.focus(function() {
					if (_self.hasClass('hph')) _self.val('').removeClass('hph');
				}).blur(function() {
					if (_self.val() == '') _self.val(_ph_text).addClass('hph');
				});
			}
		});
	}
	/**
	 * 兼容autofocus属性，第一个拥有autofoce="autofocus"的input元素会自动获得焦点
	 * @author renguodong ~ 2011-07-13
	 */
	function _fixAutofocus() {
		var _af = false;
		$('input').each(function() {
			if (!_af && 'autofocus' in $(this).get(0)) {
				$(this).focus();
				_af = true;
			}
		});
	}
};
/**
 * 请求队列；非队列头部的元素一定是待处理的请求，队列头部的元素一定是正在响应的请求。
 * @author renguodong ~ 2011-07-14
 * pj.ajax.reqQueue 全局的存放请求队列对象的变量，默认为空；当新建一个请求对象时，会自动创建一个请求队列对象并赋值给这个变量，该操作只在请求队列不存在时执行
 */
pj.ajax.reqQueue = null;
/**
 * 请求队列对象
 * @author renguodong ~ 2011-07-14
 */
pj.ajax.ReqQueue = function() {
	this._queue = [];
	this._autoRequest();
};
/**
 * 向请求队列添加一个请求的方法
 * @author renguodong ~ 2011-07-14
 * @param {Object} req  请求参数列表
 */
pj.ajax.ReqQueue.prototype.add = function(req) {
	this._queue.push(req);
	if (this._queue.length == 1) {
		$.ajax(req);
	}
};
/**
 * 自动执行所有队列中的请求，先进先出的原则
 * @author renguodong ~ 2011-07-14
 */
pj.ajax.ReqQueue.prototype._autoRequest = function() {
	var _this = this;
	$(document).ajaxComplete(function() {
		_this._queue.shift();
		if (_this._queue.length) $.ajax(_this._queue[0]);
	});
};
/**
 * 请求对象，使用jQuery原生的ajax方法，因此参数也相同
 * @param {Object} options  请求参数列表
 */
pj.ajax.Request = function(options) {
	this._req = options;
	if (pj.ajax.reqQueue) pj.ajax.reqQueue.add(this._req);
	else pj.ajax.reqQueue = new pj.ajax.ReqQueue();
};
/**
 * 加载页面时初始化
 * @author renguodong ~ 2011-07-13
 */
pj.ui.init = function() {
	var _ui = pj.ui;
	pj.base.__fixSupportability();
	$('input').each(function() {
		_ui.inputHandler.push(new _ui.Input($(this)));
	});
	$('#PageLoading').fadeOut(500);
};
/**
 * 输入控件对象
 * @author renguodong ~ 2011-07-14
 * @param {Object} el   输入控件的jQuery对象
 */
pj.ui.Input = function(el) {
	this._self = el;
	this._val = el.val();
	this._ajaxUnique();
};
pj.ui.Input.prototype._ajaxUnique = function() {
	var _au = this._self.attr('ajax-unique');
	if (!!_au) {
		alert(_au);
	}
};
/**
 * 兼容console.info和console.log方式进行控制台输出
 * @author renguodong ~ 2011-07-13
 */
var cobj = function(obj) {
	if (pj.config.debug) $('#Console').html($('#Console').html() + '<p>' + obj + '</p>');
	else typeof(console) == 'undefined' ? $('#Console').html($('#Console').html() + '<p>' + obj + '</p>') : console.info(obj);
};
var cout = function(obj) {
	if (pj.config.debug) $('#Console').html($('#Console').html() + '<p>' + obj + '</p>');
	else typeof(console) == 'undefined' ? $('#Console').html($('#Console').html() + '<p>' + obj + '</p>') : console.log(obj);
	
};
$(function() {
	pj.config.debug = true;
	pj.ui.init();
	for (var i = 0; i < 60; i++) {
		new pj.ajax.Request({
			url: 'demo.html',
			type: 'GET',
			dataType: 'text',
			success: (function(n) {
				return function() {
					cout(n);
				}
			})(i)
		});
	}
});
