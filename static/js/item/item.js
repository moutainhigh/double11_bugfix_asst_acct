function initItem() {

	$("#txtItemPrice").numeralDecimal( {
		decimalPlace : 2,
		maxLength : 10
	});
	$("#txtMailCosts").numeralDecimal( {
		decimalPlace : 2,
		maxLength : 10
	});
	$("#txtDeliveryCosts").numeralDecimal( {
		decimalPlace : 2,
		maxLength : 10
	});
	$("#txtEmsCosts").numeralDecimal( {
		decimalPlace : 2,
		maxLength : 10
	});
	$("#txtItemNumber").numeral( {
		maxLength : 8
	});

	$("#txtFreight").numeral( {
		decimalPlace : 2,
		maxLength : 8
	});
	$(".sku-price").live("focus", function(){
		$(this).numeralDecimal({
			decimalPlace : 2,
			maxLength : 10
		});
	});
	
	$(".sku-capacity").live("focus", function(){
		$(this).numeral({
//			decimalPlace : 2,
			maxLength : 10
		});
	}).live('blur keyup', function() {
		var sum = 0;
		$(".sku-capacity").each(function() {
			var val = parseInt(this.value, 10);
			if (val) {
				sum += val;
			}
		});
		$('#txtItemNumber').val(sum);
	});
	$("#selectStartDate").addItemReleasedDate( {
		radioName : "itemInput.releasedType",
		yearName : "itemInput.releasedYear",
		hourName : "itemInput.releasedHour",
		minuteName : "itemInput.releasedMinute"
	});
	
	if($("#currProvinces")){
		$("#provcity").selectprov( {
			provBuildName : "itemInput.provinces",
			cityBuildName : "itemInput.city",
			currProv : $("#currProvinces").val(),
			currCity : $("#currCity").val()
		})
		
	}else{
		$("#provcity").selectprov( {
			provBuildName : "itemInput.provinces",
			cityBuildName : "itemInput.city"
		})
	}

	// 不允许提交按钮
	function preventSubmit() {
		$('#released').prop('disabled', true);
	}

	// 允许提交
	function allowSubmit() {
		$('#released').prop('disabled', false);
	}
	// 显示加载中
	function showLoading(i) {
		var picImg = $("#picImg" + i);
		var preview = $('#picImgLi' + i).find('.preview');
		var loading = $('<span class="loading-area"><img src="http://static.pinju.com/img/page-loading.gif" style="width:15px;height:15px;" />上传中 ...</span>');
		picImg.hide();
		preview.append(loading);
	}
	// 隐藏加载中
	function hideLoading(i) {
		var picImg = $("#picImg" + i);
		var preview = $('#picImgLi' + i).find('.preview');
		preview.find('.loading-area').remove();
		picImg.show();
	}
	// 商品图片
	function uploadItemPic(i) {
		var picFile = $("#picFile" + i);
		var picImg = $("#picImg" + i);
		var picImgLi = $('#picImgLi' + i);
		var itemEditPicUrl = $('#itemEditPicUrl' + i);
		picFile.change(function() {
			showLoading(i);
			preventSubmit();
			var ppath = picImg.attr("src");
			var form = $('<form action="/image/uploadImage.htm?size=1024&type=1&dir=image" enctype="multipart/form-data" method="post"></form>');
			picFile.before(form);
			form.append(picFile);
			var preloadImg = $('<img style="position:absolute;top:-9999px;left:-9999px;visibility:hidden;" />').appendTo(document.body)[0];
			form.ajaxSubmit({
				dataType : 'text',
				target : '#output',
				success : function(str){
					// str = '<div class="content">\n<h3>\n很抱歉，您上传的文件超过最大限制3M！\n</h3>\n<ul>\n<li>\n 返回 <a href="http://www.pinju.com" title="品聚首页">品聚首页</a></li>\n</ul>\n</div>';
					hideLoading(i);
					allowSubmit();
					form[0].reset();
					form.after(picFile);
					form.remove();
					var data;
					try {
						data = $.parseJSON(str);
					} catch (e) {
						var msg, match;
						if ((match = /<h3>([\s\S]+?)<\/h3>/.exec(str))) {
							alert($.trim(match[1]));
							return;
						}
						alert('图片上传失败。');
					}
					if (data) {
						if (data.error == 1) {
							picImg.attr("src", ppath);
							if (/pic-none\.jpg$/.test(ppath)) {
								picImgLi.removeAttr('class');
								if(i==5){
									picImgLi.addClass('last');
								}else{
									picImg.addClass('pic-none').css('width', '100px').css('height', '100px')
								}
							}
							alert(data.message);
							return;
						}
						var thumbUrl = data.url + '_80x80.jpg';
						preloadImg.onload = function() {
							itemEditPicUrl.val(data.url);
							picImg.attr('src', thumbUrl).css({
								width : this.width,
								height : this.height
							});
							picImgLi.addClass('hasimg');
						};
						preloadImg.src = thumbUrl;
					}
				}
			});
		});
	}

	for (var i = 1; i <= 5; i++) {
		uploadItemPic(i);
	}

	// 添加图片
	function addPic(url, thumbUrl, width, height) {
		var error = 0, i;
		$('#picImg1,#picImg2,#picImg3,#picImg4,#picImg5').each(function() {
			if (this.src == thumbUrl) {
				error = 1;
				return false;
			}
			if (/pic-none\.jpg$/.test(this.src)) {
				i = /(\d+)$/.exec(this.id)[1];
				return false;
			}
		});
		if (error === 1) {
			alert('相同图片不能重复添加。');
			return;
		}
		if (!i) {
			alert('最多只能选择5张图片。');
			return;
		}
		$('#itemEditPicUrl' + i).val(url);
		$('#picFile' + i).val('');
		$('#picImg' + i).attr('src', thumbUrl).css({
			width : width,
			height : height
		});
		$('#picImgLi' + i).addClass('hasimg');
	}
	// 从图片空间选择
	$('#J_selectImageFromSpace').click(function(e) {
		e.preventDefault();
		var preloadImg = $('<img style="position:absolute;top:-9999px;left:-9999px;visibility:hidden;" />').appendTo(document.body)[0];
		var editor = KindEditor.editor({
			fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
			categoryJson : '/images/getImagesCategoryJsonActon.htm',
			allowFileManager : true
		});
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				clickFn : function(url) {
					var thumbUrl = url + '_80x80.jpg';
					preloadImg.onload = function() {
						addPic(url, thumbUrl, this.width, this.height);
					};
					preloadImg.src = thumbUrl;
					editor.hideDialog();
				}
			});
		});
	});
}

