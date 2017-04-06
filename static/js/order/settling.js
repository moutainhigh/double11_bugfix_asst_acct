/** 
 * Create on 2011-6-7
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * 买家结算页面专用(购物车页面结算)
 * 
 */
jQuery(document).ready(function($) {
	/**
	 * Create on 2011-6-7
	 * @author:[杜成]
	 * 加载页面时动态加载地区编码级联下拉框
	 */
	link = $('#province, #city, #district').linkage(pcd, {
		debug : true,
		links : $('#mprovince, #mcity, #mdistrict'),
		lastLink : '#mpcdCode'
	});
	
	/**
	 * Create on 2011-08-04
	 *   根据物流模板freeTemplateId，商品平邮费用、快递费用、EMS费用和当前用户选择的收货地址动态加载运费模板
	 */
	loadLogisticsTemp();
	
	//商品单价数组
	var orderCreationPrice = $('input[orderCreationPrice*=orderCreationPrice]');
	
	//单个商品的购买数量数组
	var buyNum = $('input[buyNum*=buyNum]');
		
	//商品总价
	var sumPrice = 0;
	
	//总物流运费
	var sumLogisticsPrice = eval($("input[id=sumLogisticsPrice]").val());
	
	for(var i=0;i<orderCreationPrice.length;i++){
		var num = Number(buyNum[i].value);
		var price = Number(orderCreationPrice[i].value);
		sumPrice = eval(sumPrice) + num*price;
	}
	$('#sumPrice').html(FormatNumber(sumPrice,2));
	$("input[id=sumPriceItem]").val(sumPrice);
	$('#anywayPrice').html(FormatNumber(sumPrice+sumLogisticsPrice,2));
	
	/**
	 * Create on 2011-08-04
	 *   计算运费
	 */
	callCarriage();
		
	/**
	 * Create on 2011-08-03
	 * 按单选按钮换色效果
	 */
	radioClickColor();
	
	/**
	 * Create on 2011-6-7
	 * 判断收货人信息是否有可选地址：
	 *       如果有则显示"使用其他地址",反之"请添加收获地址"且添加地址层显示
	 */
	isAddress();
	
	/**
	 * Create on 2011-08-04
	 * 为收货人信息中的单选按钮设置事件，如果商品物流模板ID不为空，
	 * 则运送方式下拉框中的选项是 “根据当前用户的选则的地址和商品物流模板ID查询得到”
	 */
	radioClick();
	
	/**
	 * Create on 2011-08-10
	 * 显示”修改“和“设为默认”
	 */
	showUpdate();
});


/**
 * Create on 2011-08-11
 * 异步加载收货地址
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function loadAddress(){
	$.getJSON("/orderBuyer/loadAddressAjax.htm?x=Math.Random()",function(json){
		if(json.deliveries.length <=0){
			isAddress();
			return;
		}
		
		$("#myTab").html("");
		var address="";
		address+='<ul>';
		for(i in json.deliveries){
		
			address+='<li>';
			address+="<input name='memberDeliveryId' checkName="+json.deliveries[i].id+" areaValue="+json.deliveries[i].pcdCode+" type='radio' value="+json.deliveries[i].id+" style='cursor:pointer;' onclick='radioClickColor()' title='选择地址'/>";
	
			address+='&nbsp;'+encode(json.deliveries[i].receiverName)+'，&nbsp;&nbsp;';
			address+='&nbsp;'+json.deliveries[i].province;
			address+='，'+json.deliveries[i].city;
			address+='，&nbsp;'+json.deliveries[i].district;
			address+='，'+encode(json.deliveries[i].address);
			address+='，&nbsp;'+json.deliveries[i].zipCode;
			
			if(json.deliveries[i].phone != null && json.deliveries[i].mobile != null){
				address+='，&nbsp;'+json.deliveries[i].phone+'，'+json.deliveries[i].mobile;
			}else if(json.deliveries[i].phone != null && json.deliveries[i].mobile == null){
				address+='，&nbsp;'+json.deliveries[i].phone;
			}else if(json.deliveries[i].phone == null && json.deliveries[i].mobile != null){
				address+='，&nbsp;'+json.deliveries[i].mobile;
			}
			if(json.deliveries[i].status == 1){
				address+='&nbsp;&nbsp;<span style="color:red">（默认）</span>';
			}
			
			address+='<span style="display:none;" id="addressSpan'+json.deliveries[i].id+'">&nbsp;&nbsp;<a href="#" class="lan" onmousedown="editAddress('+json.deliveries[i].id+','+json.deliveries[i].pcdCode+');" title="点击后编辑框中进行修改">修改</a>';
			if(json.deliveries[i].status == 0){
				address+='&nbsp;&nbsp;<a href="#" class="lan" onmousedown="defAddress('+json.deliveries[i].id+','+json.deliveries[i].pcdCode+');" title="将此条地址设为默认收货地址">&nbsp;&nbsp;设为默认</a>';
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
		}
		address+='</ul>';
		$("#myTab").append(address);
		
		showUpdate();
		isAddress();
		setSellect(updateId);
		/**
		 * Create on 2011-08-03
		 * 按单选按钮换色效果
		 */
		radioClickColor();
		loadLogisticsTemp();
		radioClick();
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
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * 显示地址编辑form并动态填入对应值
 */

