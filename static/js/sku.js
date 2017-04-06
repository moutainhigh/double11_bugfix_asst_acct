/**
 * Sku类
 * @author renguodong ~ 2011-07-26
 * @update lintao ~ 2011-07-26
 */
function Sku() {
	this.options = {};
	this.existed = {};
	this.existed.hash = {};
	this.existed.array = [];
	this.computed = [];
	this.computing = [];
	this.ui = {};
	this.initUi();
};
Sku.prototype.getOption = function(name) {
	if (arguments.length) { return this.options[arguments[0]]; }
	return this.options;
};
Sku.prototype.getExisted = function() {
	if (arguments.length) {
		if (!!this.existed.hash[arguments[0]]) return this.existed.hash[arguments[0]];
		var _size = this.existed.array.length;
		for (var i = 0; i < _size; i++) {
			if (this.existed.array[i].eq(arguments[0])) return this.existed.array[i];
		}
		return false;
	}
	return this.existed;
};
Sku.prototype.addOption = function() {
	var _option = arguments.length == 2 ? new SkuOption(arguments[0], arguments[1]) : arguments[0];
	this.options[_option.getName()] = _option;
};
Sku.prototype.addProperty = function() {
	var _property = arguments.length == 6 ? new SkuProperty(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5]) : arguments[0];
	var _opt_name = arguments.length == 6 ? arguments[3] : arguments[1];
	this.options[_opt_name].addProperty(_property);
};
Sku.prototype.addExisted = function() {
	var _existed = arguments.length == 1 ? arguments[0] : new SkuExisted(arguments[0], arguments[1] || 0, arguments[2] || 0, arguments[3] || '');
	if (this.getExisted(_existed.getName()) === false) {
		this.existed.array.push(_existed);
		this.existed.hash[_existed.getName()] = _existed;
		var _combination = _existed.getCombination();
		// 在这里反复执行genForm，性能很差
		for (var opt in _combination) {
			//this.select(opt, _combination[opt]);
			if (this.getOption(opt) && this.getOption(opt).getProperty(_combination[opt])) {
				this.getOption(opt).getProperty(_combination[opt]).setChecked(true);
			}
		}
	}
	else this.setExisted(_existed);
};
Sku.prototype.setExisted = function() {
	var _existed = arguments.length == 1 ? arguments[0] : new SkuExisted(arguments[0], arguments[1] || 0, arguments[2] || 0, arguments[3] || '');
	var _got = this.getExisted(_existed.getName());
	if (_got === false) this.addExisted(_existed);
	else {
		_got.setPrice(_existed.getPrice());
		_got.setCapacity(_existed.getCapacity());
		_got.setSellerCode(_existed.getSellerCode());
	}
};
Sku.prototype.select = function(option, property) {
	if (this.getOption(option)) {
		this.getOption(option).getProperty(property).setChecked(true);
	}
	this.genForm();
};
Sku.prototype.unSelect = function(option, property) {
	if (this.getOption(option)) {
		this.getOption(option).getProperty(property).setChecked(false);
	}
	this.genForm();
};
Sku.prototype.getTitleByType = function(type, val, title) {
	var leftTitle = '';
	if (type == 2) {
		leftTitle = '<label class="custom-sku-block custom-sku-bgcolor-' + val + '" title="' + title + '"></label>';
	} else if (type == 3) {
		leftTitle = '<label class="custom-sku-block" style="background-color:' + val + ';" title="' + title + '"></label>';
	} else {
		leftTitle = val;
	}
	return leftTitle;
};
Sku.prototype.genForm = function() {
	// save data
	var inputData = {};
	$('#SkuForm .sku-price, #SkuForm .sku-capacity, #SkuForm .sku-sellerCode').each(function() {
		inputData[this.name] = this.value;
	});
	this.resetForm();
	this.computed = [];
	for (var opt in this.options) {
		var _properties = this.options[opt].getProperty();
		var _cs = this.computed.length;
		this.computing = [];
		if (_cs) for (var i = 0; i < _cs; i++) {
			this.genGroup(_properties, opt, i);
		}
		else this.genGroup(_properties, opt, 0);
		if (this.computing.length == 0) {
			//没有任何属性被选中时，本次计算被抛弃
			this.resetForm();
			this.computed = [];
			return false;
		}
		this.computed = this.computing;
	}
	for (var i = 0; i < this.computed.length; i++) {
		this.addExisted(this.computed[i].id, 0, 0, '');
	}
	this.drawForm();
	// restore data
	$.each(inputData, function(key, val) {
		$('#SkuForm input[name="' + key + '"]').val(val);
	});
};
Sku.prototype.genCustomForm = function($option, skuType) {
	var self = this;
	// save data
	var nameData = {}, fileData = {};
	if ($option.next()[0].className == 'custom-option') {
		$option.next().find('.custom-name').each(function() {
			nameData[this.name] = this.value;
		});
		$option.next().find('.custom-file').each(function() {
			fileData[this.name] = this.value;
		});
		$option.next().remove();
	}
	var checkboxList = $option.find('input:checked');
	if (checkboxList.length === 0) {
		return;
	}
	var name1 = $option.attr('sku-name');
	var title = $option.attr('sku-title');
	var html = [
		(skuType == 1 ? '<table class="l-table">' : '<table class="l-table" style="width:auto;">'),
		'<tr>',
		(skuType == 1 ? '<th class="left-th">' : '<th colspan="2">') + '自定义' + title + '名称</th>',
		(skuType == 1 ? '<th>图片（无图片可以不填）</th>' : ''),
		'</tr>'].join('');
	checkboxList.each(function() {
		var $checkbox = $(this);
		var name2 = $checkbox.attr('sku-name');
		var type = $checkbox.attr('data-value-type');
		var showValue = $checkbox.attr('data-show-value');
		var inputName = name1 + ':' + name2;
		html += '<tr>';
		if (skuType == 1) {
			html += '<td class="short">' + self.getTitleByType(type, $checkbox.val(), showValue) + ' <input type="text" name="customName-' + inputName + '" class="text custom-name" maxlength="15" />';
		} else {
			html += '<td class="short">' + self.getTitleByType(type, $checkbox.val(), showValue) + '</td>';
			html += '<td class="short"><input type="text" name="customName-' + inputName + '" class="text custom-name" maxlength="15" />';
		}
		html += '<input type="hidden" name="itemInput.skuCustomIds" value="' + inputName + '" /></td>';
		html += (skuType == 1 ? '<td><input type="file" name="imgFile" data-name="' + inputName + '" /><input type="hidden" name="customFile-' + inputName + '" class="custom-file" /></td>' : '');
		html += '</tr>';
		var customName = $checkbox.attr('data-custom-name');
		if (typeof nameData['customName-' + inputName] == 'undefined' && customName) {
			nameData['customName-' + inputName] = customName;
		}
		var customUrl = $checkbox.attr('data-custom-url');
		if (typeof fileData['customFile-' + inputName] == 'undefined' && customUrl) {
			fileData['customFile-' + inputName] = $('#imageServer').val() + customUrl;
		}
	});
	html += '</table>';
	$option.after('<tr class="custom-option"><th></th><td>' + html + '</td></tr>');
	$option.each(function() {
		var $customOption = $(this).next();
		function showImage(skuName, url) {
			var fileBox = $customOption.find('input[data-name="' + skuName + '"]');
			var parent = fileBox.parent();
			var urlBox = parent.find('input[name="customFile-' + skuName + '"]');
			fileBox.hide();
			urlBox.val(url);
			var img = $('<img src="' + url + '_40x40.jpg" width="30" height="30" />');
			var deleteBtn = $('<a href="javascript:;" class="delete-btn">删除</a>');
			parent.append(img).append(deleteBtn);
			deleteBtn.click(function(e) {
				e.preventDefault();
				img.remove();
				deleteBtn.remove();
				fileBox.show();
				urlBox.val('');
			});
		}
		function showLoading(skuName) {
			var fileBox = $customOption.find('input[data-name="' + skuName + '"]');
			var parent = fileBox.parent();
			fileBox.hide().after('<span class="loading-img"><img src="http://static.pinju.com/img/page-loading.gif" width="15" height="15" />上传中，请稍候 ...</span>');
		}
		function hideLoading(skuName) {
			var fileBox = $customOption.find('input[data-name="' + skuName + '"]');
			var parent = fileBox.parent();
			parent.find('.loading-img').remove();
			fileBox.show();
		}
		// restore data
		$.each(nameData, function(key, val) {
			$customOption.find('input[name="' + key + '"]').val(val);
		});
		$.each(fileData, function(key, val) {
			if (val !== '') {
				showImage(key.replace(/^customFile-/i, ''), val);
			}
		});
		// upload file
		$customOption.find('input[name="imgFile"]').change(function() {
			var fileBox = $(this);
			var skuName = fileBox.attr('data-name');
			var form = $('<form action="/image/uploadImage.htm?size=1024&type=1&dir=image" enctype="multipart/form-data" method="post"></form>');
			fileBox.before(form);
			form.append(fileBox);
			showLoading(skuName);
			form.ajaxSubmit({
				dataType : 'text',
				target : '#output',
				success : function(str){
					// str = '<div class="content">\n<h3>\n很抱歉，您上传的文件超过最大限制3M！\n</h3>\n<ul>\n<li>\n 返回 <a href="http://www.pinju.com" title="品聚首页">品聚首页</a></li>\n</ul>\n</div>';
					hideLoading(skuName);
					form[0].reset();
					form.after(fileBox);
					form.remove();
					var data;
					try {
						data = $.parseJSON(str);
					} catch (e) {
						var msg, match;
						if ((match = /<h3>([\s\S]+?)<\/h3>/.exec(str))) {
							alert($.trim(match[1]));
							return;
						}
						alert('图片上传失败。');
					}
					if (data) {
						if (data.error == 1) {
							alert(data.message);
							return;
						}
						showImage(fileBox.attr('data-name'), data.url);
					}
				}
			});
		});
	});
};
Sku.prototype.genGroup = function(properties, option, i) {
	var _cs = this.computed.length;
	for (var prop in properties) {
		var _property = properties[prop];
		if (_property.isChecked()) {
			var _pair = option + ':' + prop;
			var _td = '<td>' + this.getTitleByType(_property.getType(), _property.getTitle(), _property.getShowTitle()) + '</td>';
			this.computing.push({
				id: _cs ? this.computed[i].id + '-' + _pair : _pair,
				html: _cs ? this.computed[i].html + _td : _td
			});
		}
	}
};
Sku.prototype.initUi = function() {
	var _this = this;
	this.ui = {
		options: $('.sku-option'),
		form: $('#SkuForm'),
		head: $('#SkuFormHead'),
		head_html: '',
		row: $('#SkuFormRow')
	};
	this.ui.options.each(function() {
		var $option = $(this),
			_opt = $(this).attr('sku-name'),
			_opt_title = $(this).attr('sku-title'),
			_opt_custom = $(this).attr('sku-custom');
		_this.addOption(_opt, _opt_title);
		$(this).find('input').each(function() {
			var _prop = $(this).attr('sku-name');
			var _type = $(this).attr('data-value-type');
			var _showValue = $(this).attr('data-show-value');
			_this.addProperty(_prop, $(this).attr('value'), false, _opt, _type, _showValue);
			// 这里没必要每次都执行一次genForm
			// if ($(this).is(':checked')) _this.select(_opt, _prop);
			if ($(this).is(':checked')) {
				if (_this.getOption(_opt)) {
					_this.getOption(_opt).getProperty(_prop).setChecked(true);
				}
			}
		}).change(function() {
			var _prop = $(this).attr('sku-name'),
				checked = $(this).is(':checked');
			// 自定义属性
			if (_opt_custom > 0) {
				_this.genCustomForm($option, _opt_custom);
			}
			if (checked) _this.select(_opt, _prop);
			else _this.unSelect(_opt, _prop);
		});
		_this.ui.head_html += '<th>' + _opt_title + '</th>';
		// 自定义属性
		if (_opt_custom > 0) {
			_this.genCustomForm($option, _opt_custom);
		}
	});
	_this.genForm();
};
Sku.prototype.drawForm = function() {
	var _this=this;
	var _form = this.ui.form.html('');
	var _head = this.ui.head.clone();
	var _c = this.computed;
	var _cs = _c.length;
	if (_cs) {
		_head.attr('id', '').prepend($(this.ui.head_html));
		_form.append(_head);
	}
	for (var i = 0; i < _cs; i++) {
		var _row = this.ui.row.clone();
		var _id = _c[i].id;
		var _existed = this.getExisted(_id);
		_row.attr('id', _id).prepend($(_c[i].html));
		_row.find('.sku-price').attr('name', 'price-' + _id).val(_existed.getPrice()).keyup(function() {
			var _name = $(this).attr('name').substr(6);
			_this.getExisted(_name).setPrice($(this).val());
		});
		_row.find('.sku-capacity').attr('name', 'capacity-' + _id).val(_existed.getCapacity()).keyup(function() {
			var _name = $(this).attr('name').substr(9);
			_this.getExisted(_name).setCapacity($(this).val());
		});
		_row.find('.sku-sellerCode').attr('name', 'sellerCode-' + _id).val(_existed.getSellerCode()).keyup(function() {
			var _name = $(this).attr('name').substr(11);
			_this.getExisted(_name).setSellerCode($(this).val());
		});
		_row.find('.sku-id').attr('name', 'itemInput.skuIds').val(_id);
		_form.append(_row);
	}
};
Sku.prototype.resetForm = function() {
	this.ui.form.html('');

};
/**
 * SkuOption类
 * @author renguodong ~ 2011-07-18
 */
