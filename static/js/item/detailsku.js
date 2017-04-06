/*
 *  支持任意数量属性，任意数量的属性值，按以下格式枚举，
 *  options = {
 *      'option-name' : ['属性名（如“尺码”）', {
 *              'name-a' : ['属性值', 'checked'],   //0未选中，1为选中，属性名请纯小写
 *              'name-b' : ['XXL', ''],
 *              ...
 *      }]
 *  }
 *  options = {
 *      'PNC' : 1,
 *      'upper-limit' : '9999.99',
 *      'lower-limit' : '1999.99',
 *      'p0-p1-p2-pn' : ['888.88',1000,123]
 *  }
 */

/**
	2011-12-10: 只有一个属性时默认选中
	2011-12-10: 用户选择一项SKU属性和该sku属性组合的SKU库存为0时，该未选的sku属性变灰且不可选

*/

function SmartSKU(sel_target, options) {
    var _this = this;
    this._target = $(sel_target);
    this._options = options;
    this._selected_options = {};
	var _st = sel_target;
	var _ss = this._target.find('li');
	this._ss = _ss;
	var _iselect = '<i>s</i>';
	_this._target.each(function(i) {
		$(this).find('li').click(function() {
			if ($(this).hasClass('disabled')) {
				return;
			}
			$(this).siblings('li').removeClass("ho");
			$(this).siblings('li').find('i').remove();
			if ($(this).hasClass("ho")) {
				$(this).removeClass("ho");
				$(this).find('i').remove();
				_this.disableEmptySKU();
				return;
			}
			$(this).addClass('ho');
			$(this).append(_iselect);
			_this.disableEmptySKU(i);
		});
	});
	// 只有一个属性时默认选中
	_this._target.each(function() {
		var li = $(this).find('li:not(.disabled)');
		if (li.length == 1) {
			li.addClass('ho');
			li.append(_iselect);
		}
	});
	_this.disableEmptySKU();
}

