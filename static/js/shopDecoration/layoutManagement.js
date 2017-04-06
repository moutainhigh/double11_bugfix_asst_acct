var dialog, layout_editor;
$(function() {
	dialog = new Dialog();
	layout_editor = new LayoutEditor(dialog);
});

function promoteBindClick(again) {
	var _a = !!again;
	$(".promote")
			.each(
					function() {
						if (_a && $(this).find('span').text() == '取消推荐')
							return false;
						$(this)
								.click(
										function() {
											var txt = $(this).find('span')
													.text();
											if (txt == "取消推荐") {
												var _t = $(this).closest(
														'.goods');
												var _tid = _t.attr('id');
												_t.attr('id', '');
												$('#' + _tid).find("span")
														.text("推荐");
												$('#' + _tid).find(".verygood")
														.show();
												$('#' + _tid)
														.find(".promoted")
														.removeClass("promoted");
												$('#' + _tid).find("cite")
														.removeClass("ci-gray");
												_t.remove();
												$("#itemNumId")
														.text(
																parseInt($(
																		"#itemNumId")
																		.text()) - 1);
											} else {
												if (txt == '推荐') {
													
													var itemCountHidden = $("#itemCountHidden").val();
													var promotedCount = parseInt($("#itemNumId").text());
													if(itemCountHidden && promotedCount +1 > itemCountHidden){
														alert("对不起,您只能推荐"+itemCountHidden+"个商品!");
														return;
													}
													
													$(this).find("span").text(
															"取消推荐");
													$(this)
															.closest('.goods')
															.clone(true, true)
															.appendTo(
																	$('.list2'))
															.find(".verygood")
															.hide();
													$(this).find("span").text(
															"已推荐");
													$(this).find(".verygood")
															.hide();
													$(this)
															.addClass(
																	"promoted");
													$(this)
															.closest("cite")
															.addClass("ci-gray");
													$("#itemNumId")
															.text(
																	parseInt($(
																			"#itemNumId")
																			.text()) + 1);
												}
											}
										});
					});
}

function setButtonDisabled(currentPage, totalPage) {
	if (currentPage == totalPage) {
		$("#nextButton").attr("disabled", "disabled");
		$("#nextButton").removeClass("button_previous").addClass(
				"button_previous_none");
	} else {
		$("#nextButton").removeAttr("disabled");
		$("#nextButton").removeClass("button_previous_none").addClass(
				"button_previous");
	}

	if (currentPage == 1) {
		$("#prevButton").attr("disabled", "disabled");
		$("#prevButton").removeClass("button_next")
				.addClass("button_next_none");
	} else {
		$("#prevButton").removeAttr("disabled");
		$("#prevButton").removeClass("button_next_none")
				.addClass("button_next");
	}

}

function textnumeral() {

	$("#SlideImageList #myFileSeq").each(function() {
		$(this).numeral( {
			decimalPlace : 0,
			maxLength : 10
		});
	});
}