function SkuOption(name, title) {
	this.name = name || '';
	this.title = title || '';
	this.properties = {};
};
SkuOption.prototype.getName = function() {
	return this.name;
};
SkuOption.prototype.getProperty = function() {
	if (arguments.length) { return this.properties[arguments[0]]; }
	return this.properties;
};
SkuOption.prototype.addProperty = function() {
	var _property = arguments.length == 3 ? new SkuProperty(arguments[0], arguments[1], arguments[2]) : arguments[0];
	this.properties[_property.getName()] = _property;
};
/**
 * SkuProperty类
 * @author renguodong ~ 2011-07-18
 */
function SkuProperty(name, title, checked, _opt, type, showTitle) {
	this.name = name || '';
	this.title = title || '';
	this.type = type || '';
	this.showTitle = showTitle || '';
	this.checked = !!checked;
};
SkuProperty.prototype.getName = function() {
	return this.name;
};
SkuProperty.prototype.getType = function() {
	return this.type;
};
SkuProperty.prototype.getTitle = function() {
	return this.title;
};
SkuProperty.prototype.getShowTitle = function() {
	return this.showTitle;
};
SkuProperty.prototype.isChecked = function() {
	return this.checked;
};
SkuProperty.prototype.setChecked = function(checked) {
	this.checked = !!checked;
};
/**
 * SkuExisted类
 * @author renguodong ~ 2011-07-18
 */
