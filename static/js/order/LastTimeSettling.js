/** 
 * Create on 2011-6-7
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * 立即购买页面专用
 * 
 */
jQuery(document).ready(function($) {
	//数字框校验(只能输入整数，且数字不能以 0 开头，最大长度为6位)
	$(".text-amount").each(function(){
		$(this).numeral({
			decimalPlace:0, 
	 		maxLength:6
		});
	});

	/**
	 * Create on 2011-6-7
	 * 加载页面时动态加载地区编码级联下拉框
	 */
	link = $('#province, #city, #district').linkage(pcd, {
		links : $('#mprovince, #mcity, #mdistrict'),
		lastLink : '#mpcdCode'
	});
	
	
	$("#tipBuyNum").hide();
	
	/**
	 * Create on 2011-08-03
	 * 根据购买类型判断购买类型，控制下拉框：
	 *    注意：
	 *      购买类型=200或100   一口价
	 *		购买类型=300或400   限时折扣
	 */
	loadSelect();
	
	/**
	 * Create on 2011-07-29
	 * Ajax异步加载物流运费模板
	 */
	queryAreaLogistics();
	
	/**
	 * Create on 2011-07-29
	 * Ajax异步加载物流运费模板，为单选按钮添加事件
	 */
	loadAreaLogistics();
	
	/**
	 * Create on 2011-07-29
	 * 选择优惠方案下拉框事件，改变购买数量的显示和隐藏
	 */
	selectFavo();
	
	/**
	 * Create on 2011-08-01
	 * 专用来不同情况下的价格
	 */
	sumPrice();
	
	/**
	 * Create on 2011-08-10
	 * 显示”修改“和“设为默认”
	 */
	showUpdate();
	
	/**
	 * Create on 2011-08-01
	 * 根据购买数量和折扣率改变商品价格
	 */
	$("input[id=guoMaiNum]").blur(function(){
		if($(this).val() == ""){
			alert("请正确输入购买数量,且购买数不能小于 1 件");
			$("input[id=guoMaiNum]").val(1);
		}
		loadSelect(); //加载下拉框
		sumPrice();
	});
	
	/**
	 * Create on 2011-08-03
	 * 判断收货人信息是否有可选地址：
	 *       如果有则显示"使用其他地址",反之"请添加收获地址"且添加地址层显示
	 */
	isAddress();
	
	/**
	 * Create on 2011-08-03
	 * 换色效果
	 */
	radioClickColor();
	
	/**
	 * Create on 2011-08-01
	 * 获取验证码
	 */
	// 将输入验证码字符转为大写
	$('#validateCode').keyup(function() {
	  var t = $(this);
	  t.val(t.val().toUpperCase());
	});

	// 更改验证码图片
	var changeCaptcha = function() {
	  var t = $('#captcha-img');
	  if($.browser.msie && $.browser.version == 6){
		// stupid ie6 !!!
		setTimeout(function() {
		  t.attr('src', t.attr('basesrc') + '&r=' + Math.random());
		}, 0);
	  } else {
		t.attr('src', t.attr('basesrc') + '&r=' + Math.random());
	  }
	  $('#validateCode').val('').focus();
	}
	$('#captcha-img').click(changeCaptcha);
	$('#captcha-change').click(changeCaptcha);
	
	getRadio();
	
});



