

function del(obj){
	  $(obj).parent().remove();
};


function createTr(){
	//创建tr
	tRoot = Doc.createElement('tr');
	Dom.addClass(tRoot, c.isSup ? 'cat-sup': 'cat-sub');
	DocFragment.appendChild(tRoot);

	//创建td-1
	temp = Doc.createElement('td');
	Dom.addClass(temp, 'td-1');
	temp.innerHTML = '<input type="text" id="name-' + c.catId + '" data-id="' + c.catId + '" class="text J_CatName" value="' + c.name + '" maxlength="20" data-condition="' + c.condition + '"/>';
	tRoot.appendChild(temp);
}

function selectAllCate(id){
	//alert($("#"+id).attr("checked"));
	if($("#"+id).attr("checked") == "checked"){
		$('.cateTr').each(function(){
			var cateItemCheck= $(this).find('.cateItemId').attr("checked");
			if(!cateItemCheck){
				$(this).find('.cateItemId').attr("checked","checked");
			}
		});
	}else{
		$('.cateTr').each(function(){
			var cateItemCheck= $(this).find('.cateItemId').attr("checked");
			if(cateItemCheck){
				$(this).find('.cateItemId').attr("checked",false);
			}
		});
	}
	
}

function changeCateList(){
	$.ajax( {
					// 后台处理程序
					url : "shop/queryCategoryGoodsList.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						cateKey:$('#cateList').val()
						//itemIdString:     ,
						//cateString:
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
							for(var i=0;i<json.ITEMLIST.length;i++){
								if(promotedIds.indexOf(json.ITEMLIST[i].id)!=-1){
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite class='ci-gray'>" +
									"<a class='promoted' href='javascript:void(0)'>" +
									"<img class='verygood' style='display:none' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>已推荐</span></a></cite>" +
									"<li><a href='#'>"+json.ITEMLIST[i].title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}else{
									$(".list1").append("<ul class='goods' id='"+json.ITEMLIST[i].id+"'>" +
									"<a href='javascript:void(0)'><img src='"+json.ITEMLIST[i].picUrl+"' class='img'/></a>" +
									"<cite>" +
									"<a class='promote' href='javascript:void(0)'>" +
									"<img class='verygood' src='http://static.pinju.com/images/sc-hander-11.gif'>" +
									"<span>推荐</span></a></cite>" +
									"<li><a href='#'>"+json.ITEMLIST[i].title+"</a>" +
									"<cite>收藏人气：1212</cite><strong>￥"+json.ITEMLIST[i].priceByYuan+"</strong>" +
									"</li>" +
									"</ul>");
								}
							}
							promoteBindClick();
							setButtonDisabled(json.currentPage,json.totalPage);
						}

				});
}