function SkuExisted(combination, price, capacity, sellerCode) {
	this.combination = combination || {};
	this.price = price || 0;
	this.capacity = capacity || 0;
	this.sellerCode = sellerCode || '';
	this.convert();
};
SkuExisted.prototype.convert = function() {
	if (typeof(this.combination) == 'string') {
		var _tmp = this.combination.split('-');
		var _s = _tmp.length;
		this.combination = {};
		for (var i = 0; i < _s; i++) {
			_tmp[i] = _tmp[i].split(':');
			this.combination[_tmp[i][0]] = _tmp[i][1];
		}
	}
};
SkuExisted.prototype.getCombination = function() {
	if (arguments.length) { return this.combination[arguments[0]]; }
	else { return this.combination; }
};
SkuExisted.prototype.getName = function() {
	var _ca = [];
	for (var opt in this.combination) {
		_ca.push(opt + ':' + this.combination[opt]);
	}
	return _ca.join('-');
};
SkuExisted.prototype.getPrice = function() {
	return this.price;
};
SkuExisted.prototype.getCapacity = function() {
	return this.capacity;
};
SkuExisted.prototype.getSellerCode = function() {
	return this.sellerCode;
};
SkuExisted.prototype.setPrice = function(price) {
	$(".sku-price[name=\"price-" + this.getName() + "\"]").val(price);
	this.price = price;
};
SkuExisted.prototype.setCapacity = function(capacity) {
	$(".sku-capacity[name=\"capacity-" + this.getName() + "\"]").val(capacity);
	this.capacity = capacity;
};
SkuExisted.prototype.setSellerCode = function(sellerCode) {
	$(".sku-sellerCode[name=\"sellerCode-" + this.getName() + "\"]").val(sellerCode);
	this.sellerCode = sellerCode;
};
SkuExisted.prototype.eq = function(combination) {
	var _tmp = combination.split('-');
	var _s = _tmp.length;
	for (var i = 0; i < _s; i++) {
		_tmp[i] = _tmp[i].split(':');
		if (!(_tmp[i][0] in this.combination && this.combination[_tmp[i][0]] == _tmp[i][1])) return false;
	}
	return true;
};
