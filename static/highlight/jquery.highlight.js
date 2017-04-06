/*******************************************************************************
* 照亮当前照片，其它照片变暗
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

/*
	$('.class img').highlight();
*/

(function($) {
	$.fn.highlight = function(options) {
		var self = this;
		var defaults = {
			zIndex : 1000
		};
		options = $.extend(defaults, options);
		function moveMask(el, mask) {
			el = $(el), mask = $(mask);
			var borderTopWidth = el.css('border-top-width'),
				borderLeftWidth = el.css('border-left-width'),
				offset = el.offset();
			//
			// IE will return values like 'medium' as the default border, 
			// but we need a number
			//
			borderTopWidth = isNaN(parseInt(borderTopWidth)) ? 0 : borderTopWidth;
			borderLeftWidth = isNaN(parseInt(borderLeftWidth)) ? 0 : borderLeftWidth;

			mask.css({
				top : (offset.top + parseInt(borderTopWidth)) + 'px',
				left : (offset.left + parseInt(borderLeftWidth)) + 'px'
			});
		}
		function createMask(el) {
			el = $(el);
			var mask = $('<div />').css({
				position : 'absolute',
				zIndex : options.zIndex,
				width : parseInt(el.width()) + parseInt(el.css('padding-right')) + parseInt(el.css('padding-left')),
				height : parseInt(el.height()) + parseInt(el.css('padding-top')) + parseInt(el.css('padding-bottom')),
				'background-color' : '#000',
				filter : 'alpha(opacity=30)',
				opacity : 0.3
			}).hide().appendTo(document.body);
			moveMask(el, mask);
			return mask;
		}
		var masks = [];
		function addMasks() {
			var timeoutId, hideFlag = true;
			self.each(function(i) {
				masks[i] = createMask(this);
			});
			self.each(function(idx) {
				var me = this, mask = masks[idx], timeoutId;
				mask.hover(function() {
					hideFlag = false;
					clearTimeout(timeoutId);
					$(this).hide();
				}, function() {
					moveMask(me, this);
					$(this).show();
				});
				$(me).hover(function() {
					hideFlag = false;
					clearTimeout(timeoutId);
					self.each(function(i) {
						moveMask(this, masks[i]);
						me == this ? masks[i].hide() : masks[i].show();
					});
				}, function() {
					hideFlag = true;
					timeoutId = setTimeout(function() {
						if (hideFlag) {
							self.each(function(i) {
								masks[i].hide();
							});
						}
					}, 100);
				});
			});
		}
		setTimeout(function() {
			addMasks();
			$(window).resize(function() {
				self.each(function(i) {
					moveMask(this, masks[i]);
				});
			});
		}, 0);
		return self;
	};
})(jQuery);