////////////////////////////////////////处理收货地址开始//////////////////////////////////////////////////////////////////////////////////////////
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
		for(i in json.deliveries){
			var address="";
			address+="<li style='width:97%'>";
			address+="<input name='memberDeliveryId' checkName="+json.deliveries[i].id+" areaName="+json.deliveries[i].province+" areaValue="+json.deliveries[i].pcdCode+" type='radio' value="+json.deliveries[i].id+" style='cursor:pointer;' onclick='radioClickColor()' title='选择地址'/>&nbsp;";
			address+=encode(json.deliveries[i].receiverName)+"&nbsp;&nbsp;&nbsp;&nbsp;";
			address+=json.deliveries[i].province+"&nbsp;&nbsp;";
			address+=json.deliveries[i].city+"&nbsp;";
			address+=json.deliveries[i].district;
			address+=encode(json.deliveries[i].address)+'，';
			address+=json.deliveries[i].zipCode+"&nbsp;";
			
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
		radioVal = updateId;
		queryAreaLogistics();
		loadAreaLogistics();
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
		if($("input[name=memberDeliveryId]").size() == 11){
			$("#countspan").show();
 	    	return;
 	    }
	}
	
	//收货人姓名
	var receiverName = $("input[memberDelivery=receiverName]").val();
	receiverName   =   receiverName.replace(/\s+/g,"");
	//街道地址
	var address      = $("textarea[memberDelivery=address]").val();
	address   =   address.replace(/\s+/g,"");
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
	$.post("/orderBuyer/orderDelivery.htm?k=Math.Random()",date,function(json){
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
 * 
 * Create on 2011-6-7
 * 更新提交框的隐藏值
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function callEditDelivery(json){
	var hiddenZipCode      = "input[hidedenMemberDelivery*=" + json.memberDelivery.id + "zipCode]";
	var hiddenAddress      = "input[hidedenMemberDelivery*=" + json.memberDelivery.id + "address]";
	var hiddenReceiverName = "input[hidedenMemberDelivery*=" + json.memberDelivery.id + "receiverName]";
	var hiddenPhone        = "input[hidedenMemberDelivery*=" + json.memberDelivery.id + "phone]";
	var hiddenMobile       = "input[hidedenMemberDelivery*=" + json.memberDelivery.id + "mobile]";
	
	$(hiddenZipCode).val(json.memberDelivery.zipCode);
	$(hiddenAddress).val(json.memberDelivery.address);
	$(hiddenReceiverName).val(json.memberDelivery.receiverName);
	$(hiddenPhone).val(json.memberDelivery.phone);
	$(hiddenMobile).val(json.memberDelivery.mobile);
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
		$("#countspan").show();
		$("input[id=addNewAddress]").attr('disabled','disabled');
	   	return;
	}
	$("#editAddressTable").show();
	clearForm();
}

/**
 * Create on 2011-7-29
 * 取消新地址
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function cancelAddAddress(){
	$("#editAddressTable").hide();
	$("#addNewAddress").removeAttr('checked');
	
	clearForm();
	radioClickColor();
}

var radioVal;
function getRadio(){
	$("input[name*=memberDeliveryId]").each(function(){
		if($(this).attr('checked') == 'checked'){
			//如果单选“使用其他收货地址”，则返回
			if($(this).val() == "add"){
				return;
			}
			//买家ID
			radioVal = $(this).val();
		}
	});
}

/**
 * Create on 2011-08-03
 * 按单选按钮换色效果
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function radioClickColor(){
	showUpdate();    //显示“修改”和设为默认
	
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
		radioClickColor(); //变色效果
    }else{
    	$("span[id=otherAddress]").html("使用新地址");
		$("#editAddressTable").hide();
		radioClickColor(); //变色效果
    }
}

/**
 * Create on 2011-08-10
 * 显示”修改“和“设为默认”
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
//////////////////////////////////////处理收货地址结束//////////////////////////////////////////////////////////////////////////////////////////






/**
 * Create on 2011-07-29
 * 加载物流运费模板
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function queryAreaLogistics(){
	//平邮费用
	var mail           = $("input[id=mail]").val();
	//快递费用
	var delivery       = $("input[id=delivery]").val();
	//EMS费用
	var ems            = $("input[id=ems]").val();
	//物流运费模板ID
	var freeTemplateId = $("input[id=TemplateId]").val();
	//地区ID
	var areaValue = "";
	//地区名称
	var areaName = "";
	
	//平邮费用、快递费用、EMS费用都不为空的情况
	if((mail != "0" && mail != "") || (delivery != "0" && delivery != "") || (ems != "0" && ems != "")){
		$("#tempMethod").html("");
		$("#temp").html("以下运送方式供您选择"); 
		//判断是否为0，如为 0 则不显示
		if(mail != "0" &&　mail != ""){
			$("#tempMethod").append("<li style='float:none'><input type='radio' style='cursor:pointer;' logisticsMode='1' onclick='sumPrice()' name='radioname' value='"+mail+"' checked>平邮："+FormatNumber(eval(mail)/100,2)+"元</li>");
		}
		if(delivery != "0" && delivery != ""){
			$("#tempMethod").append("<li style='float:none'><input type='radio' style='cursor:pointer;' logisticsMode='2' onclick='sumPrice()' name='radioname' value='"+delivery+"'>快递："+FormatNumber(eval(delivery)/100,2)+"元</li>");
		}
		if(ems != "0" && ems != ""){
			$("#tempMethod").append("<li style='float:none'><input type='radio' style='cursor:pointer;' logisticsMode='3' onclick='sumPrice()' name='radioname' value='"+ems+"'>EMS："+FormatNumber(eval(ems)/100,2)+"元</li>");
		}
	//平邮费用、快递费用、EMS费用、物流运费模板ID都为空情况
	}else if((mail == "0" || mail == "") && (delivery == "0" || delivery == "") && (ems == "0" || ems == "") && (freeTemplateId == "" || freeTemplateId == "0")){
		$("#temp").html("卖家承担运费<input type='hidden' id='sell' value='0'>");
		$("#tempMethod").remove();
		
	//平邮费用、快递费用、EMS费用为空，且物流运费模板ID不为空情况(则根据商品物流模板ID和买家所在省份查询)
	}else{
		$("input[name*=memberDeliveryId]").each(function(){
  			if($(this).attr('checked') == 'checked'){
  				//如果单选“使用其他收货地址”，则返回
  				if($(this).val() == "add")
  					return;
  				//地区ID
  				areaValue  = $(this).attr('areaValue');
  				//地区名称
  				areaName   = $(this).attr('areaName');
  			}
		});
		
		//判断当前是否选中了收获地址和是否有默认地址
		if(areaValue == ""){
			/**
			 * Create on 2011-07-29
			 * 根据模板ID Ajax异步加载物流**默认**运费模板
			 */
			loadLogisticsDefaultTemp(freeTemplateId,areaName);
		}else{
			/**
			 * Create on 2011-07-29
			 *根据模板ID Ajax异步加 **指定地区** 载物流运费模板
			 */
			loadLogisticsTemp(freeTemplateId,areaValue,areaName);
		}
	}
}



