//@author liuboen ,商品详情页面
//异步处理购物车逻辑
var postShopCart=function(){
	if(!validateItemInput())return;
	var itemId=$('#itemId').val();
	var itemNum=$('#SelectedNum').val();
	var skuid=$('#SelectedSkuId').val();
	var skuDesc=$('#SelectedSKU').val();
	var itemPrice=$('#SelectedPrice').val();
	var channelId=$('#channelId').val();
	if(channelId==null||channelId=='undefind'){
		channelId='';
	}
	if(skuid==null||skuid=='undefind'){
		skuid='';
	}
	if(skuDesc==null||skuDesc=='undefind'){
		skuDesc='';
	}
	//请求购物车
	var postUrl=shopCartAction+"?itemId="+itemId+"&itemCount="+itemNum+"&itemPrice="+itemPrice+"&skuid="+skuid+"&skuDesc="+skuDesc+"&channelId="+channelId;
       jQuery.post(postUrl,
    function(data){ //回传函数
		var cartNum=$$('cart_num');
		var cartPrice=$$('cart_price');
    	//var postInfos = eval('('+data+')'); 
    	var postInfos = eval(data); 
    	if(postInfos.stockCountLower == true){
    		alert('购买数量不能大于库存');
    	}else if(postInfos.addSuccess==true){
    		cartNum.innerHTML=postInfos.catCount;
			cartPrice.innerHTML=postInfos.totalPrice;
			$(".shop_cart_pop .tips-error").hide();
			$(".shop_cart_pop .tips-right").show();
			$(".shop_cart_pop").show()
			setItemCount();
    	}else{
    		$(".shop_cart_pop .tips-error").show();
			$(".shop_cart_pop .tips-right").hide();
			$(".shop_cart_pop").show()
        }
       }
  )

}

var postForm=function(action){
	if(validateItemInput()){
		$("#detailForm").attr("action",action);
		$("#detailForm").submit();
	}
}

//设置购物车商品数量
function setItemCount(){
	var pc = new PinjuCookie("/");
	var a = decodeURIComponent(pc.getShoppingCartContent());
    var items = a.split("~");
    if(items.length){
    	$('#carts').html(items.length);
    }else{
    	$('#carts').html("0");
    }
}

var validateItemInput=function(){
	var itemId=$('#itemId').val();	         //商品ID
	var itemNum=$('#SelectedNum').val();     //数量
	var stock=$('#SpecCapacity').text();      //库存
	var skuid=$('#SelectedSkuId').val();     //SKU id
	var skuDesc=$('#SelectedSKU').val();     //SKU description
	var selPrice=$('#SelectedPrice').val(); //商品价格
	var specPrice=$('#SpecPrice').text();                      
	//try{
	var regexStr=/^\+?[1-9][0-9]*$/;
	if(isNaN(itemId)||!parseFloat(itemId)>0)
		return false;
	if(isSku){
		//验证是否选择SKU
		var isChoiceAllSku=true;
		isChoiceAllSku=validateSku()
		if(!isChoiceAllSku){
			return false;
		}
		if(selPrice!=specPrice||isNaN(selPrice)){
		return false;
		}
	}
	
	if(isNaN(itemNum)||!parseFloat(itemNum)>0||!regexStr.test(itemNum)){
		alert("请正确填写购买数量");
		$('#SelectedNum').val("1");
		return false;
	}
	if(isActDiscount==true){
		var limitdiscount=$('#actLimitCount').text();     //限购数量
		if(parseFloat(limitdiscount)>0&&parseFloat(limitdiscount)<parseFloat(itemNum)){
			alert("您所填写的商品数量超过限购数量");
			return false;
		}
	}
	if(parseFloat(stock)<parseFloat(itemNum)){
		alert("购买数量不能大于库存");
		return false;
	}
	
	return true;
}

var validateSku=function(){
	var skuNotSelect="请选择: ";
	var isChoiceAllSku=true;
	var skulist=$("div[name='sort_list']") 
	$.each( skulist, function(i, n){
	  var skuSpanText=($(n).find('span.sort_tit').text());
	  var isSkuSel=false;
	  var skuValueList=$(n).find('div').find('ul').find('li');
	  $.each(skuValueList, function(j, m){
	  	var selectSkuClass=$(m).attr("class");
	  	if(selectSkuClass=='ho'||selectSkuClass=='imgsitem-outline ho'){
	  		isSkuSel=true;
	  		return;
	  	}
	  });
	  if(!isSkuSel){
	  	skuNotSelect=skuNotSelect+skuSpanText.replace("："," ");
	  	isChoiceAllSku=false;
	  }
	});
	if(!isChoiceAllSku){
		alert(skuNotSelect);
		return false;
	}else{
		return true;
	}
}

