;(function($) {
	$.extend({
		"dialog": function(options) {
			var html = {
				pageMaskHtml: '<div class="pageMask"><iframe src="about:blank" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></div>'
			};
			var dialog = $('.dialog');
			var images = $('.images img', dialog);
			var imgLength = images.length;
			var index = 0;
			var pageMask = $(html.pageMaskHtml);
			var wWidth = $(window).width();
			var wHeight = $(window).height();
			$('body').append(pageMask);
			pageMask.width(document.body.clientWidth).height(document.body.clientHeight);
			$('iframe', pageMask).width(document.body.clientWidth).height(document.body.clientHeight);
			dialog.show().css({'left': (wWidth - dialog.outerWidth())/2, 'top': (wHeight - dialog.outerHeight())/2});
			images.show().not(':eq(0)').hide();
			$(window).resize(function() {
				$.resizeDialog();
			});
			$('.close', dialog).click(function() {
				dialog.hide();
				index = 0;
				pageMask.remove();
			});
			$('.next', dialog).click(function() {
				if(index + 1 < imgLength) {
					index++;
					images.hide().eq(index).show();
				}
			});
			$('.prev', dialog).click(function() {
				if(index - 1 >= 0) {
					index--;
					images.hide().eq(index).show();
				}
			});
		},
		"resizeDialog": function() {
			var dialog = $('.dialog');
			var pageMask = $('.pageMask');
			var dWidth = dialog.outerWidth();
			var dHeight = dialog.outerHeight();
			var wWidth = $(window).width();
			var wHeight = $(window).height();
			if(dialog.is(':hidden')) {
				return;
			}
			pageMask.width(document.body.clientWidth).height(document.body.clientHeight);
			$('iframe', pageMask).width(document.body.clientWidth).height(document.body.clientHeight);
			dialog.css({'left': (wWidth - dWidth)/2, 'top': ($(window).height() - dHeight)/2});
		}
	});
})(jQuery);