SmartSKU.prototype.disableEmptySKU = function(currentIndex) {
	var self = this;
	var options = [], selectedArr = [];
	// 每行属性是否被选中
	self._target.each(function(i) {
		var li = $(this).find('li.ho');
		selectedArr[i] = li.length > 0 ? li.attr('id') : '';
	});
	// 选择有库存的options
	$.each(self._options, function(key, val) {
		if ($.isArray(val)) {
			var count = 0;
			$.each(key.split('-'), function(i, v) {
				if (selectedArr[i] === v || selectedArr[i] === '') {
					count++;
				}
			});
			count === self._target.length && options.push(key);
		}
	});
	// 根据库存改成disable状态
	self._target.each(function(i) {
		// 跳过当前行
		if (i === currentIndex) {
			return;
		}
		// 已经选中时跳过
		if (selectedArr[i] !== '') {
			return;
		}
		$(this).find('li').removeClass('disabled').each(function() {
			var id = this.id, capacity = 0;
			$.each(options, function(i, option) {
				if (('-' + option + '-').indexOf('-' + id + '-') >= 0) {
					capacity += self._options[option][1];
				}
			});
			if (capacity === 0) {
				if ($(this).hasClass("ho")) {
					$(this).removeClass("ho");
					$(this).find('i').remove();
				}
				$(this).addClass('disabled');
			}
		});
	});
	self.getSKU();
};
SmartSKU.prototype._compute = function(properties) {
    var _cs = this._computed.length;
    var _ps = properties.length;
    this._computing = [];
    if (_cs) for (var i = 0; i < _cs; i++) {
        for (var j = 0; j < _ps; j++) {
            var name = this._computed[i][0] + '-' + properties[j][0];
            var text = this._computed[i][1] + '-' + properties[j][1];
            var html = this._computed[i][2] + '<td>' + properties[j][1] + '</td>';
            this._computing.push([name, text, html]);
        }
    }
    else for (var i = 0; i < _ps; i++) {
        this._computing.push([properties[i][0], properties[i][1], '<td>' + properties[i][1] + '</td>']);
    }
    return this._computed = this._computing;
};
SmartSKU.prototype.generateForm = function() {
    this._target.html('');
    this._computed = [];
    for (var opt in this._options) {
        var avaliable = false;
        this._selected_options[opt] = [this._options[opt][0], []];
        for (var prop in this._options[opt][1]) {
            if (this._options[opt][1][prop][1]) {
                this._selected_options[opt][1].push([prop, this._options[opt][1][prop][0]]);
                avaliable = true;
            }
        }
        if (!avaliable) return false;
    }
    var _th = '<thead>';
    var _opts = [];
    for (var opt in this._selected_options) {
        _th += "<th>" + this._selected_options[opt][0] + "</th>";
        _opts.push(opt);
        this._compute(this._selected_options[opt][1]);
    }
    $(_th + "<th>价格</th><th>数量</th></thead>").prependTo(this._target);
    var _cs = this._computed.length;
    for (var i = 0; i < _cs; i++) {
        var _tr = $($('#SKUTemplate').val()).appendTo(this._target);
        var _rid = this._computed[i][0].split('-');
        var _loop = _rid.length;
        for (var j = 0; j < _loop; j++) {
            _rid[j] += ':' + _opts[j];
        }
        _rid = _rid.join('-');
        _tr.find('.sku-price input').attr('name', 'price-' + _rid);
        _tr.find('.sku-capacity input').attr('name', 'capacity-' + _rid);
        _tr.find('.sku-uid input').val(_rid);
        $(this._computed[i][2]).prependTo(_tr);
    }
};
SmartSKU.prototype.setProperty = function(opt, prop, stat, reset) {
    if (!!reset) for (var p in this._options[opt][1]) {
        this._options[opt][1][p][1] = 0;
    }
    this._options[opt][1][prop][1] = stat;
};
SmartSKU.prototype.getSKU = function() {
    if (!!!this._options['PNC']) return false;
    var _names = [];
    var _ss = this._target.children('.ho');
    _ss.each(function() {
        _names.push($(this).attr('id'));
        
    });
    _names = _names.join('-');
    if (!!this._options[_names]) {
        $('#SpecPrice').text(this._options[_names][0]);
   		if(isActDiscount){
   			var thisPrice=parseFloat(this._options[_names][0])*actDiscount;
   			 $('#saleValue').text(thisPrice.toFixed(2));
   		}
        $('#SpecCapacity').text(this._options[_names][1]);
        $('#SelectedPrice').val(this._options[_names][0]);
   		$('#SelectedSku').val(_names);
   		$('#SelectedSkuId').val(this._options[_names][2]);
    }
    else {
        $('#SpecPrice').text(this.getPriceRange());
        if(isActDiscount){
   			 $('#saleValue').text(this.getPriceDiscountRange());
   		}
        $('#SpecCapacity').text(this.getTotalCapacity());
   		$('#SelectedPrice').val(this.getPriceRange());
   		$('#SelectedSku').val('');
   		$('#SelectedSkuId').val('');
    }
    $('#SelectedSKU').val(_names);
    return this._options[_names];
};
SmartSKU.prototype.getPriceRange = function() {
    if (!!!this._options['PNC']) return false;
    if (!!!this._options['upper-limit']) return false;
    if (!!!this._options['lower-limit']) return false;
    if (this._options['upper-limit'] == this._options['lower-limit']) return this._options['lower-limit'];
    return this._options['lower-limit'] + ' - ' + this._options['upper-limit'];
};
SmartSKU.prototype.getPriceDiscountRange = function() {
    if (!!!this._options['PNC']) return false;
    if (!!!this._options['upper-limit']) return false;
    if (!!!this._options['lower-limit']) return false;
   var lowerPrice= parseFloat(this._options['lower-limit'])*actDiscount;
   var upperPrice= parseFloat(this._options['upper-limit'])*actDiscount;
    if (this._options['upper-limit'] == this._options['lower-limit']) return lowerPrice.toFixed(2);
    return lowerPrice.toFixed(2) + ' - ' + upperPrice.toFixed(2);
};
SmartSKU.prototype.getTotalCapacity = function() {
    var _tc = 0;
    if (!!!this._options['PNC']) return false;
    for (var name in this._options) {
        if (name == 'PNC') continue;
        if (name == 'upper-limit') continue;
        if (name == 'lower-limit') continue;
        _tc += parseInt(this._options[name][1]);
    }
    return _tc;
};