/**
 * Create on 2011-07-29
 * Ajax异步加载物流运费模板，为单选按钮添加事件(根据地区ID)
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function loadAreaLogistics(){
	//物流运费模板ID
	var freeTemplateId = $("input[id=TemplateId]").val();
	//买家会员ID
	var memberDeliveryId;
	//地区ID
	var areaValue;
	//地区名称
	var areaName;
	
	//如果物流模板ID为空则返回
	if(freeTemplateId == "" || freeTemplateId == "0"){
		return;
	}

	$("input[name=memberDeliveryId]").click(function(){
		var isAdd;
		$("input[name*=memberDeliveryId]").each(function(){
  			if($(this).attr('checked') == 'checked'){
  				//如果单选“使用其他收货地址”，则返回
  				if($(this).val() == "add"){
  					isAdd = "add";
  					return;
  				}
  				//买家会员编号
  				memberDeliveryId = $(this).val();
  				
  				$("#memberDeliveryId").val(memberDeliveryId);
  				//地区ID
  				areaValue  = $(this).attr('areaValue');
  				//地区名称
  				areaName   = $(this).attr('areaName');
  			}
		});
		
		if(!areaValue || isAdd == "add"){
			return;
		}
		
		if(confirm("更换地址后您必须重新确认购买信息")){
			radioVal = memberDeliveryId;
			/**
			 * Create on 2011-07-29
			 * 根据模板ID Ajax异步加 **指定地区** 载物流运费模板
			 */
			loadLogisticsTemp(freeTemplateId,areaValue,areaName);
			
		}else{
			setSellect(radioVal);
			radioClickColor();
		}
		sumPrice();
	});
}


/**
 * Create on 2011-07-29
 * 根据模板ID Ajax异步加 **指定地区** 载物流运费模板
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * @parameter：freeTemplateId：模板ID
 *             areaValue:地区ID
 *             areaName:地区名称
 */
