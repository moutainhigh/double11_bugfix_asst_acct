/*
 *  Layout Editor Component
 */
function LayoutEditor(dialog) {
	this._construct(dialog);
}

LayoutEditor.prototype._construct = function(dialog) {
	var _this = this;
	this._self = $('#LayoutEditor');
	this._layouts = this._self.find('.layout');
	this._slots = this._self.find('.slot');
	this._modules = this._self.find('.module');
	this._form = this._self.find('#LayoutForm');
	this._sel_page = this._self.find('#SelectPage');
	this._del = this._self.find('.delete-module');
	this._add = this._self.find('.add-module');
	this._dialog = dialog;
	this._slots.each(function() {
		$(this).disableSelection().sortable( {
			items : "li[module-name!=topShopSign]"
		});
	});
	this._modules.hover(function(e) {
		$(this).addClass('module-hovered');
	}, function(e) {
		$(this).removeClass('module-hovered');
	});
	this._form.submit(function() {
		return _this.save();
	});
	this._sel_page.change(function() {
		window.location.href = $(this).val();
	});
	this._del.click(function() {
		if (confirm('您确定删除该模块吗？被删除模块的个人设置将不可恢复！'))
			$(this).closest('.module').remove();
		return false;
	});
	this._add.click(function() {
		_this._dialog.open($(this).attr('href').substring(1), 510, 510);
		return false;
	});
};
LayoutEditor.prototype.parseToXML = function() {
	var _self = this._self;
	var xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	xml += "\n<layouts type=\"" + $('#LayoutArea').attr('layout-type') + "\">";
	this._layouts.each(function() {
		xml += "\n\t<layout type=\"" + $(this).attr('layout-type') + "\">";
		$(this).find('.slot').each(function() {
			xml += "\n\t\t<slot>";
			$(this).find('.module').each(function() {
				xml += "\n\t\t\t<module name=\"" + $(this).attr('module-name');
				xml += "\" id=\"" + $(this).attr('module-id');
				xml += "\" title=\"" + $(this).find('span').text();
				xml += "\" is-custom-code=\"" + $(this).attr('is-custom-code');
				xml += "\" />";
			});
			xml += "\n\t\t</slot>";
		});
		xml += "\n\t</layout>";
	});
	xml += "\n</layouts>";
	return xml;
};
LayoutEditor.prototype.save = function(DEBUG_MODE) {
	this._self.find('#LayoutXML').val(this.parseToXML());
	if (DEBUG_MODE)
		console.log(this.parseToXML());
	return !DEBUG_MODE;
};
LayoutEditor.prototype.addModule = function(el, sid) {
	var _int_sid = parseInt(sid);
	var _sid = (typeof (_int_sid) == 'number' && _int_sid >= 0 && _int_sid < this._slots
			.size()) ? _int_sid : 0;
	var _slot = this._slots.eq(_sid);
	var _name = el.attr('module-name');
	var _id = parseInt(el.attr('module-id'));
	var _title = el.attr('module-title');
	var _icc = el.attr('is-custom-code');
	var _existed = _slot.find(".module[module-name=\"" + _name + "\"]").size();
	if (_existed) {
		alert(_title + '已经存在');
		return false;
	}
	var _obj = this._modules.eq(0).clone(true, true);
	_obj.attr( {
		'module-name' : _name,
		'module-id' : _id,
		'is-custom-code' : _icc
	}).find('span').text(_title);
	_slot.append(_obj);
	return true;
};
/*
 *  Dialog Component
 */
function Dialog() {
	this._construct();
}

Dialog.prototype._construct = function() {
	var _this = this;
	this._self = $('#Dialog');
	this._mask = $('#DialogMask');
	this._layout = $('#DialogLayout');
	this._caption = $('#DialogCaption');
	this._content = $('#DialogContent');
	$('#DialogCaption a').click(function() {
		_this.close();
		return false;
	});
	this._resizeMask();
	this._resizeDialog();
	this._locateDialog();
	this._autoFitWindow();
};
Dialog.prototype._resizeMask = function() {
	$('#DialogMask, #DialogMask iframe').css( {
		'width' : Math.max($(document.body).outerWidth(), $(window).width()),
		'height' : Math.max($(document.body).outerHeight(), $(window).height())
	});
};
Dialog.prototype._resizeDialog = function(width, height) {
	var _width = (typeof (width) == 'number' && width >= 310) ? width : 430;
	var _height = (typeof (height) == 'number' && height >= 150) ? height : 270;
	$('#Dialog, #DialogShadow').css( {
		'width' : _width + 10,
		'height' : _height + 10
	});
	this._layout.css( {
		'width' : _width,
		'height' : _height
	});
	this._content.css( {
		'width' : _width,
		'height' : _height - 30
	});
};
Dialog.prototype._locateDialog = function() {
	var _left = ($(window).width() - this._self.width()) * 0.5;
	var _top = ($(window).height() - this._self.height()) * 0.382;
	var scrollLeft = $(window).scrollLeft();
	var scrollTop = $(window).scrollTop();
	this._self.css( {
		'left' : _left > 0 ? _left + scrollLeft : scrollLeft,
		'top' : _top > 0 ? _top + scrollTop : scrollTop
	});
};
Dialog.prototype._autoFitWindow = function() {
	var _this = this;
	$(window).resize(function() {
		_this._resizeMask();
		_this._locateDialog();
	}).scroll(function() {
		_this._locateDialog();
	});
};
Dialog.prototype.caption = function(text) {
	var _caption = this._caption.find('span');
	if (typeof (text) == 'string' && text != ''){
		_caption.text(text);
	}else{
		_caption.text('编辑模块');
	}
	return _caption.text();
}
Dialog.prototype.close = function() {
	this._self.hide()
	this._content.attr('src', 'about:blank');
	this._mask.hide();
};
Dialog.prototype.open = function(url, width, height) {
	var _url = (typeof (url) == 'string' && url != '') ? url : 'about:blank';
	this._resizeDialog(width, height);
	this._locateDialog();
	this._resizeMask();
	this._mask.show();
	this._content.attr('src', _url);
	this._self.show();
};
/*
 *  Switchable Component
 */