function validateItemInput(update) {
	//点发布按钮，防止重复提交
	document.getElementById("released").disabled="disabled";
	$.showFullLoading({
		description : '商品发布中，请稍候...'
	});
	$("#itemMsg").html("");
	var msg = [];

	var flag = true;
	var iy = 0;
	iy = $("#radioItemType1").attr("checked") ? "1" : iy;
	iy = $("#radioItemType2").attr("checked") ? "2" : iy;
	iy = $("#radioItemType3").attr("checked") ? "3" : iy;
	if (iy == 0) {
		msg.push("请选择商品类型。");
	}
	var catetoryId = $.trim($("#catetoryId").val());
	if (!catetoryId || catetoryId == "" || catetoryId == -1) {
		msg.push("请选择商品类目。");
	}
	var title = $.trim($("#txtItemTitle").val());
	if (!title || title == "") {
		msg.push("请输入商品标题。");
	} else {
		//title.replace(/[^\x00-\xff]/g, "**").length
//		var length = title.length;
//		for(var i = 0;i < title.length;i++) {
//      if(title.charCodeAt(i)>=0x4E00&&title.charCodeAt(i)<=0x9FA5)   { 
//      	length = length +1;
//    	}  
//    } 
    var titlelength = 0;
		for (var i = 0 ; i < title.length; i++) {
		  titlelength += (title.charCodeAt(i) < 0xff) ? 1 : 2;   
		}
		if (titlelength > 60) {
			msg.push("商品标题字数保持在30个汉字以内。");
		}
	}
	
	EditorObject.sync(); //ff
	var description = $("#txaItemDiscription").val();
	if (!description || description == "") {
		msg.push("请输入商品描述。");
	}else{
		if(description.length > 40000){
			msg.push("商品描述长度不符。");
		}
	}

	if ($("#freightRadio2").attr("checked")) {
		if (!$("#CostsRadio").attr("checked") && $("#TransportHidden").val()==0) {
			msg.push("请选择一个运费模式。");
		} else {
			if ($("#CostsRadio").attr("checked")) {
				var mailCosts = Number($.trim($("#txtMailCosts").val()));
				var deliveryCosts = Number($.trim($("#txtDeliveryCosts").val()));
				var emsCosts = Number($.trim($("#txtEmsCosts").val()));

				if (isNaN(mailCosts) || isNaN(deliveryCosts) || isNaN(emsCosts)) {
					msg.push("请输入正确的运费。");
				} else {
					if(mailCosts <= 0){
						msg.push("请输入有效的平邮价格。");
					}
					if(deliveryCosts <= 0){
						msg.push("请输入有效的快递价格。");
					}
//					if (mailCosts <= 0 && deliveryCosts <= 0 && emsCosts <= 0) {
//						msg.push("请输入有效的运费。");
//					}
				}
			}

			//运费模板验证
			if ($("#TransportRadio").attr("checked") && $("#TransportHidden").val() == "") {
				msg.push("请选择一个运费模板。");
			}
		}
	}

	var prov = $("#itemInputprovinces").val();
	if (!prov || prov == "") {
		msg.push("请选择商品所在地。");
	}

	var city = $("#itemInputcity").val();
	if (!city || city == "") {
		msg.push("请选择商品所在城市。");
	}

	var attr = {};
	$("#itemAtt").find("tr").each(function() {
		var cid = $(this).attr("cId");
		if (cid && !$(this).hasClass("hide")) {
			var need = $(this).attr("need");
			var mult = $(this).attr("mult");
			var enu = $(this).attr("enu");
			var notEmpty = false;
			var errLength = false;
			if (enu == 2) {
				var text = {}, num = 0;
				$(this).find("input[type='text']").each(function() {
//					var v = stripscript($(this).val());
					var v = $(this).val();
					v = $.trim(v);
					if (v != "") {
						text[num] = v;
						num++; 
						notEmpty = true;
					}
					var vlength = 0;
					for (var i = 0 ; i < v.length; i++) {
					  vlength += (v.charCodeAt(i) < 0xff) ? 1 : 2;   
					}
					if (vlength > 250) {
						errLength = true;
					}
//					if (v.replace(/[^\x00-\xff]/g, "**").length > 60) {
//						errLength = true;
//					}
				});
				if (num > 0) {
					attr[cid] = text;
				}
			} else if (enu == 1) {
				if (mult == 1) {
					$(this).find("select").each(function() {
						if ($(this).val() != 0) {
							attr[cid] = {'0' : $(this).val() };
							notEmpty = true;
						}
					});
				} else if (mult == 2) {
					//单选框
			$(this).find("input[type='radio']").each(function() {
				if ($(this).attr("checked") == "checked") {
					attr[cid] = {'0' : $(this).attr("id") };
					notEmpty = true;
				}
			});

		} else if (mult == 3) {
			//多选框
			var check = {}, num = 0;
			$(this).find("input[type='checkbox']").each(function() {
				if ($(this).attr("checked") == "checked") {
					check[num] = $(this).attr("id");
					num++;
					notEmpty = true;
				}
			});
			if (num > 0) {
				attr[cid] = check;
			}

		}
	}
	if (need == 1 && !notEmpty) {
		msg.push("请设置商品属性：" + $(this).attr("name") + "。");
	}
	if (errLength) {
		msg.push("商品属性\"" + $(this).attr("name") + "\"长度不符。");
	}
}
}	);

	var skuAttr = "";
	var skuSellPrice = 0;
	var skuSellCapacity = 0;
	var hasSku = false;
	var minPrice = false;
	var minCapacity = false;

	$(".sku-price").each(function() {
		var name = $(this).attr("name");
		if (name) {
			hasSku = true;
			var v = $(this).val();
			if (isNaN(v) || v == "") {
				v = 0;
			}
			v = Number(v);
			if (!minPrice && v < 0.1) {
				minPrice = true;
			}
			if (skuSellPrice == 0) {
				skuSellPrice = v;
			} else {
				if (v < skuSellPrice) {
					skuSellPrice = Number(v);
				}
			}
		}
	});

	if (minPrice) {
		msg.push("销售属性价格不能小于0.1元。");
	}

	$(".sku-capacity").each(function() {
		var name = $(this).attr("name");
		if (name) {
			var v = $(this).val();
			if (isNaN(v) || v == "") {
				v = 0;
			}
			if (!minCapacity && v < 1) {
				minCapacity = true;
			}
			skuSellCapacity += Number(v);
		}
	});

//	if (minCapacity) {
//		msg.push("销售属性数量不能小于1件。");
//	}

	if (hasSku && skuSellPrice == 0) {
		msg.push("至少输入一个销售属性价格");
	}
//	if (hasSku && !skuSellPrice || skuSellPrice == "" || isNaN(skuSellPrice)) {
//		msg.push("请输入有效的销售属性价格。");
//	}
	if (hasSku && skuSellCapacity == 0) {
		msg.push("至少输入一个销售属性数量");
	}
//	if (hasSku && !skuSellCapacity || skuSellCapacity == "" || isNaN(skuSellCapacity)) {
//		msg.push("请输入有效的销售属性数量。");
//	}
	
	var price = $.trim($("#txtItemPrice").val());
	if (!price || price == "" || isNaN(price) || price == 0) {
		msg.push("请输入有效的商品价格。");
	} else {
		if (price < 0.1) {
			msg.push("商品价格不能小于0.1元。");
		}
		if (skuSellPrice > 0 && (Number(price) < Number(skuSellPrice))) {
			msg.push("商品价格不能低于销售属性表中的最低价。");
		}
	}

	var number = $.trim($("#txtItemNumber").val());
	if (!number || number == "" || isNaN(number)) {
		msg.push("请输入有效的商品数量。");
	} else {
		if (skuSellCapacity > 0 && Number(number) !== Number(skuSellCapacity)) {
			msg.push("商品数量必须和销售属性表中的数量之和一致。");
		}
	}

	//图片验证
	var exts = "jpg|gif|bmp|png";
	var picFileFlag = false;
	for(i=1;i<=5;i++){
		var value = $("#picFile"+i).val();
		var img=new Image();
		img.src= $("#picFile"+i).val();
		if (update) {
			if (value && !RegExp("\.(?:" + exts + ")$$", "i").test(value)) {
				msg.push("商品图片格式不符。");
			}else if(img.fileSize>500*1024){
				msg.push("图片大小不能超过500K。");
			}else{
				picFileFlag = true;
			}
		} else {
			if (!value) {
//				picFileFlag = false;
			} else if (!RegExp("\.(?:" + exts + ")$$", "i").test(value)) {
				msg.push("商品图片格式不符。");
			} else if(img.fileSize>500*1024){
				msg.push("图片大小不能超过500K。");
			} else {
				picFileFlag = true;
			}
		}
	}
	if (!picFileFlag) {
		$('#itemEditPicUrl1,#itemEditPicUrl2,#itemEditPicUrl3,#itemEditPicUrl4,#itemEditPicUrl5').each(function() {
			if (/^http:\/\//.test(this.value)) {
				picFileFlag = true;
				return false;
			}
		});
	}
	if(!picFileFlag)msg.push("请上传商品图片。");
	
	//创建商品属性
	if (attr != null && attr != "") {
		//var a = JsonToStr(attr);
		var a = JSON.stringify(attr);
		$("#propertyValuePair").val(a);
	}
	
	if (title || description ) {
		$.ajax({
		  url: "/itemReleased/validateSensitiveWord.htm",
		  type:"post",
		  data:{"itemInput.title" : title,"itemInput.description" : description},
		  dataType:"json",
		  async:false,
		  success: function(obj){
		    if (obj) {
				if (obj.isBadTitleFlag) {
					msg.push("商品标题存在违规字符请重新填写。");
				}
				if(obj.isBadUrlFlag){
					msg.push("商品描述存在违规字符请重新填写。");
				}
			}
//			console.log("2222");
		  }
		});
		/*$.post("/itemReleased/validateSensitiveWord.htm", {
			"itemInput.title" : title,
			"itemInput.description" : description
		},function(obj) {
			if (obj) {
				if (obj.isBadTitleFlag) {
					msg.push("商品标题存在违规字符请重新填写。");
				}
				if(obj.isBadUrlFlag){
					msg.push("商品描述存在违规字符请重新填写。");
				}
			}
		}, "json").complete(function() {
		});*/
	}
		
	if (msg.length > 0) {
		document.getElementById("released").disabled="";
		$.hideFullLoading();
		window.scrollTo(0, 0);
		$("#itemRelMsg").show();
		for (i in msg) {
			$("#itemMsg").append("<li>" + (Number(i) + 1) + "." + msg[i] + "</li>");
		}
		return false;
	} else {
		$("#itemRelMsg").hide();
		return true;
	} 

}