function loadLogisticsTemp(freeTemplateId,areaValue,areaName){
	$("#temp").html("");
	$("#tempMethod").html("");
	
	//Ajax异步调用方
	$.getJSON("/logistics/areaLogisticsAjax.htm",{templateId:freeTemplateId,areaId:areaValue},function(obj){
		//返回根据地区和模板ID查询得到的结果集长度
		var returnLength = obj.length;

		if(returnLength >= 1){
			// 加载指定地区运费
			for(var i=0; i< returnLength; i++){
				//运送方式类型
				var logisticsTypeId = obj[i].logisticsTypeId;
				
				var logisticsType;
				if(logisticsTypeId == 1){
					logisticsType = "平邮";
				}
				if(logisticsTypeId == 2){
					logisticsType = "快递";
				}
				if(logisticsTypeId == 3){
					logisticsType = "EMS";
				}
				var post = eval(obj[i].areaCarriage)/100;
				var much  = eval(obj[i].areaCarriageIncrease)/100;

				$("#temp").html("（至  "+areaName+"）");
				$("#tempMethod").append("<li style='float:none'><input type='radio' style='cursor:pointer;' logisticsMode='"+logisticsTypeId+"' onclick='sumPrice()' name='radioname' much='"+obj[i].areaCarriageIncrease+"' value='"+obj[i].areaCarriage+"'>"+logisticsType+"："+FormatNumber(post,2)+"元（单件商品"+FormatNumber(post,2)+"元，每多加一件商品加收"+FormatNumber(much,2)+"元）</li>");
			}
			//将第一个收货地址单选按钮选中
			var checkName = "input[name=radioname]";
			$(checkName).get(0).checked = true;
			sumPrice();
		}else{
			/**
			 * Create on 2011-07-29
			 * 根据模板ID Ajax异步加载物流**默认**运费模板
			 */
			loadLogisticsDefaultTemp(freeTemplateId,areaName);
		}	
	});
}

/**
 * Create on 2011-07-29
 * 根据模板ID Ajax异步加载物流**默认**运费模板
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 * @parameter：freeTemplateId：模板ID
 *             areaName:地区名称
 */
function loadLogisticsDefaultTemp(freeTemplateId,areaName){
	// 加载默认地区运费
	$.getJSON("/logistics/logisticsCarriageAjax.htm",{templateId:freeTemplateId},function(obj){
		for(var i=0; i <obj.length; i++){
			//运送方式类型
			var logisticsTypeId = obj[i].logisticsTypeId;
			
			var logisticsType;
			if(logisticsTypeId == 1){
				logisticsType = "平邮";
			}
			if(logisticsTypeId == 2){
				logisticsType = "快递";
			}
			if(logisticsTypeId == 3){
				logisticsType = "EMS";
			}
			
			var post  = eval(obj[i].defaultCarriage)/100;
			var much  = eval(obj[i].carriageIncrease)/100;
			
			if(areaName !=""){
				$("#temp").html("（至  "+areaName+"）");
			}else{
				$("#temp").html("以下运送方式供您选择"); 
			}
			$("#tempMethod").append("<li style='float:none'><input type='radio' style='cursor:pointer;' logisticsMode='"+logisticsTypeId+"' onclick='sumPrice()' name='radioname' much='"+obj[i].carriageIncrease+"' value='"+obj[i].defaultCarriage+"'>"+logisticsType+"："+FormatNumber(post,2)+"元（单件商品"+FormatNumber(post,2)+"元，每多加一件商品加收"+FormatNumber(much,2)+"元）</li>");
		}
		//将第一个收货地址单选按钮选中
		var checkName = "input[name=radioname]";
		$(checkName).get(0).checked = true;
		sumPrice();
	});
}


