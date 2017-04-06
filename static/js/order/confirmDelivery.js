$(document).ready(function () {
	//弹出的发货层 点击选中变红
	$("#logisCorp span").live("click",function(){
		$("#logisCorp span").removeClass("h");								   
		$(this).addClass("h");
	})
	
	//弹出的发货层 点击要搜索的字母 变红
	$(".select-wl span").click(function(){
		$(".select-wl span").removeClass("h");								   
		$(this).addClass("h");
	
	})
	
	//加载物流公司数据
	loadLogisticsCorp();
	
	//加载已设置为默认物流的物流公司
	loadDefLogisCorp();
	
	if (document.getElementById("addNewAddress").checked) {
		 $("#editAddressTable").css('display','block');
	} else {
		 $("#editAddressTable").css('display','none');
	}
	
	//新增地址时 城市下拉选择框联动
	link = $('#province, #city, #district').linkage(pcd, {
		debug : true,
		links : $('#mprovince, #mcity, #mdistrict'),
		lastLink : '#mpcdCode'
	});

	//显示修改和设为默认
	showUpdate();
	
	//按单选按钮换色效果
	radioClickColor();
	
	//判断收货地址是否超过10条
	checkAddress();
	
	//判断收货人信息是否有可选地址
	isAddress();
});

//切换tab标签
function switchTab(status) {
	if (status == 1) {
		$("#ltab").show();
		$("#no-wl").hide();
		$("#tabB").removeClass("pl-here");
		$("#tabA").addClass("pl-here");
	} else {
		$("#ltab").hide();
		$("#no-wl").show();
		$("#tabA").removeClass("pl-here");
		$("#tabB").addClass("pl-here");
	}
}

function showLogisCorpDiv() {
	//显示物流公司 层
	$("#wl-pop").modal({closeClass:"close",minHeight:"380",zIndex:2000});
}

var otherLogisType = "5";
var otherLogisCode = "otherCorp";
var noLogisType = "6";
var noLogisCode = "noLogisCorp";

//所有物流公司list
var logisList;
//当前页的list
var curList;
//当前页要显示的list
var curListDis;
//总页数
var pageCount=0;
//每页显示数
var pageSize = 24;
//当前页数
var curPage = 0;

//加载物流公司list
function loadLogisticsCorp() {
	$.ajax({
	  	type: "post",
	  	url: "orderSeller/getLogisticsCorp.htm",
	  	async :false,
	  	success: function(corpList){
	  		$.each(corpList,function (i,e) {
	  			logisList = corpList;
	  			curList = corpList;
			});
	  	}
	});
	operPage(1,logisList);
}

/**
 * 发货层 计算分页信息以及要显示的list 
 * 	curPageNum:当前页数
 * 	len:总记录数
 * 	list:需要显示的list
 */ 
function operPage(curPageNum,list) {
	curPage = curPageNum;
	pageCount = Math.floor((list.length+pageSize-1)/pageSize);
	var startx = pageSize * (curPage - 1);
	var endx = pageSize*curPage;
	var j = 0;
	curListDis=[];
	//alert(startx + "\t" +endx);
	for (i=startx; i<endx; i++) {
		if (i < list.length) {
			curListDis[j] = list[i];
			j++;
		}
	}
	isShowPaginator();
	addlogisCorp();
}

//发货层 把物流公司内容设置到层中
function addlogisCorp() {
	var corpHtml="";
	$.each(curListDis,function (i,e) {
		if (i == 0) {
			corpHtml+="<li><span class='h' corpCode='"+e.corpCode+"' logisType='"+e.logisticsType+"'>"+e.corpName+"</span></li>";
		} else {
			corpHtml+="<li><span  corpCode='"+e.corpCode+"' logisType='"+e.logisticsType+"'>"+e.corpName+"</span></li>";
		}
	});
	$("#logisCorp").html();
	$("#logisCorp").html(corpHtml);
}

//发货层 点击上一页 下一页的操作
function previousOrNext(status) {
	if (status == "pre" && curPage > 1) {
		operPage(curPage-1, curList);
	} else if (status == "next" && curPage < pageCount) {
		operPage(curPage+1, curList);
	}
}

