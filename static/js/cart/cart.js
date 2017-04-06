$(function() {
	$.fn.numeral_quantity = function(opts) {
		opts = jQuery.extend( {
			decimalPlace : 0,
			maxLength : 30
		}, opts || {});
		$(this).css("ime-mode", "disabled");
		this.bind("keypress", function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);
			if (!$.browser.msie && (e.keyCode == 0x8)) {
				return;
			}
			if (code == 37 || code == 39) {
				return;
			}
			if ($(this).val().length >= opts.maxLength) {
				return false;
			}
			if (opts.decimalPlace == 0) {
				return code >= 48 && code <= 57
			} else {
				var d = $(this).val().indexOf(".");
				if (code == 46 && d != -1) {
					return false;
				}
				if (code == 46 && this.value == "") {
					return false;
				}
				if (code == 46 && this.value.length + 1 == opts.maxLength) {
					return false;
				}
				return code >= 48 && code <= 57 || code == 46;
			}
		});
		this.bind("blur", function() {
			if (this.value.length==0)this.value = "1";
			if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
				this.value = this.value.substr(0, this.value.length - 1);
			} else if (isNaN(this.value)) {
				this.value = "1";
			}
		});
		this.bind("paste", function() {
			var s = clipboardData.getData('text');
			if (!/\D/.test(s))
				;
			value = s.replace(/^0*/, "1");
			return false;
		});
		this.bind("dragenter", function() {
			return false;
		});
		this.bind("keyup", function() {
			if (opts.decimalPlace > 0) {
				var d = $(this).val().indexOf(".");
				if (d != -1 && $(this).val().length - d > opts.decimalPlace + 1) {
					this.value = this.value.substring(0, d + opts.decimalPlace + 1);
				}
			}
			if (/(^0+)/.test(this.value)) {
				this.value = this.value.replace(/^0*/, "1");
			}
		});
	};
});

$(document).ready(function(){
	//数字框校验
	$(".quantity").each(function(){
		$(this).numeral_quantity({
			decimalPlace:0,
	 		maxLength:4
		});
		
		$(this).blur(function(){
			var itemId = $(this).parent().find('input[name=_curItemId]').val();
			var skuid = $(this).parent().find('input[name=_skuid]').val();
			var curStock = $(this).parent().find('input[name=_curStock]').val();
			var count = $(this).val();
			
			if(eval(count) > eval(curStock)){
				$(this).parent().next().addClass("ts_sty");
				$(this).parent().next().html("最多只可购买" + curStock + "件");
				$(this).val(curStock);
				count = curStock;
			}else{
				$(this).parent().next().html("");
				$(this).parent().next().removeClass("ts_sty");
			}
			
			setItemCount(itemId, skuid, count);
			//setDisplayPrice();
			countCheckedItemPrices();
		});
	});
	
	//结算
	$(".go_settlement").click(function() {
		
		var checkboxValues=getAllCheckedValue();
		if(checkboxValues ==""){
			alert("请至少选择一件商品进行结算");
			return;
		}else{
			var itemIdSku=checkboxValues.split(",");
			for(i=0;i<itemIdSku.length;i++){
				var IdsSku=itemIdSku[i].split(" ");
				var val=$("#status_not_"+IdsSku[0]+"_"+IdsSku[1]).val();
				if(val==IdsSku[0]+" "+IdsSku[1]){
					alert("请将不能购买的商品删除后再结算");
					return;
				}
			}
			//删掉没有选中的items
			delNoCheckedItems();
		}
		$('#form').submit();
	});
	
	//批量删除
	$('#btnBatchDel').click(function(){
		var count = 0;
		var value = "";
		$('.itemList').each(function(){
			if(this.checked){
				count = count + 1;
				if(count == 1)
					value = this.value ;
				else
					value += "," + this.value ;
			}
		});
		
		if(count == 0){
			alert("请至少选择一条记录");
			return;
		}else{
			//$('#delItemId').attr("value", value);
			//$('#delForm').submit();
			if(!confirm("您确认要把该物品从购物车中删除吗？")) return;
			batchDelItems(value);
		}
		
		//setDisplayPrice();
		countCheckedItemPrices();
	});

	
	$("#selectall").each(function(n,tab) {
		$(this).bind("click",function(){
			var selAll = this.checked;
			$('.itemList').each(function(){
				$(this).attr("checked", selAll);
			});
			countCheckedItemPrices();
		});
	});
 });	
	/**
	//全选
	$('#selectall').click(function(){
		var selAll = this.checked;
		
		$('.itemList').each(function(){
			$(this).attr("checked", selAll);
		});
		countCheckedItemPrices();
	});
});
**/
//删除所有没有选中的Items
function delNoCheckedItems(){
	$('.itemList').each(function(){
		if(!this.checked){
			var idss=this.value.split(" ");
			$("tr[id$=_" + idss[0] + "_" + idss[1] + "]").each(function(){
				$(this).remove();
			});
		}
	});	
}