//判断是添加还是修改参数(用于判断用户收货地址是否大于10条)
var idNotAdd;

//存储当前修改记录的ID
var updateId;

function editAddress(memberDeliveryId, pcdCode) {
	updateId = memberDeliveryId;
	
	idNotAdd = 1;	//修改状态
	setEditValue(memberDeliveryId, pcdCode);
	link.setValue(pcdCode);
	$("#editAddressTable").show();
}


/**
 * Create on 2011-6-7
 * 编辑默认地址
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function defAddress(memberDeliveryId,pcdCode){
	setEditValue(memberDeliveryId, pcdCode);
	link.setValue(pcdCode);
	$("#statusCheckbox").attr("checked","true");
	submitEditDelivery(2);
}


/**
 * Create on 2011-6-7
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * 动态填入要更新的卖家收货信息
 */
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
		$("#statusCheckbox").attr("checked","true");
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

/**
 * Create on 2011-6-7
 * 更新或添加收货地址异步递交
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function submitEditDelivery(flag) {
	//判断添加是否超出条数(限制10条)
	if(idNotAdd == 0){
		if($("input[name=memberDeliveryId]") == 11 ){
			 $("span[id=countspan]").css('display','block');
	    	return;
	    }
	}
	
	//收货人姓名
	var receiverName = $("input[memberDelivery=receiverName]").val();
	receiverName     = receiverName.replace(/\s+/g,"");
	//街道地址
	var address      = $("textarea[memberDelivery=address]").val();
	address          = address.replace(/\s+/g,"");
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
	if(address.length >= 200){
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
	var phoneTest =/^(?:[1-9][0-9]{5,7})?$/;
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
	$.post("/orderBuyer/orderDelivery.htm",date,function(json){
		alert(json.resultMessage);
		updateId = json.memberDelivery.id;  //将修改的数据和新增的信息数据单选按钮选中
		loadAddress();
		$("#editAddressTable").hide();
		clearForm();
	});
}


function clearForm(){
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
	$("#statusCheckbox").removeAttr("checked");
	$("select[id=province]")[0].selectedIndex = 0;
	$("select[id=city]")[0].selectedIndex = 0;
	$("select[id=district]")[0].selectedIndex = 0;
	$("#area").val("");
	$("#tel").val("");
	$("#ext").val("");
}

/**
 * Create on 2011-6-7
 * 把当前编辑的地址,设置为默认选中
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function setSellect(memberDeliveryId){
	var checkName = "input[checkName*="+memberDeliveryId+"]";
	$(checkName).attr('checked','checked');
}


/**
 * Create on 2011-6-7
 * 添加新地址
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function addAddress(){
	idNotAdd = 0;   //添加状态
	
	if($("input[name=memberDeliveryId]").size() == 11){
		$("span[id=countspan]").show();
		$("input[id=addNewAddress]").attr('disabled','disabled');
	   	return;
	}
	
	$("#editAddressTable").show();
	clearForm();
}


/**
 * Create on 2011-08-03
 * 按单选按钮换色效果
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function radioClickColor(){
	showUpdate(); //显示”修改“和“设为默认”
	
	$("input[name*=memberDeliveryId]").each(function(){
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
 * Create on 2011-08-03
 * 点击“取消”按钮，“使用其他地址”层隐藏
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function cancelAddAddress(){
	$("#editAddressTable").hide();
	$("#addNewAddress").removeAttr('checked');
	clearForm();
	//按单选按钮换色效果
	radioClickColor();
}


/**
 * Create on 2011-08-03
 * 判断收货人信息是否有可选地址：
 *       如果有则显示"使用其他地址",反之"请添加收获地址"且添加地址层显示
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function isAddress(){
	clearForm();
	if($("input[name=memberDeliveryId]").size() == 1 ){
		$("input[id=addNewAddress]").attr('checked','checked');
		$("span[id=otherAddress]").html("新增收货地址");
		$("#editAddressTable").show();
		//按单选按钮换色效果
		radioClickColor();
	}else{
		$("span[id=otherAddress]").html("使用新地址");
		$("#editAddressTable").hide();
		//按单选按钮换色效果
		radioClickColor();
	}
}

/**
 * Create on 2011-08-10
 * 显示”修改“和“设为默认”
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function showUpdate(){
	$("input[name*=memberDeliveryId]").each(function(){
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
 * Create on 2011-08-04
 *   根据物流模板freeTemplateId，商品平邮费用、快递费用、EMS费用和当前用户选则的收货地址动态加载运费模板！！
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function loadLogisticsTemp(){
	//存放商品平邮费用、快递费用、EMS费用、运费模板TemplateId的隐藏层
	var myLi = $("div[id=myLi]");
	//平邮费用
	var postPrice;
	//快递费用
	var deliveryPrice;
	//EMS费用
	var emsPrice;
	//运费模板TemplateId
	var templateId;
	//动态下拉框参数
	var selectParam;
	//店铺ID
	var muchShopsId ='?';
	//订单金额(不含运费)
	var orderPrice ="";
	var itemId = "";
	
	//循环取值
	for(var i = 0; i < myLi.length; i++){
		//获得平邮费用
		postPrice     = $(myLi[i]).find("input[id=postPrice]").val();
		//获得平邮费用
		deliveryPrice = $(myLi[i]).find("input[id=deliveryPrice]").val();
		//获得EMS费用
		emsPrice      = $(myLi[i]).find("input[id=emsPrice]").val();
		//获得运费模板TemplateId
		templateId    = $(myLi[i]).find("input[id=templateId]").val();
		//动态下拉框参数
		selectParam   = $(myLi[i]).find("input[id=paramId]").val();
		//店铺ID
		id            = $(myLi[i]).find("input[id=shopId]").val();
		muchShopsId        += "&muchShopsId=".concat(id);
		
		orderPrice    += $(myLi[i]).find("input[id=itemPrice]").val() + ";";
		itemId    += $(myLi[i]).find("input[id=itemId]").val() + ";";
		
		//清空下拉框
		$("select[id=select"+selectParam+"]").remove();
		//平邮费用、平邮费用、EMS费用都不为空，且都不为0和运费模板TemplateId为空
		if((postPrice != "0" && postPrice != "") || (deliveryPrice != "0" && deliveryPrice != "") || (emsPrice != "0" && emsPrice != "")){
			var select="<select area='changes' id='select"+selectParam+"' onchange='callCarriage()'>";
			if(postPrice != "0" && postPrice != ""){
				select+="<option value='"+postPrice+"' much='0' logisticsMode='1'>平邮：￥"+FormatNumber(eval(postPrice)/100,2)+"元</option>";
			}
			if(deliveryPrice != "0" && deliveryPrice != ""){
				select+="<option value='"+deliveryPrice+"' much='0' logisticsMode='2'>快递：￥"+FormatNumber(eval(deliveryPrice)/100,2)+"元</option>";
			}
			if(emsPrice != "0" && emsPrice != ""){
				select+="<option value='"+emsPrice+"' much='0' logisticsMode='3'>EMS：￥"+FormatNumber(eval(emsPrice)/100,2)+"元</option>";
			}
			select+="</select>";
			$(select).appendTo($("div[id=mySelect"+selectParam+"]"));
		//平邮费用、平邮费用、EMS费用都为空，且都为0和运费模板TemplateId不为空
		}else if(templateId != "" && templateId != "0"){
			//获取当前放置下拉框的位置
			var selectIdCurrent = $(myLi[i]).find("input[id=paramId]").val();
			
			//地区名称
			var areaValue = "";
			$("input[name*=memberDeliveryId]").each(function(){
	  			if($(this).attr('checked') == 'checked'){
	  				areaValue  = $(this).attr('areaValue');
	  			}
	  		});
			
			if(areaValue=="add")
				return;
			
			var tempIdCurrent = $(myLi[i]).find("input[id=templateId]").val();
			
			
			//判断当前是否选中了收获地址和是否有默认地址
			if(areaValue == ""){
				//根据当前用户选中的地区 Ajax异步获默认费模板
				loadLogisDefaultTemp(tempIdCurrent,selectIdCurrent);
			}else{
				//根据当前用户选中的地区 Ajax异步获指定地区运费模板
				loadAddressLogsTemp(tempIdCurrent,areaValue,selectIdCurrent);
			}
			
			
		}else if( (postPrice == "0" || postPrice == "") && (deliveryPrice == "0" || deliveryPrice == "") && (emsPrice == "0" || emsPrice == "") && (templateId == "0" ||templateId == "")){
			var select ="<select area='changes' id='select"+selectParam+"' onchange='callCarriage()'>";
				select+="<option value='0' much='0' logisticsMode='4'>卖家承担运费</option>";
				select+="</select>";
			$(select).appendTo($("div[id=mySelect"+selectParam+"]"));
		}
	}
	loadShopCoupon(muchShopsId);
}

/**
 * 根据当前用户选中的地区 Ajax异步获指定地区运费模板
 * @param tempIdCurrent:商品物流模板ID
 * 		  areaValue:地区ID
 *        selectIdCurrent:下拉框的位置
 * */
