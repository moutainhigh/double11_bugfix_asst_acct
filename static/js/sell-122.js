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
CategorySelecter.prototype.getCategories = function(id) {
	var _data = [];
	var _id = id || 0;
	//_category_data这个变量用于存放AJAX获取的类目列表，暂时使用程序自动生成
	for (var i = 0; i < 20; i++) {
		_data.push({
			id: Math.round(Math.random() * 10000 + 1),
			title: '商品类目' + Math.round(Math.random() * 100 + 1),
			spell: Math.round(Math.random() * 10000 + 1),
			isleaf: this.selected.length == 5 || Math.round(Math.random() * 10000 + 1) > 5000
		});
	}
	//模拟类目列表生成完毕
	return _data;
};
CategorySelecter.prototype.initUi = function() {
	this.ui = {
		editor: $('#CategorySelecter'),
		wrapper: $('#CategoryWrapper'),
		result: $('#CategoryResult'),
		categories_tpl: $($('#CategoriesTpl').val()),
		category_tpl: $($('#CategoryTpl').val()),
		result_tpl: $($('#CategoryResultTpl').val())
	};
};
CategorySelecter.prototype.genChildCategory = function(id) {
	var _this = this;
	var _data = this.getCategories(id);
	var _categories = this.ui.categories_tpl.clone();
	var _lv = this.selected.length;
	if (_lv == 0) _categories.addClass('first');
	for (var i = 0; i < _data.length; i++) {
		var _category = this.ui.category_tpl.clone();
		var _a = _category.find('a')
		_a.attr('id', _data[i].id);
		_a.attr('spell', _data[i].spell);
		_a.attr('isleaf', _data[i].isleaf ? 'yes' : 'no');
		_a.text(_data[i].title);
		_a.click(function() {
			if ($(this).hasClass('selected')) return false;
			var _self = $(this);
			var _parent = _self.closest('.categories');
			var _il = _self.attr('isleaf') == 'yes';
			_parent.nextAll().remove();
			_parent.find('a').removeClass('selected');
			_self.addClass('selected');
			_this.selected.length = parseInt(_parent.attr('level'));
			_this.select(_self.attr('id'), _self.text(), _self.attr('spell'), _il);
			_this.genResult();
			if (!_il) _this.genChildCategory(_self.attr('id'));
		});
		_categories.attr('level', _lv).append(_category);
	}
	this.ui.wrapper.append(_categories);
};
CategorySelecter.prototype.genResult = function() {
	var _this = this;
	var _selected = this.selected;
	var _s = _selected.length;
	this.ui.result.html('');
	for (var i = 0; i < _s; i++) {
		var _tpl = $('<a></a>');
		var _cur = _selected[i];
		_tpl.attr('href', '##');
		_tpl.attr('cid', _cur.get('id'));
		_tpl.attr('level', i);
		_tpl.attr('spell', _cur.get('spell'));
		_tpl.attr('isleaf', _cur.isleaf ? 'yes' : 'no');
		_tpl.text(_cur.get('title'));
		_tpl.click(function() {
			var _self = $(this), _l = _self.attr('level'), _c = _self.attr('cid'), _il = _self.attr('isleaf') == 'yes';
			$(".categories[level=\"" + _l + "\"]").eq(0).nextAll().remove();
			_this.selected.length = parseInt(_l);
			_this.select(_c, _self.text(), _self.attr('spell'), _il);
			if (!_il) _this.genChildCategory(_c);
			_this.genResult();
		});
		this.ui.result.append(_tpl);
		if (!_cur.isleaf) this.ui.result.append('<span>&gt;</span>');
	}
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
//<a id="50012834" class="box-icon" href="javascript:" spell="yxrj" isleaf="true">游戏软件</a>