function changeSPU(selectObj) {

	$("#spuShow").html("");
	$("#spuId").val(0);
	setAttrDisplay();

	var cid = selectObj.attr("cId");
	var vid = selectObj.val();

	if (cid != 0 && vid != 0) {
		$.post("/item/getSpu.htm", {
			cid : cid,
			vid : vid
		}, function(obj) {
			if (obj) {
				if (obj.spuId) {
					$("#spuId").val(obj.spuId);
					selectObj.attr("spuId", obj.spuId);
					if (obj.name) {
						$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("名称：" + obj.name));
					}
					if (obj.marketPrice) {
						$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("市场价：" + obj.marketPrice));
					}
					if (obj.idsList) {
						for (i in obj.idsList) {
							var id = obj.idsList[i].pid;
							if (id && id != "" && id != "undefined") {
								for (j in obj.idsList[i].vids) {
									var li = $("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").attr("cid", id);
									li.append(obj.idsList[i].name + "：" + obj.idsList[i].vids[j]);
									$("#spuShow").append(li);
								}
								setAttrDisplay(id);
							}
						}
					}
					if (obj.memo) {
						$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("描述：" + obj.memo));
					}
				}
			}
		}, "json");
	}
}

function setAttrDisplay(id) {
	$("#itemAtt").find("tr").each(function() {
		if (!id) {
			$(this).removeClass("hide");
		}
		if ($(this).attr("cid") == id) {
			$(this).addClass("hide");
		}
	});
}