//发货层  前一页 后一页 链接是否变灰
function isShowPaginator() {
	if (pageCount > 1) {
		if (curPage <= 1) {
			$("#pre").removeClass("l");								   
			$("#pre").addClass("gl");
			
			$("#next").removeClass("gr");								   
			$("#next").addClass("r");
		}
		if (curPage >= pageCount) {
			$("#next").removeClass("r");								   
			$("#next").addClass("gr");
			
			$("#pre").removeClass("gl");								   
			$("#pre").addClass("l");
		}
	} else {
		$("#next").removeClass("r");								   
		$("#next").addClass("gr");
		
		$("#pre").removeClass("l");								   
		$("#pre").addClass("gl");
	}
	
	$("#pageDis").text(curPage+"/"+pageCount);
}

//发货层 根据key搜索物流公司
function searchCorp(searchParam) {
	var j = 0;
	curList=[];
	
	if (searchParam == "all") {
		curList = logisList;
	} else {
		var arr = searchParam.split(",");
		$.each(logisList,function (i,e) {
			var searchKey = e.memo.substr(e.memo.length-1);
			for (i=0; i<arr.length; i++) {
				if (searchKey == arr[i]) {
					curList[j] = e;
					j++;
				}
			}
		});
	}
	
	operPage(1, curList);
}

//获取弹出层 点击确定后选择的物流公司的值 
function selLogisCorp() {
	//$("ul[id='logisCorp'] li span").attr("class","h")
	var otherCorpName = $("#otherLogisCorp").val().trim();
	
	if (otherCorpName == "") {
		var corpCode = $("#logisCorp span[class='h']").attr("corpCode");
		var corpName = $("#logisCorp span[class='h']").text();
		var logisType = $("#logisCorp span[class='h']").attr("logisType");
		
		$("input[name='logistics.companyId']").val(corpCode);
		$("input[name='logistics.companyName']").val(corpName);
		$("input[name='logistics.logisticsType']").val(logisType);
		
		$("#selectedCorp").html(corpName+" <a class='wl-pop-btn' href='##' onclick='showLogisCorpDiv();'>重新选择</a>");
	} else {
		$("input[name='logistics.companyId']").val(otherLogisCode);
		$("input[name='logistics.companyName']").val(otherCorpName);
		$("input[name='logistics.logisticsType']").val(otherLogisType);
		
		$("#selectedCorp").html(otherCorpName+" <a class='wl-pop-btn' href='##' onclick='showLogisCorpDiv();'>重新选择</a>");
	}
	
	//关闭选择层
	$.modal.close();
}


