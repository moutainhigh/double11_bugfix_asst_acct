$(document).ready(function(){
	$("#createTemplate").click(function(){
		
		$('#pageForm').attr("action", "createTemplate.htm");
		$('#pageForm').submit();
	});
	
	//根据单运货号  获取物流跟踪信息
	$("#logisticsSearch").click(function(){
		$("#J_SRLo").hide();
	 	var billId=$("#outLogisticsId").val();
	 	var selText=$("#logisticsType").find("option:selected").text(); 
	 	var selValue=$("#logisticsType").val();
	 	if(selValue==""){
	 		$("#tipTypeMess").html("请选择物流公司名称");
	 		$("#J_SRLo").hide();
	 		return;
	 	}else 
	 	$("#tipTypeMess").html("");
	 	
	 	if(billId==""){
	 		$("#tipBillMess").html("请输入运单号");
	 		$("#J_SRLo").hide();
	 		return;
	 	}else
	 	$("#tipBillMess").html("");
	 	$("#wuliuTip").html("");
	 	$("#trackInfo").html("");
	 	$("#J_Loading").html('<img src="http://static.pinju.com/images/ajax/loadding.gif" style="vertical-align:middle;margin-right:10px" />正在读取物流信息...');
		$("#J_Loading").insertBefore("#J_SRLo");
		$("#J_Loading").show();
	 	$.ajax({
	 	   type: "POST",
	 	   url: "/logistics/getLogisticsInfo.htm",
	 	   data: "outLogisticsId="+billId+"&logisticsType="+selValue,
	 	   dataType: "json",
	 	   success: function(data){
	 	   $("#J_Loading").hide();
	 	   $("#J_SRLo").show();
	 		var logidticsInfo =data.step;
	 	     if(logidticsInfo && logidticsInfo.length !=0 ){
	 	     	for(i = 0; i < logidticsInfo.length; i++){
	 	     		if(i==logidticsInfo.length-1){
	 	     			$("#trackInfo").append('<tr class="odd"><td class="cell-time">'+logidticsInfo[i].datetime+'</td><td class="hong">'+logidticsInfo[i].remark+'</td></tr>');
	 	     		}else{
	 	     			if(i%2==0){
	 	     				$("#trackInfo").append('<tr class="odd"><td class="cell-time">'+logidticsInfo[i].datetime+'</td><td>'+logidticsInfo[i].remark+'</td></tr>');
	 	     			}else{
	 	     				$("#trackInfo").append('<tr><td class="cell-time">'+logidticsInfo[i].datetime+'</td><td>'+logidticsInfo[i].remark+'</td></tr>');
	 	     			}
	 	     		}
	 	     	}
	 	    }else{
				if(data.resultInfo ==""){
					$("#trackInfo").append('<tr><td colspan="2" style="text-align: center;"><label class="hong">没有找到相关的物流信息！</label></td></tr>');
				}else{
					$("#trackInfo").append('<tr><td colspan="2" style="text-align: center;"><label class="hong">'+data.resultInfo+'</label></td></tr>');
				}
	 	    }
	 	     
	 	     $("#wuliuTip").append('物流跟踪：<span class="bor-yellow">！以下信息由物流公司提供，如有疑问请咨询 <a href="'+data.logisticsUrl+'" class="lan" target="_blank">【'+selText+'】</a></span>');
	 	  }
	 	});
	 });
	
});

/**
 * 删除模板
 * tempId:当前要删除的模板ID
 * */
function delTemplate(tempId){
	$.post("/logistics/checkLogisticsTemplate.htm",{templateId:tempId,x:Math.random()},function(data){
		if(!data){
			if(confirm("您确认要删除吗？")){
				location.href="/logistics/deleteTemplate.htm?templateId="+tempId;
			}
		}else{
		    alert("尚有商品正在使用该模板，模板不允许删除!");
		}
	});
}
