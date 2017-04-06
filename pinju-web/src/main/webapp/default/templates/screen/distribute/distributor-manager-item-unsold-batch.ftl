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
		   <div id="unsold" class="lable_present" style="cursor: default"> 未分销 </div>
            <div id="sold" class="lable_after" style="cursor: pointer">已分销</div>
		</div><!--标签 end --> 
        
        <div class="box_806 lable_bg_style padding_10px ">
		    <#if (distrbuteSupplierItemDOList)?? && ( distrbuteSupplierItemDOList?size >0)>
	        	<input id="startSold" name="button" type="button" class="orange_button_005" value="批量分销">
	        <#else>
	        	<input name="button" type="button" class="orange_button_005" value="批量分销">
	        </#if>
        </div> 
        
        <div class="box_826">
        <table width="826" border="0" class="bottom_1px_gray bg_gray01 fontbold">
          <tr>
            <td width="220"><input id="checkAll" name="checkbox" type="checkbox" value="">&nbsp; 商品名 </td>
            <td width="100">零售价</td>
            <td width="126">分成比例</td>
            <td width="80">库存</td>
            <!--<td width="100">分销状态</td>
            <td width="100">备注</td>-->
          </tr>
        </table>
      <form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0">  
<#if (distrbuteSupplierItemDOList)?? && (distrbuteSupplierItemDOList?size >0)>
		<#list distrbuteSupplierItemDOList as distrbuteSupplierItemDO>
          <tr class="bottom_1px_gray_dashed fontgray_03">
            <td width="220"><input supplierId="${distrbuteSupplierItemDO.supplierId}" itemId="${distrbuteSupplierItemDO.itemId}" name="checkbox" type="checkbox" value="">&nbsp;
		      	<a href="/detail/${(distrbuteSupplierItemDO.itemDO.id)!0}.htm" target="_blank">
			      <#if distrbuteSupplierItemDO.itemDO??>
		          	${(distrbuteSupplierItemDO.itemDO.title!'')?html}
		          </#if>
	          	</a>
			</td>
            <td width="100">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${distrbuteSupplierItemDO.itemDO.priceByYuan!0.00}
	            </#if>
			</td>
            <td width="126">${distrbuteSupplierItemDO.reward!0}%</td>
            <td width="80">
				<#if distrbuteSupplierItemDO.itemDO??>
	            	${distrbuteSupplierItemDO.itemDO.curStock!''}
	            </#if>
			</td>
            <!--<td width="100"><form name="form1" method="post" action="">
              <label for="select"></label>
              <select name="select" id="select" class="box_width_90px">
              		<option>分销</option>
                    <option>停止分销</option>
              </select>
            </form></td>
            <td width="100">
			
			</td>-->
          </tr>
       </#list>
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何未分销商品信息！</label></td></tr>
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
</div><!--右侧结束 -->
<script type="text/javascript">
	var channelArray = new Array();
	$(function(){
		//$('#unsold').click(
			//function(){window.location.href = "/distributor/unsoldManager.htm";}
		//);
		$('#sold').click(
			function(){window.location.href = "/distributor/soldManager.htm";}
		);
		$("#checkAll").click(function(){
			if($(this).attr("checked")){
		     	$("input:checkbox[id!='checkAll']").each(function(){$(this).attr("checked",true);});
		     }else{
		     	$("input:checkbox[id!='checkAll']").each(function(){$(this).attr("checked",false);});
		     }
		});
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
		$('#startSold').click(function(){
			var submitFlag = false;
			$("input:checkbox[id!='checkAll']").each(
				function(){
					if($(this).attr("checked")){
						submitFlag = true;
						itemIdAdd(this);
					}
			});
			if(submitFlag){
				var goodsListString="";
				var supplierIdString="";
				var version=""
				for(var i=0 ; i<channelArray.length ; i++){
					if(channelArray[i].modify){
						goodsListString = (goodsListString+"$"+channelArray[i].value);
						supplierIdString = (supplierIdString+"$"+channelArray[i].key);
						version = (version+"$"+channelArray[i].version);
					}
				}
				$.ajax({
					data:{"goodsListString":goodsListString,"supplierIdString":supplierIdString,"versionString":version},
					url:"/distributor/sold.htm",
					type:"POST",
					success:function(callback){
						if(callback.status){
							alert(callback.message);
						}else{
							alert(callback.message);
						}
						window.location.href = "/distributor/unsoldManager.htm";
					},
					error:function(){
					}
				});
			}else{alert("没有任何选项被选中");}
		});
	});
	function itemIdAdd(element){
		var currentIndex = getIndex($(element).attr('supplierId'));
		var itemIds = channelArray[currentIndex].value;
		var itemId = $(element).attr('itemId');
		if(currentIndex != -1){
			channelArray[currentIndex].value = (itemIds != ","?(itemIds+","+itemId):itemIds+itemId);
			channelArray[currentIndex].modify = true;
			
		}
	}
	function getIndex(_key){
		try{
			for(var i=0 ; i<channelArray.length ; i++){
				if(channelArray[i].key == _key){
					return i;
				}
			}
		}catch(e) {
			return null;   
		}
		return -1;
	}
</script>