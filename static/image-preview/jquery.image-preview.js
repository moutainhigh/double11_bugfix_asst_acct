/*******************************************************************************
* 预览本地图片
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

// Note: 支持IE6-8，Firefox，Chrome，Opera，IE9显示【图片已上传】提示

(function($) {
	var basePath = (function() {
		var els = document.getElementsByTagName('script'), src;
		for (var i = 0, len = els.length; i < len; i++) {
			src = els[i].src || '';
			if (/jquery\.image-preview\.js/.test(src)) {
				return src.substring(0, src.lastIndexOf('/') + 1);
			}
		}
		return '';
	})();
	$.imagePreview = function(options) {
		// 默认值
		var defaults = {
			file : null,
			img : null,
			maxWidth : 0,
			maxHeight : 0
		};
		// 合并参数和默认值
		options = $.extend(defaults, options);

		var file = $(options.file)[0],
			img = $(options.img)[0],
			preloadImg = $('<img style="position:absolute;top:-9999px;left:-9999px;visibility:hidden;" />').appendTo(document.body)[0],
			blankSrc = $.browser.msie && $.browser.version <= 7 ?
				basePath + 'images/blank.gif' :
				'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		// 显示图片
		function showImg(src, width, height) {
			var ratio = Math.min(1,
				Math.max(0, options.maxWidth) / width || 1,
				Math.max(0, options.maxHeight) / height || 1
			);
			img.style.width = Math.round(width * ratio) + 'px';
			img.style.height = Math.round(height * ratio) + 'px';
			img.src = src;
		}
		// 计算图片大小，并显示图片
		function preload(src) {
			if ($.browser.msie && $.browser.version >= 7 && $.browser.version <= 8) {
				preloadImg.src = blankSrc;
				preloadImg.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + src + "\")";
				showImg(blankSrc, preloadImg.offsetWidth, preloadImg.offsetHeight);
				img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + src + "\")";
			} else {
				preloadImg.onload = function() {
					showImg(src, this.width, this.height);
				};
				preloadImg.src = src;
			}
		}
		// 取得图片数据
		function getImgSrc(fn) {
			if ($.browser.msie) {
				if ($.browser.version <= 6) {
					fn(file.value);
					return;
				} else if ($.browser.version <= 8) {
					var src = '';
					file.select();
					try {
						src = document.selection.createRange().text;
					} finally {
						document.selection.empty();
					}
					src = src.replace(/[)'"%]/g, function(s){ return escape(escape(s)); });
					fn(src);
					return;
				}
			}
			if ($.browser.mozilla) {
				var oFile = file.files[0];
				if (oFile.getAsDataURL) {
					fn(oFile.getAsDataURL());
					return;
				}
			}
			try {
				var oFile = file.files[0];
				var oFReader = new FileReader();
				oFReader.onload = function (oFREvent) {
					fn(oFREvent.target.result);
				};
				oFReader.readAsDataURL(oFile);
			} catch(e) {
				fn(basePath + 'images/upload-ok.png');
			}
		}
		// 预览
		function previewHandler() {
			getImgSrc(function(src) {
				preload(src);
			});
		}
		file.onchange = previewHandler;
	};
})(jQuery);