/**
 * Create on 2011-07-29
 * 选择优惠方案下拉框事件，改变购买数量的显示和隐藏
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function selectFavo(){
	//下拉框事件
	$("select[id=selectFavo]").change(function(){
		var _val = $("select[id=selectFavo]").val();
		
		//判断是否加载店铺优惠券
		if(_val == 200){
			loadShopCoupon();
		}else{
			$("select[id=shopCouponSelect]").find("option").remove();
			$("<option value='0' couponMoney ='0'>无优惠</option>").appendTo("select[id=shopCouponSelect]");
		}
		sumPrice();
	});
}

/**
 * Create on 2011-08-01
 * 专用来不同情况下的价格
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function sumPrice(){
	//库存数量
	var kuCunNum   = $("input[name=kuCunNum]").val();
	//商品价格
	var itemPrice  = $("input[name=itemPrice]").val();
	//购买数量
	var guoMaiNum  = $("input[id=guoMaiNum]").val();
	//限购数量
	var xianGuoNum = $("input[name=xianGuoNum]").val();
	//物流运费
	var postPrice  = 0;
	//判断是一口价还是限时折扣
	var favo;
	//得到优惠券的金额
	var shopCouponPrice = 0;
	//优惠券条件
	var shopCouponTerm = 0;
	//折扣率
	var discount   = eval($("#dicountRate").val())/1000;
	//多一件的商品的运费
	var muchPostPrice = 0;
	//物流类型：1、平邮  2、快递  3、EMS 4、卖家
	var logisticsMode = 4;
		
	$("input[name=radioname]").each(function(){
		if($(this).attr('checked') == 'checked'){
			//物流运费
			postPrice     = $(this).attr('value');
			//多一件的商品的运费
			muchPostPrice = $(this).attr('much');
			
			logisticsMode = $(this).attr('logisticsMode');
		}
	});
	
	//运费 + 多一件上商品的运费价格（专用计算物流运费）
	var muchPrice = 0;
	//如果多一件
	if(muchPostPrice){
		if(eval(guoMaiNum) > 1 ){
			//计算“多一件”商品的运费
			var num  = (eval(guoMaiNum) - 1) * eval(muchPostPrice);
			muchPrice = eval(num) + eval(postPrice);
		}else{
			//计算“一件”的运费
			muchPrice = eval(postPrice);
		}
	}else{
		//计算“一件”的运费                
		muchPrice = eval(postPrice);
	}
	
	//物流运费
	$("#logisticsPrice").val(muchPrice);
	
	//物流类型
	if(logisticsMode == "4"){
		$("#logisticsMode").val(4);
	}else{
		$("#logisticsMode").val(logisticsMode);
	}
	
	//折扣
	$("select[id=selectFavo]").each(function(){
		favo=$("select[id=selectFavo]").val();
	});
	
	//店铺优惠券
	$("select[id=shopCouponSelect]").each(function(){
		shopCouponPrice = $(this).find("option:selected").attr('couponMoney');
		shopCouponTerm  = $(this).find("option:selected").attr('term');
	});
	
	if(shopCouponPrice == 0 || shopCouponTerm == 0){
		$("span[name=shopCouponTip]").html("");
	}else{
		$("span[name=shopCouponTip]").html("订单满"+FormatNumber(shopCouponTerm/100,2)+"元(不包含邮费)");
	}
	
	//限时打折(300是限时打折,400是团购)
	if(favo == "300" || favo == "400"){
		if(eval(discount) != 0){
			//总价格
			var sum  = eval(discount) * eval(itemPrice) * eval(guoMaiNum) + eval(muchPrice);
			$("strong[class=cheng]").html(FormatNumber(eval(sum)/100,2));
		}else{
			//总价格
			var sum  = (eval(itemPrice)* eval(guoMaiNum) + eval(muchPrice)) - eval(shopCouponPrice);
			$("strong[class=cheng]").html(FormatNumber(eval(sum)/100,2));
		}
		if(xianGuoNum != "0"){
			$("span[id=disNum]").html("（限购 "+xianGuoNum+" 件，库存 "+kuCunNum+" 件）");
			$("span[name=buyHong]").html("限时打折订单必须在下单后30分钟内完成支付，超时则订单关闭");
			$("span[name=buyHong]").show();
		}else{
			$("span[id=disNum]").html("（库存"+kuCunNum+" 件）");
			$("span[name=buyHong]").html("");
			$("span[name=buyHong]").hide();
		}
		
	//无优惠(200无优惠)
	}else{
		//总价格
		var sum  = (eval(itemPrice) * eval(guoMaiNum) + eval(muchPrice)) - eval(shopCouponPrice);
		$("strong[class=cheng]").html(FormatNumber(eval(sum)/100,2));
		$("span[id=disNum]").html("（库存"+kuCunNum+" 件）");
		$("span[name=buyHong]").html("");
		$("span[name=buyHong]").hide();
	}
}


/**
 * Create on 2011-08-03
 * 判断购买类型，控制下拉框：
 *    注意：
 *      购买类型=200或100   一口价
 *		购买类型=300       限时折扣
 *      购买类型=400       团购
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 *
	<option>限时打折</option>
	<option>无优惠</option>
 */