/** 
* Create on 2011-7-14
* author 杜成
* 
* modify by caoxiao 2012-01-05
* 
* 卖家发货页面专用
* logisCode 发货物流公司code
* logisName 发货物流公司名称
* logisType 发货物流公司类型
* outLogisName 发货运单号
*/
//function confirmDelivery(logisticsCompanyCode,logisticsName){
function confirmDelivery(logisCode,logisName,logisType,outLogisName){
	
	var orderId = $("input[name='sendDelivery.orderId']").val();

	//输入的发货物流编号
	var outLogisId="";
	
	//买家ID
	var memberDeliveriesId = "";
	$("input[name*=memberDeliveryId]").each(function(){
  		if($(this).attr('checked') == 'checked'){
  			//买家会员编号
  			memberDeliveriesId = $(this).val();
  		}
	});
	
	//无需物流
	if (logisCode == "" && logisName == "" && logisType == "" && outLogisName == "") {
		logisCode = noLogisCode;
		logisName = "无需物流";
		logisType = noLogisType;
	} else {
		outLogisId = $("input[logisticsName='"+outLogisName+"']").val();
		
		if (logisCode == "" && logisName == "" && logisType == "") {
			logisCode = $("input[name='logistics.companyId']").val();
			logisName = $("input[name='logistics.companyName']").val();
			logisType = $("input[name='logistics.logisticsType']").val();
		} 
		
		if (memberDeliveriesId == "" || memberDeliveriesId=="add") {
			alert("请选择发货地址!");
			return;
		}
		
		if(outLogisName == "logisticsId"){
			if(logisCode == "" && logisName == "" && logisType == ""){
				alert("请选择物流公司!");
				return false;
			}
		}

		//买家选择的物流类型
		var orderOonsignmentMode = $("#consignmentMode").val();
		
		if (logisType <= 3 && orderOonsignmentMode != 4) {
			if (logisType != orderOonsignmentMode) {
				var msg="";			
				if (orderOonsignmentMode == 1) {
					msg="【平邮】";
				} else if (orderOonsignmentMode == 2) {
					msg="【快递】";
				} else if (orderOonsignmentMode == 3) {
					msg="【ems】";
				}
				alert("请使用买家选择的物流方式"+msg+"进行发货!");
				return false;
			}
		}
		
		if(outLogisId == ""){
			alert("请输入运单号码!");
			return false;
		}
	}
	
	//jquery Array自动转换不支持sendDelivery.orderId 的key,这里动手拼装(ducheng)
	var url = "${base}/confirmdelivery.htm";
	var validateUrl = "${base}/isOrderRefund.htm";
	var param = "?sendDelivery.orderId="+orderId+
				"&sendDelivery.outLogisticsId="+outLogisId+
				"&sendDelivery.logisticsType="+logisCode+
				"&sendDelivery.logisticsName="+logisName+
				"&sendDelivery.memberDeliveriesId="+memberDeliveriesId+
				"&sendDelivery.consignmentMode="+logisType;
	
	$.ajax({
		type: "POST",
		url: validateUrl+param,
		async: false,
		success: function(flag){
			if (flag) {
				if (confirm("强行发货并不会停止退款申请，如果您未与买家协商一致，请不要强行发货，否则很可能会造成您财货损失")) {
					sendCargo(url,param);
				}
			} else {
				sendCargo(url,param);
			}
		}
	}); 
}

function sendCargo(url,param) {
	$.ajax({
		type: "POST",
		url: url+param,
		async: false,
		success: function(msg){
			if(msg=='操作成功'){
				//alert("操作成功");
				window.location.href="/orderSeller/ordermanagesell.htm"; 
			} else {
				alert("订单已发货！");
				window.location.href="/orderSeller/ordermanagesell.htm"; 
			}
		}
	}); 
}

/**
 * Create on 2011-7-14
 * 设置默认收货地址
 * $(":radio:checked") 当前页面有多组radio时$(":radio:checked")会引起歧义
 * author 杜成
 */
function defDelivery(){
	var value = $(":radio:checked").val();
	$.post("/orderBuyer/setDefDelivery.htm",{deliveryId:value},function(result){
   		$('input[defMd*=defaultmemberDeliveries]').val(value);
   		$('li[defDeliveries*=deliveries] strong').remove();
		$('li[defDeliveries*=deliveries]').append("<strong></strong>");
		var name = "li[defDeliveries*="+value+"] strong";
		$('li[defDeliveries*=deliveries] strong').append($(name).html());
   		alert("更新成功");
  	});
}

/**
 * Create on 2011-7-29
 * 设置默认收货地址
 * author caoxiao
 
function defDeliveryByRadio(radioVal,memberDeliveriesDOId) {
	$('input[defMd*=defaultmemberDeliveries]').val(memberDeliveriesDOId);
}
*/

function delLogisticsById(logisticsId) {
	$("input[name='logistics.id']").val(logisticsId);
	$.ajax({
	  	type: "post",
	  	url: "orderSeller/delLogisticsSellerDeliveryById.htm",
	  	data: $('#del').serialize(),
	  	cache:false,
	  	success: function(data){
	  		loadDefLogisCorp();
		}
	});
}

function isSubmit() {
	if ($("input[name='logistics.companyId']").val() == "") {
		alert("请选择物流公司！");
	} else {
		$.ajax({
		  	type: "post",
		  	url: "orderSeller/isLogisticsSellerDevliery.htm",
		  	data: $('#add').serialize(),
		  	cache:false,
		  	success: function(data){
				if (data == "0") {
					addLogistics();
				} else if (data == "1") { 
					alert("物流信息不能超过10个");
				} else if (data == "2") { 
					alert("物流信息已存在");
				}
			}
		});
	}
}

