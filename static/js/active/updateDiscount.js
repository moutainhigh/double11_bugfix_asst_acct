String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
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

/**
 * 第一步表单校验
 */
function checkFirst() {
	// 进行中的活动，不需校验
	var actName = $("#actName").val();
	if ($.trim(actName) == "") {
		$("#tip-1").html("活动名称不能为空");
		$("#tip-1").show();
		return false;
	} else if($.trim(actName).length > 40) {
		// 校验活动名称长度
		$("#tip-1").html("活动名称限40个汉字字符");
		$("#tip-1").show();
		return false;
	}
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if ($.trim(startTime) == "") {
		$("#tip-1").html("促销开始时间不能为空");
		$("#tip-1").show();
		return false;
	}
	if ($.trim(endTime) == "") {
		$("#tip-1").html("促销结束时间不能为空");
		$("#tip-1").show();
		return false;
	}
	if (startTime > endTime) {
		$("#tip-1").html("促销结束时间不能早于促销开始时间");
		$("#tip-1").show();
		return false;
	} else if (startTime == endTime) {
		//$("#timeTip").addClass("tip-red");
		$("#tip-1").html("促销结束时间不能等于促销开始时间");
		$("#tip-1").show();
		return false;
	} else {
		var start = new Date(Date.parse(startTime.replace(/-/g, "/")));
		var end = new Date(Date.parse(endTime.replace(/-/g, "/")));
		var now = new Date();
		var hours = parseInt(Math.abs(end - start) / 1000 / 60 / 60);
		var duration = $("#duration").val();
		
		if(start < now){
			$("#tip-1").html("促销开始时间不能早于系统当前时间");
			$("#tip-1").show();
			return false;
		}
		
		if (hours > 240 || hours < 1) {
			//$("#timeTip").addClass("tip-red");
			//单个活动时间只能在1到240个小时之间(包括1和240)
			$("#tip-1").html("单个活动时间只能在1到240个小时之间");
			$("#tip-1").show();
			return false;
		}
		// 修改后时长小于原时长，不需要判断
		if(hours > duration){
			// 否则判断现在的时长是否大于剩余时长和原时长之和
			var surplus = $("#surplus").val();
			if (hours > parseInt(surplus) + parseInt(duration)) {
				$("#tip-1").html("所剩活动时间不足" + hours + "小时,请重设");
				$("#tip-1").show();
				return false;
			}
		}
	}
	return true;
}

/**
 * 跳转到第二步
 */
function goToNext(status){
	var flag = true;
	if (parseInt(status) != 1) {
		flag = checkFirst(status);
	}
	if(flag){
		hideAndShow('#second');
	}
}

/**
 * 跳转到第三步
 */
function goToLast(){
	var itemCount = parseInt($("#itemCount").val());
	if(itemCount == 0){
		$("#tip-2").show();
		return false;
		/*if(confirm("您确定不添加任何商品？")){
			hideAndShow('#third');
		}*/
	}else if(itemCount > 20){
		alert("每个活动最多添加20件商品,已达上限无法继续添加");
		return false;
	}else{
		hideAndShow('#third');
	}
	$("#checkAll").attr("checked", false);
	return true;
}

$("#secondButton").blur(function(){
	$("#tip-2").hide();
});

/**
 * 隐藏第一步提示框
 */
function hideFirstTip() {
	$("#tip-1").hide();
	// $("#timeTip").removeClass("tip-red");
}

/**
 * 取消编辑
 */
function cancelEdit() {
	if (confirm("取消后将丢失本次的修改设置，您确定要取消编辑吗？")) {
		document.location.href = "/active/promotionManager.htm";
	}
}

/**
 * 加载页面时查询
 */
$(function(){
	$("#itemList").val($("#marker").val());
	InitData();
});

/**
 * 隐藏非编辑下DIV
 */