function getAllCheckedValue(){
	var count = 0;
	var value = "";
	$('.itemList').each(function(){
		if(this.checked){
			count = count + 1;
			if(count == 1)
				value = this.value ;
			else
				value += "," + this.value ;
		}
	});
	return value; 
}

//如果任何一个子checkbox没有被选中  全选不能被选中，反之 亦然
function checkSelectall(ob){
	if(!ob.checked){
		document.getElementById("selectall").checked=false;
	}else{
		 var isUnchecked=true;
		  $.each($('.itemList'),function(n,doms){
			  if(!doms.checked) isUnchecked=false;
		  });
		if(isUnchecked) document.getElementById("selectall").checked=true;
	}
	countCheckedItemPrices();
}

function writeShoppingCartCookie(content){
	var validMin = 90*24*60;
	PinjuCookie.writeCookie(PinjuCookie.SC, content, PJ.COOKIE_DOMAIN, '/', validMin);
}

function setXiaoji(itemId, skuid, count){
	var id = itemId + "_" + skuid;
	var price = eval($("#_danjia_" + id).val());
	var xiaoji = (price*count).toFixed(2);
	
	$("#xiaoji_" + id).html("￥" + xiaoji);
}

function setItemCount(itemId, skuid, count){
	var pc = new PinjuCookie("/");
	var a = decodeURIComponent(pc.getShoppingCartContent());

	var nc = "";
    var items = a.split("~");

    for(var i = 0; i < items.length; i++) {
    	
      var b = items[i].split(",");
      if(b[0] == itemId && b[3] == skuid){
    	  b[1] = count;
    	  var ni = "";
    	  for(j = 0; j < b.length; j++){
    		  if(ni == "")
    			  ni = b[j];
        	  else
        		  ni = ni + "," + b[j];
    	  }
    	  
    	  if(nc == "")
    		  nc = ni;
    	  else
    		  nc = nc + "~" + ni;
    	  
      }else{
    	  if(nc == "")
    		  nc = items[i];
    	  else
    		  nc = nc + "~" + items[i];
      }

    }
    
    writeShoppingCartCookie(nc);
    
    setXiaoji(itemId, skuid, count);
}

//加减收藏数量
function addCount(obj, isAdd, itemId, skuid){
	//alert(pc.getShoppingCartCount());
	$(obj).parent().next().html("");
	$(obj).parent().next().removeClass("ts_sty");
	var stock = $(obj).parent().find('input[name=_curStock]');
	
	//库存数量
	var stockCount = 0;
	if(stock)
		stockCount = stock[0].value;

	$(obj).parent().find('input[type=text]').each(function(){
		//输入框数量
		var curnum = Number($(this).val());
		
		//数量加
		if(isAdd){
			if(curnum < stockCount){
				$(this).val(curnum + 1);
				setItemCount(itemId, skuid, curnum + 1);
			}else{
				$(obj).parent().next().addClass("ts_sty");
				$(obj).parent().next().html("最多只可购买" + stockCount + "件");
			}
		
		//数量减
		}else{
			if(curnum > 1){
				$(this).val(curnum - 1);
				setItemCount(itemId, skuid, curnum - 1);
			}
		}
	});
	//setDisplayPrice();
	countCheckedItemPrices();
}