function Switchable(el_selector) {
	this._construct(el_selector);
}

Switchable.prototype._construct = function(el_selector) {

	var _sel = (typeof (el_selector) == 'string' && el_selector != '') ? el_selector
			: document.body;
	this._self = $(_sel);
	this._height = this._self.height();
	this._width = this._self.width();
	this._self.find('.sw-panel, .sw-panels, .sw-panel img, .sw-panel a').css( {
		'width' : this._width,
		'height' : this._height
	});
	this._tab = this._self.find('.sw-tab');
	this._panel = this._self.find('.sw-panel');
	this._type = this._self.attr('module-type');
	this._effect = this._self.attr('module-effect');
	this._auto_delay = parseInt(this._self.attr('module-auto-delay'));
	this._ani_duration = 1000;
	this._count = 0;
	if (this._type == 'slide-show')
		this._manualSwitch();
	if (this._effect == 'fade')
		this._initFadeEffect();
	if (this._effect == 'slide')
		this._initSlideEffect();
	if (this._auto_delay > 0)
		this._aa_timer = setTimeout(this.autoAnimate(), this._auto_delay);

	var __height = this._height;
	var __width = this._width;
	this._self.find('.sw-panel, .sw-panels, .sw-panel img, .sw-panel a').css( {
		'width' : __width,
		'height' : __height
	});
}
Switchable.prototype._prev = function() {
	this._count = this._count > 0 ? this._count - 1 : this._panel.size() - 1;
	return this._count;
}
Switchable.prototype._next = function() {
	this._count = this._count < this._panel.size() - 1 ? this._count + 1 : 0;
	return this._count;
}
Switchable.prototype._initFadeEffect = function() {
	this._self.css('background', '#FFF');
	this._panel.hide().eq(0).show();
	this._tab.eq(this._count).addClass('cur-tab');
}
Switchable.prototype._initSlideEffect = function() {
	this._self.css('background', '#FFF');
	this._panel.hide().eq(0).show();
	this._tab.eq(this._count).addClass('cur-tab');
}
Switchable.prototype.autoAnimate = function() {
	var _this = this;
	return function() {
		_this.goTo('next');
		_this._aa_timer = setTimeout(_this.autoAnimate(), _this._auto_delay);
	};
}
Switchable.prototype.goTo = function(i) {
	var _this = this, _c = parseInt(this._count), _n = this._count = i == 'next' ? parseInt(this
			._next())
			: parseInt(i);
	if (_c == _n)
		return;
	this._tab.eq(_c).removeClass('cur-tab');
	if (this._effect == 'fade') {
		this._panel.stop(true, true).eq(_c).fadeOut(this._ani_duration);
		this._panel.eq(_n).fadeIn(this._ani_duration);
	}
	if (this._effect == 'slide') {
		if (_n < _c) {
			this._panel.stop(true, true).eq(_n).css('left',
					0 - this._self.width()).show().animate( {
				'left' : 0
			}, this._ani_duration / 2);
			this._panel.eq(_c).animate( {
				'left' : this._self.width()
			}, this._ani_duration / 2, function() {
				_this._panel.eq(_c).hide();
			});
		} else {
			this._panel.stop(true, true).eq(_n).css('left', this._self.width())
					.show().animate( {
						'left' : 0
					}, this._ani_duration / 2);
			this._panel.eq(_c).animate( {
				'left' : 0 - this._self.width()
			}, this._ani_duration / 2, function() {
				_this._panel.eq(_c).hide();
			});
		}
	}
	this._tab.eq(_n).addClass('cur-tab');
}
Switchable.prototype._manualSwitch = function() {
	var _this = this;
	if (this._type == 'slide-show') {
		this._tab.hover(function() {
			clearTimeout(_this._aa_timer);
			_this.goTo(_this._tab.index($(this)));
		},
				function() {
					_this._aa_timer = setTimeout(_this.autoAnimate(),
							_this._auto_delay);
				});
	}
}
