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
 * @author renguodong ~ 2011-07-13
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
			var _ph_text = _self.attr('placeholder');
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
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.ReqQueue = function() {
	this._queue = [];
	this._immediately = [];
	this._current = null;
	this._autoRequest();
};
/**
 * 向请求队列添加一个请求的方法
 * @author renguodong ~ 2011-07-14
 * @param {Object} req  请求参数列表
 */
pj.ajax.ReqQueue.prototype.add = function(req) {
	var _q = req.isImmediately() ? this._immediately : this._queue;
	_q.push(req);
	if (!!!this._current) {
		this._current = req;
		$.ajax(req.getOptions());
	}
};
/**
 * 返回普通队列的长度
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.ReqQueue.prototype.qSize = function() {
	return this._queue.length;
};
/**
 * 返回立即队列的长度
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.ReqQueue.prototype.iSize = function() {
	return this._immediately.length;
};
/**
 * 自动执行所有队列中的请求，先进先出的原则
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.ReqQueue.prototype._autoRequest = function() {
	var _this = this;
	$(document).ajaxComplete(function() {
		var _r = null;
		_this._current.isImmediately() ? _this._immediately.shift() : _this._queue.shift();
		_r = _this._immediately.length ? _this._immediately[0] : (_this._queue.length ? _this._queue[0] : null);
		_this._current = _r;
		if (_r) $.ajax(_r.getOptions());
	});
};
/**
 * 请求对象，使用jQuery原生的ajax方法，因此参数也相同
 * @author renguodong ~ 2011-07-15
 * @param {Object} options          请求参数列表
 * @param {Boolean} immediately     直接请求选项
 */
pj.ajax.Request = function(options, immediately) {
	this._options = options;
	this._immediately = !!immediately;
	if (pj.ajax.reqQueue) pj.ajax.reqQueue.add(this);
	else pj.ajax.reqQueue = new pj.ajax.ReqQueue();
};
/**
 * 获取请求的参数列表
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.Request.prototype.getOptions = function() {
	return this._options;
};
/**
 * 判断请求是否是立即请求
 * @author renguodong ~ 2011-07-15
 */
pj.ajax.Request.prototype.isImmediately = function() {
	return this._immediately;
};
/**
 * 创建请求的简单方式，参数和$.post类似
 * @author renguodong ~ 2011-07-15
 * @param {Object} url          请求地址，必填
 * @param {Object} data         请求的数据，可选
 * @param {Object} callback     请求成功时的回调函数，可选
 * @param {Object} dataType     返回数据类型，默认为json，可选
 * @param {Object} immediately  立即请求参数，可选
 */
pj.ajax.Post = function(url, data, callback, dataType, immediately) {
	this._req = new pj.ajax.Request({
		url: url,
		type: 'POST',
		data: data || {},
		success: callback,
		dataType: dataType || 'json'
	}, immediately);
}
/**
 * 加载页面时初始化
 * @author renguodong ~ 2011-07-13
 */
pj.ui.init = function() {
	var _ui = pj.ui;
	pj.base.__fixSupportability();
	$('input').each(function() {
		_ui.inputHandler.push();
		new _ui.Input($(this));
	});
	$('#PageLoading').fadeOut(500);
};
/**
 * 输入控件对象
 * 给input元素增加ajax-validation="$AJAX-URL$"，即可使控件支持AJAX验证
 * @author renguodong ~ 2011-07-15
 * @param {Object} el   输入控件的jQuery对象
 */
pj.ui.Input = function(el) {
	this._self = el;
	this._val = el.val();
	this._ajax_validation = !!el.attr('ajax-validate') ? el.attr('ajax-validate') : '';
	//提示元素
	this._warning = el.nextAll('.warning').eq(0);
	//初始化控件
	var _this = this;
	el.keyup(function(evt) {
		_this._val = $(this).val();
		_this._validate();
	});
	//初始化完成，加入控件句柄列表（全局）
	pj.ui.inputHandler.push(this);
};
/**
 * 验证输入
 * @author renguodong ~ 2011-07-15
 */
pj.ui.Input.prototype._validate = function() {
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
	//	for (var i = 0; i < 60; i++) {
	//		var imm = Math.ceil(Math.random(0, 1) * 10000);
	//		new pj.ajax.Request({
	//			url: 'demo.html',
	//			type: 'GET',
	//			dataType: 'text',
	//			success: (function(n, r) {
	//				return function() {
	//					cout(n + ':' + r);
	//				}
	//			})(i, imm)
	//		}, imm >= 5000);
	//	}
});