var $$=function(idName){
	return document.getElementById(idName);
}

var unDisplayShopCartLayer=function(){
	$('#J_CartInfo').hide();
}
var displayShopCartLayer=function(){
	$('#J_CartInfo').show();
}

/** 运费相关JS*/
	function getLogisticsByCity(cName){
		$("#current_value").text("物流信息加载中..");
  		$.ajax({
			type: "post",
			url: logisticsAction,
			data:{logisticsTemplateId:templateId,cityName:cName,memberId:sellerMemberId},
			dataType: "json",
			cache:false,
			success: function(regionCarriage){
				var innerTest="";
				var py="";
				var kd="";
				var ems="";
				$.each(regionCarriage,function (key,value) {
					if(value!=null&&value!=0){
						var tempStr=parseFloat(value/100).toFixed(2)+"元　";
						if(key==1){
							py="平邮:"+tempStr;
						}else if(key==2){
							kd="快递:"+tempStr;
						}else if(key=3){
							ems="EMS:"+tempStr;
						}
					}
				});
				innerTest=ems+kd+py;
				$("#current_value").text(innerTest);
			}
		});
	}
	/** 获取购买数量，评论数量 */
	function getTradeCount(){
		if (typeof base == 'undefined') {
			return;
		}
		var buyNumpostUrl=base+"/orderQuery/queryajaxitembuynum.htm?itemId="+itemId;
		jQuery.post(buyNumpostUrl,
			function(data){ //回传函数
					var	buyNum=data.buyNum;
					if(buyNum!=0){
						$(".trade_buy_count").text(buyNum);
					}
			}
		);
		jQuery.getJSON(base + "/async/comments/item-count.htm?item=" + itemId + "&callback=?", function(data){
			$(".trade_comment_count").text(data.count);
		});
	}
	
	/**异步获取购买详情*/
		//翻页调用   
		function buyListPageCallback(index, jq) {        
			initBuyListTable(index, $("#J_buyTable"), $("#Pagination"), $("#pd_tabar"));  
		}
		function contentBuyListPageCallback(index, jq) {        
			initBuyListTable(index, $("#J_contentBuyTable"), $("#J_contentBuyPagination"), $("#J_buyTitleBar"));  
		}
      //请求数据   
      function initBuyListTable(buyPageIndex, container, pagination, topContainer) {
		var table= container.find(".already_buy_list");
      	var page=buyPageIndex+1;
      	var url=base+"/async/detail/getBuyOrderList.htm?itemId="+itemId+"&page="+page+"&size="+buyPageSize+"&jsoncallback=?&t="+new Date();
      	var loadingDiv = $('<div style="margin-top:10px;">正在读取购买记录,请稍候...</div>');
		table.html('');
		container.after(loadingDiv);
		jQuery.post(url, function(data){
				loadingDiv.remove();
      			var firstBuyHtml='<tr><td colspan="5"></td></tr>';
				table.html(firstBuyHtml);
      			var count =data.count;
      			var allItem=data.allItem;
      			//$("#buy_piece").text(allItem);
		     	 pagination.pagination(count, {  
		          callback: container.attr('id') == 'J_contentBuyTable' ? contentBuyListPageCallback : buyListPageCallback,  
		          link_to:"javascript:;",//点击属性
		          prev_text: '上一页',       //上一页按钮里text   
		          next_text: '下一页',       //下一页按钮里text   
		          items_per_page: buyPageSize,  //显示条数   
		          num_display_entries: 4,    //连续分页主体部分分页条目数   
		          current_page: buyPageIndex,   //当前页索引   
		          num_edge_entries: 2        //两侧首尾分页条目数   
			  });  
				 $.each(data.list, function(i,obj){
				 	var otherBuyHtml = "";
				 	var saleImageUrl="&nbsp;<img src='http://static.pinju.com/images/detail/buy-list-sale.png' />";
				 	if(obj.isCoupon==2){
				 		otherBuyHtml='<tr class=""><td>'+obj.buyerNick+'</td><td>'+obj.price+saleImageUrl+'</td><td>'+obj.buycount+'</td><td>'+obj.gmtCreate+'</td><td>'+obj.skuAttr+'</td></tr>';
				 	}else{
				 		otherBuyHtml='<tr class=""><td>'+obj.buyerNick+'</td><td>'+obj.price+'</td><td>'+obj.buycount+'</td><td>'+obj.gmtCreate+'</td><td>'+obj.skuAttr+'</td></tr>';
				 	}
			  		table.append(otherBuyHtml);
				 });
				//table tr hover
				container.find("tr").hover(function(){				
					$(this).addClass("hover");
				},function(){
					$(this).removeClass("hover");;
				});
				//scroll
				if (topContainer) {
					var pos = topContainer.offset().top;
					$(window).scrollTop(pos);
				}
		});                                       
      }  
	
      function initPageStyle(idname,count){
      	 $(idname).pagination(count, {  
		          callback: buyListPageCallback,  
		          link_to:"javascript:;",//点击属性
		          prev_text: '上一页',       //上一页按钮里text   
		          next_text: '下一页',       //下一页按钮里text   
		          items_per_page: buyPageSize,  //显示条数   
		          num_display_entries: 4,    //连续分页主体部分分页条目数   
		          current_page: buyPageIndex,   //当前页索引   
		          num_edge_entries: 2        //两侧首尾分页条目数   
			  });  
      }
	
	/** 是否正确限购码*/
	function isLimitBuyCode(){
		var itemId=$('#itemId').val();
		var xianGouCode=$('#limit_input').val();
		if(xianGouCode==null||xianGouCode==''||xianGouCode=='undefind'){
			alert("请输入限购码");
			return;
		}
		//请求限购
		var postUrl=limitBuyAction+"?itemid="+itemId+"&xianGouCode="+xianGouCode;
		jQuery.post(postUrl,function(data){ //回传函数
			var postInfos =  data; 
			if(postInfos.error!=null){
  				alert(postInfos.error);
			}else if(postInfos.success!=null){
				$("#limitBuyCode").val(xianGouCode);
				postForm(buyNowAction);
			}else{
				alert("网络错误");
			}

	})
}


	//购买评价部分     
	function ratePageCallback(index, jq) {    
		$("#review_list").text("正在读取评价信息,请稍候...");         
		rateInitTable(index, $("#review_list"), $("#ratePagination"), $('#pd_tabar'));  
	}
	function contentRatePageCallback(index, jq) {    
		$("#J_contentReviewList").text("正在读取评价信息,请稍候...");         
		rateInitTable(index, $("#J_contentReviewList"), $("#J_contentRatePagination"), $('#J_commentTitleBar'));  
	}
      //请求数据   
      function rateInitTable(pageIndex, reviewList, pagination, topContainer) {
		      	var page=pageIndex+1;
		      	pageIndex=page;
		      	//var url=snsRateUrl+"/cuttle/mall/goods/comments/json/"+itemId+"/"+pageIndex+"/"+ratePageSize+"/?jsoncallback=?";
		      	var url=base+"http://www.pinju.com/async/comments/item.htm?item="+itemId+"&page="+pageIndex+"&pageSize="+ratePageSize+"&t="+new Date();
		      	$.getJSON(url, function(data){
					var rateNum=data.count;
					//分页，PageCount是总条目数，这是必选参数，其它参数都是可选   
					 pagination.pagination(rateNum, {  
					  callback: reviewList.attr('id') == 'J_contentReviewList' ? contentRatePageCallback : ratePageCallback,  
					  link_to:"javascript:;",//点击属性
					  prev_text: '上一页',       //上一页按钮里text   
					  next_text: '下一页',       //下一页按钮里text   
					  items_per_page: ratePageSize,  //显示条数   
					  num_display_entries: 4,    //连续分页主体部分分页条目数   
					  current_page: pageIndex-1,   //当前页索引   
					  num_edge_entries: 2        //两侧首尾分页条目数   
					}); 
					reviewList.text("");
					if (data.comments) {
						$.each(data.comments, function(i,item){
							//var commentTime=timeMillisToDate(item.commentTime);
							var rateImgUrl=(!item.anonymous && item.bid) ? getAvatarPath(item.bid) : RATE_DEFAULT_IMG;
							var rateNameAndUrl=(!item.anonymous && item.bid) ? '<a href="http://sns.pinju.com/' + item.bid + '" target="_blank">' + replaceHTMLTag(item.name) + '</a>' : replaceHTMLTag(item.name);
							var comment=replaceHTMLTag(item.comment);
							//var rateHtml="<li><div class=\"avatar_box\"> <div class=\"avatar\"><img src=\"#\" /></div><div class=\"use_name\">"+item.buyerNickname+"</div></div><div class=\"text\">"+comment+"<span class=\"date\">["+commentTime+"] </span></div></li>";
							var rateHtml="<li><div class=\"avatar_box\"> <div class=\"avatar\"><img src=\""+rateImgUrl+"\" /></div><div class=\"use_name\">"+rateNameAndUrl+"</div></div><div class=\"text\">"+comment+"<span class=\"date\">["+item.time+"] </span></div></li>";
							 reviewList.append(rateHtml);    
						});
					}
					if(rateNum==0){
						reviewList.text("没有找到结果")
					}else{
						//review_list bg
						reviewList.find(">li:odd").addClass("gray_bg");
					}
					//$('.trade_comment_count').text(rateNum);
					//scroll
					if (topContainer) {
						var pos = topContainer.offset().top;
						$(window).scrollTop(pos);
					}
				});                             
                 
      }  
     //获取评价评论人头像
	function getAvatarPath(id) {
	  var baseTemp = ('' + id).substr(0, 6);
	  return IMG_SERVER + '/usericon/' +  parseInt(baseTemp.substr(0, 3)) + '/' + parseInt(baseTemp.substr(3, 3)) + '/' + id + '.jpg_50x50.jpg';
	}
	
    $(function(){
    		//物流
			$("#express_ar").hover(function(){					
				$(this).clearQueue();
				s=setTimeout(function(){$("#national_list").show()},100);
			},function(){
				$(this).clearQueue();
				t=setTimeout(function(){$("#national_list").hide()},300);
			})
			$("#national_list").hover(function(){
				clearTimeout(t);
			},function(){
				$(this).clearQueue().hide();
			});
			$("#national_list>a").each(function(){
				$(this).click(function(){
					getLogisticsByCity($(this).text());
					$("#current_city").text($(this).text());
					$("#national_list").hide();
				});
			});
			//图片
			$(".sp_list>li").find("a").hover(function(){
				$(this).clearQueue();
			});
			//放大镜
			if ('jqzoom' in $.fn) {
				$('#J_itemPhoto').jqzoom({
					zoomType: 'standard',
					zoomWidth: 425,
					zoomHeight: 310,
					xOffset: 10,
					yOffset: 0,
					preloadText: '图片加载中',
					lens: true,
					title: '',
					preloadImages: true,
					alwaysOn: false
				});
			}
			//立即购买
			$("#submitBuyNow").click(function(){
				//判断是否限购 
				if(isLimitBuyItem){
					if(validateItemInput()){
						$(".stint_pop").show();
					}
				}else{
					postForm(buyNowAction);
				}
			});
			
			//加入购物车
			$(".btn_addshopcart").click(function(){
				postShopCart();
			})
			$(".shop_cart_pop .colse").click(function(){
				$(".shop_cart_pop").hide()
			})
			$(".tips-right>.otherclose").click(function(){
				$(".shop_cart_pop").hide()
			})
			
			//活动倒计时
			isActDiscount = typeof isActDiscount == 'undefined' ? null : isActDiscount;
			if(isActDiscount){
				function saleTime(){
					actStartMinute=actStartMinute-1;
					if(actStartMinute<0){
						actStartMinute=59;
						actStartHour=actStartHour-1;
					}
					if(actStartHour>=0&&actStartMinute>=0){
						$("#sale_time").text('(剩 '+actStartHour+'小时'+actStartMinute+'分钟 结束)');
						setTimeout(saleTime,60000);
					}else{
						$("#sale_time").text('活动已结束');
					}
				}
				saleTime();
			}

			//限购部分
			isLimitBuyItem = typeof isLimitBuyItem == 'undefined' ? null : isLimitBuyItem;
			if(isLimitBuyItem){
				//限购层关闭
				$(".stint_pop_close").click(function(){
					$(".stint_pop").hide();
				});
				
				//限购层提交
				$("#limit_buy_submit").click(function(){
					isLimitBuyCode();
				});
			}
			
			//切换tab
			$(".tabar a").click(function(e){
				$(".tabar li").removeClass("selected");
				$(this).parent().addClass("selected");
				var conbyid = $(this).attr("href");
				$(".tab_container").hide();
				$(conbyid).show();
				//scroll
				//var pos = $("#pd_tabar").offset().top;
				//$(window).scrollTop(pos);
				e.preventDefault();
			});
			
			//加载购买记录
			$("#buy_record").click(function(){
				if(!isClickBuyRecord){
					initBuyListTable(0, $("#J_buyTable"), $("#Pagination"), $("#pd_tabar"));
					isClickBuyRecord=true;
				}
			});
			//加载购买记录
			/*$("a#buy_record_navigate").click(function(e){
				var self = this;
				e.preventDefault();
				$(".tabar li").removeClass("selected");
				$("#buy_record").addClass("selected");
				//$("#buy_record").click();
				$(".tab_container").hide();
				$("#buy_recorder").show();
				//scroll
				function scroll() {
					var href = $(self).attr("href");
					var pos = $(href).offset().top;
					$(window).scrollTop(pos);
				}
				if(!isClickBuyRecord){
					initBuyListTable(0, scroll);
					isClickBuyRecord=true;
					return;
				}
				scroll();
			});*/

			
			//加载购买评价
			$("#buy_rate_view").click(function(e){
				e.preventDefault();
				if(!isLoadRate){
					rateInitTable(0, $("#review_list"), $("#ratePagination"), $('#pd_tabar'));
					isLoadRate=true;
				}
			});

			// 加载卖家宝贝推荐
			function loadRecommendItems() {
				if (typeof base == 'undefined') {
					return;
				}
				var ul = $('#J_recommendItemsContent');
				var html = '';
				function _escape(val) {
					return val.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
				}
				$.getJSON(base + '/async/detail/getShopkeeperRecommend.htm?mid=' + sellerMemberId, function(data) {
					if (data.itemDOList) {
						$.each(data.itemDOList, function() {
							html += '<li class="item">';
							html += '<div class="itemwrap">';
							html += '	<div class="img">';
							html += '		<a href="' + base + '/detail/' + this.id + '.htm" target="_blank"><img src="' + IMG_SERVER + '/' + this.picUrl + '_160x160.jpg" /></a>';
							html += '	</div>';
							html += '	<ul>';
							html += '		<li class="titletext">';
							html += '			<a href="' + base + '/detail/' + this.id + '.htm" target="_blank">' + _escape(this.title) + '</a>';
							html += '		</li>';
							html += '		<li class="price">';
							if (this.discountPrice == null) {
								html += '			￥ <strong>' + this.price + '</strong>';
							} else {
								html += '			￥ <strong>' + this.discountPrice + '</strong> <del>' + this.price + '</del>';
							}
							html += '		</li>';
							html += '	</ul>';
							html += '</div>';
							html += '</li>';
						});
					} else {
						html = '<li style="margin-bottom:20px;">没有找到结果</li>';
					}
					ul.html(html);
				});
			}

			// 加载描述里的评论和购买记录、保障须知、支付方式
			if ($('#J_commentTitleBar').length > 0) {
				var isLoadContentComment = isLoadContentBuy = isLoadIndemnityNotice = isLoadPayType = isLoadRecommendItems = false;
				$(window).scroll(function() {
					var top = $(window).scrollTop() + $(window).height();
					if (top > $('#J_commentTitleBar').offset().top) {
						if (!isLoadContentComment) {
							rateInitTable(0, $('#J_contentReviewList'), $('#J_contentRatePagination'));
							isLoadContentComment = true;
						}
					}
					if (top > $('#J_buyTitleBar').offset().top) {
						if (!isLoadContentBuy) {
							initBuyListTable(0, $("#J_contentBuyTable"), $("#J_contentBuyPagination"));
							isLoadContentBuy = true;
						}
					}
					if (top > $('#J_indemnityNotice').offset().top) {
						if (!isLoadIndemnityNotice) {
							$('#J_indemnityNoticeContent').show();
							isLoadIndemnityNotice = true;
						}
					}
					if (top > $('#J_payType').offset().top) {
						if (!isLoadPayType) {
							$('#J_payTypeContent').show();
							isLoadPayType = true;
						}
					}
					if (top > $('#J_recommendItems').offset().top) {
						if (!isLoadRecommendItems) {
							loadRecommendItems();
							isLoadRecommendItems = true;
						}
					}
				});
			}

			//获取购买数量
			getTradeCount();
			
			//广告推广
			var adKey = new PinjuCookie("/").getAdvertise();
			if(adKey){
				$("#ad_key").val(adKey);
			}		
			$("#pd_description img").lazyload({ placeholder : "http://static.pinju.com/img/blank.gif" });
		});
		
	/**jQuery(document).ready(function(){
		 var loadedRateScroll = false;
		 function showRate(){
		  	  var top = jQuery("#review_list").offset().top;
			  if(!loadedRateScroll && jQuery(window).scrollTop() + jQuery(window).height() > top ){
			  	$("#buy_review").show();
			   	$("#buy_rate_view").click();
			  	loadedRateScroll=true;
			  }
		 }
	 	jQuery(window).scroll(showRate);
	});**/

	