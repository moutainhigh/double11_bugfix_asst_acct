<title>分销商品管理</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${nickName!''}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px border_1px_gray bg_white margin_bottom"><span class="fontgray_03">我是买家 > 云营销 ></span> <span class="fontred">分销商品管理</span></div>
      
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 fontbold">基本信息：</div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">合作中的供应商<span class="fontred">（${itemQuery.applyStatusCount!0}家） </span></div>
        <div class="floatleft margin_left20">已分销商品 <span class="fontred">（${itemQuery.soldItemCount!0}件） </span></div>
      </div>
        
      <div class="box_806 padding_10px ltr_1px_gray bg_white fontgray_0 fontbold">商品管理：</div> 
             
<div class="box_806 lable_bg_style padding_lr_10px left_1px_gray right_1px_gray">
		   <div id="unsold" class="lable_after" style="cursor: pointer"> 未分销 </div>
            <div id="sold" class="lable_present" style="cursor: default">已分销</div>
		</div><!--标签 end --> 
        
       <!-- <div class="box_806 lable_bg_style padding_10px ">
	        <#if (distrbuteSupplierItemDOList)?? && ( distrbuteSupplierItemDOList?size >0)>
	        	<input id="stopSold" name="button" type="button" class="gray_button_005" value="批量停止分销">
	        <#else>
	        	<input name="button" type="button" class="gray_button_005" value="批量停止分销">
	        </#if>
        </div> -->
        
        <div class="box_826">
        <form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0">
          <tr class="bottom_1px_gray bg_gray01 fontbold">
            <td width="220">商品名 </td>
            <td width="100">零售价</td>
            <td width="126">分成比例</td>
            <td width="80">库存</td>
            <!--<td width="100">分销状态</td>
            <td width="100">备注</td>-->
            <td width="100">&nbsp;</td>
          </tr>
<#if (distrbuteSupplierItemDOList)?? && (distrbuteSupplierItemDOList?size >0)>
		<#list distrbuteSupplierItemDOList as distrbuteSupplierItemDO>
          <tr class="bottom_1px_gray_dashed fontgray_03">
            <td width="220">
		      	<a href="/detail/${(distrbuteSupplierItemDO.itemDO.id)!0}.htm" target="_blank">
			      <#if distrbuteSupplierItemDO.itemDO??>
		          	${(distrbuteSupplierItemDO.itemDO.title!'')?html}
		          </#if>
	          	</a>
			</td>
            <td width="100">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${(distrbuteSupplierItemDO.itemDO.priceByYuan)!0.00}
	            </#if>
			</td>
            <td width="126">${(distrbuteSupplierItemDO.reward)!0}%</td>
            <td width="80">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${(distrbuteSupplierItemDO.itemDO.curStock)!''}
	            </#if>
			</td>
            <td width="100">
            	<input supplierId="${distrbuteSupplierItemDO.supplierId}" itemId="${distrbuteSupplierItemDO.itemId}" name="stopSold" name="button" type="button" class="orange_button_005" value="下架">  
            </td>
          </tr>
        </#list>
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何已分销商品信息！</label></td></tr>
</#if>
        </table>
<#if (distrbuteSupplierItemDOList)?? && ( distrbuteSupplierItemDOList?size >0)>
	<div class="cbottom imgbtn">
	  <#include "/default/templates/control/bottomPage.ftl">
	</div>
</#if>
	  </form>
		<!--<div class="box_806 padding_10px fontgray_01" align="center">
        	<input name="button" type="button" class="orange_button_002" value="确定">
            <input name="button" type="button" class="gray_button_002 margin_left20" value="取消">	
        </div>-->

        </div>      
        
  	</div><!--分销大框架结束 -->
  	<form id="stopSoldForm" name="stopSoldForm" method="post" action="/distributor/unsold.htm">
  		<input id="channelId" name="channelId" type="hidden" value="">
  		<input id="supplierId" name="supplierId" type="hidden" value="">
  		<input id="itemId" name="itemId" type="hidden" value=""/>
  		<input id="imageUrl" name="imageUrl" type="hidden" value=""/>
  		<input id="recommendReason" name="recommendReason" type="hidden" value=""/>
  	</form>
</div><!--右侧结束 -->
<script type="text/javascript">
	var channelArray = new Array();
	$(function(){
		$('#itemManagerDistributor').addClass('count');
	
		/** tab 跳转 */
		$('#unsold').click(
			function(){window.location.href = "/distributor/unsoldManager.htm";}
		);
		/** 全选 checkbox start */
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
		/** 全选 checkbox end */
		$('input[name="stopSold"]').click(function(){
				$('#supplierId').val($(this).attr('supplierId'));
				$('#itemId').val($(this).attr('itemId'));
				$.ajax({
					data:$('#stopSoldForm').serializeArray(),
					url:"/distributor/unsold.htm",
					type:"POST",
					success:function(callback){
						if(callback.status){
							alert(callback.message);
						}else{
							alert(callback.message);
						}
						window.location.href = "/distributor/soldManager.htm";
					},
					error:function(){
					}
				});
		});
	});
</script>