function hideAndShow(showDiv){
	$("div[class='drape-block']").hide();
	$(showDiv).show();
	switch(showDiv){
		case '#third':
			$("a[id*='_modify']").hide();
			$("#second_modify").show();
			$("#first_modify").show();
			break;
		case '#second':
			$("a[id*='_modify']").hide();
			$("#first_modify").show();
			checkTable();
			InitData();
			break;
		case '#first':
			$("a[id*='_modify']").hide();
			break;
	}
		
}

/**
 * 没有数据，显示提示信息
 */
function checkTable(){
	$("#itemName").val("");
	if($("#pages").val() == 0){
		$("#linkTable tr:gt(0)").remove();
		var tbody = "<tr><td colspan=\"4\"><div style=\"text-align:center;color:red;\"><span>您没有可以参加此次活动的商品</span></div></td></tr>";
		$("#linkTable").append(tbody);
	}else{
		$("#linkTable tr:gt(0)").remove();
		refeshPages(null, $("#linkTable tr:gt(0)"), $("#linkTable"));
	}
}

function clearText(module, text){
	if(module.val() == text){
		module.val('');
	}
	module.css('color', '#000');
}

function autoTip(module, text){
	if($.trim(module.val()) == ""){
		module.val(text);
		module.css('color', '#CCC');
	}else{
		module.css('color', '#000');
	}
}

/**
 * 批量添加时的全选
 */
function checkAllItemBox(box) {
	if($(box).attr("checked")) {
		//$("input:checkbox[name='itemBox']").attr("checked", true);
		$("input:checkbox[name='itemBox']").each(function(){
			if($(this).attr("disabled") == undefined){
				$(this).attr("checked", true);
			}
		});
	}else{
		$("input:checkbox[name='itemBox']").each(function(){
			if($(this).attr("disabled") == undefined){
				$(this).attr("checked", false);
			}
		});
		/*if($("input:checkbox[name='itemBox']").attr("disabled")){
			$("input:checkbox[name='itemBox']").attr("checked", false);
		}*/
	}
}

/**
 * 点击checkBox选择商品
 */
function singleBoxCheck(box) {
	var itemButton = $(box).parent("td").parent("tr").find("input[type='button']");
	if($(box).prop("checked")) {
		if(checkItemCount()){
			itemButton.val("不参加了");
			itemButton.removeClass("pl-btn-juse");
			itemButton.addClass("pl-btn-hui");
			$(box).prop("disabled", true);
			var itemId = $(box).val();
			modifyItemList(itemId, true);
			appendTr(itemId);
		}else{
			alert("每个活动最多添加20件商品,已达上限无法继续添加");
			return;
		}
	}else if(itemButton.val() == "不参加了"){
		itemButton.val("参加打折");
		itemButton.removeClass("pl-btn-hui");
		itemButton.addClass("pl-btn-juse");
		$(box).prop("disabled", false);
		var itemId = $(box).val();
		modifyItemList(itemId, false);
		removeTr(itemId);
	}
}

/**
 * 点击button选择商品
 */
function singleButtonClick(itemButton) {
	var itemBox = $(itemButton).parent("td").parent("tr").find("input[type='checkbox']");
	if($(itemButton).val() == "参加打折"){
		if(checkItemCount()){
			var itemId = itemBox.val();
			modifyItemList(itemId, true);
			if(appendTr(itemId)){
				$(itemButton).val("不参加了");
				$(itemButton).removeClass("pl-btn-juse");
				$(itemButton).addClass("pl-btn-hui");
				itemBox.prop("disabled", true);
				itemBox.prop("checked", true);
			}
		}else{
			alert("每个活动最多添加20件商品,已达上限无法继续添加");
			return;
		}
	}else{
		var itemId = itemBox.val();
		modifyItemList(itemId, false);
		if(removeTr(itemId)){
			$(itemButton).val("参加打折");
			$(itemButton).removeClass("pl-btn-hui");
			$(itemButton).addClass("pl-btn-juse");
			itemBox.prop("disabled", false);
			itemBox.prop("checked", false);
		}
	}
}

