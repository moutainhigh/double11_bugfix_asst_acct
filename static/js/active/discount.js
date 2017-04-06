	//本地
	//第一步参数设置较验************************************begin
	function checkFirst(){
		var actname = $("#actname").val();
		if($.trim(actname) == ""){
			$("#errorMsg").html("活动名称不能为空");
			$("#errorMsg").css("display","");
			return false;
		}
		var length = actname.length;
		if(length>40){
			$("#errorMsg").html("活动名称限40个汉字字符");
			$("#errorMsg").css("display","");
			return false;
		}
		var startT = $("#startT").val();
		var endT = $("#endT").val();
		if($.trim(startT)==""){
			$("#errorMsg").html("促销开始时间不能为空");
			$("#errorMsg").css("display","");
			return false;
		}else if($.trim(endT)==""){
			$("#errorMsg").html("促销结束时间不能为空");
			$("#errorMsg").css("display","");
			return false;
		}
		var start = new Date(Date.parse(startT.replace(/-/g,"/")));
		var end = new Date(Date.parse(endT.replace(/-/g,"/")));
		// 获取当前系统时间
		var myDate = new Date();
     	var year = myDate.getFullYear();   // 获取完整的年份(4位,1970-????)
     	var month = myDate.getMonth()+1;       // 获取当前月份(0-11,0代表1月)
     	if(month >= 1 && month <= 9){
     		month = "0"+month;
     	}
     	var date = myDate.getDate();       // 获取当前日(1-31)
     	var hours = myDate.getHours(); 
     	var mm = myDate.getMinutes();// 获取当前小时数(0-23)
     	if(hours >= 1 && hours <= 9){
     		hours =  "0" + hours;
     	}
     	var date = year + "-" + month +"-" + date + " " + hours +":"+ mm;
		var dd = new Date(Date.parse(date.replace(/-/g,"/")));
		if(start-dd<0){
			$("#errorMsg").html("促销开始时间不能早于系统当前时间");
			$("#errorMsg").css("display","");
			return false;
		}
		if(start-end>0){
			$("#errorMsg").html("促销结束时间不能早于促销开始时间");
			$("#errorMsg").css("display","");
			return false;
		}
		if(start-end == 0){
			$("#errorMsg").html("促销结束时间不能等于促销开始时间");
			$("#errorMsg").css("display","");
			return false;
		}
		var df=(end-start)/3600/1000;
		var timeLeft = $("#timeLeft").val();
		var leftTime = parseInt(timeLeft);
		if(df>240){
			$("#errorMsg").html("单个活动时间只能在1到240个小时之间");
			$("#errorMsg").css("display","");
			return false;
		}
		if(df>leftTime){
			$("#errorMsg").html("所剩活动时间不足"+df+"小时,请重设");
			$("#errorMsg").css("display","");
			return false;
		}
		$("#startTime").val(startT);
		$("#endTime").val(endT);
		$("#errorMsg").css("display","none");
		return true;
	} 
	
	// 第一步参数设置较验************************************end
	
	/**
	 * 隐藏第一步提示框
	 */
	function hideFirstTip() {
		$("#errorMsg").css("display","none");
	}
	
	$(function() {
		InitData();
	});
	
	$("#first").click(function(){
		if(checkFirst()){
			setHt();
			$("#firstStep").hide();
			$("#firstUp").show();
			$("#secondStep").show();
			InitData();
		}
	})
	
	function setHt(){
		$("#itemName").val("");
		$("#categoryId").val("");
		if($("#pages").val() == 0){
			$("#Pagination").hide();
			$("#linkTable tr:gt(0)").remove();
			var tbody = "<tr><td colspan=\"4\"><div style=\"text-align:center;color:red;\"><span>您没有可以参加此次活动的商品</span></div></td></tr>";
			$("#linkTable").append(tbody);
		} else {
			refeshPages(null, $("#linkTable tr:gt(0)"), $("#linkTable"));
		}
	}
	
	// 第一步修改
	$("#firstUp").click(function() {
		$("#firstStep").css("display","");
		$("#firstUp").css("display","none");
		$("#secondStep").css("display","none");
		$("#secondUp").css("display","none");
		$("#thirdStep").css("display","none");
	})
	
	// 商品选择*******************************************************start
	// 全选和反选
	var index = 0;// 选中商品的计数器
    $('#hobby_all').click(function () {
    	// 全选
    	if($(this).prop("checked")){
	    	$('input[name="checkItem"]').each(function(){  
	    		 if(!$(this).prop("checked")){ 
	    		 	$(this).prop("checked",true);
	    		 }
	  		});
    	}
        // 反选
    	else{
	  		$('input[name="checkItem"]').each(function(){
	  			if($(this).prop("checked")&&$(this).prop("disabled")==false){
				    $(this).prop("checked",false);
	  			}
	    	});
	    	$("#countDiscount").html("第三步 设置限时打折("+index+")");
	  	}
    	checkOne();
    }) 
    
    
	// 点击参加打折
	function clickParticipate(id){
		index = index+1;
		if(index>20){
		  		index = index-1;
	  			alert("每个活动最多添加20个商品,已达上限无法继续添加");
		  	}else{
		  		// 添加第三步中的内容
		  		var hid = "b"+id.substring(id.indexOf("d")+1,id.length);
		  		var trVal = $("#"+hid).val();
		  		if(getThirdTr(trVal)){
		  			$("#"+id).css("display","none");
					var nid = id.substring(id.indexOf('d')+1,id.length);
					$("#n"+nid).css("display","")
					$("#"+nid).prop({"checked":true,"disabled":true});
		  		}else{
		  			index = index-1;
		  		}
		  		$("#countDiscount").html("第三步 设置限时打折("+index+")");
		  	} 
		checkOne();
	}
	
	// 点击不参加打折
	function clickNoParticipate(id){
		$("#"+id).css("display","none");
		var did = id.substring(id.indexOf('n')+1,id.length);
		$("#d"+did).css("display","");
		$("#d"+did).prop("disabled",false);
		$("#"+did).prop({"checked":false,"disabled":false});
		index = index-1;
		$("#countDiscount").html("第三步 设置限时打折("+index+")");
		move(id);
		checkOne();
	}
	
	// 批量参加
	$("#batch").click(function(){
		checkOne();
		var batchCount = 0;
		$('input[name="checkItem"]').each(function(){  
	    	if($(this).prop("checked")&&$(this).prop("disabled")==false){ 
	    		 batchCount ++;
	    	}
	  	});
		if(batchCount==0){
			alert("至少选中一件商品")
			return;
		}else{
			var idStr = "";
			$('input[name="checkItem"]').each(function(){
			    if($(this).prop("checked")&&$(this).prop("disabled")==false){ 
			    	index ++;
			    	$(this).prop("disabled",true);
			    	$("#n"+this.id).css("display","")
	  				$("#d"+this.id).css("display","none")
			    	var trVal = $("#b"+this.id).val();
		        	if(!getThirdTr(trVal)){
		        		index --;
		        		$(this).prop("disabled",false);
				    	$("#n"+this.id).css("display","none")
		  				$("#d"+this.id).css("display","")
		        	}else{
		        		idStr += this.id+",";
		        	}
			    }
			 });
		}
		var x = idStr.lastIndexOf(",");
		var t = idStr.substring(0, x);
		var st = t.split(",");
		// 全选后的总数
	  	if(index>20){
	  		for(i=0;i<st.length;i++){
    			 	$("#"+st[i]).prop("checked",false);
    			 	$("#"+st[i]).prop("disabled",false);
    			 	$("#n"+st[i]).css("display","none")
	  				$("#d"+st[i]).css("display","")
    			 	index --;
    			 	move("n"+st[i]);
    		}
  			$('#hobby_all').prop("checked",false);
  			if(20-index>0){
  				alert("每个活动最多添加20件商品,您最多还可添加"+(20-index)+"件商品,请您选择要参加的商品");
  				return;
  			}
  			if(20-index == 0){
  				alert("每个活动最多添加20个商品,已达上限无法继续添加");
  				return;
  			}
	  	}
		$("#countDiscount").html("第三步 设置限时打折("+index+")");
	})
	
	// 添加第三步中的内容
	function getThirdTr(trVal){
		var tr = trVal.split(";");
		var title = $("#title_" + tr[0]).val();
		var iserver = $("#iserver").val();
		var trStr = "<tr id=\"c"+tr[0]+"\">"
						+ "<input type =\"hidden\" name=\"itemId\" value=\""+tr[0]+"\">"
						+ "<td class=\"table-long\">"
							  + "<img src=\""+iserver+tr[1]+"_40x40.jpg\"/>"
							+ "<a href=\"/detail/"+tr[0]+".htm\" target=\"_brank\" class=\"lan\">"+replaceHTMLTag(title)+"</a>"
						+ "</td>"
						+ "<td class=\"center\" id=\"p"+tr[0]+"\" name=\"prices\">"+(parseInt(tr[2])/100).toFixed(2)+"</td>"
						+ "<td class=\"center\">"
							+ "<input type=\"text\" class=\"text\" name=\"discontPrice\" maxlength=\"4\" onClick=\"clearSpan(this.id);\" onkeyup=\"clearNoNum(this);\" onBlur=\"discountPrice(this.id);\" id=\"dis"+tr[0]+"\" />折"
							+ "<div class=\"absolute\" style=\"display:none;\" id=\"div"+tr[0]+"\">"
								+ "<span class=\"tip-red\" style=\"display:none;\" id=\"msg"+tr[0]+"\"></span>"
							+ "</div>"
						+ "</td>"
						+ "<td class=\"center\" id=\"dp"+tr[0]+"\" name=\"disPrice\">0.00</td>"                     
						+ "<td class=\"center\"><input id=\"u"+tr[0]+"\" name=\"disnum\" type=\"text\" onChange=\"disCountCheck(this);\" onkeyup=\"up(this);\" onafterpaste=\"afterpaste(this);\" maxlength=\"8\" value=\"1\" class=\"text\"/></td>"
							+ "<td class=\"center\"><input type=\"button\" id=\"a"+tr[0]+"\"" + " onClick=\"del(this.id);\" class=\"pl-btn-juse\" value=\"删除\"/></td>" 
						+ "</tr>";
		var id = $("#c"+tr[0]).val();	
	    if(id==undefined){
	    	$("#tThird").append(trStr); 
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	// 删除第三步中的内容
	function move(id){
		var tid = "c"+id.substring(id.indexOf("n")+1,id.length);
		var cid = $("#"+tid).val();
	    if(cid != undefined){
	    	$("#"+tid).remove();   
	    }
	}
	
	// 完成选择************************
	$("#complete").click(function(){
		if(index==0){
			$("#secondMs").html("至少添加一件商品");
			$("#secondMs").css("display","");
			return;
		}else{
			$("#thirdStep").css("display","");
			$("#secondStep").css("display","none");
			$("#secondUp").css("display","");
			$("#secondMs").html("");
			$("#secondMs").css("display","none");
		}
		$('#hobby_all').prop("checked",false);
		$('input[name="checkItem"]').each(function(){  
		    if($(this).prop("checked")){ 
		    	$(this).prop("disabled",false);
		    	$(this).prop("checked",false);
		    	$("#n"+this.id).css("display","none")
	  			$("#d"+this.id).css("display","")
	  			$("#d"+this.id).prop("disabled",false);
		    }
	  	});
	})
	// 商品选择*******************************************************end
	
	
	// 修改第二步********************
	$("#secondUp").click(function() {
		$("#secondStep").css("display","");
		$("#secondUp").css("display","none");
		$("#firstStep").css("display","none");
		$("#firstUp").css("display","");
		$("#thirdStep").css("display","none");
		setSyle();
	})
	
	// 第三步删除
	function del(id){
		if(!del_true()){
			return;
		}
		var cid = "c"+id.substring(id.indexOf("a")+1,id.length);
		$("#"+cid).remove();
		index--;
		$("#countDiscount").html("第三步  设置限时打折("+index+")");
	}
	
	// 删除确认
	function del_true() {
		var gnl = confirm("确定要从本限时打折活动中删除本商品吗?");
		if (gnl == true) {
			return true;
		} else {
			return false;
		}
    }
/**
 * Create on 2011-08-03
 * 用js格式化数字(用于金额的处理)
 * @author:[贺泳]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 *  注意：srcStr：金额   nAfterDot：小数位数
 */
function FormatNumber(srcStr,nAfterDot){
   var srcStr,nAfterDot;
   var resultStr,nTen;
   srcStr = ""+srcStr+"";
   strLen = srcStr.length;
   dotPos = srcStr.indexOf(".",0);
   if (dotPos == -1){
	 resultStr = srcStr+".";
	 for (i=0;i<nAfterDot;i++){
	   resultStr = resultStr+"0";
	 }
	 return resultStr;
   }
   else{
	 if ((strLen - dotPos - 1) >= nAfterDot){
	   nAfter = dotPos + nAfterDot + 1;
	   nTen =1;
	   for(j=0;j<nAfterDot;j++){
　        nTen = nTen*10;
	   }
	   resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
	   return resultStr;
	 }
	 else{
	   resultStr = srcStr;
	   for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
　        resultStr = resultStr+"0";
	   }
	   return resultStr;
	 }
   }
}
	// 折扣计算
	function discountPrice(id){
		var dis = $("#"+id).val();
		var i = dis.indexOf(".");
		if($.trim(dis)!=""){
			if(i==-1){
				dis = parseInt(dis,10);
			}else{
				dis = parseFloat(dis);
			}
			$("#"+id).val(dis);
		}
		dis = eval($("#"+id).val());
		var ids = id.substring(id.indexOf("dis")+3,id.length);
		if($.trim(dis)==""){
			$("#dp"+ids).html("0.00");
			$("#msg"+ids).html("");
			$("#msg"+ids).css("display","none");
			$("#div"+ids).css("display","none");
			return false;
		}
		if(dis == 0){
			$("#msg"+ids).html("折扣值不能等于0");
			$("#msg"+ids).css("display","");
			$("#div"+ids).css("display","");
			return false;
		}
		if(dis>9.5){
			$("#msg"+ids).html("折扣值不能大于9.5");
			$("#msg"+ids).css("display","");
			$("#div"+ids).css("display","");
			return false;
		}
		// 折后价
		var price = eval($("#p"+ids).html());
		var dp = FormatNumber((price * dis) / 10, 2);
		if(dp<1){
			$("#msg"+ids).html("折后价不能小于1元");
			$("#msg"+ids).css("display","");
			$("#div"+ids).css("display","");
			return false;
		}else{
			$("#dp"+ids).html(dp);
		}
		$("#msg"+ids).html("");
		$("#msg"+ids).css("display","none");
		$("#div"+ids).css("display","none");
	}
	
	function clearNoNum(obj){
		// 除掉两个0开头的
		obj.value=obj.value.replace(/^0{2,}/g,'');
		// 先把非数字的都替换掉，除了数字和.
		obj.value = obj.value.replace(/[^\d.]/g,"");
		// 必须保证第一个为数字而不是.，且不能以.开头
		obj.value = obj.value.replace(/^\.{1,}/g,"");
		// 保证只有出现一个.而没有多个.
		obj.value = obj.value.replace(/\.{2,}/g,".");
		// 保证.只出现一次，而不能出现两次以上
		obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		//var k = obj.value.indexOf(".");
		//if(k!=-1){
			//if(obj.value.length-k>2){
				//obj.value = obj.value.substring(0,k+2);
			//}
		//}
		
	}
	
	// 设置批量参数
	function checkDisA(){
		$("#discount").onfo
		var dis = $("#discount").val();
		var disnumber = $("#disnumber").val();
		if($.trim(dis)!=""){
			var r = dis.indexOf(".");
			if(r==-1){
				dis = parseInt(dis,10);
			}else{
				dis = parseFloat(dis);
			}
			$("#discount").val(dis);
		}
		dis = $("#discount").val();
		var str = "请先输入折扣或者限购数再使用批量设置，且数值不能为0";
		if($.trim(dis)==""&&$.trim(disnumber)==""){
			$("#thirdMsg").removeClass("tip");
			$("#thirdMsg").addClass("tip-red");
			$("#thirdMsg").html(str);
			$("#thirdMsg").css("display","");
			return false;
		}
		if($.trim(dis) != "" && parseFloat($.trim(dis)).toFixed(1) == 0 ){
				$("#thirdMsg").removeClass("tip");
				$("#thirdMsg").addClass("tip-red");
				$("#thirdMsg").html(str);
				$("#thirdMsg").css("display","");
				return false;
		}
		if($.trim(disnumber) != "" && parseFloat($.trim(disnumber)).toFixed(1) == 0 ){
				$("#thirdMsg").removeClass("tip");
				$("#thirdMsg").addClass("tip-red");
				$("#thirdMsg").html(str);
				$("#thirdMsg").css("display","");
				return false;
		}	
		if(dis>9.5){
			$("#thirdMsg").removeClass("tip");
			$("#thirdMsg").addClass("tip-red");
			$("#thirdMsg").html("折扣值不能大于9.5");
			$("#thirdMsg").css("display","");
			return false;
		}
		return true;
	}
	
	$("#discount").focus(function() {
		$("#discount").select();
		$("#thirdMsg").removeClass("tip-red");
		$("#thirdMsg").addClass("tip");
		$("#thirdMsg").html("可设置的最高折扣为9.5折，且折后价不能小于1.00元");
	})
	
	$("#disnumber").focus(function() {
		$("#disnumber").select();
		$("#thirdMsg").removeClass("tip-red");
		$("#thirdMsg").addClass("tip");
		$("#thirdMsg").html("可设置的最高折扣为9.5折，且折后价不能小于1.00元");
	})
	
	// 批量设置
	$("#setDisAll").click(function(){
		var k = 0;
		if(checkDisA()){
			var dis = $("#discount").val();
			if($.trim(dis)!=""){
				var r = dis.indexOf(".");
				if(r==-1){
					dis = parseInt(dis,10);
				}else{
					dis = parseFloat(dis);
				}
				$("#discount").val(dis);
			}
			var disnumber = $("#disnumber").val();
			var names = document.getElementsByName("discontPrice");
			var i=0;
	 		for(;i<names.length;i++){
    			var id = names[i].id.substring(names[i].id.indexOf("dis")+3,names[i].id.length);
    			if($.trim(dis)!=""){
    				$("#"+names[i].id).val(dis);
    				// 折后价
					var price = $("#p"+id).html();
					var dp = (Math.round(price*dis*100)/1000).toFixed(2);
					if(dp<1){
						$("#msg"+id).html("折后价不能小于1元");
						$("#msg"+id).css("display","");
						$("#div"+id).css("display","");
						k++;
					}else{
						$("#dp"+id).html(dp);
					}
    			}else{
    				$("#dp"+id).html("0.00");
    				$("#"+names[i].id).val("");
    			}
    			if($.trim(disnumber)!=""){
    				$("#u"+id).val(disnumber);
    			}
    			if(k==0){
    				$("#msg"+id).html("");
        			$("#msg"+id).css("display","none");
					$("#div"+id).css("display","none");
    			}
			}
		}	
		checkOne();
	})
	
	// 完成创建时的较验
	function checkAllVal(){
		var names = document.getElementsByName("discontPrice");
		var j=0;
	 	for(i=0;i<names.length;i++){
	 		var idv = names[i].id;
	 		var id =idv.substring(idv.indexOf("dis")+3,idv.length);
	 		var disvalue = $("#"+names[i].id).val();
	 		if($.trim(disvalue)==""){
	 			$("#msg"+id).html("折扣值不能为空");
	 			$("#msg"+id).css("display","");
				$("#div"+id).css("display","");
	 			j++;
	 		}
	 		if($.trim(disvalue)!=""){
	 			var price = $("#p"+id).html();
				var dprice = price*disvalue;  
				var dp = (Math.round(price*disvalue*100)/1000).toFixed(2);
				if(disvalue == 0){
					$("#msg"+id).html("折扣值不能等于0");
	 				$("#msg"+id).css("display","");
					$("#div"+id).css("display","");
	 				j++;
				}
	 			if(dp<1&&disvalue!=0){
	 				$("#msg"+id).html("折后价不能小于1元");
	 				$("#msg"+id).css("display","");
					$("#div"+id).css("display","");
	 				j++;
	 			}
		 		if(disvalue>9.5){
		 			$("#msg"+id).html("折扣值不能大于9.5");
		 			$("#msg"+id).css("display","");
					$("#div"+id).css("display","");
		 			j++;
		 		}
	 		}
	 	}
	 	if(j!=0){
	 		return false;
	 	}
	 	return true;
	}
	
	// 点击完成创建
	$("#lastStep").click(function(){
			if(checkAllVal()){
				if(checkFirst()){
          $("#lastStep").attr("disabled", true);
					$("#form1").submit();
				}else{
					$("#firstStep").css("display","");
					$("#firstUp").css("display","none");
					$("#secondStep").css("display","none");
					$("#secondUp").css("display","none");
					$("#thirdStep").css("display","none");
				}
			}
	})
	
	function clearSpan(d) {
		var cid = d.substring(d.indexOf("dis")+3,d.length);
		$("#msg"+cid).html("");
		$("#msg"+cid).css("display","none");
		$("#div"+cid).css("display","none");
	}
	
	// 第三步折扣限购数校验
	function disCountCheck(d) {
		var v =  $(d).val();
		if($.trim(v)!=""){
			if(parseInt(v)==0){
				$(d).val("");
			}
		}
	}
	
	
	// 将本次活动中已被选的商品在点修改和翻页时置为选中状态
	function setSyle(){
		var names = document.getElementsByName("discontPrice");
		if(names.leng!=0||names!=undefined){
			$('input[name="checkItem"]').each(function(){
				for(i=0;i<names.length;i++){
					var id = names[i].id.substring(names[i].id.indexOf("dis")+3,names[i].id.length);
					if(id == this.id){
						$("#"+id).prop({"checked":true,"disabled":true});
			  			$("#n"+id).css("display","")
						$("#d"+id).css("display","none")
					}
				}
		  	});
		}
	}
	
	// 放弃创建
	function give_up() {
		if(confirm("确认后本次的促销设置将丢失，确定放弃吗?")){
			document.location.href = "/active/promotionManager.htm";
		}
	}
	
	/**
	 * 商品类目下拉框
	 */
	$("#categoryId").change(function(){
		// 清空商家名称
		// $("#itemName").val("");
		var categoryId = $("#categoryId").val();
		var param = {'categoryId': $.trim(categoryId)};
		refeshPages(param, $("#linkTable tr:gt(0)"), $("#linkTable"));
	});

	$("#categoryId").click(function(){
		$("#query-tip").hide();
	});

	/**
	 * 查询按钮
	 */
	$('#searche').click(function(){
		var itemName = $("#itemName").val();
		var categoryId = $("#categoryId").val();
		var param = {'categoryId': $.trim(categoryId), 'itemName': $.trim(itemName)};
		refeshPages(param, $("#linkTable tr:gt(0)"), $("#linkTable"));
		$(this).blur();
	});
	
	$('#searche').dblclick(function(){
		return false;
	});
	
	$("#itemName").focus(function(){
		$("#query-tip").hide();
	});
	
	$("#searche").blur(function(){
		$("#query-tip").hide();
	});
	
	function ajaxCallBack(data) {
		var imageServer = $("#iserver").val();
		var tbody = "";
		var list = eval(data);
		if(list.length > 0){
			$('#hobby_all').prop("checked",false);
			$.each(list, function(i, item) {
					$("#linkTable tr:gt(0)").remove();
					var trs = "";
					if(item.featuresMap['limitDiscount']==undefined){
						trs += 
						"<tr>" +
							"<input type='hidden' id='b"+item.id+"' value='"+item.id+";"+item.picUrl+";"+item.price+";"+item.curStock+"' />" +
							"<input type='hidden' id='title_"+item.id+"' value='"+replaceHTMLTag(item.title)+"' />" +
							"<td class=\"w5 center\">" +
								"<input type='checkbox' id='"+item.id+"' onClick='checkOne();' name='checkItem' value='" + item.id + "'>" +						
							"</td>" +
							"<td>" +
								"<img src='" + imageServer + item.picUrl + "_40x40.jpg' />" +
								"<a href='/detail/" + item.id + ".htm ' target='_blank' class='lan'>" + replaceHTMLTag(item.title) +"</a>" +
							"</td>" +
							"<td class=\"w15 center\">" + (parseInt(item.price)/100).toFixed(2) + "</td>" +
							"<td class=\"w15 center\">" +
								"<input type='button' value='参加打折' id='d" +item.id+ "' class='pl-btn-juse' onClick='clickParticipate(this.id);'/>" +
								"<input type='button' value='不参加了' class='pl-btn-hui' id='n"+item.id+"' style='display:none;' onClick='clickNoParticipate(this.id);'/>" +
							"</td>"+
						"</tr>";
					}else{
						trs += 
						"<tr>"+
							"<td class=\"w5 center\">"+	
								"<input type='checkbox' disabled='disabled' checked='checked'>" +				
							"</td>"+
							"<td>"+
								"<img src='" + imageServer + item.picUrl + "_40x40.jpg' />"+
								"<a href='/detail/" + item.id + ".htm ' target='_blank' class='lan'>" + replaceHTMLTag(item.title) + "</a>" +
							"</td>"+
							"<td class=\"w15 center\">" + (parseInt(item.price)/100).toFixed(2) + "</td>" +
							"<td class=\"w15 center\">" +
								"<input type='button' class='pl-btn-hui' value='已参加活动' disabled='disabled'>"+
							"</td>" +
						"</tr>";
					}
					tbody += trs;
			});
			$("#linkTable").append(tbody);
			setSyle();
		}
	}
		
		// 限制批量打折文本框所填的内容
	function up(v){
		if(v.value.length==1){
				v.value=v.value.replace(/[^1-9]/g,'');
		}else if(v.value.length>1){
			v.value=v.value.replace(/^0{1,}/g,'');
			v.value=v.value.replace(/\D/g,'');
		}
	}
		
	function afterpaste(v) {
		if(v.value.length==1){
				v.value=vvalue.replace(/[^1-9]/g,'');
		}else {
			v.value=v.value.replace(/^0{1,}/g,'');
			v.value=v.value.replace(/\D/g,'');
		}
	}
		
	function ups(v){
		v.value=v.value.replace(/^0{1,}/g,'');
	}
		
	function afterpastes(v) {
		v.value=v.value.replace(/^0{1,}/g,'');
	}
		
	// 单选
	function checkOne(){
		$("#secondMs").css("display","none");
	}
		
	
    /**
	 * 替换所有HTML标签
	 * @param {需要替换的html} str
	 * @return {替换后的html}
	*/
	function replaceHTMLTag(str) {
		str = str.replace(/&/g,'&amp;');
		str = str.replace(/</g,'&lt;');
		str = str.replace(/>/g,'&gt;');
		str = str.replace(/\'/g,'&#039;');
		str = str.replace(/\"/g,'&quot;');
		return str;
	}
