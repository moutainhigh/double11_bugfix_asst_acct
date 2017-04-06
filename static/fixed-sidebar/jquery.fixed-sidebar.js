/*******************************************************************************
* 滑动页面时，保持在画面同一位置的动态效果
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

/*
	$('.class sidebar').fixedSidebar();
*/

(function($) {
	$.fn.fixedSidebar = function(options) {
		// 默认值
		var defaults = {
			parent : null,
			zIndex : 1
		};
		// 合并参数和默认值
		options = $.extend(defaults, options);

		return this.each(function() {
			var self = $(this);
			var parent = options.parent || self.parent();
			parent.css('position', 'relative');
			var parentTop = parent.offset().top,
				parentHeight = parseInt(parent.height()) + parseInt(parent.css('padding-top')) + parseInt(parent.css('padding-bottom')),
				height = parseInt(self.height()) + parseInt(self.css('padding-top')) + parseInt(self.css('padding-bottom')),
				pos = self.position(),
				top = parseInt(pos.top),
				maxTop = parentHeight - height - 50;
			self.css({
				position : 'absolute',
				top : top + 'px',
				left : parseInt(pos.left) + 'px',
				'z-index' : options.zIndex
			});
			$(window).scroll(function() {
				var newTop = $(document).scrollTop() - parentTop + 10;
				if (newTop < top) {
					newTop = top;
				}
				if (newTop > maxTop) {
					newTop = maxTop;
				}
				self.css('top', newTop + 'px');
			});
		});
	};
})(jQuery);