function loadAddressLogsTemp(tempIdCurrent,areaValue,selectIdCurrent){
	//根据当前用户选中的地区 Ajax异步获指定地区运费模板
	$.getJSON("/logistics/areaLogisticsAjax.htm",{templateId:tempIdCurrent,areaId:areaValue,x:Math.random()},function(obj){
		//返回根据地区和模板ID查询得到的结果集长度
		var returnLength = obj.length;
		if(returnLength >= 1){
			var selectOption = "";
			selectOption +="<select area='changes' id='select"+selectIdCurrent+"' onchange='callCarriage()'>";
			for(var i =0; i < returnLength; i++){
				//运送方式类型
				var logisticsTypeId = obj[i].logisticsTypeId;
				//物流类型
				var logisticsType;
				//物流方式
				var logisticsMode;
				if(logisticsTypeId == 1){
					logisticsType = "平邮";
					logisticsMode = "1";
				}
				if(logisticsTypeId == 2){
					logisticsType = "快递";
					logisticsMode = "2";
				}
				if(logisticsTypeId == 3){
					logisticsType = "EMS";
					logisticsMode = "3";
				}
				
				/**
				 * 显示的价格
				 * */
				var post = eval(obj[i].areaCarriage)/100;
				var much  = eval(obj[i].areaCarriageIncrease)/100;
				
				/***
				 * 后台用到的价格
				 */
				var Newpost = eval(obj[i].areaCarriage);
				
				selectOption += "<option value='"+Newpost+"' much='"+obj[i].areaCarriageIncrease+"' logisticsMode ='"+logisticsMode+"'>"+logisticsType+"：￥"+FormatNumber(post,2)+"元（单件商品"+FormatNumber(post,2)+"元，每多加一件商品加收"+FormatNumber(much,2)+"元）</option>";
			}
			selectOption  +="</select>";
			$(selectOption).appendTo($("div[id=mySelect"+selectIdCurrent+"]"));
			//重新计算一次价格
			callCarriage();
		}else{
			//根据当前用户选中的地区 Ajax异步获默认费模板
			loadLogisDefaultTemp(tempIdCurrent,selectIdCurrent);
		}
	});
}



