/*******************************************************************************
* 滚动新闻
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

(function($) {
		$.fn.scrollNews = function(options) {
		// 默认值
		var defaults = {
			speed: 500,
			duration : 3000
		};
		// 合并参数和默认值
		options = $.extend(defaults, options);
		return this.each(function() {
			var div = $(this),
				ul = div.find('ul');
			// 内容高度小于容器高度时不处理
			if (ul.height() <= div.height()) {
				return;
			}
			// 停止状态
			var stopped = false;
			// 完成一次动画
			// 先把第一条复制后插入到最后，滚动后将第一条删除
			var timeoutId;
			function start(ul) {
				if (timeoutId) {
					clearTimeout(timeoutId);
				}
				timeoutId = setTimeout(function() {
					if (!stopped) {
						ul.stop();
						var first = ul.children('li:first');
						ul.animate({
							top : '-=' + first.height() + 'px'
						}, options.speed, function() {
							ul.append(first.clone(true));
							first.remove();
							ul.css('top', 0);
						});
					}
					start(ul);
				}, options.duration);
			}
			// 开始滚动
			start(ul);
			// 鼠标移上去时停止滚动
			div.hover(
				function() {
					stopped = true;
				},
				function() {
					stopped = false;
				}
			);
		});
	};
})(jQuery);
