$(document).ready(function(){
	//数字框校验
	$(".pinju-fee").each(function(){
		$(this).numeralDecimal({
			decimalPlace:2, 
	 		maxLength:6
		});
		
		
		$(this).change(function(){
			if(eval($(this).val()) > 999.99){
				$(this).val(999.99);
			}
		});
	});
	
	//未指定地区设置模板特效***********************************************************
	$('.add-area-pack').click(function() {
		
		//将所有的复选框不选中
		$("table input[type=checkbox]").each(function(){
		  $(this).attr("checked",false);
		});
	
	
		$('.transport-area').hide();
		$('.pay-info').css('z-index', '0');
		$(this).closest('.pay-info').css('z-index', '2').find('.transport-area').show();
	});
	$('.transport-area .cancel').click(function() {
		$(this).closest('.transport-area').hide().closest('.pay-info').css('z-index', 1);
	});
	//*********************************************************未指定地区设置模板特效结束**
	
	
	//返回模板列表
	$("#returnTempList").click(function(){
		location.href="/logistics/listTemplate.htm";
	});


	//保存模板**********************************************************************
	$("#saveTemplate").click(function(){
		var inputOk = true;
		//模板名称检查
		var _templateName = $("#templateName").attr("value");
	
		_templateName = $.trim(_templateName);
		$("#templateName").attr("value", _templateName);
		
		if(_templateName == "" || getLength(_templateName) > 50){
			$("#tipTemplateName").html("运费模板名称为必填项，并且不能超过25个字");
			$("#tipTemplateName").show();
			$("#tipTemplateNameExist").hide();
			inputOk = false;
			
		}else{
			$("#tipTemplateName").hide();
		}

		if(getLength($("#templateMemo").attr("value")) > 40){
			inputOk = false;
			$("#templateMemo").focus();
			$("#templateMemo").select();
			$("#tipTemplateMemo").show();
		}else{
			$("#tipTemplateMemo").hide();
		}
		
		//物流方式
		var noLogisticsType = true;
		$(".logisticsType").each(function(){
			if(this.checked){
				noLogisticsType = false;
			}
		});

		if(noLogisticsType){
			$("#tipLogisticsType").show();
			inputOk = false;
		}else{
			$("#tipLogisticsType").hide();
		}

		//默认运费与增加运费
		//平邮
		if($('#logisticsTypePost').attr("checked")){
			if($("#postFee").attr("value") == "" && $("#postFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipPostFee").html("运费和增加运费必须为有效值，且不得大于999.99元");
				$("#tipPostFee").show();
				$("#tipPostFeeIncrease").hide();	
				
			}else if($("#postFee").attr("value") == "" || eval($("#postFee").attr("value")) <= 0 ){
				inputOk = false;
				$("#tipPostFee").show();
				$("#tipPostFeeIncrease").hide();	
			}else if($("#postFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipPostFee").html("增加运费必须为有效值，且不得大于999.99元");
				$("#tipPostFee").show();
				$("#tipPostFeeIncrease").hide();	
			}else if(eval($("#postFeeIncrease").val()) > eval($("#postFee").val())){
				inputOk = false;
				$("#tipPostFee").hide();
				$("#tipPostFeeIncrease").show();
		    }else{
				$("#tipPostFee").hide();
				$("#tipPostFeeIncrease").hide();	
			}
		}
		
		//快递
		if($('#logisticsTypeCourier').attr("checked")){
			if($("#courierFee").attr("value") == ""&&$("#courierFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipCourierFee").html("运费和增加运费必须为有效值，且不得大于999.99元");
				$("#tipCourierFee").show();
				$("#tipCourierFeeIncrease").hide();	
			}else if($("#courierFee").attr("value") == "" || eval($("#courierFee").attr("value")) <= 0){
				inputOk = false;
				$("#tipCourierFee").show();
				$("#tipCourierFeeIncrease").hide();	
			}else if($("#courierFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipCourierFee").show();
				$("#tipCourierFee").html("增加运费必须为有效值，且不得大于999.99元");
				$("#tipCourierFeeIncrease").hide();	
			}else if(eval($("#courierFeeIncrease").val()) > eval($("#courierFee").val())){
				inputOk = false;
				$("#tipCourierFeeIncrease").show();
				$("#tipCourierFee").hide();
		    }else{
				$("#tipCourierFee").hide();
				$("#tipCourierFeeIncrease").hide();	
			}
		}
		
		//EMS
		if($('#logisticsTypeEMS').attr("checked")){
			if($("#emsFee").attr("value") == "" && $("#emsFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipEmsFee").html("运费和增加运费必须为有效值，且不得大于999.99元");
				$("#tipEmsFee").show();
				$("#tipEmsFeeIncrease").hide();	
			}else if($("#emsFee").attr("value") == "" || eval($("#emsFee").attr("value")) <= 0 ){
				inputOk = false;
				$("#tipEmsFee").show();
				$("#tipEmsFeeIncrease").hide();	
			}else if($("#emsFeeIncrease").attr("value") == ""){
				inputOk = false;
				$("#tipEmsFee").show();
				$("#tipEmsFee").html("增加运费必须为有效值，且不得大于999.99元");
				$("#tipEmsFeeIncrease").hide();	
			}else if(eval($("#emsFeeIncrease").val()) > eval($("#emsFee").val())){
				inputOk = false;
				$("#tipEmsFeeIncrease").show();
				$("#tipEmsFee").hide();
		    }else{
		    	$("#tipEmsFee").hide();
				$("#tipEmsFeeIncrease").hide();	
			}
		}
		
		//为指定地区设置运费(验证是否输入有效值)
		if(!getInfor()){
			inputOk = false;
		}
		
		
		if(inputOk){
			//模板名称
			var tempName = $.trim($("#templateName").val());
			//修改传来的模板名称
			var updTempName = $.trim($("#isTempName").val());
			
			/**
			 * 包含新增和修改
			 * */
			if(updTempName!=""){
				//修改
				if(tempName != updTempName){
					$.post("/logistics/checkTempName.htm",{templateName:tempName,x:Math.random()},function(data){
						if(!data){
							$("#tipTemplateNameExist").hide();
							$("#tipTemplateName").hide();
							//提交表单
							$('#form').submit(); 
						}
					});
				}else{
					$("#tipTemplateNameExist").hide();
					$("#tipTemplateName").hide();
					//提交表单
					$('#form').submit(); 
				}
			}else{
				//新增
				$.post("/logistics/checkTempName.htm",{templateName:tempName,x:Math.random()},function(data){
					if(!data){
						$("#tipTemplateNameExist").hide();
						$("#tipTemplateName").hide();
						//提交表单
						$('#form').submit(); 
					}
				});
			}
		}
		
		
	});
	//**********************************************************保存模板结束**
	
	
	//选中物流方式(层的显示和隐藏)
	$(".logisticsType").each(function(){
		$(this).click(function(){
			var id = "#divlogisticsType" + this.value;
			if(this.checked){
				$(id).show();
			}else{
				$(id).hide();
			}
		});
	});
	
	//复选框选中，运费模板层显示(页面加载)
	$("#templateId").each(function(){
		hideTip();
		
		if($(this).attr("value") == "")
			return;

		if($("#_logisticsTypePost").attr("value") != ""){
			$("#logisticsTypePost").attr("checked", true);
			$("#divlogisticsType1").show();
		}
		
		if($("#_logisticsTypeCourier").attr("value") != ""){
			$("#logisticsTypeCourier").attr("checked", true);
			$("#divlogisticsType2").show();
		}

		if($("#_logisticsTypeEMS").attr("value") != ""){
			$("#logisticsTypeEMS").attr("checked", true);
			$("#divlogisticsType3").show();
		}
	});

	
	//取消(点击未指定地区设置运费地区层的取消)***************************************
	$('.transport-area .close').click(function() {
		$(this).closest('.transport-area').hide().closest('.pay-info').css('z-index', 1);
	});
	
	//***********************************取消(点击未指定地区设置运费地区层的取消)结束*


	
	//复选框效果**************************************************************
	//江浙沪
	$("input[id=jzh]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=1]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=1]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//华东
	$("input[id=hd]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=2]").each(function(){
				$(this).attr('checked',true);
			});

			$("input[id=1]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=2]").each(function(){
				$(this).attr('checked',false);
			});

			$("input[id=1]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//华北
	$("input[id=hb]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=3]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=3]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//华中
	$("input[id=hz]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=4]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=4]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//华南
	$("input[id=hn]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=5]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=5]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//东北
	$("input[id=db]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=6]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=6]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//西北
	$("input[id=xb]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=7]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=7]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 


	//西南
	$("input[id=xn]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=8]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=8]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//港澳台
	$("input[id=gat]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=9]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=9]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 


	//海外
	$("input[id=hw]").click(function(){
  		if($(this).attr('checked')=='checked'){
  			$("input[id=10]").each(function(){
				$(this).attr('checked',true);
			});
  		}else{
			$("input[id=10]").each(function(){
				$(this).attr('checked',false);
			});
		}
  	}); 

	//**********************************************************复选框效果结束**
	
	
	
	//确定取值*****************************************************************
	
	//确定取值(地区==>>平邮)
	$("#postOK").click(function(){
		var postValue=",";
		var postValueHidden=",";
		$("#postTable").find("input[type=checkbox]").each(function(i,obj){
			if($(this).attr('checked')=='checked'){
				postValue+=$(this).val().split("|")[1]+",";
				postValueHidden+=$(this).val()+",";
			}
		});

		if(postValue==","){
			alert("请先选择地区");
			return;
		}
		
		$(this).parent().hide();
		postValue=postValue.substring(1,postValue.length-1);
		postValueHidden=postValueHidden.substring(1,postValueHidden.length-1);
		var post = "<dl class='lister' id='postTemp1'>" +
						"至<input id='txtPostAddress' readonly='readonly' type='text' value='"+postValue+"' class='text' title='"+postValue+"'>" +
						"<input id='txtPostAddressCode' readonly='readonly' type='hidden' value='"+postValueHidden+"' class='text'>"+
						"的运费：<input name='' id='txtPostMoney' type='text' class='text pinju-number' value='0.01' onFocus='f_focus(this)' onBlur='f_blur(this)'>元，" +
						"每多一件商品加收<input name='' id='txtPostManyMoney' type='text' class='text pinju-number' value='0.00' onFocus='f_focus(this)' onBlur='f_blur(this)'>	元" +
						"<a href='##' onclick='deltemp(this)' title='删除指定地区后别忘记点击“保存”'>删除</a>" +
					"</dl>";
		$("#postTemp").append(post);
		f_number();	//为文本框校验(只能输入数字和小数，且必须小于999.99)
	});
	
	
	//确定取值(地区==>>快递)
	$("#courierOK").click(function(){
		var courierValue=",";
		var courierValueHidden=",";
		$("#courierTable").find("input[type=checkbox]").each(function(i,obj){
			if($(this).attr('checked')=='checked'){
				courierValue+=$(this).val().split("|")[1]+",";
				courierValueHidden+=$(this).val()+",";
			}
		});

		if(courierValue==","){
			alert("请先选择地区");
			return;
		}
		
		$(this).parent().hide();
		courierValue=courierValue.substring(1,courierValue.length-1);
		courierValueHidden=courierValueHidden.substring(1,courierValueHidden.length-1);
		var courier = "<dl class='lister' id='courierTemp1'>" +
						"至<input id='txtCourierAddress' readonly='readonly' type='text' value='"+courierValue+"' class='text' title='"+courierValue+"'>" +
						"<input id='txtCourierAddressCode' readonly='readonly' type='hidden' value='"+courierValueHidden+"' class='text'>"+
						"的运费：<input name='' id='txtCourierMoney' type='text' class='text pinju-number' value='0.01' onFocus='f_focus(this)' onBlur='f_blur(this)'>元，" +
						"每多一件商品加收<input name='' id='txtCourierManyMoney' type='text' class='text pinju-number' value='0.00' onFocus='f_focus(this)' onBlur='f_blur(this)'>	元" +
						"<a href='##' onclick='deltemp(this)' title='删除指定地区后别忘记点击”保存“'>删除</a>" +
					"</dl>";
		$("#courierTemp").append(courier);		
		f_number();	//为文本框校验(只能输入数字和小数，且必须小于999.99)
	});
	
	
	
	//确定取值(地区==>>EMS)
	$("#emsOK").click(function(){
		var EMSValue=",";
		var EMSValueHidden=",";
		$("#EMSTable").find("input[type=checkbox]").each(function(i,obj){
			if($(this).attr('checked')=='checked'){
				EMSValue+=$(this).val().split("|")[1]+",";
				EMSValueHidden+=$(this).val()+",";
			}
		});

		if(EMSValue==","){
			alert("请先选择地区");
			return;
		}
		
		$(this).parent().hide();
		EMSValue=EMSValue.substring(1,EMSValue.length-1);
		EMSValueHidden=EMSValueHidden.substring(1,EMSValueHidden.length-1);
		var ems = "<dl class='lister' id='emsTemp1'>" +
						"至<input id='txtEmsAddress' readonly='readonly' type='text' value='"+EMSValue+"' class='text' title='"+EMSValue+"'>" +
						"<input id='txtEmsAddressCode' readonly='readonly' type='hidden' value='"+EMSValueHidden+"' class='text'>"+
						"的运费：<input name='' type='text' class='text pinju-number' id='txtEmsMoney' value='0.01' onFocus='f_focus(this)' onBlur='f_blur(this)'>元，" +
						"每多一件商品加收<input name='' type='text' class='text pinju-number' id='txtEmsManyMoney' value='0.00' onFocus='f_focus(this)' onBlur='f_blur(this)'>	元" +
						"<a href='##' onclick='deltemp(this)' title='删除指定地区后别忘记点击”保存“'>删除</a>" +
					"</dl>";
		$("#emsTemp").append(ems);	
		f_number();	//为文本框校验(只能输入数字和小数，且必须小于999.99)
	});
	//****************************************************************确定取值结束*
	
	//验证模板名称是否存在
	$("#templateName").blur(function(){
		//模板名称
		var tempName = $.trim($("#templateName").val());
		//修改传来的模板名称
		var updTempName = $.trim($("#isTempName").val());
		
		//修改时，检测模板名称是否存在
		if(updTempName!="" && tempName!=""){
			if(tempName != updTempName){
				$.post("/logistics/checkTempName.htm",{templateName:tempName,x:Math.random()},function(data){
					if(data){
						$("#tipTemplateNameExist").show();
						$("#tipTemplateName").hide();
						$("#tipTemplateNameExist").html(data);
					}else{
						$("#tipTemplateNameExist").hide();
						$("#tipTemplateName").hide();
					}
				});
			}else{
				$("#tipTemplateNameExist").hide();
				$("#tipTemplateName").hide();
			}
		//新增时，检测模板名称是否存在
		}else if(tempName!=""){
			$.post("/logistics/checkTempName.htm",{templateName:tempName,x:Math.random()},function(data){
				if(data){
					$("#tipTemplateNameExist").show();
					$("#tipTemplateName").hide();
					$("#tipTemplateNameExist").html(data);
				}else{
					$("#tipTemplateNameExist").hide();
					$("#tipTemplateName").hide();
				}
			});
		}else{
			$("#tipTemplateNameExist").hide();
			$("#tipTemplateName").show();
		}
		
	});
	
});



	 //校验数据(为指定地区设置运费模板用)===>>>>为文本框校验(只能输入数字和小数，且必须小于999.99)
	 function f_number(){
		//数字框校验
		$(".pinju-number").each(function(){
			$(this).numeralDecimal({
				decimalPlace:2, 
		 		maxLength:6
			});
			
			$(this).change(function(){
				if(eval($(this).val()) > 999.99){
					$(this).val(999.99);
				}
			});
		});
	 }

    //删除 为指定地区设定运费
	function deltemp(obj){
		if(confirm("删除指定地区后别忘记点击“保存”")){
			var temp = $(obj).parent();
			temp.remove();
		}
	}


	//获取 为指定地区设置运费**********************************************************
	function getInfor(){
		var tempOk = true;
		if($("#logisticsTypePost").attr("checked")=="checked"){
			//平邮
			if($("#txtPostAddress").attr("value") != ""){
				var post="";
				$("#postTemp dl").each(
					function(i,obj){
						if($(obj).find("#txtPostMoney").val()=="" && $(obj).find("#txtPostManyMoney").val()==""){
							$("#tipPostArea").html("地区运费和增加运费必须为有效值，且不得大于999.99元");
							$("#tipPostArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtPostMoney").val()=="" || eval($(obj).find("#txtPostMoney").val()) <= 0){
							$("#tipPostArea").html("地区运费必须为有效值（大于0.00元），且不得大于999.99元");
							$("#tipPostArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtPostManyMoney").val()==""){
							$("#tipPostArea").html("多一件商品运费必须为有效值，且不得大于999.99元");
							$("#tipPostArea").show();
							tempOk=false;
							return;
						}else if(eval($(obj).find("#txtPostManyMoney").val()) > eval($(obj).find("#txtPostMoney").val())){
							$("#tipPostArea").html("多一件商品的加收运费不得大于商品地区运费的数值");
							$("#tipPostArea").show();
							tempOk=false;
							return;
						}else{
							$("#tipPostArea").hide();
						}
						
						post+=$("#logisticsTypePost").val()+":";
						post+=$(obj).find("#txtPostAddressCode").val()+":";
						post+=$(obj).find("#txtPostMoney").val()+":";
						post+=$(obj).find("#txtPostManyMoney").val();
						post+=";";
					}
				);
				post=post.substring(0,post.length-1);
				//填充到隐藏文本框
				$("#postInfor").val(post);
				$("#allInfor").val(post);
			}else{
				//清空
				$("#postInfor").val("");
				$("#allInfor").val("");
			}
		}else{
			//清空
			$("#postInfor").val("");
		}
		
		
		//快递
		if($("#logisticsTypeCourier").attr("checked")=="checked"){
		
			if($("#txtCourierAddress").attr("value") != ""){
				var courier="";
				$("#courierTemp dl").each(
					function(i,obj){
						
						
						if($(obj).find("#txtCourierMoney").val()=="" && $(obj).find("#txtCourierManyMoney").val()==""){
							$("#tipCourierArea").html("地区运费和增加运费必须为有效值，且不得大于999.99元");
							$("#tipCourierArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtCourierMoney").val()=="" || eval($(obj).find("#txtCourierMoney").val()) <= 0){
							$("#tipCourierArea").html("地区运费必须为有效值（大于0.00元），且不得大于999.99元");
							$("#tipCourierArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtCourierManyMoney").val()==""){
							$("#tipCourierArea").html("多一件商品运费必须为有效值，且不得大于999.99元");
							$("#tipCourierArea").show();
							tempOk=false;
							return;
						}else if(eval($(obj).find("#txtCourierManyMoney").val()) > eval($(obj).find("#txtCourierMoney").val())){
							$("#tipCourierArea").html("多一件商品的加收运费不得大于商品地区运费的数值");
							$("#tipCourierArea").show();
							tempOk=false;
							return;
						}else{
							$("#tipCourierArea").hide();
						}
						
						courier+=$("#logisticsTypeCourier").val()+":";
						courier+=$(obj).find("#txtCourierAddressCode").val()+":";
						courier+=$(obj).find("#txtCourierMoney").val()+":";
						courier+=$(obj).find("#txtCourierManyMoney").val();
						courier+=";";
					}
				);
				courier=courier.substring(0,courier.length-1);
				//填充到隐藏文本框		
				$("#courierInfor").val(courier);
				
				if($("#postInfor").val()!=""){
					$("#allInfor").val(post+";"+courier);
				}else{
					$("#allInfor").val(courier);
				}
				
			}else{
				//清空
				$("#courierInfor").val("");
				$("#allInfor").val("");
			}
		}else{
			//清空
			$("#courierInfor").val("");
		}
	
		//EMS
		if($("#logisticsTypeEMS").attr("checked")=="checked"){
			if($("#txtEmsAddress").attr("value") != ""){
				var ems="";
				$("#emsTemp dl").each(
					function(i,obj){
						if($(obj).find("#txtEmsMoney").val()=="" && $(obj).find("#txtEmsManyMoney").val()==""){
							$("#tipEmsArea").html("地区运费和增加运费必须为有效值，且不得大于999.99元");
							$("#tipEmsArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtEmsMoney").val()=="" || eval($(obj).find("#txtEmsMoney").val()) <= 0){
							$("#tipEmsArea").html("地区运费必须为有效值（大于0.00元），且不得大于999.99元");
							$("#tipEmsArea").show();
							tempOk=false;
							return;
						}else if($(obj).find("#txtEmsManyMoney").val()==""){
							$("#tipEmsArea").html("多一件商品运费必须为有效值，且不得大于999.99元");
							$("#tipEmsArea").show();
							tempOk=false;
							return;
						}else if(eval($(obj).find("#txtEmsManyMoney").val()) > eval($(obj).find("#txtEmsMoney").val())){
							$("#tipEmsArea").html("多一件商品的加收运费不得大于商品地区运费的数值");
							$("#tipEmsArea").show();
							tempOk=false;
							return;
						}else{
							$("#tipEmsArea").hide();
						}
						
						ems+=$("#logisticsTypeEMS").val()+":";
						ems+=$(obj).find("#txtEmsAddressCode").val()+":";
						ems+=$(obj).find("#txtEmsMoney").val()+":";
						ems+=$(obj).find("#txtEmsManyMoney").val();
						ems+=";";
					}
				);
				ems=ems.substring(0,ems.length-1);
				//填充到隐藏文本框
				$("#emsInfor").val(ems);
				
				if($("#courierInfor").val()!=""&&$("#postInfor").val()!=""){
					$("#allInfor").val(post+";"+courier+";"+ems);
				}else if($("#courierInfor").val()==""&&$("#postInfor").val()!=""){
					$("#allInfor").val(post+";"+ems);
				}else if($("#courierInfor").val()!=""&&$("#postInfor").val()==""){
					$("#allInfor").val(courier+";"+ems);
				}else if($("#courierInfor").val()==""&&$("#postInfor").val()==""){
					$("#allInfor").val(ems);
				}
					
			}else {
				//清空
				$("#emsInfor").val("");
				$("#allInfor").val("");
			}
		}else{
			$("#emsInfor").val("");
		}
		return tempOk;
	}

    var _val = 0.10;
    function f_focus(obj){
		_val = $(obj).val();
		$(obj).val("");
	}

	function f_blur(obj){
		var _inputVal = $(obj).val();
		if(_inputVal == ""){
			$(obj).val(_val);
		}
	}

	//********************************************************获取 为指定地区设置运费结束**
	
	//隐藏提示
	function hideTip(){
		$("#tipTemplateName").hide();
		$("#tipTemplateNameExist").hide();
		$("#tipLogisticsType").hide();
		$("#tipTemplateMemo").hide();

		$("#tipPostFee").hide();
		$("#tipPostFeeIncrease").hide();
		$("#tipCourierFee").hide();
		$("#tipCourierFeeIncrease").hide();
		$("#tipEmsFee").hide();
		$("#tipEmsFeeIncrease").hide();
		
		$("#tipPostArea").hide();
		$("#tipCourierArea").hide();
		$("#tipEmsArea").hide();
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
			} else {
				if (ts.substring(0, 3) == "%D7") {
					len += 2;
				} else {
					len += 1;
				}
			}
		}
		
		return len;
	}