/**
 * 根据当前用户选中的地区 Ajax异步获取地区默认运费模板
 * @param tempIdCurrent:商品物流模板ID
 *        selectIdCurrent:下拉框的位置
 * */
function loadLogisDefaultTemp(tempIdCurrent,selectIdCurrent){
	// 根据当前用户选中的地区 Ajax异步获取地区默认运费模板
	$.getJSON("/logistics/logisticsCarriageAjax.htm",{templateId:tempIdCurrent,x:Math.random()},function(obj){
		var selectOption = "";
		selectOption +="<select area='changes' id='select"+selectIdCurrent+"' onchange='callCarriage()'>";
		for(var i =0; i < obj.length; i++){
			//运送方式类型
			var logisticsTypeId = obj[i].logisticsTypeId;
			
			//物流类型
			var logisticsType;
			//物流方式
			var logisticsMode;
				if(logisticsTypeId == 1){
					logisticsType = "平邮";
					logisticsMode = "1";
				}
				if(logisticsTypeId == 2){
					logisticsType = "快递";
					logisticsMode = "2";
				}
				if(logisticsTypeId == 3){
					logisticsType = "EMS";
					logisticsMode = "3";
				}
				
		    /**
			 * 显示的价格
			 * */
			var post = eval(obj[i].defaultCarriage)/100;
			var much  = eval(obj[i].carriageIncrease)/100;
			/***
			 * 后台用到的价格
			 */
			var Newpost = eval(obj[i].defaultCarriage);
			selectOption += "<option value='"+Newpost+"' much='"+obj[i].carriageIncrease+"' logisticsMode ='"+logisticsMode+"'>"+logisticsType+"：￥"+FormatNumber(post,2)+"元（单件商品"+FormatNumber(post,2)+"元，每多加一件商品加收"+FormatNumber(much,2)+"元）</option>";
		}
		selectOption  +="</select>";
		$(selectOption).appendTo($("div[id=mySelect"+selectIdCurrent+"]"));
		//重新计算一次价格
		callCarriage();
	});
}



