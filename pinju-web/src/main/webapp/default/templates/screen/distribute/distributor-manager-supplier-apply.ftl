<title>申请中的供应商</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${nickName!''}，欢迎进入云营销</div>
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 ">我是买家 > 分销 ></span> <span class="fontred">供应商管理</span></div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">申请中的供应商<span class="fontred">（${supplierParam.applyCount!0}）  </span></div>
        <div class="floatleft margin_left20">合作中的供应商 <span class="fontred">（${supplierParam.alignmentCount!0}） </span></div>
        <div class="floatleft margin_left20">历史供应商 <span class="fontred">（${supplierParam.historyCount!0}）</span></div>
      </div>
      
      <div class="box_806 lable_bg_style padding_lr_10px ">
        <div id="apply" class="lable_present" style="cursor: default">申请中的供应商</div>
            <div id="alignment" class="lable_after" style="cursor: pointer"> 合作中的供应商 </div>
            <div id="history" class="lable_after" style="cursor: pointer"> 历史供应商 </div>
		</div><!--标签 end --> 
         
        <div class="box_826">
        <form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0">
          <tr  class="bottom_1px_gray bg_gray01 fontbold">
            <td width="326"><input id="checkAll" name="checkbox" type="checkbox" value="">&nbsp; 供应商名称 </td>
            <td width="150">申请时间</td>
            <td width="150">申请状态</td>
            <td width="200">操作</td>
          </tr>
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
          <#list distributeSupplierDOList as distributeSupplierDO>
          <tr class="bottom_1px_gray_dashed fontgray_03">
            <td width="220"><input id="channelId_${distributeSupplierDO.channelId}" name="channelIdList" type="checkbox" value="${distributeSupplierDO.channelId}">
            <a href="/shopDecoration/viewShop.htm?shopId=${distributeSupplierDO.shopId!''}" target="_blank">
				<#if distributeSupplierDO.shopInfoAllDO??>
					${distributeSupplierDO.shopInfoAllDO.name!''}
				</#if>
			</a></td>
            <td>${distributeSupplierDO.gmtCreate?date}</td>
            <td>申请中</td>
            <td><input channelId="${distributeSupplierDO.channelId}" name="cancel" type="button" class="gray_button_004" value="撤销申请"></td>
          </tr>
          </#list> 
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何申请中供应商信息！</label></td></tr>
</#if>                 
        </table>
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
	<div class="cbottom imgbtn">
        <#include "/default/templates/control/bottomPage.ftl">
    </div>
</#if>
        </form>  
		<div class="box_806 padding_10px fontgray_01" align="center">
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
		<input name="batch" type="button" class="gray_button_005 margin_left20" value="批量撤销">
<#else>
		<!-- <input type="button" class="gray_button_005 margin_left20" value="批量撤销"> -->
</#if>
        </div>

        </div>      
        
  	</div><!--分销大框架结束 -->
</div><!--右侧结束 -->
<script type="text/javascript">
	$(function(){
		$('#supplierManager').addClass('count');
	
		//$('#apply').click(
			//function(){window.location.href = "/distributor/applyManager.htm";}
		//);
		$('#alignment').click(
			function(){window.location.href = "/distributor/alignmentManager.htm";}
		);
		$('#history').click(
			function(){window.location.href = "/distributor/historyManager.htm";}
		);
		$("input:checkbox[id!='checkAll']").each(
			function(){
				$(this).click(
					function(){
						if($(this).attr("checked")){
							var checkAll = true;
							$("input:checkbox[id!='checkAll']").each(function(){if(!$(this).attr("checked")){checkAll = false;}});
							$("#checkAll").attr("checked",checkAll);
						}else{
							$("#checkAll").attr("checked",false);
						}
					}
				);
			}
		);
		$("#checkAll").click(function(){
			if($(this).attr("checked")){
		     	$("input:checkbox[id!='checkAll']").each(function(){$(this).attr("checked",true);});
		     }else{
		     	$("input:checkbox[id!='checkAll']").each(function(){$(this).attr("checked",false);});
		     }
		});
		$('input[name="batch"]').click(function(){
			var submitFlag = false;
			$("input:checkbox[id!='checkAll']").each(function(){
				if($(this).attr("checked")){
					submitFlag = true;
				}
			});
			if(submitFlag){
				if(confirm("您确定要批量撤消选中申请吗?")){
					submitForm();
				}
			}else{
				alert("没有任何选项被选中");
			}
		});
		$('input[name="cancel"]').click(
			function(){
				$("input:checkbox[id!='checkAll']").each(function(){$(this).attr("checked",false);});
				$('#channelId_'+$(this).attr('channelId')).attr("checked",true);
				if(confirm("您确定要撤消选中申请吗?")){
				submitForm();}
			}
		);
	});
	var submitForm = function submitForm(){
		$('#pageForm').attr("action","/distributor/cancelApply.htm").submit();
	}
</script>