function addLogistics() {
	$.ajax({
	  	type: "post",
	  	url: "orderSeller/addLogisticsSellerDelivery.htm",
	  	data: $('#add').serialize(),
	  	cache:false,
	  	success: function(data){
	  		loadDefLogisCorp();
		}
	});
}

function loadDefLogisCorp() {
	//清空 隐藏域 保存的 已选择的物流公司
	$("input[name='logistics.companyId']").val("");
	$("input[name='logistics.companyName']").val("");
	$("input[name='logistics.logisticsType']").val("");
	
	$("#ltab").html("<tbody><tr class='tr2'>"+
		    "<td>操作</td>"+
		    "<td>物流公司</td>"+
		    "<td>输入运单号码</td>"+
		    "<td>确认发货</td>"+
		  "</tr>"+
		"<tr>"+
		    "<td><a href='##' onclick='isSubmit();'>设为默认</a></td>"+
		    "<td id='selectedCorp'><a class='wl-pop-btn' href='##' onclick='showLogisCorpDiv();'>请选择</a></td>"+
		    "<td><input type='text' logisticsName='logisticsId' maxlength='50'/></td>"+
		    "<td><input type='button' class='pl-btn-juse'  style='cursor:pointer' onclick=\"confirmDelivery('','','','logisticsId');\" value='确认发货' /></td>"+
		  "</tr></tbody>");
	
	$.ajax({
	  	type: "post",
	  	url: "orderSeller/queryByMemberId.htm",
	  	cache:false,
	  	success: function(data){
	  		$.each(data,function (i,e) {
				$("#ltab").append(
					"<tr>" +
					"<td><a href='##' onclick='delLogisticsById("+e.id+");'>取消默认</a></td>" +
					"<td>"+e.companyName+"</td>" +
					"<td><input type='text' logisticsName='logisticsId"+i+"' maxlength='50'/></td>" +
					"<td><input type='button' class='pl-btn-juse'  style='cursor:pointer' " +
					"onclick=\"confirmDelivery('"+e.companyId+"','"+e.companyName+"','"+e.logisticsType+"','logisticsId"+i+"');\" value='确认发货'/></td>"
				);
			});
	  	}
	});
}


//判断是添加还是修改参数(用于判断用户收货地址是否大于10条)
var idNotAdd;

function editAddress(memberDeliveryId, pcdCode) {
	idNotAdd = 1;	//修改状态
	setEditValue(memberDeliveryId, pcdCode);
	link.setValue(pcdCode);
	$("#editAddressTable").show();
}

function defAddress(memberDeliveryId,pcdCode){
	setEditValue(memberDeliveryId, pcdCode);
	link.setValue(pcdCode);
	$("#statusCheckbox").attr("checked","checked");
	submitEditDelivery(2);
}

function setEditValue(memberDeliveryId, pcdCode) {
	var hiddenZipCode      = "input[hidedenMemberDelivery*=" + memberDeliveryId + "zipCode]";
	var hiddenAddress      = "input[hidedenMemberDelivery*=" + memberDeliveryId + "address]";
	var hiddenReceiverName = "input[hidedenMemberDelivery*=" + memberDeliveryId + "receiverName]";
	var hiddenPhone        = "input[hidedenMemberDelivery*=" + memberDeliveryId + "phone]";
	var hiddenMobile       = "input[hidedenMemberDelivery*=" + memberDeliveryId + "mobile]";
	var hiddenProvince       = "input[hidedenMemberDelivery*=" + memberDeliveryId + "province]";
	var hiddenCity       = "input[hidedenMemberDelivery*=" + memberDeliveryId + "city]";
	var hiddenDistrict      = "input[hidedenMemberDelivery*=" + memberDeliveryId + "district]";
	var hiddenStatus     = "input[hidedenMemberDelivery*=" + memberDeliveryId + "status]";
	
	$("textarea[memberDelivery*=address]").val($(hiddenAddress).val());
	$("input[memberDelivery*=zipCode]").val($(hiddenZipCode).val());
	$("input[memberDelivery*=receiverName]").val($(hiddenReceiverName).val());
	$("input[memberDelivery*=phone]").val($(hiddenPhone).val());
	$("input[memberDelivery*=mobile]").val($(hiddenMobile).val());
	$("input[memberDelivery*=deliveryId]").val(memberDeliveryId);
	$("input[memberDelivery*=pcdCode]").val(pcdCode);
	$("input[memberDelivery=province]").val($(hiddenProvince).val());
	$("input[memberDelivery=city]").val($(hiddenCity).val());
	$("input[memberDelivery=district]").val($(hiddenDistrict).val());

	if($(hiddenStatus).val() == "1"){
		$("#statusCheckbox").attr("checked","checked");
	}else{
		$("#statusCheckbox").removeAttr("checked");
	}

	//处理电话格式
	var _phone = $(hiddenPhone).val();
	$("#area").val("");
	$("#tel").val("");
	$("#ext").val("");
	
	var n=/(?:\((\d+)\))?(\d+)?(?:\*(\d+))?/g.exec(_phone);
	if(!n){
		return;
	}
	$('#area').val(n[1]);
	$('#tel').val(n[2]);
	$('#ext').val(n[3]);
}