function getSonPro(obj, init) {

	var cpid = obj.attr("cid");
	var cpvid = obj.val();

	if (init && cpvid == 0) {
		cpvid = obj.attr("selid");
	}

	var cids = obj.attr("cids");
	if (!cids || cids == "" || cids == "undefined") {
		return;
	}

	if (!cpvid || cpvid == "" || cpvid == "undefined") {
		clearChildHtml(cids);
	} else {
		$.ajax( {
			url : "/category/getSonPro.htm",
			data : {
				cpId : cpid,
				cpvId : cpvid
			},
			success : function(obj) {
				clearChildHtml(cids);
				if (obj) {
					var son = $("#property-" + obj.sonCpId);
					var selId = son.attr("selid");
					for (i in obj.sonCpValueIds) {
						var opt = $("<option></option>").val(i).html(obj.sonCpValueIds[i])
						if (selId && selId == i) {
							opt.attr("selected", "selected");
						}
						son.append(opt);
					}
				}
			},
			async : false,
			type : "post",
			dataType : "json"
		});
	}
	function clearChildHtml(cids) {
		var s = cids.split(",");
		for (i in s) {
			var ojb = $("#property-" + s[i]);
			ojb.html("<option value=\"0\"></option>");
			var cid = ojb.attr("cId");
			var spu = new Spu(cid, 0);
		}
	}

}