var ENCODE = [
  {reg: /&/g, rep: '&amp;'},
  {reg: /</g, rep: '&lt;'},
  {reg: />/g, rep: '&gt;'},
  {reg: /"/g, rep: '&quot;'},
  {reg: / /g, rep: '&nbsp;'}
];

function encode(s) {
  if (!s) return null;
  for (var i = 0; i < ENCODE.length; i++) {
    s = s.replace(ENCODE[i]['reg'], ENCODE[i]['rep']);
  }
  return s;
}

$(document).ready(function() {
	//$(function() {
		//	CKEDITOR.replace('CustomCode');
		//});
		// 直接把onclick事件写在了JS中
		var ss = new Switchable('.slide-show');
		
		promoteBindClick();
		
		textnumeral();
		
		
			$("#moduleheightCustomer").numeral( {
				decimalPlace : 0,
				maxLength : 3
			});
			
			$("#itemShowCountCustomer").numeral( {
				decimalPlace : 0,
				maxLength : 2
			});
		
				
		$("#savePromote").bind("click", function() {
			var result = []
			$('.list2 .goods').each(function() {
				result.push($(this).attr('id'));
			});
			var returnValue = result.join(',');
			$("#promoteHidden").val(returnValue);
			$("#savePromoteForm").submit();
		});

		$("#promoteItem").bind("click", function() {
			$("#promoteItemDiv").css("display", "");
			$("#showSettingDiv").css("display", "none");
			
			$("#promoteItem").addClass("count");
			$("#showSetting").removeClass("count");
		});

		$("#showSetting").bind("click", function() {
			$("#promoteItemDiv").css("display", "none");
			$("#showSettingDiv").css("display", "");
			
			$("#showSetting").addClass("count");
			$("#promoteItem").removeClass("count");
		});
		$("#saveShowSetting").bind("click", function() {
			var result = [];
			$('.list2 .goods').each(function() {
				result.push($(this).attr('id'));
			});
			var returnValue = result.join(',');
			$("#promoteHidden").val(returnValue);
			$("#savePromoteForm").submit();

		});
		$("#isShowTitle").bind("click", function() {
			if ($("#isShowTitle").attr("checked") == "checked") {
				$("#isShowTitle").val("1");
			} else {
				$("#isShowTitle").val("0");
			}

		});

		

		$('#nextButton').click(function() {
			$.ajax( {
					// 后台处理程序
					url : "shopDecoration/queryPromoteItemAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						page:$("#nextPage").val(),
						moduleId:$("#moduleId").val(),
						pageId:$("#pageId").val()
					},
					// 回传函数
					success : function callBack(result) {
							var json = eval("(" + result + ")");
							$("#nextPage").val(json.nextPage);
							$("#prevPage").val(json.prevPage);
							$("#currentPageShow").html(json.currentPage+"/"+json.totalPage);
							var promotedIds = "";
							$(".list2 .goods").each(function(){
								promotedIds += $(this).attr("id")+",";
							});
							promotedIds = promotedIds.substring(0,promotedIds.length-1);
							$(".list1 .goods").each(function(){
								$(this).remove();
							});
							var picServer = $("#picServer").val();
							for(var i=0;i<json.ITEMLIST.length;i++){
								if(promotedIds.indexOf(json.ITEMLIST[i].id)!=-1){
									var title = encode(json.ITEMLIST[i].title);
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+picServer+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite class='ci-gray'>" +
									"<a class='promote promoted' href='javascript:void(0)'>" +
									"<img class='verygood' style='display:none' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>已推荐</span></a></cite>" +
									"<li><a href='#'>"+title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}else{
									var title = encode(json.ITEMLIST[i].title);
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+picServer+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite>" +
									"<a class='promote' href='javascript:void(0)'>" +
									"<img class='verygood' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>推荐</span></a></cite>" +
									"<li><a href='#'>"+title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}
							}
							promoteBindClick(true);
							setButtonDisabled(json.currentPage,json.totalPage);
						}

				});
		});
		$('#prevButton').click(function() {
			$.ajax( {
					// 后台处理程序
					url : "shopDecoration/queryPromoteItemAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						page:$("#prevPage").val(),
						moduleId:$("#moduleId").val(),
						pageId:$("#pageId").val()
					},
					// 回传函数
					success : function callBack(result) {
							var json = eval("(" + result + ")");
							$("#nextPage").val(json.nextPage);
							$("#prevPage").val(json.prevPage);
							$("#currentPageShow").html(json.currentPage+"/"+json.totalPage);
							var promotedIds = "";
							$(".list2 .goods").each(function(){
								promotedIds += $(this).attr("id")+",";
							});
							promotedIds = promotedIds.substring(0,promotedIds.length-1);
							$(".list1 .goods").each(function(){
								$(this).remove();
							});
							var picServer = $("#picServer").val();
							for(var i=0;i<json.ITEMLIST.length;i++){
								if(promotedIds.indexOf(json.ITEMLIST[i].id)!=-1){
									var title = encode(json.ITEMLIST[i].title);
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+picServer+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite class='ci-gray'>" +
									"<a class='promote promoted' href='javascript:void(0)'>" +
									"<img class='verygood' style='display:none' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>已推荐</span></a></cite>" +
									"<li><a href='#'>"+title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}else{
									var title = encode(json.ITEMLIST[i].title);
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+picServer+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite>" +
									"<a class='promote' href='javascript:void(0)'>" +
									"<img class='verygood' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>推荐</span></a></cite>" +
									"<li><a href='#'>"+title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}
							}
							promoteBindClick(true);
							setButtonDisabled(json.currentPage,json.totalPage);
						}

				});
		});

	});
	
	
function addCustomer() {
	if ($("#itemShowCount").val() == "-1") {
		$("#itemShowCountCustomer").css("display", "");
	} else {
		$("#itemShowCountCustomer").css("display", "none");
	}
}

function addCustomerHeight() {
	if ($("#moduleheight").val() == "6") {
		$("#moduleheightCustomer").css("display", "");
	} else {
		$("#moduleheightCustomer").css("display", "none");
	}
}