//批量删除商品
function batchDelItems(itemIds){
	if(itemIds!="undefined" && itemIds!=null){
		var url="${base}/cart/deleteItem.htm?delItemId="+itemIds;
		var para = {};
		$.get(url, 
				para,
				function(data){
					var ids=itemIds.split(",");
					for(i=0;i<ids.length;i++){
					//删除商品行
						
					var idss=ids[i].split(" ");
					$("tr[id$=_" + idss[0] + "_" + idss[1] + "]").each(function(){
						$(this).remove();
					});

					//删除店铺行
					$(".hd").each(function(){
						var shopid = $(this).attr("id");
						var item_count = 0;
						$("tr[id^=" + shopid + "_]").each(function(){
							item_count++;
						});
						
						if(item_count == 0)
							$(this).remove();
					});
					
					//删除提示行
					var caution_id = "#catuion_" + idss[0]+"_"+idss[1]; 
					$(caution_id).remove();
					if($("#cart-tips").find('li').size() == 0)
						$("#cart-tips").remove();
					}
					
					var itemstotal=getItemTotal();
					var del_total=ids.length;
					//更新快捷购物车中总数
					$('#carts').html(itemstotal-del_total);
					
					//如果购物车中没有商品, 调到空购物车页面
					if (itemstotal==del_total){
						window.location.href="/cart/shoppingCartDetail.htm?longTimeId="+new Date().getTime();
					}
					$("#now-item-total").val(itemstotal-del_total);
			});
		}
}

// 获取购物车中的剩下的Item 
function getItemTotal(){
	var totals=$("#now-item-total").val();
	return totals;
}

//删除商品
function delItem(itemId, skuid){
	if(!confirm("您确认要把该物品从购物车中删除吗？")) return;
	//提示行
	var caution_id = "#catuion_" + itemId+"_"+skuid; 

	//列表显示行
	var url="${base}/cart/deleteItem.htm?delItemId="+itemId + " " + skuid;
	var para = {};

	$.get(url, 
		para,
		function(data){
		
			var itemstotal=getItemTotal();
			if((itemstotal-1)==0){
				window.location.href="/cart/shoppingCartDetail.htm";
			}
			//删除商品行
			$("tr[id$=_" + itemId + "_" + skuid + "]").each(function(){
				$(this).remove();
			});

			//删除店铺行
			$(".hd").each(function(){
				var shopid = $(this).attr("id");
				var item_count = 0;
				$("tr[id^=" + shopid + "_]").each(function(){
					item_count++;
				});
				
				if(item_count == 0)
					$(this).remove();
				
			});
			
			//删除提示行
			$(caution_id).remove();
			if($("#cart-tips").find('li').size() == 0)
				$("#cart-tips").remove();
			
			$("#now-item-total").val(itemstotal-1);
			$('#carts').html(itemstotal-1);
			countCheckedItemPrices();
		});
}

/*
function setDisplayPrice(){
	var pc = new PinjuCookie("/");
	var a = decodeURIComponent(pc.getShoppingCartContent());
    var items = a.split("~");

    if(!items)
    	return;
    
    var total = 0;
    for(var i = 0; i < items.length; i++) {
      var b = items[i].split(",");
      var itemId=b[0];
      var count = b[1];
     // var price = b[2];
      var skuid = b[3];
      var price=$("#currentPrice_"+itemId+"_"+skuid).val();
      total += (count*price);
    }
    total=total.toFixed(2)
	$(".jies_jg").each(function(){
		$(this).html(total);
	});
}
*/

//联动总价格 
function countCheckedItemPrices(){
	var pc = new PinjuCookie("/");
	var a = decodeURIComponent(pc.getShoppingCartContent());
    var items = a.split("~");

    if(!items)
    	return;
    
    var total = 0;
    var checkValue=getAllCheckedValue();
    var valueArray=checkValue.split(",");
    for(var i = 0; i < items.length; i++) {
      var b = items[i].split(",");
      var itemId=b[0];
      var count = b[1];
     // var price = b[2];
      var skuid = b[3];
      for(var j=0; j < valueArray.length; j++){
    	  var ItemSku=valueArray[j].split(" ");
    	  if(ItemSku[0]==itemId && ItemSku[1]==skuid){
    		  var price=$("#currentPrice_"+itemId+"_"+skuid).val();
    	      total += (count*price); 
    	  }
      }
    }
    total=total.toFixed(2)
	$(".jies_jg").each(function(){
		$(this).html(total);
	});
}