function showListTemplate(page) {

	var createUrl = "/logistics/createTemplate.htm";
	var showUrl = "/logistics/editTemplate.htm";

	$.getJSON("/logistics/listTemplateAjax.htm?query.page=" + page, {rand:Math.random()}, function(obj) {
		if (obj) {
			$("#TransportList").html("");
			if (obj.templateList && obj.templateList.length > 0) {
				$("#TransportPage").html(obj.query.page + "/" + obj.query.pages);
				if (obj.query.page > 1) {
					$("#TransportP").unbind("click");
					$("#TransportP").click(function() {
						if(page == 1){
							showListTemplate(page)
						}else{
							showListTemplate(--page)
						}
					});
				}
				if (page < obj.query.pages) {
					$("#TransportN").unbind("click");
					$("#TransportN").click(function() {
						if(page == obj.query.pages){
							showListTemplate(page);
						}else{
							showListTemplate(++page);
						}
					});
				}
				$("#TransportClose").click(function() {
//				$("#TransportClose").unbind("click");
					$("#TransportSelecter").addClass("hide");
				});
				
				var btnValue = "选择运费模板";
				$("#TransportTrigger").unbind("click");
				$("#TransportTrigger").html(btnValue).click(function() {
					if ($("#TransportSelecter").hasClass("hide")) {
						$("#TransportSelecter").removeClass("hide");
					} else {
						$("#TransportSelecter").addClass("hide");
					}
					$("#TransportRadio").attr("checked", "checked");
				});
				for (i in obj.templateList) {
					$("#TransportList").append(
							"<dl><cite>" + "<a href=\"" + showUrl + "?templateId="+ obj.templateList[i].id + "\" target=\"_blank\" >详细</a>&nbsp;"
									+ "<a href=\"javascript:void(0)\" onclick=\"setTemplate(this);\" data-id=\"" + obj.templateList[i].id + "\" data-name=\"" + obj.templateList[i].templateName + "\">使用</a></cite>"
								+ replaceHTMLTag(obj.templateList[i].templateName) + "</dl>");
				}
			} else {
				$("#TransportTrigger").html("创建运费模板").attr("href", createUrl).attr("target", "_blank");
			}
		}
	});

	$("#CostsRadio").click(function() {
		$("#TransportSelecter").addClass("hide");
	});

}

