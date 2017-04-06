
$(function() {
	var msg = '多个标签词之间请用空格分开';
	var defaultMsg = '一次最多可以提交5个标签，每个标签的字符必须在1-14个字符范围内';
	var inputBox = $('#addTagInput'),
		taglistshow = $('#taglistshow'),
		nolabelrow = $('#J_nolabelrow'),
		wordDataBox = $('#J_wordData');

	function escape(val) {
		return val.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
	}
	// 显示错误
	function showError(str) {
		nolabelrow.css('color', '#be0000');
		inputBox.css('border', '1px solid #be0000');
		inputBox.focus();
		nolabelrow.text(str);
	}
	// 取得input hidden的值
	function getWordDataList() {
		var val = $.trim(wordDataBox.val());
		return val === '' ? [] : val.split(/\s+/);
	}
	// 添加标签到input hidden
	function addWordData(word) {
		var wordList = getWordDataList();
		// 已经存在时不添加
		for (var i = 0, len = wordList.length; i < len; i++) {
			if ($.trim(word) === wordList[i]) {
				return;
			}
		}
		wordList.push(word);
		wordDataBox.val(wordList.join(' '));
	}
	// 删除一个input hidden里的值
	function removeWordData(word) {
		var wordList = getWordDataList();
		var newList = [];
		for (var i = 0, len = wordList.length; i < len; i++) {
			if ($.trim(word) !== wordList[i]) {
				newList.push(wordList[i]);
			}
		}
		wordDataBox.val(newList.join(' '));
	}
	// 根据input hidden值，创建DOM
	function drawLabels() {
		var wordList = getWordDataList();
		taglistshow.empty();
		if (wordList.length > 0) {
			for (var i = 0, len = wordList.length; i < len; i++) {
				var word = wordList[i];
				var li = $('<li data-value="' + word + '"><a href="javascript:;">' + word + '</a> <a href="javascript:;" class="icon-del" title="删除标签"></a></li>');
				li.appendTo(taglistshow);
				var delBtn = li.find('.icon-del');
				if (word == '品聚') {
					delBtn.remove();
				} else {
					delBtn.click(function(e) {
						e.preventDefault();
						var el = $(this).closest('li');
						removeWordData(escape(el.attr('data-value')));
						el.remove();
					});
				}
			}
			taglistshow.find('li').removeClass('first').eq(0).addClass('first');
		}
	}

	drawLabels();

	// placeholder
	inputBox.focus(function() {
		if($(this).val() == msg){
			$(this).val('');
		}
		$(this).css('color', '#666');
	}).blur(function() {
		if ($(this).val() == '') {
			$(this).val(msg);
			$(this).css('color', '');
		}
	});
	if(inputBox.val() != msg){
		inputBox.css('color', '#666');
	}
	// 添加标签
	$("#addTag").click(function(e) {
		e.preventDefault();
		var currentVal = $.trim(inputBox.val());
		// 内容为空
		if (currentVal === '' || currentVal == msg){
			showError('请输入标签');
			return;
		}
		var taglistArr = currentVal.split(/\s+/);
		// 一次最多可以提交5个标签
		if (taglistArr.length > 5) {
			showError('一次最多可以提交5个标签');
			return;
		}
		// 每个标签的字符必须在1-14个字符范围内
		for (var i = 0, len = taglistArr.length; i < len; i++) {
			if (taglistArr[i].length > 14) {
				showError('每个标签的字符必须在1-14个字符范围内');
				return;
			}
		}
		if (taglistshow.find('li').length + taglistArr.length > 10){
			showError('最多只能添加10个店铺标签');
			return;
		}
		nolabelrow.text(defaultMsg);
		inputBox.css('border', '1px solid #CCC');
		nolabelrow.css('color', '');
		for (var i = 0, len = taglistArr.length; i < len; i++) {
			addWordData(escape(taglistArr[i]));
		}
		drawLabels();
	});
});
