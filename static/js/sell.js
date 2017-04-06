/**
 * GoodsSeller类
 * @author renguodong
 * @update lintao ~ 2011-08-03
 * @update luolonghao ~ 2011-08-26
 */

function GoodsSeller() {
};
function CategorySelecter() {
	this.selected = [];
	this.ui = {};
	this.initUi();
};
CategorySelecter.prototype.select = function(id, title, spell, isleaf) {
	var _category = new Category(id, title, spell, isleaf, this.selected.length);
	this.selected.push(_category);
};
// 支持多个id，0:1234:1235
CategorySelecter.prototype.getCategories = function(id, fn) {
	var _id = (id || 0).toString(10);
	var _idList = _id.split(':');
	$.ajax({
		url: "/item/getCategoryListByPath.htm",
		data: {
			categoryPath: _id
		},
		success: function(obj) {
			var _data = {};
			if (obj && obj.categoryPathAll) {
				$.each(_idList, function(i, id) {
					if (obj.categoryPathAll[id]) {
						_data[id] = obj.categoryPathAll[id].itemCategory || [];
					}
				});
				if (fn) {
					fn(_data);
				}
			}
		},
		async: false,
		type: "get",
		dataType: "json"
	});
};
CategorySelecter.prototype.initUi = function() {
	this.ui = {
		editor: $('#CategorySelecter'),
		wrapper: $('#CategoryWrapper'),
		result: $('#CategoryResult'),
		categories_tpl: $($('#CategoriesTpl').val()),
		category_tpl: $($('#CategoryTpl').val()),
		result_tpl: $($('#CategoryResultTpl').val()),
		c_path: $('#categoryPath'),
		c_id: $('#categoryId'),
		c_title: $('#categoryTitle'),
		submitable: $('#Submitable'),
		categoryPath : $('#categoryPath').val()
	};
};
CategorySelecter.prototype.genChildCategoryAll = function(categoryPath) {
	var self = this;
	self.getCategories(categoryPath, function(dataAll) {
		$.each(dataAll, function(id, data) {
			self.genChildCategoryByData(data);
			if (id > 0) {
				var a = $('#' + id),
					parent = a.closest('.categories');
				self.clickCategory(a, data);
				// 滚动条滚动到被选位置
				parent.scrollTop(a.position().top - 114);
			}
		});
	});
};
// main function
CategorySelecter.prototype.genChildCategory = function(id) {
	var self = this;
	// 编辑分类
	if (self.ui.categoryPath) {
		self.genChildCategoryAll('0:' + self.ui.categoryPath);
		return;
	}
	// 新进分类
	self.getCategories(id, function(dataAll) {
		self.genChildCategoryByData(dataAll[id]);
	});
};
CategorySelecter.prototype.clickCategory = function(a, data) {
	var self = this;
	var _parent = a.closest('.categories');
	var _il = a.attr('isleaf') == 'yes';
	_parent.nextAll().remove();
	_parent.find('a').removeClass('selected');
	a.addClass('selected');
	self.selected.length = parseInt(_parent.attr('level'));
	self.select(a.attr('id'), a.text(), a.attr('spell'), _il);
	self.genResult();
	if (!_il) {
		if (data) {
			self.genChildCategoryByData(data);
			return;
		}
		var id = a.attr('id');
		self.getCategories(id, function(dataAll) {
			self.genChildCategoryByData(dataAll[id]);
		});
	}
};
CategorySelecter.prototype.genChildCategoryByData = function(_data) {
	var _this = this;
	var _categories = this.ui.categories_tpl.clone();
	var _search = _categories.find('.t').eq(0);
	var _lv = this.selected.length;
	if (_lv == 0) _categories.addClass('first');
	_categories.find('.t').keyup(function() {
		var _self = $(this);
		var _a = _self.closest('.categories').find('a');
		var _val = _self.val();
		if (_val == '') _a.closest('li').removeClass('hide');
		else {
			_a.closest('li').addClass('hide');
			_a.each(function() {
				if ($(this).attr('spell').toString().match(_val) || $(this).text().toString().match(_val)) $(this).closest('li').removeClass('hide');
			});
		}
	});
	for (var i = 0; i < _data.length; i++) {
		var _category = this.ui.category_tpl.clone();
		var _a = _category.find('a');
		_a.attr('id', _data[i].id);
		_a.attr('spell', _data[i].spell);
		_a.attr('isleaf', _data[i].isleaf ? 'yes' : 'no');
		_a.text(_data[i].title);
		_a.addClass(_data[i].isleaf ? 'yes' : 'no');
		_a.click(function() {
			_this.clickCategory($(this));
		});
		_a.mousedown(function(e) { e.preventDefault(); }).mousemove(function(e) { e.preventDefault(); }).attr('unselectable', 'on');
		_categories.attr('level', _lv).append(_category);
	}
	this.ui.wrapper.append(_categories);
};
CategorySelecter.prototype.genResult = function() {
	var _this = this;
	var _selected = this.selected;
	var _s = _selected.length;
	var _path = [];
	this.ui.result.html('');
	for (var i = 0; i < _s; i++) {
		var _tpl = $('<a></a>');
		var _cur = _selected[i];
		_tpl.attr('href', 'javascript:;');
		_tpl.attr('cid', _cur.get('id'));
		_tpl.attr('level', i);
		_tpl.attr('spell', _cur.get('spell'));
		_tpl.attr('isleaf', _cur.isleaf ? 'yes' : 'no');
		_tpl.text(_cur.get('title'));
		_tpl.click(function() {
			var _l = $(this).attr('level'), _c = $(this).attr('cid');
			$(".categories[level=\"" + _l + "\"]").find("a[id=\"" + _c + "\"]").click();
		});
		_tpl.mousedown(function(e) { e.preventDefault(); }).mousemove(function(e) { e.preventDefault(); }).attr('unselectable', 'on');
		_path.push(_cur.get('id'));
		this.ui.result.append(_tpl);
		if (!_cur.isleaf) this.ui.result.append('<span>&gt;</span>');
	}
	this.ui.c_path.val(_path.join(':'));
	this.ui.c_id.val(_selected[_s - 1].get('id'));
	this.ui.c_title.val(_selected[_s - 1].get('title'));
	this.ui.submitable.val(_selected[_s - 1].get('isleaf') ? 'yes' : 'no');
};
function Category(id, title, spell, isleaf, level) {
	this.id = id || 0;
	this.title = title || '';
	this.spell = spell || '';
	this.isleaf = !(isleaf === false);
	this.level = level || 0;
};
Category.prototype.get = function(prop) {
	if (prop in this) return this[prop];
	else { return undefined; }
};