function setTemplate(self) {
	var id = $(self).attr('data-id'), name = $(self).attr('data-name');
	$("#TransportTitle").html(replaceHTMLTag(name));
	$("#TransportHidden").val(id);
	$("#TransportSelecter").addClass("hide");
	$("#TransportTrigger").html("重新选择运费模板");
}

function setCostsRadio() {
	$("#TransportSelecter").addClass("hide");
	$("#CostsRadio").attr("checked", "checked");
	if( $("#TransportHidden").val() != 0){
		$("#TransportTitle").html("请点击");
		$("#TransportHidden").val("0");
		$("#TransportSelecter").addClass("hide");
	}
}

function setDisLength(){
	var count = 0;
	if (typeof EditorObject != 'undefined') {
		count = EditorObject.count();
	}
	if(count > 40000){
		$("#disLength").css("color","red");
	}else{
		$("#disLength").css("color","green");
	}
	$("#disLength").html(count);
}

function clearNoNum(v){
	//先把非数字的都替换掉，除了数字和.
	v.value = v.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	v.value = v.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	v.value = v.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	v.value = v.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

function clearNoNumLong(v){
	//先把非数字的都替换掉，除了数字
	v.value = v.value.replace(/[^\d]/g,"");
//	var re=/^[+|-]{0,1}[1-9][0-9]+$/g;
//	if(!re.test(v.value))
//	v.value="";

}

function checkinput(v,max,min,id){ 
		if(v.value != ""){
			v.value = parseFloat(v.value);
	    if (v.value>parseFloat(max)) { 
	      v.value=max; 
	//      alert("输入值不能大于"+max); 
	    } 
	    if(v.value<parseFloat(min)) { 
	      v.value=min; 
	//      alert("输入值不能小于"+min); 
	    } 
		}
		
}

// 删除图片
function removePic(name) {
	var staticServer = $("#staticServer").val();
	$("#picImg"+name).attr("src",staticServer+"/img/pic-none.jpg").addClass('pic-none').css('width', '100px').css('height', '100px');
	$("#picFile"+name).val("");
	$("#picImgLi"+name).removeClass('hasimg');
	$("#itemEditPicUrl"+name).val("true");
	resetInputFile(name);
}

function resetInputFile(name) {
	var file = $("#picFile" + name);
	var tempForm = document.createElement('form');
	file.before(tempForm);
	$(tempForm).append(file);
	tempForm.reset();
	$(tempForm).after(file);
	$(tempForm).remove();
}