function loadSelect(){
	//购买类型
	var bussinessType = $("#bussinessType").val();
	//商品价格
	var itemPrice     = eval($("input[name=itemPrice]").val())/100;
	//购买数量
	var guoMaiNum     = $("input[id=guoMaiNum]").val();
	//折扣率
	var discount      = eval($("input[id=dicountRate]").val())/1000;
	//清空下拉框
	$("select[id=selectFavo]").find("option").remove();
	
	$("select[id=shopCouponSelect]").find("option").remove();
	$("<option value='0' couponMoney ='0'>无优惠</option>").appendTo("select[id=shopCouponSelect]");
	
	//动态创建下拉框
	if(bussinessType=="" || bussinessType=="200" || bussinessType=="100"){
		$("<option id='disKo' value='200' discount='1' selected>无优惠</option>").appendTo("select[id=selectFavo]");
		loadShopCoupon();
	}else if(bussinessType=="300"){
		var stint = (guoMaiNum*itemPrice) - (eval(itemPrice) * eval(guoMaiNum) * eval(discount));
		$("<option id='disTime' value='300' selected >省"+FormatNumber(stint,2)+"元:限时打折</option>").appendTo("select[id=selectFavo]");
		$("<option id='disKo' value='200' discount='1'>无优惠</option>").appendTo("select[id=selectFavo]");
	}else if(bussinessType=="400"){
		if(eval(discount) == 0 || discount == ""){
			$("<option id='disKo' value='400' discount='1'>无优惠</option>").appendTo("select[id=selectFavo]");
			loadShopCoupon();
		}else{
			var stint = (guoMaiNum*itemPrice) - (eval(itemPrice) * eval(guoMaiNum) * eval(discount));
			$("<option id='disTime' value='400' selected >省"+FormatNumber(stint,2)+"元:限时打折</option>").appendTo("select[id=selectFavo]");
			$("<option id='disKo' value='200' discount='1'>无优惠</option>").appendTo("select[id=selectFavo]");
		}
	}
}

/**
 * ajax异步取得优惠券
 * shopId：10106(测试用)
 */
function loadShopCoupon(){
	var shopId = $("#shopId").val();  // 店铺ID
	if(shopId == "" || shopId == undefined){
		return;
	}
	
	//商品价格
	var itemPrice  = $("input[name=itemPrice]").val();
	//购买数量
	var guoMaiNum  = $("input[id=guoMaiNum]").val();
	//折扣率
	var discount   = eval($("#dicountRate").val())/1000;
	//折扣
	$("select[id=selectFavo]").each(function(){
		favo=$("select[id=selectFavo]").val();
	});
	var orderSumPrice = 0;
	//限时打折(300是限时打折,400是团购)
	if(favo == "300" || favo == "400"){
		if(eval(discount) != 0){
			//订单总价格(除去运费)
			orderSumPrice  = eval(discount) * eval(itemPrice) * eval(guoMaiNum);
		}else{
			//订单总价格(除去运费)
			orderSumPrice  = eval(itemPrice)* eval(guoMaiNum);
		}
	}else{
		//订单总价格(除去运费)
		orderSumPrice  = eval(itemPrice) * eval(guoMaiNum);
	}
	
	$.ajax({
		type: "post",
		timeout: 10000000, 
	    cache: false,
	    dataType: 'json',
	    data:{shopId:shopId},
	    async : false,
		url: "/coupon/buyerAjaxGetCoupon.htm",
		success: function(data){ 
			var dataLength = data.tradeCouponList;
			$("select[id=shopCouponSelect]").find("option").remove();
			for(var i=0; i < dataLength.length; i++){
				var use = dataLength[i].useCondition;
				if(use == null){
					use = 0;
				}
				if(eval(orderSumPrice) >= eval(dataLength[i].useCondition)){
					$("<option value="+dataLength[i].id+" couponMoney ="+dataLength[i].couponMoney+" term="+use+">省"+dataLength[i].couponMoneyByYuan+"元:店铺优惠券</option>").appendTo("select[id=shopCouponSelect]");
				}
			}
			$("<option value='0' couponMoney ='0'>无优惠</option>").appendTo("select[id=shopCouponSelect]");
		}
 	});
}