/**
 * 批量添加
 */
$("#batchAdd").click(function() {
	//if(checkItemCount()){
		var batchCount = 0;
		$('input[name="itemBox"]').each(function() {
			if ($(this).attr("checked") && $(this).attr("disabled") == undefined) {
				batchCount++;
			}
		});
	
		if (batchCount == 0) {
			alert("至少选中一件商品");
			return;
		}
		
		var itemCount = parseInt($("#itemCount").val());
		
		if(itemCount >= 20){
			alert("每个活动最多添加20件商品,已达上限无法继续添加");
			return;
		}
		
		if (itemCount + batchCount > 20) {
			alert("每个活动最多添加20件商品,您只能再选择" + (20 - itemCount) + "件商品");
			return;
		} else {
			$('input[name="itemBox"]').each(function() {
				if ($(this).attr("checked")
						&& $(this).attr("disabled") == undefined) {
					singleBoxCheck(this);
				}
			});
			$("#checkAll").attr("checked", false);
		}
	//}else{
		//alert("每个活动最多添加20件商品,已达上限无法继续添加");
		//return;
	//}
});

/**
 * 验证已选择商品总数
 */
function checkItemCount(){
	var itemCount = $("#itemCount").val();
	// TODO 测试时设为10
	if(parseInt(itemCount) >= 20){
		return false;
	}
	return true;
}

/**
 * 更新全局商品列表itemList
 */
function modifyItemList(itemId, isAdd){
	// 一律转为字符串，便于比较
	itemId = itemId + "";
	var itemList = $('#itemList').val();
	if($.trim(itemList) == ""){
		itemList = itemId;
	}else{
		var itemArray = itemList.split(",");
		itemArray = $.grep(itemArray, function(key, value){
					return key != "";
				});
		if(isAdd){
			if($.inArray(itemId, itemArray) == -1){
				itemArray.push(itemId);
			}
		}else{
			if($.inArray(itemId, itemArray) != -1){
				itemArray = $.grep(itemArray, function(key, value){
					return key != itemId;
				});
			}
		}
		itemList = itemArray.join(",");
	}
	$('#itemList').val(itemList);
}

/**
 * 往第三步中添加一行
 */
function appendTr(dataId){
	var imageServer = $("#imageServer").val();
	var data = $("#data_" + dataId).val();
	var datas = data.split("#");
	var title = $("#title_" + dataId).val();
	var tr = "<tr id='selected_" + datas[0]+ "'>"
		+ "<td class=\"table-long\">"
		+ "<input type=\"hidden\" value=\"" + datas[0] + "\" id=\"selectedItemId\" name=\"selectedItemId\"/>"
		+ "<img src='" + imageServer + datas[1] + "_40x40.jpg'/>"
		+ "<a href='/detail/" + datas[0] + ".htm ' target='_blank' class='lan'>" + replaceHTMLTag(title) + "</a>"
		+ "</td>"
		+ "<td class=\"center\" id=\"price_" + datas[0] + "\">" + datas[2] + "</td>"
		+ "<td class=\"center\">"
		+ "<input type=\"text\" class=\"text\" name=\"discount_rate\" maxlength=\"4\" onkeyup=\"clearNoNum(this);\" onblur=\"autoDiscount(this);\"  onfocus=\"autoSelectRate(this);\" id=\"rate_" + datas[0] + "\" />折"
		+ "<div class=\"absolute\" style=\"display:none;\" id=\"div_" + datas[0] + "\">"
		+ "<span class=\"tip-red\" style=\"display:none;\" id=\"tips_" + datas[0] + "\"></span>"
		+ "</div>"
		+ "</td>"
		+ "<td id=\"discount_" + datas[0] + "\" class=\"center\">0.00</td>"                     
		+ "<td class=\"center\"><input id=\"limit_" + datas[0] + "\" name=\"discount_limit\" value=\"1\" maxlength=\"8\" type=\"text\" onkeyup=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}\" onafterpaste=\"if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'');}else{this.value=this.value.replace(/\D/g,'');}if(this.value.startWith('0')){this.value='';}\" class=\"text\"/></td>"
		+ "<td class=\"center\"><input type=\"button\" onclick=\"deleteSelected(" + datas[0] + ")\" value=\"删除\" class=\"pl-btn-juse\"/></td>" 
		+ "</tr>";
	if($("#selected_" + datas[0]).html() == null){
		$("#selectedTable").append(tr);
		changeItemCount(1);
	}else{
		alert("该商品已经添加到活动列表中");
	}
	return true;
}