/**
 * Create on 2011-08-04
 * 为收货人信息中的单选按钮设置事件，如果商品物流模板ID不为空，
 * 则运送方式下拉框中的选项是 “根据当前用户的选则的地址和商品物流模板ID查询得到”
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function radioClick(){
	$("input[name=memberDeliveryId]").click(function(){
  		if($(this).attr('checked') == 'checked'){
  			//如果单选“使用其他收货地址”，则返回
  			if($(this).val() == "add"){
  				return;
  			}
  			loadLogisticsTemp();
  		}
	});
}


/**
 * Create on 2011-08-04
 * 计算运费
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function callCarriage(){
	var carriagePrice=0;
	var muchPrice=0;
	$("select[area=changes]").each(function(){
		//取得当前选中多一件的运费
		muchPrice = eval($(this).find("option:selected").attr("much"));
		//取得当前下拉框的ID
		var id  = $(this).attr("id");
		//取得当前下拉框的父节点(也就是页面放置下拉框的DIV)
		var selectid = $("select[id="+id+"]").parent().attr("id");
		//取得购买数量
		var num = $("div[id="+selectid+"]").find("input[id="+selectid+"]").val();
		if(eval(num)>1){
			
			//计算“多一件”商品的运费
			var sums  = (eval(num) - 1) * eval(muchPrice);
			carriagePrice = carriagePrice + eval(sums)/100;
			var sumPrice = eval($(this).val())+eval(sums);
			$("input[id="+selectid+"Price]").val(sumPrice);
		}else{
			$("input[id="+selectid+"Price]").val($(this).val());
		}
		
		//当前选中的物流类型
		$("input[id="+selectid+"Mode]").val($(this).find("option:selected").attr("logisticsMode"));
		
		//把当前选中的运费累加
		carriagePrice += eval($(this).val()/100);
		
	});
	
	/**
	 * 优惠券金额
	 */
	var shopCouponPrice = 0
	var selectboxList = $('.shopCoupinClass');
	selectboxList.each(function() {
		shopCouponPrice += eval($(this).find("option:selected").attr('couponMoney'));
	});

	$("span[id=carriagePrice]").html("￥"+FormatNumber(carriagePrice,2));
	$("input[id=sumLogisticsPrice]").val(carriagePrice);
	//计算总价 = 总订单价格 + 物流运费 — 优惠券金额
	var sumPriceItem = eval($("input[id=sumPriceItem]").val());
	var sumLogisticsPrice = eval($("input[id=sumLogisticsPrice]").val());
	var anywayPrice = (sumPriceItem+sumLogisticsPrice)-(eval(shopCouponPrice)/100);
	$('#anywayPrice').html(FormatNumber(anywayPrice,2));
}



/**
 * ajax异步取得优惠券
 * shopId：10106(测试用)
 */
