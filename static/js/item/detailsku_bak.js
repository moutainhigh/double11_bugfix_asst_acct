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
function SmartSKU(sel_target, options) {
    var _this = this;
    this._target = $(sel_target);
    this._options = options;
    this._selected_options = {};
    if (sel_target == '.sku-properties') {
        var _st = sel_target
        var _ss = this._target.find('li');
        _ss.hover(function() {
            $(this).addClass('pj-hovered');
        }, function() {
            $(this).removeClass('pj-hovered');
        }).click(function() {
            $(this).closest(_st).find('li').removeClass('pj-selected');
            $(this).addClass('pj-selected');
            _this.getSKU();
        });
    }
}

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
}
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
}
SmartSKU.prototype.setProperty = function(opt, prop, stat, reset) {
    if (!!reset) for (var p in this._options[opt][1]) {
        this._options[opt][1][p][1] = 0;
    }
    this._options[opt][1][prop][1] = stat;
}
SmartSKU.prototype.getSKU = function() {
    if (!!!this._options['PNC']) return false;
    var _names = [];
    this._target.find('.pj-selected').each(function() {
        _names.push($(this).attr('id'));
    });
    _names = _names.join('-');
    if (!!this._options[_names]) {
        $('#SpecPrice').text(this._options[_names][0]);
        $('#SpecCapacity').text(this._options[_names][1]);
        $('#SelectedPrice').val(this._options[_names][0]);
   		$('#SelectedSku').val(_names);
   		$('#SelectedSkuId').val(this._options[_names][2]);
    }
    else {
        $('#SpecPrice').text(this.getPriceRange());
        $('#SpecCapacity').text(this.getTotalCapacity());
   		$('#SelectedPrice').val(this.getPriceRange());
   		$('#SelectedSku').val('');
   		$('#SelectedSkuId').val('');
    }
    $('#SelectedSKU').val(_names);
    return this._options[_names];
}
SmartSKU.prototype.getPriceRange = function() {
    if (!!!this._options['PNC']) return false;
    if (!!!this._options['upper-limit']) return false;
    if (!!!this._options['lower-limit']) return false;
    if (this._options['upper-limit'] == this._options['lower-limit']) return this._options['lower-limit'];
    return this._options['lower-limit'] + ' - ' + this._options['upper-limit'];
}
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
}