/**
 * 从第三步中删除一行
 */
function removeTr(itemId){
	if($("#selected_" + itemId).html() != null){
		$("#selected_" + itemId).remove();  
		changeItemCount(-1); 
	}
	return true;
}

/**
 * 第三步中删除操作
 */
function deleteSelected(itemId){
	if(confirm("确定要从本限时打折活动中删除本商品吗？")){
		// 更新全局变量
		modifyItemList(itemId, false);
		// 删除一行
		removeTr(itemId);
	}
}

/**
 * 修改选中商品数量
 */
function changeItemCount(count){
	var itemCount = parseInt($("#itemCount").val());
	if(itemCount >= 20 && count > 0){
		alert("每个活动最多添加20件商品,已达上限无法继续添加！");
		return false;
	}else{
		itemCount = itemCount + count;
		$("#itemCount").val(itemCount);
		$("#itemCountSpan").html(itemCount);
	}
	return true;
}

function autoDiscount(rateInput){
	var rate = eval($(rateInput).val());
	if(rate > 9.5){
		$(rateInput).siblings(".absolute").find("span[id*=tips_]").html("折扣值不能大于9.5");
		$(rateInput).siblings(".absolute").find("span[id*=tips_]").show();
		$(rateInput).siblings(".absolute").show();
	}else if(rate > 0){
		var oriPrice = eval($(rateInput).parent("td").siblings("td[id*=price_]").html());
		if(oriPrice > 0){
			var price = FormatNumber(rate * oriPrice / 10, 2);
			$(rateInput).parent("td").siblings("td[id*=discount_]").html(price);
			if(price < 1){
				$(rateInput).siblings(".absolute").find("span[id*=tips_]").html("折后价不能小于1元");
				$(rateInput).siblings(".absolute").find("span[id*=tips_]").show();
				$(rateInput).siblings(".absolute").show();
			}
		}
	}else if(rate == 0){
		$(rateInput).siblings(".absolute").find("span[id*=tips_]").html("折扣值不能等于0");
		$(rateInput).siblings(".absolute").find("span[id*=tips_]").show();
		$(rateInput).siblings(".absolute").show();
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
/**
 * 初始化折扣值
 */
$(function(){
	var discountList = $('#discountList').val();
	if($.trim(discountList) != ""){
		var discounts = discountList.split(',');
		$("input[name='discount_rate']").each(function(i){
			var value = FormatNumber(eval(discounts[i])/100, 2);
			if(value > 0)
				$(this).val(value);
			// 计算折后价格
			var oriPrice = eval($(this).parent("td").siblings("td[id*=price_]").html());
			if(oriPrice > 0){
				var price = FormatNumber(value * oriPrice / 10, 2);
				$(this).parent("td").siblings("td[id*=discount_]").html(price);
			}
			
		});
	}
	
	var limitList = $('#limitList').val();
	if($.trim(limitList) != ""){
		var limits = limitList.split(',');
		$("input[name='discount_limit']").each(function(i){
			var limit = parseInt(limits[i]);
			if(limit > 0){
				$(this).val(limit);
			}
		});
	}
});

/**
 * 限制折扣输入值合法性
 */
function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	if(obj.value.startWith('00')){
		obj.value = '0';
	}
	if(obj.value.startWith('0') && obj.value.length > 1 && !(obj.value.startWith('0.'))){
		obj.value = obj.value.substring(1);
	}
	if(obj.value.startWith('.')){
		obj.value = '';
	}
	if(obj.value == '0.00'){
		obj.value = "0.0";
	}
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//只能为2位数字
	obj.value = obj.value.substring(0, 4);
	if(obj.value.indexOf('.') != -1){
		obj.value = obj.value.substring(0, obj.value.indexOf('.') + 3);
	}
}

/**
 * 校验批量参数
 */ 
function checkDiscount() {

	var batchRate = $("#batchRate").val();
	var batchLimit = $("#batchLimit").val();
	var tipText = "请先输入折扣或者限购数再使用批量设置，且数值不能为0";
	if ($.trim(batchRate) == "" && $.trim(batchLimit) == "") {
		$("#tip-3").html(tipText);
		$("#tip-3").show();
		$("#tip_3").hide();
		return false;
	}
	// TODO
	if ($.trim(batchRate) != "" && parseFloat($.trim(batchRate)).toFixed(2) == 0 ) {
		$("#tip-3").html(tipText);
		$("#tip-3").show();
		$("#tip_3").hide();
		return false;
	}
	
	if ($.trim(batchLimit) != "" && parseFloat($.trim(batchLimit)).toFixed(1) == 0 ) {
		$("#tip-3").html(tipText);
		$("#tip-3").show();
		$("#tip_3").hide();
		return false;
	}
	
	var i = batchRate.indexOf(".");
	// 是小数
	if (i != -1) {
		var after = batchRate.substring(batchRate.indexOf(".") + 2, batchRate.length);
		if (after.length > 2) {
			$("#tip-3").html("小数点后面最多只有两位数");
			$("#tip-3").show();
			$("#tip_3").hide();
			return false;
		}
	}

	if ($.trim(batchRate) == "") {
		$("#tip-3").html("");
		$("#tip-3").hide();
		$("#tip_3").show();
		return true;
	}

	if (batchRate == ".") {
		$("#batchRate").val("");
		$("#tip-3").hide();
		$("#tip_3").show();
		return false;
	}

	if (parseFloat(batchRate).toFixed(1) > 9.5) {
		$("#tip-3").html("折扣值不能大于9.5");
		$("#tip-3").show();
		$("#tip_3").hide();
		return false;
	}

	$("#tip-3").html("");
	$("#tip-3").hide();
	$("#tip_3").show();

	return true;
}

/**
 * 批量折扣输入框
 */
$("#batchRate").focus(function(){
	$(this).select();
	$("#tip-3").hide();
	$("#tip_3").show();
});

/**
 * 批量限购输入框
 */
$("#batchLimit").focus(function(){
	$(this).select();
	$("#tip-3").hide();
	$("#tip_3").show();
});

/**
 * 单品折扣输入框
 */
function autoSelectRate(rateInput){
	$(rateInput).select();
	$(rateInput).siblings(".absolute").find("span[id*=tips_]").hide();
	$(rateInput).siblings(".absolute").hide();
}

/**
 * 批量设置
 */
$("#setAll").click(function() {
	if (checkDiscount()) {
		var batchRate = $("#batchRate").val();
		var batchLimit = $("#batchLimit").val();
		
		$("tr[id*='selected_']").each(function(){
			if($.trim(batchRate) != "" && parseFloat(batchRate).toFixed(2) > 0){
				$(this).find("input[name='discount_rate']").val(parseFloat(batchRate).toFixed(2));
				var oriPrice = $(this).find("td[id*=price_]").html();
				var price = (parseFloat(batchRate).toFixed(2)/10*parseFloat(oriPrice).toFixed(2)).toFixed(2);
				$(this).find("td[id*=discount_]").html(price);
				if(!parseInt(price) || parseInt(price) < 1){
					$(this).find("span[id*=tips_]").html("折后价不能小于1元");
					$(this).find("span[id*=tips_]").show();
					$(this).find("div[id*=div_]").show();
				}else{
					$(this).find("span[id*=tips_]").hide();
					$(this).find("div[id*=div_]").hide();
				}
			}
			if($.trim(batchLimit) != "" && parseInt(batchLimit) > 0){
				$(this).find("input[name='discount_limit']").val(batchLimit);
			}
		});
	}
});

/**
 * 表单提交
 */
function checkUpdateForm(form, status){
	var result = true;
	// 验证第一步（非必要）
	if(parseInt(status) != 1){
		result = checkFirst();
	}
	if(!result){
		$("div[class='drape-block']").hide();
		$('#first').show();
		hideAndShow("#first");
		return;
	}
	
	// 验证第三步
	var flag = true;
	// 先验证，不符合直接返回
	$("tr[id*='selected_']").each(function(){
		var price = $(this).find("td[id*=discount_]").html();
		if(!parseInt(price) || parseInt(price) < 1){
			$(this).find("span[id*=tips_]").html("折后价不能小于1元");
			$(this).find("span[id*=tips_]").show();
			$(this).find("div[id*=div_]").show();
			flag = false;
		}
		var rate = $(this).find("input[name='discount_rate']").val();
		if($.trim(rate) == "" || parseFloat(rate).toFixed(2) < 0){
			$(this).find("span[id*=tips_]").html("折扣值不能为空");
			$(this).find("span[id*=tips_]").show();
			$(this).find("div[id*=div_]").show();
			flag = false;
		}
		if($.trim(rate) != "" && parseFloat(rate).toFixed(2) == 0){
			$(this).find("span[id*=tips_]").html("折扣值不能等于0");
			$(this).find("span[id*=tips_]").show();
			$(this).find("div[id*=div_]").show();
			flag = false;
		}
		if($.trim(rate) != "" && parseFloat(rate).toFixed(2) > 9.5){
			$(this).find("span[id*=tips_]").html("折扣值不能大于9.5");
			$(this).find("span[id*=tips_]").show();
			$(this).find("div[id*=div_]").show();
			flag = false;
		}
	});
	if(!flag){
		hideAndShow("#third");
		return;
	}
	if(!confirm("您确定要覆盖原先的活动设置吗？")){
		hideAndShow("#third");
		return;
	}
	// $("div[class='drape-block']").hide();
	// 后取值，避免无用操作
	var itemList = "";
	var discountList = "";
	var limitList = "";
	var itemCount = 0;
	
	$("tr[id*='selected_']").each(function(){
		var selectedItemId = $(this).find("input[name='selectedItemId']").val();
		if($.trim(selectedItemId) != ""){
			itemCount++;
			if($.trim(itemList) == ""){
				itemList = selectedItemId;
			}else
				itemList = itemList + "," + selectedItemId;
			var itemRate = $(this).find("input[name='discount_rate']").val();
			var itemLimit = $(this).find("input[name='discount_limit']").val();
			if($.trim(discountList) == ""){
				discountList = parseFloat($.trim(itemRate)).toFixed(2)*100;
			}else
				discountList = discountList + "," + parseFloat($.trim(itemRate)).toFixed(2)*100;
			if($.trim(itemLimit) == "") itemLimit = 0;
			if($.trim(limitList) == ""){
				limitList = itemLimit;
			}else
				limitList = limitList + "," + itemLimit;
		}
	});
	$("#itemList").val(itemList);
	$("#itemCount").val(itemCount);
	//$("#discountList").val(discountList);
	$("#limitList").val(limitList);
	
	$("#saveUpdate").attr("disabled", true);
	form.submit();
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
$("#searchItemButton").click(function(){
	var itemName = $("#itemName").val();
	var categoryId = $("#categoryId").val();
	var param = {'categoryId': $.trim(categoryId), 'itemName': $.trim(itemName)};
	refeshPages(param, $("#linkTable tr:gt(0)"), $("#linkTable"));
	$(this).blur();
});

$("#searchItemButton").dblclick(function(){
	return false;
});

$("#itemName").focus(function(){
	$("#query-tip").hide();
});

$("#searchItemButton").blur(function(){
	$("#query-tip").hide();
});


/**
 * 重新定义动态加载函数
 */
function ajaxCallBack(data) {
	$("#checkAll").attr("checked", false);
	var imageServer = $("#imageServer").val();
	var tbody = "";
	$("#linkTable tr:gt(0)").remove();
	var list = eval(data);
	if (list.length > 0) {
		$.each(list, function(i, item) {
			var trs = "";
			var activityId = $("#activityId").val();
			if (item.featuresMap["limitDiscount"] == undefined || item.featuresMap["limitDiscount"] == activityId) {
				trs += "<tr id='select_"
						+ item.id
						+ "'>"
						+ "<input type='hidden' id='data_"
						+ item.id
						+ "' value='"
						+ item.id
						+ "#"
						+ item.picUrl
						+ "#"
						+ (parseInt(item.price) / 100).toFixed(2)
						+ "' />"
						+ "<input type='hidden' id='title_"
						+ item.id
						+ "' value='"
						+ replaceHTMLTag(item.title)
						+ "'/>"
						+ "<td class=\"w5 center\"><input type='checkbox' name='itemBox' value='"
						+ item.id
						+ "' />"
						+ "</td><td><img src='"
						+ imageServer + item.picUrl 
						+ "_40x40.jpg' /><div class='title'><a href='/detail/" + item.id + ".htm ' target='_blank' class='lan'>"
						+ replaceHTMLTag(item.title)
						+ "</a></div>"
						+ "</td>"
						+ "<td class=\"w15 center\">"
						+ (parseInt(item.price) / 100).toFixed(2)
						+ "</td><td class=\"w15 center\">"
						+ "<input type='button' value='参加打折' id='singleButton_"
						+ item.id
						+ "' onclick='singleButtonClick(this)' class='pl-btn-juse'/></td></tr>";
			} else {
				trs += "<tr>"
						+ "<td class=\"w5 center\"><input type='checkbox' checked='checked' disabled='disabled'>"
						+ "</td><td><img src='"
						+ imageServer + item.picUrl
						+ "_40x40.jpg'/>"
						+ "<div class='title'><a href='/detail/" + item.id + ".htm ' target='_blank' class='lan'>"
						+ replaceHTMLTag(item.title)
						+ "</a></div>"
						+ "</td>"
						+ "<td class=\"w15 center\">"
						+ (parseInt(item.price) / 100).toFixed(2)
						+ "</td><td class=\"w15 center\">"
						+ "<input type='button' class='pl-btn-hui' value='已参加活动' disabled='disabled'/></td></tr>";
			}
			tbody += trs;
		});
		$("#linkTable").append(tbody);

		var itemList = $('#itemList').val();
		if ($.trim(itemList) != "") {
			var items = itemList.split(',');
			$("input[name='itemBox']").each(function() {
				var itemId = $(this).val();
				for (var i = 0; i < items.length; i++) {
					if (items[i] == $(this).val()) {
						$(this).attr('checked', true);
						$(this).attr('disabled', true);
						$(this).parent("td").parent("tr")
								.find("input[type='button']").val("不参加了");
						$(this).parent("td").parent("tr")
								.find("input[type='button']").removeClass("pl-btn-juse");
						$(this).parent("td").parent("tr")
								.find("input[type='button']").addClass("pl-btn-hui");
					}
				}
			});
		}
	}else{
		tbody = "<tr><td colspan=\"4\"><div style=\"text-align:center;color:red;\"><span>您没有可以参加此次活动的商品</span></div></td></tr>";
		$("#linkTable").append(tbody);
		$("#Pagination").hide();
	}
}