function submitEditDelivery(flag) {
	//判断添加是否超出条数(限制10条)
	if(idNotAdd == 0){
		if($("input[name=memberDeliveryId]").size() == 11){
			$("#countspan").show();
 	    	return;
 	    }
	}
	
	//收货人姓名
	var receiverName = $("input[memberDelivery=receiverName]").val();
	//街道地址
	var address      = $("textarea[memberDelivery=address]").val();
	//邮政编码
	var zipCode      = $("input[memberDelivery=zipCode]").val();
	//电话号码
	var phone        = $("#tel").val();
	//区号
	var area         = $("#area").val();
	//手机号码
	var mobile       = $("input[memberDelivery=mobile]").val();
	//分机号                         
	var ext          = $("#ext").val();
	//所在地区   省份
	var province     = $("select[id=province]").val();
	//所在地区   城市
	var city         = $("select[id=city]").val();
	//所在地区  区
	var district     = $("select[id=district]").val();
	
	if(receiverName==""){
		alert("收货人姓名不能为空！");
		return;
	}
	
	if(receiverName.length < 2 ||　receiverName.length　> 20){
		alert("收货人姓名应在 2 到 20 个字符之间！");
		return;
	}

	if((province==null || province =="-1") && (city ==null || city == "-1")){
		alert("省份、城市不能为空！");
		return;
	}
	
	if(province==null || province =="-1"){
		alert("省份不能为空！");
		return;
	}
	
	if(city ==null || city == "-1"){
		alert("城市不能为空！");
		return;
	}
	
	if(address==""){
		alert("街道地址不能为空！");
		return;
	}
	
	if(address.length > 200){
		alert("街道地址应在 200 个字符以内！");
		return;
	}
	
	if(zipCode==""){
		alert("邮政编码不能为空！");
		return;
	}
	
	var chackCode = /^(?:(?:0[1-7]|[1-8][0-9])[0-9]{4})?$/;
	if(!chackCode.test(zipCode)){
		alert("邮政编码格式不正确！");
		return;
	}
	
	if(mobile=="" && phone == ""){
		alert("请输电话号码或手机号码！");
		$("#area").focus();
		return;
	}
	
	var checkArea = /^(?:0[1-9][0-9][0-9]?)?$/;
	if(area != ""){
		if(!checkArea.test(area)){
			alert("区号格式不正确！");
			return;
		}
	}
	
	/**
	 * 匹配格式： 
		11位手机号码 
		3-4位区号，7-8位直播号码，1－4位分机号 
		如：12345678901、1234-12345678-1234 
	 * */
	var phoneTest =/^(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	var mobileTest=/^(?:1[3458][0-9]{9})$/;

	if(phone != ""){
		if(!phoneTest.test(phone)){
			alert("电话号码格式不正确");
			return;
		}
	}
	
	var checkExt = /^(?:[0-9]{1,8})?$/;
	if(checkExt != ""){
		if(!checkExt.test(ext)){
			alert("分机号格式不正确！");
			return;
		}
	}
	
	if(mobile != ""){
		if(!mobileTest.test(mobile)){
			alert("手机号码格式不正确");
			return;
		}
	}
	
	var date = $("#editAddFrom").serializeArray();
	$.post("/orderBuyer/orderDelivery.htm?k=Math.Random()",date,function(json){
		updateId = json.memberDelivery.id;  //将修改的数据和新增的信息数据单选按钮选中
		loadAddress();
		$("#editAddressTable").hide();
		clearAddressForm();
	});
}


/**
 * 判断收货地址是否超过10条
 */
function checkAddress(){
	if($("input[name=memberDeliveryId]").size() == 11){
		$("#countspan").show();
		$("input[id=addNewAddress]").attr('disabled','disabled');
	}
}

function loadAddress(){
	$.getJSON("/orderBuyer/loadAddressAjax.htm?x=Math.Random()",function(json){
		if(json.deliveries.length <=0){
			isAddress();
			return;
		}
		
		$("#myTab").html("");
		for(i in json.deliveries){
			var address="";
			address+="<li>";
			address+="<input name='memberDeliveryId' checkName="+json.deliveries[i].id+" areaName="+json.deliveries[i].province+" areaValue="+json.deliveries[i].pcdCode+" type='radio' value="+json.deliveries[i].id+" style='cursor:pointer;' onclick='radioClickColor()' title='选择地址'/>";
			address+=encode(json.deliveries[i].receiverName)+"&nbsp;&nbsp;&nbsp;&nbsp;";
			address+=json.deliveries[i].province+"&nbsp;";
			address+=json.deliveries[i].city+"&nbsp;";
			address+=json.deliveries[i].district;
			address+=encode(json.deliveries[i].address)+'，';
			address+=json.deliveries[i].zipCode;
			
			if(json.deliveries[i].phone != null && json.deliveries[i].mobile != null){
				address+='，'+json.deliveries[i].phone+'，'+json.deliveries[i].mobile;
			}else if(json.deliveries[i].phone != null && json.deliveries[i].mobile == null){
				address+='，'+json.deliveries[i].phone;
			}else if(json.deliveries[i].phone == null && json.deliveries[i].mobile != null){
				address+='，'+json.deliveries[i].mobile;
			}
			if(json.deliveries[i].status == 1){
				address+='<span style="color:red">（默认）</span>';
			}
			
			address+='&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:none;" id="addressSpan'+json.deliveries[i].id+'"><a href="#" class="lan" onmousedown="editAddress('+json.deliveries[i].id+','+json.deliveries[i].pcdCode+');" title="点击后编辑框中进行修改">修改</a>';
			if(json.deliveries[i].status == 0){
				address+='<span style="margin-left:10px"><a href="#" class="lan" onmousedown="defAddress('+json.deliveries[i].id+','+json.deliveries[i].pcdCode+');" title="将此条地址设为默认收货地址">&nbsp;&nbsp;设为默认</a></span>';
			}
			address+='</span>';
			
			address+='<input type="hidden" value="'+json.deliveries[i].zipCode+'" hidedenMemberDelivery="'+json.deliveries[i].id+'zipCode"/>';
			address+='<input type="hidden" value="'+encode(json.deliveries[i].address)+'" hidedenMemberDelivery="'+json.deliveries[i].id+'address"/>';
			address+='<input type="hidden" value="'+encode(json.deliveries[i].receiverName)+'" hidedenMemberDelivery="'+json.deliveries[i].id+'receiverName"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].status+'" hidedenMemberDelivery="'+json.deliveries[i].id+'status"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].phone+'" hidedenMemberDelivery="'+json.deliveries[i].id+'phone"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].mobile+'" hidedenMemberDelivery="'+json.deliveries[i].id+'mobile"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].province+'" hidedenMemberDelivery="'+json.deliveries[i].id+'province"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].city+'" hidedenMemberDelivery="'+json.deliveries[i].id+'city"/>';
			address+='<input type="hidden" value="'+json.deliveries[i].district+'" hidedenMemberDelivery="'+json.deliveries[i].id+'district"/>';
			address+='</li>';
			
			$("#myTab").append(address);
		}
		isAddress();
		setSellect(updateId);
		//判断收货地址是否超过10条
		checkAddress();
		radioClickColor();
	});
}