/**
 * Create on 2011-08-03
 * 用js格式化数字(用于金额的处理)
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

function showTextarea(){
	$("#showText").show();
	$("#showText").focus();
	$("#hideText").hide();
}

function hideTextarea(){
	if($("#showText").val() == ""){
		$("#showText").hide();
		$("#hideText").show();
	}else{
		$("#showText").show();
		$("#hideText").hide();
	}
}


/**
 * Create on 2011-08-01
 * 提交表单，生成订单
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
function subOrder(){
	var isCheck= false;
	//库存数量
	var kuCunNum     = $("input[name=kuCunNum]").val();
	//用户输入购买数量
	var guoMaiNum    = $("input[id=guoMaiNum]").val();
	//限购数量
	var xianGuoNum = $("input[name=xianGuoNum]").val();
	//已购买数量
	var yiGuoNum = $("#yiGuoNum").val();
	//验证码
	var checkCode = $("#validateCode").val();
	//总数量=用户输入购买数量+已购买数量
	var zongNum = eval(guoMaiNum)+eval(yiGuoNum);
	//卖家留言
	var leaveWord = $("textarea[id=showText]").val();
	
	$("#tipBuyNum").html("");
	$("#tipBuyNum").hide();

	//买家ID
	var memberDeliveryId;
	$("input[name*=memberDeliveryId]").each(function(){
  		if($(this).attr('checked') == 'checked'){
  			//买家会员编号
  			memberDeliveryId = $(this).val();
  		}
	});
	
	//物流运费
	var postPrice;
	$("input[name=radioname]").each(function(){
		if($(this).attr('checked') == 'checked'){
			postPrice = $(this).attr('value');
		}
	});
	
	//判断是限时折扣还是一口价
	var isDiscount = $("#selectFavo").val();
	//判断是卖家承担运费还是客户选择运费(如果客户选择运费，则要判断客户是否选择了运费？)
	var sell = $("input[id=sell]").val();
	//只能购买的数量
	var canNum = eval(xianGuoNum)-eval(yiGuoNum);
	if(eval(xianGuoNum) > eval(kuCunNum) && eval(guoMaiNum) > eval(kuCunNum)){
		$("#tipBuyNum").html("购买数量不能大于库存");
		$("#tipBuyNum").show();
		return false;
	}else if((zongNum > eval(xianGuoNum)) &&　xianGuoNum!= "0" && yiGuoNum != "0" && (isDiscount=="300" || isDiscount=="400")){
		$("#tipBuyNum").html("本商品限购 "+xianGuoNum+" 件，您已购买了 "+yiGuoNum+" 件，您只能再购买 "+canNum+" 件");
		$("#tipBuyNum").show();
		return false;
	}else if(!memberDeliveryId || memberDeliveryId =="add"){
		alert("请选择或添加“收货地址”！");
		return false;
	}else if(guoMaiNum == ""){
		alert("请正确输入购买数量！");
		return false;
	}else if((eval(guoMaiNum) > eval(xianGuoNum)) &&　xianGuoNum != "0" && (isDiscount=="300" || isDiscount=="400")){
		$("#tipBuyNum").html("购买数量不能大于限购数量");
		$("#tipBuyNum").show();
		return false;
	}else if((eval(guoMaiNum) > eval(kuCunNum)) && (isDiscount=="200" || isDiscount=="400")){
		$("#tipBuyNum").html("购买数量不能大于库存");
		$("#tipBuyNum").show();
		return false;
	}else if(!postPrice &&　!sell){
		alert("请选择一种运送方式！");
		return false;
	}else if(leaveWord.length　> 100){
		alert("给卖家留言只能在 100 个字符以内！");
		$("textarea[id=showText]").focus();
		$("textarea[id=showText]").select();
		return false;
	}else if(checkCode==""){
		alert("请输入验证码！");
		return false;
	}else if(checkCode.length < 4){
		alert("验证码格式不正确，应是 4 个字符");
		return false;
	}else{
		$("#tipBuyNum").hide();
		//重新计算价格
		sumPrice();
		$("#memberDeliveryId").val(memberDeliveryId);
		isCheck = ajaxCheckCode();
		if(isCheck){
			$('#btn').attr("disabled",true);
			return true;
		}
		if($("#btn").attr("disabled") == "disabled"){
			$('#btn').attr("disabled",false);
		}
		return false;
	}
}

/**
 * Create on 2011-09-14
 * 异步校验 验证码
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
var ajaxCheckCode = function(){
	var result = true;  
    var sid = $("#sid").val();
    var code = $("#validateCode").val();
 	$.ajax({
		type: "post",
		timeout: 10000000, 
	    cache: false,
	    dataType: 'json',
	    async : false,
		url: "${base}/orderBuyer/ajaxcheckcode.htm?sid=".concat(sid).concat("&validateCode=").concat(code).concat("&version=").concat(new Date().getTime()),
		success: function(responseData){ 
			if(responseData!=null && responseData!=""){
			   if(responseData["error"]!=undefined){
			       alert(responseData["error"]);
			       var t=$('#captcha-img');
				   t.attr('src',t.attr('basesrc')+'&r='+Math.random());
				   result = false;
			   }
			}
		}
 	});
 	return result;
}