function loadShopCoupon(muchShopsId){
	var allCouponMap;
	var selectboxList = $('.shopCoupinClass');
	$.ajax({
		type: "post",
		timeout: 15000, 
	    cache: false,
	    dataType: 'json',
	    async : false,
		url: "/coupon/shopCoupon.htm".concat(muchShopsId),
		success: function(data){ 
			allCouponMap = data.tradeCouponMap;
			selectboxList.each(function(i) {
				var couponList = allCouponMap[$(this).attr('data-shop-id')];
				var price = $(this).attr('price');
				$(this).find("option").remove();
				for(var i=0; i < couponList.length; i++){
					var use = couponList[i].useCondition;
					if(use == null){
						use = 0;
					}
					if(eval(couponList[i].useCondition) <= eval(price)){
						$("<option couponid="+couponList[i].id+" value="+couponList[i].id+" couponMoney="+couponList[i].couponMoney+" term="+use+">省"+couponList[i].couponMoneyByYuan+"元:店铺优惠券</option>").appendTo(this);
					}
				}
				$("<option couponid='0' value='0' couponMoney='0'>无优惠</option>").appendTo(this);
			});
		}
 	});
	
	
	function changeOption() {
		var self = this;
		var id = $(this).attr('data-shop-id');
		var couponList = allCouponMap[id];
		// 保存已经被选中的couponId
		var selectedCouponId = {};
		selectboxList.each(function() {
			var option = $(this).find('option:selected');
			if (option && option.attr('couponid')) {
				selectedCouponId[option.attr('couponid')] = true;
			}
		});
		// 更新其它下拉框里的选项
		selectboxList.each(function() {
			// 当前下拉框跳过
			if (self == this) {
				return;
			}
			// 其它店铺优惠券跳过
			if ($(this).attr('data-shop-id') != id) {
				return;
			}
			var value = this.value;
			// 保留已选择的下拉框
			if (selectedCouponId[value]) {
				delete selectedCouponId[value];
			}
			var price = $(this).attr('price');
			// 清空选项
			$(this).find("option").remove();
			// 重新添加选项，不显示已经选择的选项
			for(var i=0; i < couponList.length; i++){
				var couponId = couponList[i].id;
				if(!selectedCouponId[couponId]){
					var use = couponList[i].useCondition;
					if(use == null){
						use = 0;
					}
					if(eval(couponList[i].useCondition) <= eval(price)){
						$("<option couponid="+couponId+" value="+couponId+" couponMoney="+couponList[i].couponMoney+" term="+use+">省"+couponList[i].couponMoneyByYuan+"元:店铺优惠券</option>").appendTo(this);
					}
				}
			}
			$("<option couponid='' value='0' couponMoney='0'>无优惠</option>").appendTo(this);
			// 重新选择
			this.value = value;
		});
	}
	selectboxList.change(function() {
		changeOption.call(this);
	});
	
	selectboxList.each(function(i) {
		if (i == 0) {
			$(this).find('option').eq(0).attr('selected', 'selected');
		} else {
			$(this).val(0);
		}
	});
	
	selectboxList.each(function(i) {
		if (i == 0) {
			changeOption.call(this);
		}
	});
}


/**
 * Create on 2011-6-7
 * 提交订单结算
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function subOrder(){
  		var flag = 0;
  		var deliveryId = ""; 
  		 $("input[name*=memberDeliveryId]").each(
  			function(){
  				if($(this).attr('checked')=='checked'){
  					deliveryId=$(this).val();
  				}
  		}); 
  		if(deliveryId == "" || deliveryId=="add"){
  			alert("请选择收货地址或添加使用其它地址");
  			flag = 1;
  		}
  		//var date = $("#crationOrder").formToArray(); 
  		$(".isNum").each(
  			function(i){
  				alert("请删除库存不足商品后再提交！");
  				flag = 1;
  		}); 
		if(flag == 1)
			return false;
		else if (confirm("您确认提交吗？")){
			$('input[sendDeliveryId*=sendDeliveryId]').val(deliveryId);
	   		//document.crationOrder.submit();
			return true;
		}
		return false;
}

/**
 * Create on 2011-08-03
 * 用js格式化数字(用于金额的处理)
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 *  注意：srcStr：金额   nAfterDot：小数位数
 *      eg: FormatNumber(168,2)=======>>>结果：168.00
 *      	FormatNumber(168.88,2)====>>>结果：168.88
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
	}else{
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

//取得字符串长度，一个中文字符长度为2
function getLength(s){
	if(s == "")
		return 0;
	var len = 0;
	for (i = 0; i < s.length; i++) {
		var c = s.substr(i, 1);
		var ts = escape(c);
		if (ts.substring(0, 2) == "%u") {
			len += 2;
		}else{
			if (ts.substring(0, 3) == "%D7") {
				len += 2;
			} else {
				len += 1;
			}
		}
	}
	return len;
}