//过滤html/js代码
var ENCODE = [
  {reg: /&/g, rep: '&amp;'},
  {reg: /</g, rep: '&lt;'},
  {reg: />/g, rep: '&gt;'},
  {reg: /"/g, rep: '&quot;'},
  {reg: / /g, rep: '&nbsp;'}
]

function encode(s) {
  for (var i = 0; i < ENCODE.length; i++) {
    s = s.replace(ENCODE[i]['reg'], ENCODE[i]['rep']);
  }
  return s;
}

/**
 * Create on 2011-6-7
 * 把当前编辑的地址,设置为默认选中
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function setSellect(memberDeliveryId){
	var checkName = "input[checkName*="+memberDeliveryId+"]";
	$(checkName).attr('checked','checked');
}

function clearAddressForm() {
	//清空表单(不能用$("#editAddFrom").clearForm()此方法清除)
	$("textarea[memberDelivery*=address]").val("");
	$("input[memberDelivery*=zipCode]").val("");
	$("input[memberDelivery*=receiverName]").val("");
	$("input[memberDelivery*=phone]").val("");
	$("input[memberDelivery*=mobile]").val("");
	$("input[memberDelivery*=deliveryId]").val("");
	$("input[memberDelivery*=pcdCode]").val("");
	$("input[memberDelivery=province]").val("");
	$("input[memberDelivery=city]").val("");
	$("input[memberDelivery=district]").val("");
	$("select[id=province]")[0].selectedIndex = 0;
	$("select[id=city]")[0].selectedIndex = 0;
	$("select[id=district]")[0].selectedIndex = 0;
	$("#statusCheckbox").removeAttr("checked");
	$("#area").val("");
	$("#tel").val("");
	$("#ext").val("");
}

/**
 * Create on 2011-7-29
 * 取消新地址
 * @author:[贺泳]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function cancelAddAddress(){
	$("#editAddressTable").hide();
	$("#addNewAddress").removeAttr('checked');
	clearAddressForm();
}

/**
 * Create on 2011-08-03
 * 按单选按钮换色效果
 * @author:[贺泳]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function radioClickColor(){
	showUpdate();
	$("input[name=memberDeliveryId]").each(function(){
		$(this).parent().css("background","").css("border","");
		if($(this).attr('checked') == 'checked'){
			if($(this).val() != "add"){
  				//将添加新地址层隐藏
  				$("#editAddressTable").hide();
			}
			//为当前行添加样式
			$(this).parent().css("background","#FFFFE0").css("border","1px solid #F9DFB2");
		}
	});
}

/**
 * Create on 2011-6-7
 * 添加新地址
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function addAddress(){
	$("#editAddressTable").show();
	clearAddressForm();
}

/**
 * Create on 2011-08-10
 * 显示”修改“和“设为默认”
 * @author:[贺泳]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function showUpdate(){
	$("input[name=memberDeliveryId]").each(function(){
		if($(this).attr('checked') == 'checked'){
			var addressId = $(this).val();
			$("span[id=addressSpan"+addressId+"]").show();
		}else{
			var addressId = $(this).val();
			$("span[id=addressSpan"+addressId+"]").hide();
		}
	});
}

/**
 * Create on 2011-08-03
 * 判断收货人信息是否有可选地址：
 *       如果有则显示"使用其他地址",反之"请添加收获地址"且添加地址层显示
 * @author:[贺泳]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function isAddress(){
	clearAddressForm();
	if($("input[name=memberDeliveryId]").size() == 1 ){
		$("input[id=addNewAddress]").attr('checked','checked');
		$("span[id=otherAddress]").html("新增发货地址");
		$("#editAddressTable").show();
		radioClickColor(); //变色效果
    }else{
    	$("span[id=otherAddress]").html("使用新地址");
		$("#editAddressTable").hide();
		radioClickColor(); //变色效果
    }
}
