/*!
 * Tiny Scrollbar 1.65
 * http://www.baijs.nl/tinyscrollbar/
 *
 * Copyright 2010, Maarten Baijs
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.opensource.org/licenses/gpl-2.0.php
 *
 * Date: 10 / 05 / 2011
 * Depends on library: jQuery
 *
 * changelog:
 * 2011-10-27: add setPos method
 * 2011-10-27: add options: viewport, overview, scrollbar, track, thumb
 * 2011-10-27: add $.fn.switchableScrollbar
 */

(function($){
	$.tiny = $.tiny || { };

	$.tiny.scrollbar = {
		options: {
			axis: 'y', // vertical or horizontal scrollbar? ( x || y ).
			wheel: 40,  //how many pixels must the mouswheel scroll at a time.
			scroll: true, //enable or disable the mousewheel;
			size: 'auto', //set the size of the scrollbar to auto or a fixed number.
			sizethumb: 'auto' //set the size of the thumb to auto or a fixed number.
		}
	};

	$.fn.tinyscrollbar = function(options) {
		var options = $.extend({}, $.tiny.scrollbar.options, options);
		this.each(function(){ $(this).data('tsb', new Scrollbar($(this), options)); });
		return this;
	};
	$.fn.tinyscrollbar_update = function(sScroll) { return $(this).data('tsb').update(sScroll); };
	$.fn.tinyscrollbar_pos = function(ratio) { return $(this).data('tsb').setPos(ratio); };

	function Scrollbar(root, options){
		var oSelf = this;
		var oWrapper = root;
		var oViewport = { obj: options.viewport || $('.viewport', root) };
		var oContent = { obj: options.overview || $('.overview', root) };
		var oScrollbar = { obj: options.scrollbar || $('.scrollbar', root) };
		var oTrack = { obj: options.track || $('.track', oScrollbar.obj) };
		var oThumb = { obj: options.thumb || $('.thumb', oScrollbar.obj) };
		var sAxis = options.axis == 'x', sDirection = sAxis ? 'left' : 'top', sSize = sAxis ? 'Width' : 'Height';
		var iScroll, iPosition = { start: 0, now: 0 }, iMouse = {};

		function initialize() {
			oSelf.update();
			setEvents();
			return oSelf;
		}
		// example: $(this).tinyscrollbar_pos(0.6);
		this.setPos = function(ratio) {
			var trackSize = oTrack.obj[0]['offset'+ sSize],
				thumbSize = oThumb.obj[0]['offset'+ sSize],
				offset = Math.round(trackSize * ratio);
			if (offset + thumbSize > trackSize) {
				offset = trackSize - thumbSize;
			} else if (offset > Math.round(thumbSize / 2)) {
				offset -= Math.round(thumbSize / 2);
			}
			oThumb.obj.css(sDirection, offset + 'px');
			return oSelf;
		};
		this.getPos = function() {
			var trackSize = oTrack.obj[0]['offset'+ sSize],
				thumbSize = oThumb.obj[0]['offset'+ sSize],
				offset = parseInt(oThumb.obj.css(sDirection), 10);
			return (offset + thumbSize / 2) / trackSize;
		};
		this.update = function(sScroll){
			oViewport[options.axis] = oViewport.obj[0]['offset'+ sSize];
			oContent[options.axis] = oContent.obj[0]['scroll'+ sSize];
			oContent.ratio = oViewport[options.axis] / oContent[options.axis];
			oScrollbar.obj.toggleClass('disable', oContent.ratio >= 1);
			oTrack[options.axis] = options.size == 'auto' ? oViewport[options.axis] : options.size;
			oThumb[options.axis] = Math.min(oTrack[options.axis], Math.max(0, ( options.sizethumb == 'auto' ? (oTrack[options.axis] * oContent.ratio) : options.sizethumb )));
			oScrollbar.ratio = options.sizethumb == 'auto' ? (oContent[options.axis] / oTrack[options.axis]) : (oContent[options.axis] - oViewport[options.axis]) / (oTrack[options.axis] - oThumb[options.axis]);
			iScroll = (sScroll == 'relative' && oContent.ratio <= 1) ? Math.min((oContent[options.axis] - oViewport[options.axis]), Math.max(0, iScroll)) : 0;
			iScroll = (sScroll == 'bottom' && oContent.ratio <= 1) ? (oContent[options.axis] - oViewport[options.axis]) : isNaN(parseInt(sScroll)) ? iScroll : parseInt(sScroll);
			setSize();
		};
		function setSize(){
			oThumb.obj.css(sDirection, iScroll / oScrollbar.ratio);
			oContent.obj.css(sDirection, -iScroll);
			iMouse['start'] = oThumb.obj.offset()[sDirection];
			var sCssSize = sSize.toLowerCase();
			oScrollbar.obj.css(sCssSize, oTrack[options.axis]);
			oTrack.obj.css(sCssSize, oTrack[options.axis]);
			oThumb.obj.css(sCssSize, oThumb[options.axis]);
		};
		function setEvents(){
			oThumb.obj.bind('mousedown', start);
			oThumb.obj[0].ontouchstart = function(oEvent){
				oEvent.preventDefault();
				oThumb.obj.unbind('mousedown');
				start(oEvent.touches[0]);
				return false;
			};
			oTrack.obj.bind('mouseup', drag);
			if(options.scroll && this.addEventListener){
				oWrapper[0].addEventListener('DOMMouseScroll', wheel, false);
				oWrapper[0].addEventListener('mousewheel', wheel, false );
			}
			else if(options.scroll){oWrapper[0].onmousewheel = wheel;}
		};
		function start(oEvent){
			iMouse.start = sAxis ? oEvent.pageX : oEvent.pageY;
			var oThumbDir = parseInt(oThumb.obj.css(sDirection));
			iPosition.start = oThumbDir == 'auto' ? 0 : oThumbDir;
			$(document).bind('mousemove', drag);
			document.ontouchmove = function(oEvent){
				$(document).unbind('mousemove');
				drag(oEvent.touches[0]);
			};
			$(document).bind('mouseup', end);
			oThumb.obj.bind('mouseup', end);
			oThumb.obj[0].ontouchend = document.ontouchend = function(oEvent){
				$(document).unbind('mouseup');
				oThumb.obj.unbind('mouseup');
				end(oEvent.touches[0]);
			};
			return false;
		};
		function wheel(oEvent){
			if(!(oContent.ratio >= 1)){
				oEvent = $.event.fix(oEvent || window.event);
				var iDelta = oEvent.wheelDelta ? oEvent.wheelDelta/120 : -oEvent.detail/3;
				iScroll -= iDelta * options.wheel;
				iScroll = Math.min((oContent[options.axis] - oViewport[options.axis]), Math.max(0, iScroll));
				oThumb.obj.css(sDirection, iScroll / oScrollbar.ratio);
				oContent.obj.css(sDirection, -iScroll);
				oEvent.preventDefault();
			};
		};
		function end(oEvent){
			$(document).unbind('mousemove', drag);
			$(document).unbind('mouseup', end);
			oThumb.obj.unbind('mouseup', end);
			document.ontouchmove = oThumb.obj[0].ontouchend = document.ontouchend = null;
			if (options.onChange) {
				options.onChange.call(oSelf);
			}
			return false;
		};
		function drag(oEvent){
			if(!(oContent.ratio >= 1)){
				iPosition.now = Math.min((oTrack[options.axis] - oThumb[options.axis]), Math.max(0, (iPosition.start + ((sAxis ? oEvent.pageX : oEvent.pageY) - iMouse.start))));
				iScroll = iPosition.now * oScrollbar.ratio;
				oContent.obj.css(sDirection, -iScroll);
				oThumb.obj.css(sDirection, iPosition.now);
				if (options.onChange) {
					options.onChange.call(oSelf);
				}
			}
			return false;
		};

		return initialize();
	};

	//和jquery.switchable.js联动，只支持第一个元素
	$.fn.switchableScrollbar = function(options){
		// 默认值
		var defaults = {
			// 每次切换的 Panel 数量
			steps: 1,
			// 可见区域的 Panel 数量
			visible: 1,
			// 自动播放
			autoplay: true,
			// 自动播放间隔
			interval: 3, // 3000ms
			speed : .4
		};
		// 合并参数和默认值
		options = $.extend(defaults, options);

		var trigger = $(this),
			scrollbarWrapper = options.scrollbarWrapper, // tinyscrollbar的root
			viewport = options.viewport, // tinyscrollbar的viewport
			overview = options.overview, // tinyscrollbar的overview
			container = options.container, // switchable的container
			scrollbar = options.scrollbar, // tinyscrollbar的scrollbar
			track = options.track, // tinyscrollbar的track
			thumb = options.thumb, // tinyscrollbar的thumb
			sizethumb = options.sizethumb, // tinyscrollbar的sizethumb
			viewportWidth = viewport.width(),
			containerWidth = container.width() + parseInt(container.css('margin-left')) + parseInt(container.css('margin-right')),
			panelCount = Math.ceil(container.length / options.steps);

		if (panelCount < 1) {
			return this;
		}

		trigger.switchable(container, {
			effect : 'scroll',
			speed : options.speed,
			steps : options.steps,
			visible : options.visible,
			onSwitch : function(e, i) {
				scrollbarWrapper.tinyscrollbar_pos(i / (panelCount - 1));
			}
		})
		//.autoplay({
		//	autoplay: options.autoplay,
		//	interval: options.interval
		//})
		.carousel();

		var switchable = trigger.switchable();
		overview.css('width', (containerWidth * container.length + 1000) + 'px');
		scrollbarWrapper.tinyscrollbar({
			axis : 'x',
			scroll : false,
			size : options.size || viewportWidth,
			sizethumb : sizethumb,
			viewport : viewport,
			overview : overview,
			scrollbar : scrollbar,
			track : track,
			thumb : thumb,
			onChange : function() {
				var index = Math.floor(this.getPos() * (panelCount - 1));
				switchable.setIndex(index);
			}
		});
		return this;
	};

})(jQuery);