function changeIsShowTitle() {
	if ($("#isShow").attr("checked") == "checked") {
		$("#isShowTitle").val("1");
	} else {
		$("#isShowTitle").val("0");
	}
}

function moveUp(target){
	var _obj = target.closest('.slide-image');
	                if ($('.slide-image').index(_obj) > 0) {
	                    _obj.prev().before(_obj);
	                }
	                else {
	                    alert('已经是第一个页面，无法继续向前移动');
	                    return false;
	                }
	                
}

function moveDown(target){
	var _obj = target.closest('.slide-image');
	                if ($('.slide-image').index(_obj) < $('.slide-image').size() - 1) {
	                    _obj.next().after(_obj);
	                }
	                else {
	                    alert('已经是最后一个页面，无法继续向前移动');
	                    return false;
	                }
}

function bindPictureRotationEditButton(){
	$('.slide-image .move-up').click(function() {
	   moveUp($(this));
	});
	$('.slide-image .move-down').click(function() {
	  moveDown($(this));
	});
	$('.delete-img').click(function() {
			$(this).closest('.slide-image').remove();
	});
}	

function checkPictureRotationSave (){
	window.parent.dialog.close();
}



$(document).ready(
function() {
	bindPictureRotationEditButton();
	dialog.caption('');
	$('#AddImage').click(function() {
	                if ($('.slide-image').size() < 6) {
	                    var _obj = $($('#ImageTemplate').val());
	                     
	                    _obj.find('.move-up').click(function() {
	                        moveUp($(this));
	                    });
	                    _obj.find('.move-down').click(function() {
	                        moveDown($(this));
	                    });
	                    _obj.find('.delete-img').click(function(){
	                    	$(this).closest('.slide-image').remove();
	                    });
	                    $(this).closest('li').before(_obj);
	                    var editor = KindEditor.editor({
							uploadJson : '/image/uploadImage.htm?size=1024&type=2',
							fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
							categoryJson : '/images/getImagesCategoryJsonActon.htm',
							allowFileManager : true
						});
						_obj.find('input.image').click(function() {
							var urlBox = _obj.find('input[name=url]');
							editor.loadPlugin('image', function() {
								editor.plugin.imageDialog({
									imageUrl : urlBox.val(),
									clickFn : function(url, title, width, height, border, align) {
										urlBox.val(url);
										editor.hideDialog();
									}
								});
							});
						});
	                }
	                else {
	                    alert('最多添加6张轮播图片！');
	                }
		textnumeral();
	});
	
	$("#saveImages").bind("click", function() {
		var result = [];
		var urlResult = [];
		var num = []
		var i=0;
		var str = "";
		var f = 0;
		$('.slide-image').each(function() {
				
				var txt = $(this).find(".txt").val();
				var myFile = $(this).find(".url").val();
				var hidden = $(this).find(".imgFileHidden").val();
				var targetUrl = $(this).find(".tagetUrl").val();
				
				if(!myFile && !hidden){
					alert("请上传图片!");
					f = 1;
				}
				
				if (myFile) {
					num.push(i);
					var fileServerLen = $("#fileServer").val().length;
					myFile = myFile.substring(fileServerLen,myFile.length);
					result.push("myfile" + (i+1) + "=" + myFile);
				} else {
					num.push(i);
					result.push("myfile" + (i+1) + "=" + hidden);
				}
				if(targetUrl){
					urlResult.push(targetUrl);
				}else {
					urlResult.push("");
				}
				i = i + 1;
			});
			
		if(f == 1){
			return;
		}
		var str = "";
		var flag = 0;
		$(".lister .txt").each(function (){
			if($(this).val() == ""){
				alert("请输入序号!");
				$(this).focus();
				flag = 1;
				return;
			}
			if(str.indexOf($(this).val())!=-1){
				alert("您输入的序号有重复");
				$(this).focus();
				flag = 1;
				return;
			}
			if($(this).val() == "undefined" || $(this).val() == ""){
				alert("请输入序号");
				$(this).focus();
				flag = 1;
				return;
			}
			str+=$(this).val()+";";
		});
		if(flag == 1){
			return;
		}
		if($("#moduleheight").val() == 6 && $("#moduleheightCustomer").val() > 750){
			alert("您输入的数字过大,请输入小于750的数字!");
			$("#moduleheightCustomer").focus();
			return;
		}
		var returnValue = result.join(',');
		var numValue = num.join(',');
		var urlResultValue = urlResult.join(",");
		$("#imagePaths").val(returnValue);
		$("#num").val(numValue);
		$("#urlResultValue").val(urlResultValue);
		$("#picUpload").submit();
	});
	

});
