$(document).ready(function(){
	var c;
	$("#mycart").hover(function(){
		if ($("#cart_pop").css("display")=="block"){
			$("#cart_pop").css("display","block");
			clearTimeout(c);
			return;
		}
		
		
		if($("#shoppingCartDetail").val()=="1"){
			 $("#cart_pop").hide(0);
			 $('#cart_pop_iframe').hide();
			 return;
		}
		var base = $('#cart_url_base').attr("value");
		var cartUrl ="http://www.pinju.com/cart/loadByStream.htm?version="+new Date().getTime();

		$.ajax({
			method: "POST",
			url:cartUrl,
			dataType: "JSONP",
			jsonp: "callBack",
			cache: false,
			success:function(data){
					if(data[0])
			  			loadcart(data[0]);
			  		
				}
			});
			
			
	},function(){
		c=setTimeout(function(){
			$("#cart_pop").hide(0);
			$('#cart_pop_iframe').hide();
		},200);
	});
	
	
	$("#cart_pop").mouseover(function(){
		clearTimeout(c)
	});
	$("#cart_pop").mouseout(function(){
		c=setTimeout(function(){
			$("#cart_pop").hide(0);
			$('#cart_pop_iframe').hide();
		},200);
	});
});

function escapeHTML(val) {
	return val.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function setpos(){
	var position = $('#mycart').position();
	var ie6=!window.XMLHttpRequest;
	 $("#cart_pop")[0].style.position=(ie6)?"absolute":"fixed";
	 $("#cart_pop")[0].style.zIndex=999999;
	 $("#cart_pop")[0].style.top=position.top+$('#mycart').height()+1+"px";
	 $("#cart_pop")[0].style.left=position.left+"px";
	setTimeout(function(){
		$("#cart_pop").show(0);
		if (ie6) {
			if ($('#cart_pop_iframe').length < 1) {
				$("#cart_pop").after('<iframe id="cart_pop_iframe" src="about:blank" style="position:absolute;top:0;left:0;z-index:999998;filter:alpha(opacity=0);display:none;"></iframe>');
			}
			$('#cart_pop_iframe').css({
				position : "absolute",
				top : position.top+$('#mycart').height()+1+"px",
				left : position.left+"px",
				width : $("#cart_pop").width() + 'px',
				height : $("#cart_pop").height() + 'px'
			}).show();
		}
	}, 200);
}


function loadcart(data){
	
	//alert(data.itemList);
	if(data.itemList==null || data.itemList.length==0){
		return;
	}
	
	var itemText = "";
	
	if(data.itemList==null || data.itemList.length==0){
		$("#cart_pop").hide();
		$('#cart_pop_iframe').hide();
		$("#cart_pop").html("");
		 $('#carts').html("0");
		return;
	}else{
		itemText +='<p class="cart_pop_tit">最近加入的商品<\/p>';
		itemText +='<ul class="cart_pop_items_list cf">';
	}
	
	var base = $('#cart_url_base').attr("value");
	$.each(data.itemList, function(n, item){
		
		if( n >= 5) return false;
		
		itemText +='<li id="cartItem_' + item.id + '_' + item.skuid + '">';
		itemText +='<div class="cart_pop_items_pic">';
		if(item.picUrl==""){
			itemText +='<a href="http://www.pinju.com/detail/'+item.id+'.htm">';
			itemText +='<img width="40px" height="40px" src="http://static.pinju.com\/images\/bonanza_09.gif" \/><\/a>';
		}else{

			var picurl = image_server + item.picUrl;
			itemText +='<a href="http://www.pinju.com/detail/'+item.id+'.htm">';
			itemText +='<img width="40px" height="40px" src="'+picurl+'" \/><\/a>';
		}
		itemText +='</div>';
        
		itemText +='<div class="cart_pop_items_tit">';
		itemText +='<a href="http://www.pinju.com/detail/'+item.id+'.htm">';
		var subtitle="";
		if(item.title.length > 12)
			subtitle= item.title.substring(0, 12) + "...";
		else
			subtitle= item.title;
		itemText+= escapeHTML(subtitle);
		itemText +='<\/a>';
		
		var itemProperty = data.itemPropertyMap['key' + item.id + item.skuid];
		if(itemProperty){
			var itemPropertyDisp = "";
			for(i = 0; i < itemProperty.length; i++){
				if(i == 0)
					itemPropertyDisp = itemProperty[i].name + ":" + itemProperty[i].value;
				else
					itemPropertyDisp += " " + itemProperty[i].name + ":" + itemProperty[i].value;
			}
			
			if(itemPropertyDisp.length > 12)
				itemPropertyDisp= itemPropertyDisp.substring(0, 12);
			
			itemText +='<p><span>'+itemPropertyDisp+'<\/span><span><\/span><\/p>';
		}
		
		
		itemText +='<\/div>';
		itemText +='<div class="cart_pop_items_r">';
		itemText +='<span class="cart_pop_items_price">¥<b>'+item.priceByYuan+'<\/b><\/span>';
		itemText +="<a href='javascript:deleteItemFromCart(" + item.id+","+item.skuid+");'>删除<\/a>";
        itemText +='<\/div><\/li>';
        
        
	});
	//alert(data.itemList.length);
	if(data.itemList.length>0){
		itemText+="<\/ul>";
		itemText+='<div class="cart_pop_items_f cf">';
		(data.itemList.length>5)? itemText+='<p>购物车里还有<span>'+(data.itemList.length-5)+'<\/span>件商品<\/p>': "";
		//itemText+='<p>购物车里还有<span>4</span>件商品</p>';
			//<!--购物车大于5件 出现-->
		itemText+='<a href="http://www.pinju.com\/cart\/shoppingCartDetail.htm" class="cart_pop_items_but">查看我的购物车<\/a><\/div>';
	}
	//alert(itemText);
	$("#cart_pop").html(itemText);
	$('#carts').html(data.itemList.length);
	
	setpos();
}





function writeShoppingCartCookie(content){
	var validMin = 90*24*60;
	PinjuCookie.writeCookie(PinjuCookie.SC, content, PJ.COOKIE_DOMAIN, '/', validMin);
}

function writeShoppingCartCookieCount(count){
	var validMin = 90*24*60;
	PinjuCookie.writeCookie(PinjuCookie.SD, count, PJ.COOKIE_DOMAIN, '/', validMin);
}



function deleteItemFromCart(itemId, skuid){
	var pc = new PinjuCookie("/");
	var a = decodeURIComponent(pc.getShoppingCartContent());

	var nc = "";
    var items = a.split("~");

    for(var i = 0; i < items.length; i++) {
    	
      var b = items[i].split(",");
      if(b[0] == itemId && b[3] == skuid){
				continue;
      }else{
    	  if(nc == "")
    		  nc = items[i];
    	  else
    		  nc = nc + "~" + items[i];
      }

    }
    
    writeShoppingCartCookie(nc);
    
    $("#cartItem_" + itemId + "_" + skuid).remove();
    
    var count = pc.getShoppingCartCount();
    
    if(count > 0)
    	count--;
    	
    writeShoppingCartCookieCount(count);
    $('#carts').html(count);
    
    if(count == 0){
    	$("#cart_pop").html("");
    	$("#cart_pop").hide(0);
		$('#cart_pop_iframe').hide();
    }
    
    if(count >= 5){
    	var base = $('#cart_url_base').attr("value");
		var cartUrl ="http://www.pinju.com/cart/loadByStream.htm?version="+new Date().getTime();
		
		$.ajax({
			method: "POST",
			url:cartUrl,
			dataType: "JSONP",
			jsonp: "callBack",
			cache: false,
			success:function(data){
					if(data[0])
			  			loadcart(data[0]);
			  		
				}
			});
    }
}