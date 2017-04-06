/*******************************************************************************
* textarea selection/range 操作
* Copyright (C) 2006-2011 pinju.com
*
* @author Roddy <luolonghao@gmail.com>
* @version 1.0
*******************************************************************************/

(function($) {
	function TextRange(textarea) {
		this.textarea = $(textarea)[0];
	};
	// 选中指定位置的文本
	TextRange.prototype.setSelectionRange = function(start, end) {
		var textarea = this.textarea;
		if (textarea.createTextRange) {
			var range = textarea.createTextRange();
			range.collapse(true);
			range.moveEnd('character', end);
			range.moveStart('character', start);
			range.select();
			return;
		}
		textarea.setSelectionRange(start, end);
		textarea.focus();
	};
	// 取得开始位置
	TextRange.prototype.getStartEnd = function() {
		var start = 0, end = 0;
		var textarea = this.textarea,
			content = textarea.value;
		if (textarea.createTextRange) {
			var range = document.selection.createRange();
			if (range.parentElement() == textarea) {
				var textareaRange = document.body.createTextRange();
				function startEnd(how) {
					var start = 0;
					textareaRange.moveToElementText(textarea);
					for (start = 0; textareaRange.compareEndPoints(how, range) < 0; start++){
						textareaRange.moveStart('character', 1);
					}
					for (var i = 0; i <= start; i++) {
						if (content.charAt(i) == '\n') {
							start++;
						}
					}
					return start;
				}
				start = startEnd('StartToStart');
				end = startEnd('StartToEnd');
			}
		} else {
			start = textarea.selectionStart;
			end = textarea.selectionEnd;
		}
		return {
			start : start,
			end : end
		};
	};
	// 插入文本
	TextRange.prototype.insertText = function(text, isSelect) {
		isSelect = typeof isSelect == 'undefined' ? false : isSelect;
		var textarea = this.textarea,
			content = textarea.value;
		textarea.focus();
		pos = this.getStartEnd();
		var left = content.substr(0, pos.start),
			right = content.substr(pos.end, content.length - 1);
		textarea.value = left + text + right;
		function getOffset(str) {
			if (textarea.createTextRange) {
				return str.replace(/\n/g, '').length;
			}
			return str.length;
		}
		var start = getOffset(left), end = getOffset(left + text);
		if (isSelect) {
			this.setSelectionRange(start, end);
		} else {
			this.setSelectionRange(end, end);
		}
	};
	$.textrange = function(textarea) {
		return new TextRange(textarea);
	};
})(jQuery);
