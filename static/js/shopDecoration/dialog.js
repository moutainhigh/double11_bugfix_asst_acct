function Dialog() {
    this._self = $('#Dialog');
    this._mask = $('#DialogMask');
    this._layout = $('#DialogLayout');
    this._caption = $('#DialogCaption');
    this._content = $('#DialogContent');
    this._init();
}

Dialog.prototype._init = function() {
    var _this = this;
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
    $('#DialogMask, #DialogMask iframe').css({
        'width': Math.max($(document.body).outerWidth(), $(window).width()),
        'height': Math.max($(document.body).outerHeight(), $(window).height())
    });
    //console.log($(document.body).outerWidth() + ',' + $(document.body).outerHeight() + '|' + $(window).width() + ',' + $(window).height());
};
Dialog.prototype._resizeDialog = function(width, height) {
    var _width = (typeof(width) == 'number' && width >= 310) ? width : 430;
    var _height = (typeof(height) == 'number' && height >= 150) ? height : 270;
    $('#Dialog, #DialogShadow').css({
        'width': _width + 10,
        'height': _height + 10
    });
    this._layout.css({
        'width': _width,
        'height': _height
    });
    this._content.css({
        'width': _width,
        'height': _height - 30
    });
};
Dialog.prototype._locateDialog = function() {
    var _left = ($(window).width() - this._self.width()) * 0.5 + $(window).scrollLeft();
    var _top = ($(window).height() - this._self.height()) * 0.382 + $(window).scrollTop();
    this._self.css({
        'left': _left,
        'top': _top
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
    if (typeof(text) == 'string' && text != '') _caption.text(text);
    return _caption.text();
}
Dialog.prototype.close = function() {
    this._self.hide()
    this._content.attr('src', 'about:blank');
    this._mask.hide();
};
Dialog.prototype.open = function(url, width, height) {
    var _url = (typeof(url) == 'string' && url != '') ? url : 'about:blank';
    this._resizeDialog(width, height);
    this._locateDialog();
    this._resizeMask();
    this._mask.show();
    this._content.attr('src', _url);
    this._self.show();
};
