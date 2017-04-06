/*******************************************************************************
* 全屏加载中效果
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

/*
	// 显示
	$.showFullLoading();
	// 隐藏
	$.hideFullLoading();
*/

(function($) {
	var maskDiv, loadingDiv, scrollHandler;
	function getDocElement() {
		return $.boxModel ? document.documentElement : document.body;
	}
	$.showFullLoading = function(options) {
		// 默认值
		var defaults = {
			zIndex : 800818,
			maskClass : 'loading-mask',
			descriptionClass : 'loading-description',
			description : '加载中...'
		};
		// 合并参数和默认值
		options = $.extend(defaults, options);

		var docEl = getDocElement(document),
			docWidth = Math.max(docEl.scrollWidth, docEl.clientWidth),
			docHeight = Math.max(docEl.scrollHeight, docEl.clientHeight);
		// 灰色背景
		maskDiv = $('<div class="' + options.maskClass + '" />').css({
			position : 'absolute',
			top : 0,
			left : 0,
			zIndex : defaults.zIndex - 1,
			width : docWidth,
			height : docHeight
		}).appendTo(document.body);
		// 加载中图片
		loadingDiv = $('<span class="' + options.descriptionClass + '">' + options.description + '</span>').css({
			position : ($.browser.msie && $.browser.version < 7 || !$.boxModel) ? 'absolute' : 'fixed',
			right : 0,
			bottom : 0,
			visibility : 'hidden',
			zIndex : defaults.zIndex
		}).appendTo(document.body);
		var x = Math.round((docEl.clientWidth - loadingDiv.width()) / 2),
			y = Math.round((docEl.clientHeight - loadingDiv.height()) / 2);
		loadingDiv.css({
			right : x + 'px',
			bottom : y + 'px',
			visibility : 'visible'
		});
		function scrollHandler() {
			loadingDiv[0].className = loadingDiv[0].className;
		}
		$(window).bind('scroll', scrollHandler);
	};
	$.hideFullLoading = function() {
		$(window).unbind('scroll', scrollHandler);
		maskDiv.remove();
		loadingDiv.remove();
	};
})(jQuery);
