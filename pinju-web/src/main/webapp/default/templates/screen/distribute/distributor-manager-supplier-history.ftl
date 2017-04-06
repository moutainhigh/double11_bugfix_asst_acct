<title>历史中的供应商</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${nickName!''}，欢迎进入云营销</div>
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 ">我是买家 > 分销 ></span> <span class="fontred">供应商管理</span></div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">申请中的供应商<span class="fontred">（${supplierParam.applyCount!0}） </span></div>
        <div class="floatleft margin_left20"> 合作中的供应商 <span class="fontred">（${supplierParam.alignmentCount!0}） </span></div>
        <div class="floatleft margin_left20"> 历史供应商 <span class="fontred">（${supplierParam.historyCount!0}） </span></div>
      </div>
      
      <div class="box_806 lable_bg_style padding_lr_10px ">
        <div id="apply" class="lable_after" style="cursor: pointer"> 申请中的供应商 </div>
            <div id="alignment" class="lable_after" style="cursor: pointer"> 合作中的供应商 </div>
            <div id="history" class="lable_present" style="cursor: default"> 历史供应商 </div>
		</div><!--标签 end --> 
         
        
        <div class="box_826">
        <form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0">
          <tr  class="bottom_1px_gray bg_gray01 fontbold">
            <td width="226">
            &nbsp;分销商名称</td>
            <td width="250" align="center">分销产品数</td>
            <td width="150">起始时间</td>
            <td width="150">终止时间</td>
            <td width="100">合作状态</td>
          </tr>
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
		<#list distributeSupplierDOList as distributeSupplierDO>
          <tr class="bottom_1px_gray_dashed fontgray_03">
            <td>
            <a href="/shopDecoration/viewShop.htm?shopId=${distributeSupplierDO.shopId!''}" target="_blank">
				<#if distributeSupplierDO.shopInfoAllDO??>
					${distributeSupplierDO.shopInfoAllDO.name!''}
				</#if>
			</a></td>
            <td align="center"><p>${distributeSupplierDO.count!0}</p>
            <p><a href="/distributor/supplierItemList.htm?supplierItemParam.supplierId=${distributeSupplierDO.id}">商品目录</a></p></td>
            <td>
            <#if distributeSupplierDO.agreeDate??>
            	${distributeSupplierDO.agreeDate?date}
            </#if>
            </td>
            <td>
	        	<#if distributeSupplierDO.endDate??>
	        		${distributeSupplierDO.endDate?date}
	        	</#if>
            </td>
            <#if distributeSupplierDO.status??>
	            <#if distributeSupplierDO.status == 1>
	            	<td>合同到期</td>
	            <#elseif distributeSupplierDO.status == 2>
	            	<td>审核拒绝</td>
	            <#elseif distributeSupplierDO.status == 3>
	            	<td>主动撤销</td>
	            </#if>
            </#if>
          </tr>
        </#list>
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何历史供应商信息！</label></td></tr>
</#if>              
        </table>
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
	<div class="cbottom imgbtn">
        <#include "/default/templates/control/bottomPage.ftl">
    </div>
</#if>
        </form>  

        </div>      
        
  	</div><!--分销大框架结束 -->
</div><!--右侧结束 -->
<script type="text/javascript">
	$(function(){
		
		$('#supplierManager').addClass('count');
		
		$('#apply').click(
			function(){window.location.href = "/distributor/applyManager.htm";}
		);
		$('#alignment').click(
			function(){window.location.href = "/distributor/alignmentManager.htm";}
		);
		//$('#history').click(
			//function(){window.location.href = "/distributor/historyManager.htm";}
		//);
	});
</script>