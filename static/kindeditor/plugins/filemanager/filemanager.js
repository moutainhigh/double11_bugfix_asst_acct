/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

// pinju.com 定制版

KindEditor.plugin('filemanager', function(K) {
	var self = this, name = 'filemanager',
		fileManagerJson = K.undef(self.fileManagerJson, self.basePath + 'php/file_manager_json.php'),
		imgPath = self.pluginsPath + name + '/images/',
		lang = self.lang(name + '.');
	function makeFileTitle(filename, filesize, datetime) {
		return filename + ' (' + Math.ceil(filesize / 1024) + 'KB, ' + datetime + ')';
	}
	function bindTitle(el, data) {
		el.attr('title', makeFileTitle(data.filename, data.filesize, data.datetime));
	}
	self.plugin.filemanagerDialog = function(options) {
		var width = K.undef(options.width, 520),
			height = K.undef(options.height, 510),
			dirName = K.undef(options.dirName, ''),
			viewType = K.undef(options.viewType, 'VIEW').toUpperCase(), // "LIST" or "VIEW"
			clickFn = options.clickFn;
		var html = [
			'<div style="padding:10px 20px;">',
			// header start
			'<div class="ke-plugin-filemanager-header">',
			// left start
			'<div class="ke-left">',
			lang.category + ' <select class="ke-inline-block" name="category">',
			'<option value="">所有图片</option>',
			'</select> ',
			'</div>',
			// right start
			'<div class="ke-right">',
			'<a href="/images/viewImagesUpLoadAction.htm" target="_blank">去图片空间上传图片&gt;&gt;&gt;</a>',
			'</div>',
			'<div class="ke-clearfix"></div>',
			'</div>',
			// body start
			'<div class="ke-plugin-filemanager-body"></div>',
			'</div>'
		].join('');
		var dialog = self.createDialog({
			name : name,
			width : width,
			height : height,
			title : self.lang(name),
			body : html
		}),
		div = dialog.div,
		bodyDiv = K('.ke-plugin-filemanager-body', div),
		moveupImg = K('[name="moveupImg"]', div),
		moveupLink = K('[name="moveupLink"]', div),
		categoryBox = K('[name="category"]', div),
		viewServerBtn = K('[name="viewServer"]', div);
		// 添加去图片空间上传图片链接
		var pageDiv = K('<div style="position:absolute;left:15px;bottom:0px;height:30px;"></div>');
		K('.ke-dialog-footer', div).append(pageDiv);
		function reloadPage(category, offset, limit, func) {
			// 移除图片
			K.each(elList, function() {
				this.unbind();
			});
			bodyDiv.html('');
			dialog.showLoading(self.lang('ajaxLoading'));
			var param = 'storageFileInfoDO.imageCategoryId=' + category + '&storageFileInfoDO.startRow=' + offset + '&storageFileInfoDO.pageCount=' + limit;
			K.ajax(K.addParam(fileManagerJson, param + '&d=' + new Date().getTime()), func, null, null, 'html');
		}
		// 初始化分类
		K.ajax(K.addParam(self.categoryJson, 'd=' + new Date().getTime()), function(data) {
			data = K.json(data.replace(/\\"/g, '"'));
			var i = 1;
			K.each(data, function(j, row) {
				categoryBox[0].options[i++] = new Option(row.firstCategoryName, row.firstCategoryId);
				if (row.secondCategoryList.length > 0) {
					K.each(row.secondCategoryList, function(k, subRow) {
						categoryBox[0].options[i++] = new Option('    |_' + subRow[1], subRow[0]);
					});
				}
			});
		}, null, null, 'html');
		var elList = [], aList = [], currentPageNum = 1, pageCount = 10, listRows = 20;
		// 选择图片分类时
		categoryBox.change(function() {
			currentPageNum = 1;
			reloadPage(this.value, 0, listRows, createView);
		});
		function bindClickEvent(el, data) {
			el.click(function(e) {
				clickFn.call(this, data.file_url, data.filename);
			});
		}
		function createView(result) {
			result = K.json(result.replace(/\\"/g, '"'));
			dialog.hideLoading();
			// 移除分页
			K.each(aList, function() {
				this.unbind();
			});
			pageDiv.html('');
			// 显示图片
			var fileList = result.file_list,
				totalCount = result.total_count;
			for (var i = 0, len = fileList.length; i < len; i++) {
				var data = fileList[i],
					div = K('<div class="ke-inline-block ke-item"></div>');
				bodyDiv.append(div);
				var photoDiv = K('<div class="ke-inline-block ke-photo"></div>').mouseover(function(e) {
						K(this).addClass('ke-on');
					}).mouseout(function(e) {
						K(this).removeClass('ke-on');
					});
				div.append(photoDiv);
				bindTitle(photoDiv, data);
				bindClickEvent(photoDiv, data);
				photoDiv.append('<img src="' + data.file_url + '_80x80.jpg" width="80" height="80" alt="' + data.filename + '" />');
				div.append('<div class="ke-name" title="' + data.filename + '">' + data.filename + '</div>');
				elList.push(photoDiv);
			}
			// 分页条上的开始页数和结束页数
			var startPageNum = currentPageNum - pageCount / 2;
			if (startPageNum < 1) {
				startPageNum = 1;
			}
			var endPageNum = startPageNum + pageCount - 1;
			// 最大页数
			var maxPageNum = Math.ceil(totalCount / listRows);
			if (endPageNum > maxPageNum) {
				endPageNum = maxPageNum;
				startPageNum = endPageNum - pageCount + 1;
				if (startPageNum < 1) {
					startPageNum = 1;
				}
			}
			// 显示分页条
			function bindPageEvent(el, pageNum) {
				var offset = listRows * (pageNum - 1);
				el.click(function(e) {
					e.stop();
					reloadPage(categoryBox.val(), offset, listRows, createView);
					currentPageNum = pageNum;
				});
			}
			for (var pageNum = startPageNum; pageNum <= endPageNum; pageNum++) {
				if (currentPageNum !== pageNum) {
					var a = K('<a href="javascript:;">[' + pageNum + ']</a>');
					bindPageEvent(a, pageNum);
					pageDiv.append(a);
					aList.push(a);
				} else {
					pageDiv.append(K('@[' + pageNum + ']'));
				}
				pageDiv.append(K('@&nbsp;'));
			}
		}
		reloadPage("", 0, listRows, createView);
		return dialog;
	}

});
